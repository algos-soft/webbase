package it.algos.webbase.web.entity;


import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
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
    private static ArrayList<PrePersistListener> prePersistListeners = new ArrayList();
    private static ArrayList<PostPersistListener> postPersistListeners = new ArrayList();
    private static ArrayList<PostUpdateListener> postUpdateListeners = new ArrayList();

    protected void prePersist(Class<?> entityClass) {
        for (PrePersistListener l : prePersistListeners) {
            l.prePersist(entityClass);
        }// end of for cycle
    }// end of method

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
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }// end of method


//	/**
//	 * Persists this entity to the database.
//	 * <p>
//	 */
//	@SuppressWarnings("rawtypes")
//	public void save() {
//
//		EntityManager manager = EM.createEntityManager();
//
//		try {
//
//			manager.getTransaction().begin();
//			if ((getId() != null) && (getId()!=0)) {
//				manager.merge(this);
//			} else {
//				manager.persist(this);
//			}
//
//			manager.getTransaction().commit();
//
//		} catch (ConstraintViolationException e) {
//			// rollback transaction and log
//			manager.getTransaction().rollback();
//			String violations = "";
//			for (ConstraintViolation v : e.getConstraintViolations()) {
//				if (!violations.equals("")) {
//					violations += "\n";
//				}
//				violations += "- " + v.toString();
//			}
//			logger.log(Level.WARNING, "The record cannot be saved due to the following constraint violations:\n"
//					+ violations, e);
//
//		} catch (Exception e) {
//			manager.getTransaction().rollback();
//			logger.log(Level.WARNING, "The record cannot be saved ", e);
//			e.printStackTrace();
//		}
//
//		manager.close();
//
//	}// end of method


    /**
     * Saves this entity to the database using a local EntityManager
     * <p>
     *
     * @return the merged Entity (new entity, unmanaged, has the id)
     */
    public BaseEntity save() {
        return save(null);
    }


    /**
     * Saves this entity to the database.
     * <p>
     * If the provided EntityManager has an active transaction, the operation is performed
     * inside the transaction.<br>
     * Otherwise, a new transaction is used to save this single entity.
     *
     * @param manager the entity manager to use (if null, a new one is created on the fly)
     * @return the merged Entity (new entity, unmanaged, has the id)
     */
    @SuppressWarnings("rawtypes")
    public BaseEntity save(EntityManager manager) {
        BaseEntity mergedEntity = null;
        boolean localEm = false;

        if (manager == null) {
            manager = EM.createEntityManager();
            localEm = true;
        }

        boolean createTransaction = !manager.isJoinedToTransaction();

        try {

            if (createTransaction) {
                manager.getTransaction().begin();
            }

            mergedEntity = manager.merge(this);

            if (createTransaction) {
                manager.getTransaction().commit();
            }

            // assign the new id to this entity
            if (this.id == null) {
                this.setId(mergedEntity.getId());
            }

        } catch (ConstraintViolationException e) {

            // rollback transaction
            if (createTransaction) {
                manager.getTransaction().rollback();
            }

            // log the error
            String violations = "";
            for (ConstraintViolation v : e.getConstraintViolations()) {
                if (!violations.equals("")) {
                    violations += "\n";
                }
                violations += "- " + v.toString();
            }
            logger.log(Level.SEVERE, "The record cannot be saved due to the following constraint violations:\n"
                    + violations, e);

        } catch (Exception e) {
            // log the error
            manager.getTransaction().rollback();
            logger.log(Level.SEVERE, "The record cannot be saved ", e);
        }

        if (localEm) {
            manager.close();
        }

        return mergedEntity;

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

    public interface PrePersistListener {
        public void prePersist(Class<?> entityClass);
    }

    public interface PostPersistListener {
        public void postPersist(Class<?> entityClass, long id);
    }

    public interface PostUpdateListener {
        public void postUpdate(Class<?> entityClass, long id);
    }


    /**
     * @returns the default sort properties of a given
     * class based on the DefaultSort annotation.
     * The sort properties are returned as a SortProperties object
     * which has methods to retrieve the arrays of properties and directions
     */
    public static SortProperties getSortProperties(Class clazz) {
        SortProperties sProp = new SortProperties();

        Annotation a = clazz.getAnnotation(DefaultSort.class);

        if (a != null) {

            DefaultSort aSort = (DefaultSort) a;

            String[] names = aSort.value();

            for (int i = 0; i < names.length; i++) {
                String name = names[i];
                String[] parts = name.split(",");
                String fname = "";
                String sDirection = "";
                boolean ascending = true;
                if (parts.length > 0) {
                    fname = parts[0].trim();
                }
                if (parts.length > 1) {
                    sDirection = parts[1].trim();
                    if (sDirection.equalsIgnoreCase("true")) {
                        ascending = true;
                    } else {
                        ascending = false;
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

        return sProp;
    }

    /**
     * Returns the table name for a given entity type in the {@link EntityManager}.
     *
     * @param em
     * @param entityClass
     * @return
     */
    public static <T> String getTableName(EntityManager em, Class<T> entityClass) {
    /*
     * Check if the specified class is present in the metamodel.
     * Throws IllegalArgumentException if not.
     */
        Metamodel meta = em.getMetamodel();
        EntityType<T> entityType = meta.entity(entityClass);

        //Check whether @Table annotation is present on the class.
        Table t = entityClass.getAnnotation(Table.class);

        String tableName = (t == null)
                ? entityType.getName().toUpperCase()
                : t.name();
        return tableName;
    }





}