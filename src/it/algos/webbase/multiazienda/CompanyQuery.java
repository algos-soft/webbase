package it.algos.webbase.multiazienda;

import com.vaadin.data.Container.Filter;
import it.algos.webbase.domain.company.BaseCompany;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.EM;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for FILTERED queries.
 * <p>
 * The results of these methods are always filtered on the current Company.
 */
public class CompanyQuery {

    /**
     * Ritorna il numero di record di competenza della azienda corrente
     * presenti nella domain class specificata
     *
     * @param c la domain class
     * @return il numero di record
     */
    public static long getCount(Class<? extends CompanyEntity> c) {
        EntityManager manager = EM.createEntityManager();
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<CompanyEntity> root = (Root<CompanyEntity>) cq.from(c);
        Predicate predicate = cb.equal(root.get(CompanyEntity_.company), CompanySessionLib.getCompany());
        cq.where(predicate);
        CriteriaQuery<Long> select = cq.select(cb.count(root));
        TypedQuery<Long> typedQuery = manager.createQuery(select);
        Long count = typedQuery.getSingleResult();
        if(count==null){
            count=0l;
        }
        manager.close();
        return count;
    }// end of method


    /**
     * Search for all entities with a specified attribute value.
     * Filtrato sulla azienda corrente.
     * <p>
     *
     * @param clazz the entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @param manager the EntityManager to use
     * @return a list of entities corresponding to the specified criteria
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<? extends CompanyEntity> queryList(Class<? extends CompanyEntity> clazz, SingularAttribute attr,
                                                         Object value, EntityManager manager) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<? extends BaseEntity> cq = cb.createQuery(clazz);
        Root<CompanyEntity> root = (Root<CompanyEntity>) cq.from(clazz);

        Predicate pred;
        List<Predicate> predicates = new ArrayList<Predicate>();

        pred = cb.equal(root.get(attr), value);
        predicates.add(pred);

        pred = cb.equal(root.get(CompanyEntity_.company), CompanySessionLib.getCompany());
        predicates.add(pred);

        cq.where(predicates.toArray(new Predicate[]{}));

        TypedQuery<? extends BaseEntity> query = manager.createQuery(cq);
        List<CompanyEntity> entities = (List<CompanyEntity>) query.getResultList();

        return entities;
    }

    /**
     * Search for all entities with a specified attribute value.
     * Filtrato sulla azienda corrente.
     * Crea un EntityManager al volo
     * <p>
     *
     * @param clazz the entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return a list of entities corresponding to the specified criteria
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<? extends CompanyEntity> queryList(Class<? extends CompanyEntity> clazz, SingularAttribute attr,
                                                         Object value) {
        EntityManager manager = EM.createEntityManager();
        List<? extends CompanyEntity> entities = queryList(clazz, attr, value, manager);
        manager.close();

        return entities;
    }



    /**
     * Search for the only entity with a specified attribute value.
     * <p>
     * If multiple entities exist, null is returned.
     *
     * @param clazz the entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @returnthe the only entity corresponding to the specified criteria, or null
     */
    @SuppressWarnings({"rawtypes"})
    public static BaseEntity queryOne(Class<? extends CompanyEntity> clazz, SingularAttribute attr, Object value) {
        BaseEntity bean = null;
        List<? extends CompanyEntity> entities = queryList(clazz, attr, value);
        if (entities.size() == 1) {
            bean = entities.get(0);
        }
        return bean;
    }

    /**
     * Search for the first entities with a specified attribute value.
     * <p>
     *
     * @param clazz the entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return the first entity corresponding to the specified criteria
     */
    @SuppressWarnings({"rawtypes"})
    public static BaseEntity queryFirst(Class<? extends CompanyEntity> clazz, SingularAttribute attr, Object value) {
        BaseEntity bean = null;
        List<? extends CompanyEntity> entities = queryList(clazz, attr, value);
        if (entities.size() > 0) {
            bean = entities.get(0);
        }
        return bean;
    }


    /**
     * Search for the all entities
     *
     * @param clazz the entity class
     * @return a list of entities
     */
    @SuppressWarnings("unchecked")
    public static List<? extends CompanyEntity> getList(Class<? extends CompanyEntity> clazz) {
        return getList(clazz, null);
    }// end of method


    /**
     * Return a list of entities for a given domain class and filters.
     * <p>
     *
     * @param entityClass - the entity class
     * @param filters     - an array of filters (you can use FilterFactory
     *                    to build filters, or create them as Compare....)
     * @return the list with the entities found
     */
    public static List<? extends CompanyEntity> getList(Class<? extends CompanyEntity> entityClass, Filter... filters) {
        ArrayList<CompanyEntity> list = new ArrayList<>();

//        JPAContainer<EventoEntity> container = getContainer(entityClass, filters);
//        for (Object obj : container.getItemIds()) {
//            EntityItem<EventoEntity> item = container.getItem(obj);
//            list.add(item.getEntity());
//        }
//        container.getEntityProvider().getEntityManager().close();

        EntityManager em = EM.createEntityManager();
        ELazyContainer container = new ELazyContainer(em, entityClass);
        if (filters != null) {
            for (Filter filter : filters) {
                container.addContainerFilter(filter);
            }
        }

        for (Object id : container.getItemIds()) {
            CompanyEntity entity = (CompanyEntity)container.getEntity(id);
            list.add(entity);
        }
        em.close();

        return list;
    }

    /**
     * Return a single entity for a given domain class and filters.
     * <p>
     *
     * @param entityClass - the entity class
     * @param filters     - an array of filters (you can use FilterFactory
     *                    to build filters, or create them as Compare....)
     * @return the single (or first) entity found
     */
    public static CompanyEntity getEntity(Class<? extends CompanyEntity> entityClass, Filter... filters) {
        CompanyEntity entity = null;
        List<? extends CompanyEntity> list = getList(entityClass, filters);
        if (list.size() > 0) {
            entity = list.get(0);
        }
        return entity;
    }




    /**
     * Bulk delete records with CriteriaDelete
     * for a given domain class
     * <p>
     *
     * @param entityClass - the domain class
     */
    public static void deleteAll(Class<? extends CompanyEntity> entityClass) {
        EntityManager manager = EM.createEntityManager();
        CriteriaBuilder cb = manager.getCriteriaBuilder();

        // create delete
        CriteriaDelete delete = cb.createCriteriaDelete(entityClass);

        // set the root class
        Root root = delete.from(entityClass);

        // set where clause
        Predicate pred = cb.equal(root.get(CompanyEntity_.company), CompanySessionLib.getCompany());
        delete.where(pred);

        // perform update
        try {
            manager.getTransaction().begin();
            manager.createQuery(delete).executeUpdate();
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        }

        manager.close();

    }


    /**
     * Crea un filtro sulla company corrente a una query.
     */
    public static Predicate creaFiltroCompany(Root root, CriteriaBuilder cb) {
        BaseCompany company = CompanySessionLib.getCompany();
        return cb.equal(root.get(CompanyEntity_.company), company);
    }



}

