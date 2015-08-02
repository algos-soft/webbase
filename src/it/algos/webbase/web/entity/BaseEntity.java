package it.algos.webbase.web.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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

	public static void addPostPersistListener(PostPersistListener l) {
		postPersistListeners.add(l);
	}// end of method

	public static void addPostUpdateListener(PostUpdateListener l) {
		postUpdateListeners.add(l);
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

			if (getId() != null) {
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
	
	
}// end of class
