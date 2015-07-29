package it.algos.web.query;

import it.algos.web.entity.BaseEntity;
import it.algos.web.entity.EM;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container.Filter;

/**
 * Utility methods for for queries
 */
public class AQuery {

	/**
	 * Searches a entity by id
	 * <p>
	 * 
	 * @param clazz
	 *            the Entity class
	 * @param id
	 *            the item id
	 */
	public static BaseEntity queryById(Class<? extends BaseEntity> clazz, Object id) {
		EntityManager manager = EM.createEntityManager();
		BaseEntity entity = (BaseEntity) manager.find(clazz, id);
		manager.close();
		return entity;
	}// end of method

	/**
	 * Search for all entities with a specified attribute value.
	 * <p>
	 * 
	 * @param clazz
	 *            the entity class
	 * @param attr
	 *            the searched attribute
	 * @param value
	 *            the value to search for
	 * @return a list of entities corresponding to the specified criteria
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<? extends BaseEntity> queryList(Class<? extends BaseEntity> clazz,
			SingularAttribute attr, Object value) {
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
	}

	/**
	 * Search for the first entity with a specified attribute value.
	 * <p>
	 * 
	 * @param clazz
	 *            the entity class
	 * @param attr
	 *            the searched attribute
	 * @param value
	 *            the value to search for
	 * @returnthe first entity corresponding to the specified criteria
	 */
	@SuppressWarnings({ "rawtypes" })
	public static BaseEntity queryFirst(Class<? extends BaseEntity> clazz, SingularAttribute attr,
			Object value) {
		BaseEntity bean = null;
		List<? extends BaseEntity> entities = queryList(clazz, attr, value);
		if (entities.size() > 0) {
			bean = entities.get(0);
		}
		return bean;
	}

	/**
	 * Search for the only entities with a specified attribute value.
	 * <p>
	 * If multiple entities exist, null is returned.
	 * 
	 * @param clazz
	 *            the entity class
	 * @param attr
	 *            the searched attribute
	 * @param value
	 *            the value to search for
	 * @returnthe the only entity corresponding to the specified criteria, or
	 *            null
	 */
	@SuppressWarnings({ "rawtypes" })
	public static BaseEntity queryOne(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value) {
		BaseEntity bean = null;
		List<? extends BaseEntity> entities = queryList(clazz, attr, value);
		if (entities.size() == 1) {
			bean = entities.get(0);
		}
		return bean;
	}

	public static long getCount(Class<?> c) {
//		EntityManager manager = EM.createEntityManager();
//		CriteriaBuilder qb = manager.getCriteriaBuilder();
//		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
//		cq.select(qb.count(cq.from(c)));
//		long count = manager.createQuery(cq).getSingleResult();
//		manager.close();
//		return count;
		return getCount(c, null, null);
	}// end of method



	/**
	 * Ritorna il numero di record in una tabella corrispondenti a una data condizione.
	 */
	public static long getCount(Class<?> c, Attribute attr, Object value) {
		EntityManager manager = EM.createEntityManager();
		CriteriaBuilder qb = manager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(c)));

		if(attr!=null){
			Root root = cq.from(c);
			Expression exp = root.get(attr.getName());
			Predicate restrictions = qb.equal(exp, value);
			cq.where(restrictions);
		}

		long count = manager.createQuery(cq).getSingleResult();
		manager.close();
		return count;
	}// end of method


	/**
	 * Search for the all entities
	 * 
	 * @param clazz
	 *            the entity class
	 * @return a list of entities
	 */
	public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz) {
		List<? extends BaseEntity> entities;
		CriteriaBuilder builder;
		CriteriaQuery<? extends BaseEntity> criteria;
		TypedQuery<? extends BaseEntity> query;

		EntityManager manager = EM.createEntityManager();

		builder = manager.getCriteriaBuilder();
		criteria = builder.createQuery(clazz);
		query = manager.createQuery(criteria);
		entities = query.getResultList();
		manager.close();
		return entities;
	}// end of method

	/**
	 * Return a list of entities for a given domain class and filters.
	 * <p>
	 * 
	 * @param entityClass
	 *            - the entity class
	 * @param filters
	 *            - an array of filters (you can use FilterFactory to build
	 *            filters, or create them as Compare....)
	 * @return the list with the entities found
	 */
	public static ArrayList<BaseEntity> getList(Class<? extends BaseEntity> entityClass,
			Filter... filters) {
		EntityItem<BaseEntity> item;
		ArrayList<BaseEntity> list = new ArrayList<BaseEntity>();
		JPAContainer<BaseEntity> container = getContainer(entityClass, filters);
		for (Object id : container.getItemIds()) {
			item = container.getItem(id);
			list.add(item.getEntity());
		}
		return list;
	}

	/**
	 * Return a single entity for a given domain class and filters.
	 * <p>
	 * 
	 * @param entityClass
	 *            - the entity class
	 * @param arguments
	 *            - an array of filters (you can use FilterFactory to build
	 *            filters, or create them as Compare....)
	 * @return the single (or first) entity found
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
	 * Create a read-only JPA container for a given domain class and filters.
	 * <p>
	 * 
	 * @param entityClass
	 *            - the entity class
	 * @param filters
	 *            - an array of filters (you can use FilterFactory to build
	 *            filters, or create them as Compare....)
	 * @return the JPA container
	 */
	@SuppressWarnings("unchecked")
	public static JPAContainer<BaseEntity> getContainer(Class<? extends BaseEntity> entityClass,
			Filter... filters) {
		EntityManager manager = EM.createEntityManager();
		JPAContainer<BaseEntity> container = (JPAContainer<BaseEntity>) JPAContainerFactory
				.makeNonCachedReadOnly(entityClass, manager);
		for (Filter filter : filters) {
			container.addContainerFilter(filter);
		}
		return container;
	}

	/**
	 * Delete all the records for a given domain class
	 */
	public static void deleteAll(Class<? extends BaseEntity> entityClass) {
		delete(entityClass, null, null);
	}

	/**
	 * Delete all the records for a given domain class
	 * <p>
	 * 
	 * @param entityClass
	 *            the domain class
	 * @param filter
	 *            the Filter for the records to delete (null for all records)
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

	
	/**
	 * Bulk delete records with CriteriaDelete
	 * <p>
	 * @param clazz - the domain class
	 * @param attr - the attribute to filter - null for no filtering
	 * @param attr - the value to check the attribute against
	 */
	public static void delete(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value) {
		EntityManager manager = EM.createEntityManager();
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		
		// create delete
		CriteriaDelete delete = cb.createCriteriaDelete(clazz);

		// set the root class
		Root root = delete.from(clazz);

		// set where clause
		if (attr!=null) {
		    Predicate pred = cb.equal(root.get(attr), value);
			delete.where(pred);
		}

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

}
