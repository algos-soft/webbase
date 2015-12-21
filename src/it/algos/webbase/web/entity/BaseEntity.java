package it.algos.webbase.web.entity;


import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Classi di tipo JavaBean
 * <p>
 * 1) le sottoclassi concrete devono avere un costruttore senza argomenti
 * 2) le proprietà devono essere private e accessibili solo con get, set e is (usato per i boolenai al posto di get)
 * 3) le classi deve implementare l'interfaccia Serializable (tramite questa superclasse astratta)
 * 4) le classi non devono contenere nessun metodo per la gestione degli eventi
 */
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable {

	private final static Logger logger = Logger.getLogger(BaseEntity.class.getName());
	private static ArrayList<PostPersistListener> postPersistListeners = new ArrayList<PostPersistListener>();
	private static ArrayList<PostUpdateListener> postUpdateListeners = new ArrayList<PostUpdateListener>();

	protected void postPersist(Class<?> entityClass, long id) {
		for (PostPersistListener l : postPersistListeners) {
			l.postPersist(entityClass, id);
		}// end of for cycle
	}// end of method

	protected void postUpdate(Class<?> entityClass, long id) {
		for (PostUpdateListener l : postUpdateListeners) {
			l.postUpdate(entityClass, id);
		}// end of for cycle
	}// end of method

	/**
	 * Adds a listener invoked when the entity is persisted.
	 * WARNING: ALWAYS REMOVE THE LISTENER WHEN FINISHED
	 * this collection is static and holds the references forever!
	 */
	public static PostPersistListener addPostPersistListener(PostPersistListener l) {
		postPersistListeners.add(l);
		return l;
	}// end of method

	public static void removePostPersistListener(PostPersistListener l) {
		postPersistListeners.remove(l);
	}// end of method

	/**
	 * Adds a listener invoked when the entity is updated.
	 * WARNING: ALWAYS REMOVE THE LISTENER WHEN FINISHED
	 * this collection is static and holds the references forever!
	 */
	public static PostUpdateListener addPostUpdateListener(PostUpdateListener l) {
		postUpdateListeners.add(l);
		return l;
	}// end of method

	public static void removePostUpdateListener(PostUpdateListener l) {
		postUpdateListeners.remove(l);
	}// end of method


	protected void preRemove(Class<?> entityClass, long id) {
	}// end of method

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}// end of method

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}// end of method

	
	/**
	 * Persists this entity to the database.
	 * <p>
	 */
	@SuppressWarnings("rawtypes")
	public void save() {
		
		EntityManager manager = EM.createEntityManager();

		try {
			
			manager.getTransaction().begin();
			if ((getId() != null) && (getId()!=0)) {
				manager.merge(this);
			} else {
				manager.persist(this);
			}

			manager.getTransaction().commit();

		} catch (ConstraintViolationException e) {
			// rollback transaction and log
			manager.getTransaction().rollback();
			String violations = "";
			for (ConstraintViolation v : e.getConstraintViolations()) {
				if (!violations.equals("")) {
					violations += "\n";
				}
				violations += "- " + v.toString();
			}
			logger.log(Level.WARNING, "The record cannot be saved due to the following constraint violations:\n"
					+ violations, e);

		} catch (Exception e) {
			manager.getTransaction().rollback();
			logger.log(Level.WARNING, "The record cannot be saved ", e);
			e.printStackTrace();
		}
		
		manager.close();
		
	}// end of method

	
	
	/**
	 * Removes this entity from the database.
	 * <p>
	 */
	public void delete() {
		
		EntityManager manager = EM.createEntityManager();
		
		try {
			
			manager.getTransaction().begin();
		    
			BaseEntity entityToBeRemoved = manager.getReference(this.getClass(), this.getId());
		    manager.remove(entityToBeRemoved);
		
		    manager.getTransaction().commit();

		} catch (Exception e) {
			manager.getTransaction().rollback();
			logger.log(Level.WARNING, "The record cannot be deleted ", e);
		}
		
		manager.close();
		
	}// end of method

	
	
	public static Object createBean(Class<?> clazz) {
		Object bean = null;
		try {
			bean = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return bean;
	}// end of method
	
	
//	/**
//	 * Refreshes the contents from the database
//	 */
//	public void refresh(){
//		EntityManager manager = EM.createEntityManager();
//		manager.merge(this);
//		//BaseEntity entity = manager.find(this.getClass(), this.getId());
//		manager.refresh(this);
//		manager.close();
//	}

	public interface PostPersistListener {
		public void postPersist(Class<?> entityClass, long id);
	}// end of interface

	public interface PostUpdateListener {
		public void postUpdate(Class<?> entityClass, long id);
	}// end of interface


	/**
	 * @returns the default sort properties of a given
	 * class based on the DefaultSort annotation.
	 * The sort properties are returned as a SortProperties object
	 * which has methods to retrieve the arrays of properties and directions
	 */
	public static SortProperties getSortProperties(Class clazz){
		SortProperties sProp=new SortProperties();

		Annotation a = clazz.getAnnotation(DefaultSort.class);

		if(a!=null){

			DefaultSort aSort=(DefaultSort)a;

			String[] names = aSort.value();

			for(int i=0;i<names.length;i++){
				String name=names[i];
				String[] parts = name.split(",");
				String fname="";
				String sDirection="";
				boolean ascending=true;
				if(parts.length>0){
					fname=parts[0].trim();
				}
				if(parts.length>1){
					sDirection=parts[1].trim();
					if(sDirection.equalsIgnoreCase("true")) {
						ascending=true;
					}else{
						ascending=false;
					}
				}

				//@todo check if property exists in the class before adding!
				sProp.addProperty(fname, ascending);

			}


//			// sort the container on the specified properties
//			if(cont instanceof JPAContainer){
//				JPAContainer jpaCont=(JPAContainer)cont;
//
//				ArrayList<String> lNames = new ArrayList();
//				ArrayList<Boolean> lAscending = new ArrayList();
//
//				// remove invalid properties
//				for(int i=0;i<aFnames.length;i++){
//					String pName = aFnames[i];
//					try{
//						jpaCont.getPropertyKind(pName);//if no exception then the propery exists
//						lNames.add(aFnames[i]);
//						lAscending.add(aAscending[i]);
//					}catch(IllegalArgumentException e){
//						System.err.println("property "+pName+" non trovata nel container");
//					}
//				}
//
//
//			}

		}

		return  sProp;
	}


}// end of class
