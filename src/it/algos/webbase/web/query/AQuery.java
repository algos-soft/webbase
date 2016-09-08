package it.algos.webbase.web.query;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container.Filter;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.EM;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for queries
 */
public class AQuery {


    //------------------------------------------------------------------------------------------------------------------------
    // Count records
    //------------------------------------------------------------------------------------------------------------------------

    /**
     * Ritorna il numero totale di record della Entity specificata
     * Senza filtri.
     *
     * @param clazz the Entity class
     * @return il numero totale di record nella Entity
     */
    public static long getCount(Class<? extends BaseEntity> clazz) {
        return getCount(clazz, null, null, null);
    }// end of method


    /**
     * Ritorna il numero totale di record della entity specificata
     *
     * @param clazz   the Entity class
     * @param manager the EntityManager to use
     * @return il numero totale di record nella Entity
     */
    public static long getCount(Class<? extends BaseEntity> clazz, EntityManager manager) {
        return getCount(clazz, null, null, manager);
    }// end of method

    /**
     * Ritorna il numero di record filtrati della entity specificata
     * Filtrato sul valore della property indicata.
     *
     * @param clazz the Entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return il numero di record filtrati nella Entity
     */
    public static long getCount(Class<? extends BaseEntity> clazz, Attribute attr, Object value) {
        return getCount(clazz, attr, value, null);
    }// end of method


    /**
     * Ritorna il numero di record filtrati della entity specificata
     * Filtrato sul valore della property indicata.
     * Se il manager è nullo, costruisce al volo un manager standard (and close it)
     * Se il manager è valido, lo usa (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param attr    the searched attribute
     * @param value   the value to search for
     * @param manager the EntityManager to use
     * @return il numero di record filtrati nella Entity
     */
    public static long getCount(Class<? extends BaseEntity> clazz, Attribute attr, Object value, EntityManager manager) {
        String propertyName = "";

        // se non specificato l'EntityManager, ne crea uno locale
        boolean usaManagerLocale = false;
        if (manager == null) {
            usaManagerLocale = true;
            manager = EM.createEntityManager();
        }// end of if cycle

        if (attr != null) {
            propertyName = attr.getName();
        }// end of if cycle

        CriteriaBuilder qb = manager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(clazz)));

        if (!propertyName.equals("")) {
            Root root = cq.from(clazz);
            Expression exp = root.get(propertyName);
            Predicate restrictions = qb.equal(exp, value);
            cq.where(restrictions);
        }// end of if cycle

        long count = manager.createQuery(cq).getSingleResult();

        // eventualmente chiude l'EntityManager locale
        if (usaManagerLocale) {
            manager.close();
        }// end of if cycle

        return count;
    }// end of method


    //------------------------------------------------------------------------------------------------------------------------
    // Find entity by primary key
    //------------------------------------------------------------------------------------------------------------------------

    /**
     * Searches for a single entity by standard Primary Key (all Algos primary key are long)
     * Multiple entities cannot exist, the primary key is unique.
     *
     * @param clazz the Entity class
     * @param id    the long id
     * @return the entity corresponding to the key, or null
     */
    public static BaseEntity find(Class<? extends BaseEntity> clazz, long id) {
        return find(clazz, id, null);
    }// end of static method


    /**
     * Searches for a single entity by standard Primary Key (all Algos primary key are long)
     * Multiple entities cannot exist, the primary key is unique.
     * Se il manager è nullo, costruisce al volo un manager standard (and close it)
     * Se il manager è valido, lo usa (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param id      the item id
     * @param manager the EntityManager to use
     * @return the entity corresponding to the key, or null
     */
    public static BaseEntity find(Class<? extends BaseEntity> clazz, long id, EntityManager manager) {
        BaseEntity entity = null;

        // se non specificato l'EntityManager, ne crea uno locale
        boolean usaManagerLocale = false;
        if (manager == null) {
            usaManagerLocale = true;
            manager = EM.createEntityManager();
        }// end of if cycle

        entity = manager.find(clazz, id);

        // eventualmente chiude l'EntityManager locale
        if (usaManagerLocale) {
            manager.close();
        }// end of if cycle

        return entity;
    }// end of static method


    //------------------------------------------------------------------------------------------------------------------------
    // Find entity by SingularAttribute
    //------------------------------------------------------------------------------------------------------------------------

    /**
     * Search for a single entity with a specified attribute value.
     * <p>
     * If multiple entities exist, null is returned.
     * Use a standard manager (and close it)
     *
     * @param clazz the Entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return the only entity corresponding to the specified criteria, or null
     */
    public static BaseEntity findOne(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value) {
        BaseEntity entity = null;

        EntityManager manager = EM.createEntityManager();
        entity = findOne(clazz, attr, value, manager);
        manager.close();

        return entity;
    }// end of static method


    /**
     * Search for a single entity with a specified attribute value.
     * <p>
     * If multiple entities exist, null is returned.
     * Use a specific manager (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param attr    the searched attribute
     * @param value   the value to search for
     * @param manager the EntityManager to use
     * @return the only entity corresponding to the specified criteria, or null
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static BaseEntity findOne(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value, EntityManager manager) {
        BaseEntity entity = null;

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<? extends BaseEntity> cq = cb.createQuery(clazz);
        Root<? extends BaseEntity> root = cq.from(clazz);
        Predicate pred = cb.equal(root.get(attr), value);
        cq.where(pred);
        TypedQuery<? extends BaseEntity> query = manager.createQuery(cq);

        try { // prova ad eseguire il codice
            entity = query.getSingleResult();
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        return entity;
    }// end of static method


    /**
     * Search for the first entity with a specified attribute value.
     * <p>
     * Use a standard manager (and close it)
     *
     * @param clazz the Entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return the first entity corresponding to the specified criteria
     */
    public static BaseEntity findFirst(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value) {
        BaseEntity entity = null;

        EntityManager manager = EM.createEntityManager();
        entity = findFirst(clazz, attr, value, manager);
        manager.close();

        return entity;
    }// end of method


    /**
     * Search for the first entity with a specified attribute value.
     * <p>
     * Use a specific manager (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param attr    the searched attribute
     * @param value   the value to search for
     * @param manager the EntityManager to use
     * @return the first entity corresponding to the specified criteria
     */
    @SuppressWarnings({"rawtypes"})
    public static BaseEntity findFirst(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value, EntityManager manager) {
        BaseEntity entity = null;

        List<? extends BaseEntity> entities = queryList(clazz, attr, value);
        if (entities.size() > 0) {
            entity = entities.get(0);
        }// end of if cycle

        return entity;
    }// end of method


    //------------------------------------------------------------------------------------------------------------------------
    // Find entities (list)
    //------------------------------------------------------------------------------------------------------------------------

    /**
     * Search for the all entities of a given Entity class
     * Senza filtri.
     *
     * @param clazz the Entity class
     * @return a list of all entities
     */
    public static List<? extends BaseEntity> findAll(Class<? extends BaseEntity> clazz) {
        List<? extends BaseEntity> entities;

        EntityManager manager = EM.createEntityManager();
        entities = findAll(clazz, manager);
        manager.close();

        return entities;
    }// end of method


    /**
     * Search for the all entities of a given Entity class
     * Senza filtri.
     * Se il manager è nullo, costruisce al volo un manager standard (and close it)
     * Se il manager è valido, lo usa (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param manager the EntityManager to use
     * @return a list of all entities
     */
    public static List<? extends BaseEntity> findAll(Class<? extends BaseEntity> clazz, EntityManager manager) {
        List<? extends BaseEntity> entities;

        // se non specificato l'EntityManager, ne crea uno locale
        boolean usaManagerLocale = false;
        if (manager == null) {
            usaManagerLocale = true;
            manager = EM.createEntityManager();
        }// end of if cycle

        CriteriaBuilder builder;
        CriteriaQuery<? extends BaseEntity> criteria;
        TypedQuery<? extends BaseEntity> query;

        builder = manager.getCriteriaBuilder();
        criteria = builder.createQuery(clazz);
        query = manager.createQuery(criteria);
        entities = query.getResultList();

        // eventualmente chiude l'EntityManager locale
        if (usaManagerLocale) {
            manager.close();
        }// end of if cycle

        return entities;
    }// end of method


    /**
     * Return a list of entities for a given domain class and filters.
     * Filtrato coi filtri indicati
     *
     * @param clazz   the Entity class
     * @param sorts   an array of sort wrapper
     * @param filters an array of filters (you can use FilterFactory to build filters, or create them as Compare....)
     * @return the list of founded entities
     */
    public static List<? extends BaseEntity> findAll(Class<? extends BaseEntity> clazz, SortProperty sorts, Filter... filters) {
        return findAll(clazz, sorts, filters);
    }// end of method


    /**
     * Return a list of entities for a given domain class and filters.
     * Filtrato coi filtri indicati
     * <p>
     * Se il manager è nullo, costruisce al volo un manager standard (and close it)
     * Se il manager è valido, lo usa (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param sorts   an array of sort wrapper
     * @param manager the EntityManager to use
     * @param filters an array of filters (you can use FilterFactory to build filters, or create them as Compare....)
     * @return the list of founded entities
     */
    public static List<? extends BaseEntity> findAll(Class<? extends BaseEntity> clazz, SortProperty sorts, EntityManager manager, Filter... filters) {
        List<BaseEntity> entities = new ArrayList<BaseEntity>();
        EntityItem<BaseEntity> item;

        // se non specificato l'EntityManager, ne crea uno locale
        boolean usaManagerLocale = false;
        if (manager == null) {
            usaManagerLocale = true;
            manager = EM.createEntityManager();
        }// end of if cycle

        JPAContainer<BaseEntity> container = getContainer(clazz, manager, filters);

        if (sorts != null) {
            for (String stringa : sorts.getProperties()) {
                if (stringa.contains(".")) {
                    container.addNestedContainerProperty(stringa.substring(0, stringa.lastIndexOf(".")) + ".*");
                }// end of if cycle
            }// end of for cycle

            container.sort(sorts.getProperties(), sorts.getOrdinamenti());

        }// end of if cycle

        for (Object id : container.getItemIds()) {
            item = container.getItem(id);
            entities.add(item.getEntity());
        }// end of for cycle

        // eventualmente chiude l'EntityManager locale
        if (usaManagerLocale) {
            manager.close();
        }// end of if cycle

        return entities;
    }// end of method


    /**
     * Search for all entities with a specified attribute value.
     * Filtrato sul valore della property indicata.
     *
     * @param clazz the Entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return the list of founded entities
     */
    public static List<? extends BaseEntity> findAll(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value) {
        return findAll(clazz, attr, value, null);
    }// end of method


    /**
     * Search for all entities with a specified attribute value.
     * Filtrato sul valore della property indicata.
     * <p>
     * Se il manager è nullo, costruisce al volo un manager standard (and close it)
     * Se il manager è valido, lo usa (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param attr    the searched attribute
     * @param value   the value to search for
     * @param manager the EntityManager to use
     * @return the list of founded entities
     */
    @SuppressWarnings("unchecked")
    public static List<? extends BaseEntity> findAll(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value, EntityManager manager) {
        List<? extends BaseEntity> entities;

        // se non specificato l'EntityManager, ne crea uno locale
        boolean usaManagerLocale = false;
        if (manager == null) {
            usaManagerLocale = true;
            manager = EM.createEntityManager();
        }// end of if cycle

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<? extends BaseEntity> cq = cb.createQuery(clazz);
        Root<? extends BaseEntity> root = cq.from(clazz);
        Predicate pred = cb.equal(root.get(attr), value);
        cq.where(pred);
        TypedQuery<? extends BaseEntity> query = manager.createQuery(cq);
        entities = query.getResultList();

        // eventualmente chiude l'EntityManager locale
        if (usaManagerLocale) {
            manager.close();
        }// end of if cycle

        return entities;
    }// end of method

    //------------------------------------------------------------------------------------------------------------------------
    // Utilities
    //------------------------------------------------------------------------------------------------------------------------

    /**
     * Create a read-only JPA container for a given domain class and filters.
     * <p>
     * Use a standard manager
     *
     * @param clazz   the Entity class
     * @param filters an array of filters (you can use FilterFactory to build filters, or create them as Compare....)
     * @return the JPA container
     */
    @SuppressWarnings("unchecked")
    private static JPAContainer<BaseEntity> getContainer(Class<? extends BaseEntity> clazz, Filter... filters) {
        JPAContainer<BaseEntity> container = null;

        EntityManager manager = EM.createEntityManager();
        container = getContainer(clazz, manager, filters);
//        manager.close();

        return container;
    }// end of method


    /**
     * Create a read-only JPA container for a given domain class and filters.
     * <p>
     * Use a specific manager (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param manager the EntityManager to use
     * @param filters an array of filters (you can use FilterFactory to build filters, or create them as Compare....)
     * @return the JPA container
     */
    @SuppressWarnings("unchecked")
    private static JPAContainer<BaseEntity> getContainer(Class<? extends BaseEntity> clazz, EntityManager manager, Filter... filters) {
        JPAContainer<BaseEntity> container = (JPAContainer<BaseEntity>) JPAContainerFactory.makeNonCachedReadOnly(clazz, manager);

        for (Filter filter : filters) {
            container.addContainerFilter(filter);
        }// end of for cycle

        return container;
    }// end of method


    //------------------------------------------------------------------------------------------------------------------------
    // Delete
    //------------------------------------------------------------------------------------------------------------------------

    /**
     * Delete all the records for a given domain class
     *
     * @param clazz the entity class
     */
    public static void deleteAll(Class<? extends BaseEntity> clazz) {
        EntityManager manager = EM.createEntityManager();
        deleteAll(clazz, manager);
        manager.close();
    }// end of method


    /**
     * Delete all the records for a given domain class
     *
     * @param clazz   the entity class
     * @param manager the EntityManager to use
     */
    public static void deleteAll(Class<? extends BaseEntity> clazz, EntityManager manager) {
        delete(clazz, null, null, manager);
    }// end of method


    /**
     * Bulk delete records with CriteriaDelete
     * <p>
     *
     * @param clazz the domain class
     * @param attr  the attribute to filter - null for no filtering
     * @param value the value to search for
     */
    public static void delete(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value) {
        EntityManager manager = EM.createEntityManager();
        delete(clazz, attr, value, manager);
        manager.close();
    }// end of method


    /**
     * Bulk delete records with CriteriaDelete
     * <p>
     *
     * @param clazz   the domain class
     * @param attr    the attribute to filter - null for no filtering
     * @param value   the value to search for
     * @param manager the EntityManager to use
     */
    @SuppressWarnings("unchecked")
    public static void delete(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value, EntityManager manager) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();

        // create delete
        CriteriaDelete delete = cb.createCriteriaDelete(clazz);

        // set the root class
        Root root = delete.from(clazz);

        // set where clause
        if (attr != null) {
            Predicate pred = cb.equal(root.get(attr), value);
            delete.where(pred);
        }// end of if cycle

        // perform update
        try {
            manager.getTransaction().begin();
            manager.createQuery(delete).executeUpdate();
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        }// fine del blocco try-catch

    }// end of method

    /**
     * Delete all the records for a given domain class
     * <p>
     *
     * @param entityClass the domain class
     * @param filter      the Filter for the records to delete (null for all records)
     */
    @SuppressWarnings("unchecked")
    public static void delete(Class<? extends BaseEntity> entityClass, Filter filter) {

        EntityManager manager = EM.createEntityManager();
        JPAContainer<BaseEntity> cont = (JPAContainer<BaseEntity>) JPAContainerFactory.make(
                entityClass, manager);
        if (filter != null) {
            cont.addContainerFilter(filter);
        }

        try {

            manager.getTransaction().begin();

            for (Object id : cont.getItemIds()) {
                BaseEntity entity = cont.getItem(id).getEntity();
                entity = manager.merge(entity);
                manager.remove(entity);
            }

            manager.getTransaction().commit();

        } catch (Exception e) {
            manager.getTransaction().rollback();
        }
        manager.close();

    }// end of method


    //------------------------------------------------------------------------------------------------------------------------
    // deprecated
    //------------------------------------------------------------------------------------------------------------------------
    /**
     * Search for all entities with a specified attribute value.
     * <p>
     *
     * @param clazz the entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return a list of entities corresponding to the specified criteria
     * @deprecated
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<? extends BaseEntity> queryList(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value) {
        EntityManager manager = EM.createEntityManager();
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<? extends BaseEntity> cq = cb.createQuery(clazz);
        Root<? extends BaseEntity> root = cq.from(clazz);
        Predicate pred = cb.equal(root.get(attr), value);
        cq.where(pred);
        TypedQuery<? extends BaseEntity> query = manager.createQuery(cq);
        List<? extends BaseEntity> entities = query.getResultList();
        manager.close();
        return entities;
    }// end of method


    /**
     * Search for all entities with a specified attribute value.
     * <p>
     *
     * @param clazz the entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return a list of entities corresponding to the specified criteria
     * @deprecated
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<? extends BaseEntity> queryList(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value, EntityManager manager) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<? extends BaseEntity> cq = cb.createQuery(clazz);
        Root<? extends BaseEntity> root = cq.from(clazz);
        Predicate pred = cb.equal(root.get(attr), value);
        cq.where(pred);
        TypedQuery<? extends BaseEntity> query = manager.createQuery(cq);
        List<? extends BaseEntity> entities = query.getResultList();
        return entities;
    }// end of method

    /**
     * Search for all entities with a specified attribute value.
     * <p>
     *
     * @param clazz the entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return a list of entities corresponding to the specified criteria
     * @deprecated
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static ArrayList<? extends BaseEntity> queryLista(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value) {
        ArrayList<? extends BaseEntity> lista = null;
        List<? extends BaseEntity> entities = queryList(clazz, attr, value);

        if (entities != null) {
            lista = new ArrayList<BaseEntity>(entities);
        }// end of if cycle

        return lista;
    }// end of method

    /**
     * Search for the first entity with a specified attribute value.
     * <p>
     *
     * @param clazz the entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return the first entity corresponding to the specified criteria
     * @deprecated
     */
    @SuppressWarnings({"rawtypes"})
    public static BaseEntity queryFirst(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value) {
        BaseEntity bean = null;

        List<? extends BaseEntity> entities = queryList(clazz, attr, value);
        if (entities.size() > 0) {
            bean = entities.get(0);
        }// end of if cycle

        return bean;
    }// end of method

    /**
     * Search for the only entities with a specified attribute value.
     * <p>
     * If multiple entities exist, null is returned.
     *
     * @param clazz the entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return the only entity corresponding to the specified criteria, or null
     * @deprecated
     */
    @SuppressWarnings({"rawtypes"})
    public static BaseEntity queryOne(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value) {
        BaseEntity bean = null;

        EntityManager manager = EM.createEntityManager();
        bean = queryOne(clazz, attr, value, manager);
        manager.close();

        return bean;
    }// end of method


    /**
     * Search for the only entities with a specified attribute value.
     * <p>
     * If multiple entities exist, null is returned.
     *
     * @param clazz   the entity class
     * @param attr    the searched attribute
     * @param value   the value to search for
     * @param manager the EntityManager to use
     * @return the only entity corresponding to the specified criteria, or null
     * @deprecated
     */
    @SuppressWarnings({"rawtypes"})
    public static BaseEntity queryOne(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value, EntityManager manager) {
        BaseEntity bean = null;

        List<? extends BaseEntity> entities = queryList(clazz, attr, value, manager);
        if (entities.size() == 1) {
            bean = entities.get(0);
        }// end of if cycle

        return bean;
    }// end of method


    /**
     * Search for the all entities
     *
     * @param entityClass the entity class
     * @return a list of entities
     * @deprecated
     */
    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> entityClass) {
        List<? extends BaseEntity> entities;

        EntityManager manager = EM.createEntityManager();
        entities = getList(entityClass, manager);
        manager.close();

        return entities;
    }// end of method


    /**
     * Search for the all entities
     *
     * @param entityClass the entity class
     * @param manager     the EntityManager to use
     * @return a list of entities
     * @deprecated
     */
    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> entityClass, EntityManager manager) {
        List<? extends BaseEntity> entities;
        CriteriaBuilder builder;
        CriteriaQuery<? extends BaseEntity> criteria;
        TypedQuery<? extends BaseEntity> query;

        builder = manager.getCriteriaBuilder();
        criteria = builder.createQuery(entityClass);
        query = manager.createQuery(criteria);
        entities = query.getResultList();

        return entities;
    }// end of method

    /**
     * Search for the all entities
     *
     * @param entityClass the entity class
     * @return an ArrayList of entities
     * @deprecated
     */
    public static ArrayList<? extends BaseEntity> getLista(Class<? extends BaseEntity> entityClass) {
        ArrayList<? extends BaseEntity> lista = null;

        EntityManager manager = EM.createEntityManager();
        lista = getLista(entityClass, manager);
        manager.close();

        return lista;
    }// end of method


    /**
     * Search for the all entities
     *
     * @param entityClass the entity class
     * @param manager     the EntityManager to use
     * @return an ArrayList of entities
     * @deprecated
     */
    public static ArrayList<? extends BaseEntity> getLista(Class<? extends BaseEntity> entityClass, EntityManager manager) {
        ArrayList<? extends BaseEntity> lista = null;
        List<? extends BaseEntity> entities = getList(entityClass, manager);

        if (entities != null) {
            lista = new ArrayList<BaseEntity>(entities);
        }// end of if cycle

        return lista;
    }// end of method


    /**
     * Return a list of entities for a given domain class and filters.
     * <p>
     *
     * @param entityClass - the entity class
     * @param filters     - an array of filters (you can use FilterFactory to build
     *                    filters, or create them as Compare....)
     * @return the list with the entities found
     * @deprecated
     */
    public static ArrayList<BaseEntity> getList(Class<? extends BaseEntity> entityClass, Filter... filters) {
        return getList(entityClass, null, filters);
    }// end of method

    /**
     * Return a list of entities for a given domain class and filters.
     * <p>
     *
     * @param entityClass - the entity class
     * @param filters     - an array of filters (you can use FilterFactory to build
     *                    filters, or create them as Compare....)
     * @return an ArrayList of entities
     * @deprecated
     */
    public static ArrayList<? extends BaseEntity> getLista(Class<? extends BaseEntity> entityClass, Filter... filters) {
        ArrayList<? extends BaseEntity> lista = null;
        List<? extends BaseEntity> entities = getList(entityClass, filters);

        if (entities != null) {
            lista = new ArrayList<BaseEntity>(entities);
        }// end of if cycle

        return lista;
    }// end of method


    /**
     * Return a list of entities for a given domain class and filters.
     * <p>
     *
     * @param entityClass - the entity class
     * @param sorts       - an array of sort wrapper
     * @param filters     - an array of filters (you can use FilterFactory to build
     *                    filters, or create them as Compare....)
     * @return the list with the entities found
     * @deprecated
     */
    public static ArrayList<BaseEntity> getList(Class<? extends BaseEntity> entityClass, SortProperty sorts, Filter... filters) {
        EntityItem<BaseEntity> item;
        ArrayList<BaseEntity> list = new ArrayList<BaseEntity>();
        JPAContainer<BaseEntity> container = getContainer(entityClass, filters);

        if (sorts != null) {
            for (String stringa : sorts.getProperties()) {
                if (stringa.contains(".")) {
                    container.addNestedContainerProperty(stringa.substring(0, stringa.lastIndexOf(".")) + ".*");
                }// end of if cycle
            }// end of for cycle

            container.sort(sorts.getProperties(), sorts.getOrdinamenti());
        }// end of if cycle

        for (Object id : container.getItemIds()) {
            item = container.getItem(id);
            list.add(item.getEntity());
        }// end of for cycle

        // close the EntityManager
        container.getEntityProvider().getEntityManager().close();

        return list;
    }// end of method


    /**
     * Return a single entity for a given domain class and filters.
     * <p>
     *
     * @param entityClass - the entity class
     * @param arguments   - an array of filters (you can use FilterFactory to build
     *                    filters, or create them as Compare....)
     * @return the single (or first) entity found
     * @deprecated
     */
    public static BaseEntity getEntity(Class<? extends BaseEntity> entityClass, Filter... arguments) {
        BaseEntity entity = null;
        ArrayList<BaseEntity> list = getList(entityClass, arguments);
        if (list.size() > 0) {
            entity = list.get(0);
        }
        return entity;
    }



    /**
     * Searches for a single entity by id
     *
     * @param clazz the Entity class
     * @param id    the item id
     * @deprecated
     */
    public static BaseEntity queryById(Class<? extends BaseEntity> clazz, Object id) {
        EntityManager manager = EM.createEntityManager();
        BaseEntity entity = (BaseEntity) manager.find(clazz, id);
        manager.close();
        return entity;
    }// end of static method

}// end of class
