package it.algos.webbase.multiazienda;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare;
import it.algos.webbase.domain.company.BaseCompany;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.query.AQuery;
import it.algos.webbase.web.query.SortProperty;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility methods for FILTERED queries.
 * Libreria da usare per tutte le query, quando l'applicazione USA la multiazienda (company)
 * Altrimenti si usa AQuery
 * <p>
 * The results of these methods are filtered on the current Company
 * or
 * The results of these methods are filtered on the Company passed as parameter
 * <p>
 * I metodi sono con o senza EntityManager
 */
public abstract class CompanyQuery {


    //------------------------------------------------------------------------------------------------------------------------
    // Count records
    // Con e senza Property
    // Con e senza Company (corrente o specifica)
    // Con e senza EntityManager
    // Rimanda ad un unico metodo
    // @todo Funzionamento testato nel progetto MultyCompany.ACompanyTest
    // Return int (non c'è motivo di usare un long come valore di ritorno)
    //------------------------------------------------------------------------------------------------------------------------

    /**
     * Numero totale di records della Entity specificata
     * Filtrato sulla company corrente.
     * Usa l'EntityManager di default
     *
     * @param clazz the Entity class
     * @return il numero totale di records nella Entity
     */
    public static int count(Class<? extends CompanyEntity> clazz) {
        return count(clazz, (SingularAttribute) null, null);
    }// end of static method


    /**
     * Numero di records della Entity specificata
     * Filtrato sul valore della property indicata
     * Se la property è nulla, restituisce il numero di tutti i records della company
     * Filtrato sulla company corrente.
     * Usa l'EntityManager di default
     *
     * @param clazz the Entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return il numero filtrato di records nella Entity
     */
    public static int count(Class<? extends CompanyEntity> clazz, SingularAttribute attr, Object value) {
        return count(clazz, attr, value, CompanySessionLib.getCompany(), null);
    }// end of static method

    /**
     * Numero di records della Entity specificata
     * Filtrato sulla company passata come parametro.
     * Se la company è nulla, restituisce il numero di TUTTI i records
     * Usa l'EntityManager di default
     *
     * @param clazz   the Entity class
     * @param company da filtrare
     * @return il numero filtrato di records nella Entity
     */
    public static int count(Class<? extends CompanyEntity> clazz, BaseCompany company) {
        return count(clazz, company, null);
    }// end of static method

    /**
     * Numero di records della Entity specificata
     * Filtrato sulla company corrente.
     * Usa l'EntityManager passato come parametro
     * Se il manager è nullo, costruisce al volo un manager standard (and close it)
     * Se il manager è valido, lo usa (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param manager the EntityManager to use
     * @return il numero filtrato di records nella Entity
     */
    public static int count(Class<? extends CompanyEntity> clazz, EntityManager manager) {
        return count(clazz, CompanySessionLib.getCompany(), manager);
    }// end of static method


    /**
     * Numero di records della Entity specificata
     * Filtrato sulla company passata come parametro.
     * Se la company è nulla, restituisce il numero di TUTTI i records
     * Usa l'EntityManager passato come parametro
     * Se il manager è nullo, costruisce al volo un manager standard (and close it)
     * Se il manager è valido, lo usa (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param company da filtrare
     * @param manager the EntityManager to use
     * @return il numero filtrato di records nella Entity
     */
    public static int count(Class<? extends CompanyEntity> clazz, BaseCompany company, EntityManager manager) {
        return count(clazz, null, null, company, manager);
    }// end of static method


    /**
     * Numero di records della Entity specificata
     * Filtrato sul valore della property indicata
     * Se la property è nulla, restituisce il numero di tutti i records della company
     * Filtrato sulla company passata come parametro.
     * Se la company è nulla, restituisce il numero dei records di tutte le company, filtrati sulla property
     * Usa l'EntityManager passato come parametro
     * Se il manager è nullo, costruisce al volo un manager standard (and close it)
     * Se il manager è valido, lo usa (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param attr    the searched attribute
     * @param value   the value to search for
     * @param company da filtrare
     * @param manager the EntityManager to use
     * @return il numero filtrato di records nella Entity
     */
    @SuppressWarnings({"all"})
    public static int count(Class<? extends CompanyEntity> clazz, SingularAttribute attr, Object value, BaseCompany company, EntityManager manager) {
        Long count = 0L;
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate;

        // se non specificato l'EntityManager, ne crea uno locale
        boolean usaManagerLocale = false;
        if (manager == null) {
            usaManagerLocale = true;
            manager = EM.createEntityManager();
        }// end of if cycle

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<? extends CompanyEntity> root = criteria.from(clazz);

        // questo predicato è opzionale
        // se la company è nulla, cerca per tutte le companies
        if (company != null) {
            predicate = builder.equal(root.get(CompanyEntity_.company), company);
            predicates.add(predicate);
        }// end of if cycle

        // questo predicato è opzionale
        // se la property è nulla, restituisce il numero di tutti i records della company
        if (attr != null) {
            predicate = builder.equal(root.get(attr), value);
            predicates.add(predicate);
        }// end of if cycle

        criteria.where(predicates.toArray(new Predicate[]{}));
        CriteriaQuery<Long> select = criteria.select(builder.count(root));
        TypedQuery<Long> typedQuery = manager.createQuery(select);

        try { // prova ad eseguire il codice
            count = typedQuery.getSingleResult();
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        // se usato, chiude l'EntityManager locale
        if (usaManagerLocale) {
            manager.close();
        }// end of if cycle

        return count.intValue();
    }// end of static method


    //------------------------------------------------------------------------------------------------------------------------
    // Find entity by primary key
    // Con e senza EntityManager
    // Rimanda ad un unico metodo
    // @todo Funzionamento testato nel progetto MultyCompany.ACompanyTest
    // Parametro in ingresso di tipo long (all Algos primary key are long)
    //------------------------------------------------------------------------------------------------------------------------

    /**
     * Searches for a single entity by standard Primary Key (all Algos primary key are long)
     * Multiple entities cannot exist, the primary key is unique.
     * Usa l'EntityManager di default
     *
     * @param clazz the Entity class
     * @param id    the item long id
     * @return the entity corresponding to the key, or null
     */
    public static CompanyEntity find(Class<? extends CompanyEntity> clazz, long id) {
        return find(clazz, id, null);
    }// end of static method


    /**
     * Searches for a single entity by standard Primary Key (all Algos primary key are long)
     * Multiple entities cannot exist, the primary key is unique.
     * Usa l'EntityManager passato come parametro
     * Se il manager è nullo, costruisce al volo un manager standard (and close it)
     * Se il manager è valido, lo usa (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param id      the item long id
     * @param manager the EntityManager to use
     * @return the entity corresponding to the key, or null
     */
    public static CompanyEntity find(Class<? extends CompanyEntity> clazz, long id, EntityManager manager) {
        return (CompanyEntity) AQuery.find(clazz, id, manager);
    }// end of static method


    //------------------------------------------------------------------------------------------------------------------------
    // Find entity by SingularAttribute
    // Con e senza Company (corrente o specifica)
    // Se la company è nulla, cerca per tutte le companies
    // Con e senza EntityManager
    // Rimanda ad un unico metodo
    // @todo Funzionamento testato nel progetto MultyCompany.ACompanyTest
    // Return una entity di classe CompanyEntity (casting specifico nel metodo chiamante)
    //------------------------------------------------------------------------------------------------------------------------

    /**
     * Search for a single entity with a specified attribute value.
     * If multiple entities exist, null is returned.
     * Filtrato sulla azienda corrente.
     * Usa l'EntityManager di default
     *
     * @param clazz the Entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return the only entity corresponding to the specified criteria, or null
     */
    public static CompanyEntity getEntity(Class<? extends CompanyEntity> clazz, SingularAttribute attr, Object value) {
        return getEntity(clazz, attr, value, (EntityManager) null);
    }// end of static method

    /**
     * Search for a single entity with a specified attribute value.
     * If multiple entities exist, null is returned.
     * Filtrato sulla azienda corrente.
     * Usa l'EntityManager passato come parametro
     * Se il manager è nullo, costruisce al volo un manager standard (and close it)
     * Se il manager è valido, lo usa (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param attr    the searched attribute
     * @param value   the value to search for
     * @param manager the EntityManager to use
     * @return the only entity corresponding to the specified criteria, or null
     */
    public static CompanyEntity getEntity(Class<? extends CompanyEntity> clazz, SingularAttribute attr, Object value, EntityManager manager) {
        return getEntity(clazz, attr, value, CompanySessionLib.getCompany(), manager);
    }// end of static method

    /**
     * Search for a single entity with a specified attribute value.
     * If multiple entities exist, null is returned.
     * Filtrato sulla azienda passata come parametro.
     * Se la company è nulla, cerca per tutte le companies
     * Usa l'EntityManager di default
     *
     * @param clazz   the Entity class
     * @param attr    the searched attribute
     * @param value   the value to search for
     * @param company da filtrare
     * @return the only entity corresponding to the specified criteria, or null
     */
    public static CompanyEntity getEntity(Class<? extends CompanyEntity> clazz, SingularAttribute attr, Object value, BaseCompany company) {
        return getEntity(clazz, attr, value, company, null);
    }// end of static method


    /**
     * Search for a single entity with a specified attribute value.
     * If multiple entities exist, null is returned.
     * Filtrato sulla azienda passata come parametro.
     * Se la company è nulla, cerca per tutte le companies
     * Usa l'EntityManager passato come parametro
     * Se il manager è nullo, costruisce al volo un manager standard (and close it)
     * Se il manager è valido, lo usa (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param attr    the searched attribute
     * @param value   the value to search for
     * @param company da filtrare
     * @param manager the EntityManager to use
     * @return the only entity corresponding to the specified criteria, or null
     */
    @SuppressWarnings({"all"})
    public static CompanyEntity getEntity(Class<? extends CompanyEntity> clazz, SingularAttribute attr, Object value, BaseCompany company, EntityManager manager) {
        CompanyEntity entity = null;
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate;

        // the specified attribute is mandatory
        if (attr == null) {
            return null;
        }// end of if cycle

        // se non specificato l'EntityManager, ne crea uno locale
        boolean usaManagerLocale = false;
        if (manager == null) {
            usaManagerLocale = true;
            manager = EM.createEntityManager();
        }// end of if cycle

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<? extends BaseEntity> criteria = builder.createQuery(clazz);
        Root<? extends CompanyEntity> root = criteria.from(clazz);

        // questo predicato è obbligatorio
        if (value != null) {
            predicate = builder.equal(root.get(attr), value);
            predicates.add(predicate);
        }// end of if cycle

        // questo predicato è opzionale
        // se la company è nulla, cerca per tutte le companies
        if (company != null) {
            predicate = builder.equal(root.get(CompanyEntity_.company), company);
            predicates.add(predicate);
        }// end of if cycle

        criteria.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<? extends BaseEntity> query = manager.createQuery(criteria);

        try { // prova ad eseguire il codice
            entity = (CompanyEntity) query.getSingleResult();
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        // se usato, chiude l'EntityManager locale
        if (usaManagerLocale) {
            manager.close();
        }// end of if cycle

        return entity;
    }// end of static method

    //------------------------------------------------------------------------------------------------------------------------
    // Find entities (list) by SingularAttribute
    // Con e senza Property
    // Con e senza Company (corrente o specifica)
    // Se la company è nulla, cerca per tutte le companies
    // Con e senza Sort
    // Con e senza EntityManager
    // Rimanda ad un unico metodo
    // @todo Funzionamento testato nel progetto MultyCompany.ACompanyTest
    // Return una List di oggetti CompanyEntity (casting specifico nel metodo chiamante)
    //------------------------------------------------------------------------------------------------------------------------
    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz) {
        return getList(clazz, null, null, null, CompanySessionLib.getCompany(), null);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, BaseCompany company) {
        return getList(clazz, null, null, null, company, null);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, EntityManager manager) {
        return getList(clazz, null, null, null, CompanySessionLib.getCompany(), manager);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, BaseCompany company, EntityManager manager) {
        return getList(clazz, null, null, null, company, manager);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value) {
        return getList(clazz, attr, value, null, CompanySessionLib.getCompany(), null);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value, BaseCompany company) {
        return getList(clazz, attr, value, null, company, null);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, Container.Filter... filters) {
        return getList(clazz, null, null, null, CompanySessionLib.getCompany(), null, filters);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, BaseCompany company, Container.Filter... filters) {
        return getList(clazz, null, null, null, company, null, filters);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, SortProperty sorts) {
        return getList(clazz, null, null, sorts, CompanySessionLib.getCompany(), null);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, SortProperty sorts, BaseCompany company) {
        return getList(clazz, null, null, sorts, company, null);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, EntityManager manager, Container.Filter... filters) {
        return getList(clazz, null, null, null, CompanySessionLib.getCompany(), manager, filters);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, BaseCompany company, EntityManager manager, Container.Filter... filters) {
        return getList(clazz, null, null, null, company, manager, filters);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value, EntityManager manager) {
        return getList(clazz, attr, value, null, CompanySessionLib.getCompany(), manager);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value, BaseCompany company, EntityManager manager) {
        return getList(clazz, attr, value, null, company, manager);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, SortProperty sorts, Container.Filter... filters) {
        return getList(clazz, null, null, sorts, CompanySessionLib.getCompany(), null, filters);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, SortProperty sorts, BaseCompany company, Container.Filter... filters) {
        return getList(clazz, null, null, sorts, company, null, filters);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, SortProperty sorts, EntityManager manager, Container.Filter... filters) {
        return getList(clazz, null, null, sorts, CompanySessionLib.getCompany(), manager, filters);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, SortProperty sorts, BaseCompany company, EntityManager manager, Container.Filter... filters) {
        return getList(clazz, null, null, sorts, company, manager, filters);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value, SortProperty sorts, EntityManager manager) {
        return getList(clazz, attr, value, sorts, CompanySessionLib.getCompany(), manager, (Container.Filter) null);
    }// end of method

    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value, SortProperty sorts, BaseCompany company, EntityManager manager) {
        return getList(clazz, attr, value, sorts, company, manager, (Container.Filter) null);
    }// end of method

    /**
     * Search for the entities of a given Entity class
     * Filtrate sul valore (eventuale) della property indicata
     * Filtrate sui filtri (eventuali) passati come parametro
     * Se manca sia la property sia i filtri, restituisce tutte le entities della Entity
     * Ordinate secondo il filtro
     * Usa l'EntityManager passato come parametro
     * Se il manager è nullo, costruisce al volo un manager standard (and close it)
     * Se il manager è valido, lo usa (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param attr    the searched attribute
     * @param value   the value to search for
     * @param sorts   ordinamento (ascendente o discendente)
     * @param company da filtrare
     * @param manager the EntityManager to use
     * @param filters an array of filters (you can use FilterFactory to build filters, or create them as Compare....)
     * @return a list of founded entities
     */
    public static List<? extends BaseEntity> getList(Class<? extends BaseEntity> clazz, SingularAttribute attr, Object value, SortProperty sorts, BaseCompany company, EntityManager manager, Container.Filter... filters) {
        ArrayList<BaseEntity> entities = new ArrayList<>();
        ArrayList<Container.Filter> filtroArray = null;
        Container.Filter filter;
        JPAContainer<BaseEntity> container;
        EntityItem<BaseEntity> item;

        // se non specificato l'EntityManager, ne crea uno locale
        boolean usaManagerLocale = false;
        if (manager == null) {
            usaManagerLocale = true;
            manager = EM.createEntityManager();
        }// end of if cycle

        // converte in un ArrayList per poter eventualmente aggiungere il vincolo della Property (SingularAttribute attr)
        if (filters != null && filters.length > 0 && filters[0] != null) {
            filtroArray = new ArrayList<>(Arrays.asList(filters));
        }// end of if cycle

        // aggiunge il vincolo della Property (SingularAttribute attr), trasformato in filtro
        if (attr != null) {
            filter = new Compare.Equal(attr.getName(), value);
            if (filtroArray == null) {
                filtroArray = new ArrayList<>();
            }// end of if cycle
            filtroArray.add(filter);
        }// end of if cycle

        // create a read-only JPA container for a given domain class (eventually sorted) and filters
        container = getContainer(clazz, sorts, company, manager, filtroArray);

        // costruisce la lista, spazzolando il container
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
     * Create a read-only JPA container for a given domain class and filters.
     * <p>
     * Ordinato secondo la SortProperty
     * Filtrato sulla azienda passata come parametro.
     * Se la company è nulla, cerca per tutte le companies
     * Il manager NON può essere nullo
     * Il manager DEVE essere valido (must be close by caller method)
     * Filtrate sui filtri (eventuali) passati come parametro
     *
     * @param clazz   the Entity class
     * @param sorts   ordinamento (ascendente o discendente)
     * @param company da filtrare
     * @param manager the EntityManager to use
     * @param filters an array of filters (you can use FilterFactory to build filters, or create them as Compare....)
     * @return the JPA container
     */
    @SuppressWarnings("unchecked")
    public static JPAContainer<BaseEntity> getContainer(Class<? extends BaseEntity> clazz, SortProperty sorts, BaseCompany company, EntityManager manager, ArrayList<Container.Filter> filters) {
        JPAContainer<BaseEntity> container = (JPAContainer<BaseEntity>) JPAContainerFactory.makeNonCachedReadOnly(clazz, manager);

        if (company != null) {
            Container.Filter filterCompany = new Compare.Equal(CompanyEntity_.company.getName(), company);
            container.addContainerFilter(filterCompany);
        }// end of if cycle

        if (filters != null) {
            for (Container.Filter filter : filters) {
                container.addContainerFilter(filter);
            }// end of for cycle
        }// end of if cycle

        if (sorts != null) {
            for (String stringa : sorts.getProperties()) {
                if (stringa.contains(".")) {
                    container.addNestedContainerProperty(stringa.substring(0, stringa.lastIndexOf(".")) + ".*");
                }// end of if cycle
            }// end of for cycle
            container.sort(sorts.getProperties(), sorts.getOrdinamenti());
        }// end of if cycle

        return container;
    }// end of method


    //------------------------------------------------------------------------------------------------------------------------
    // deprecated
    //------------------------------------------------------------------------------------------------------------------------


    /**
     * Search for all entities of the current company.
     * <p>
     *
     * @param clazz the entity class
     * @return a list of entities corresponding to the specified criteria
     * @deprecated
     */
    public static List<? extends CompanyEntity> queryList(Class<? extends CompanyEntity> clazz) {
        EntityManager manager = EM.createEntityManager();
        List<? extends CompanyEntity> entities = queryList(clazz, null, null, manager);
        manager.close();
        return entities;
    }

    /**
     * Search for all entities of the company.
     * Filtrato sulla azienda passata come parametro.
     * <p>
     *
     * @param clazz   the entity class
     * @param company azienda da filtrare
     * @return a list of entities corresponding to the specified criteria
     * @deprecated
     */
    public static List<? extends CompanyEntity> queryList(Class<? extends CompanyEntity> clazz, BaseCompany company) {
        EntityManager manager = EM.createEntityManager();
        List<? extends CompanyEntity> entities = queryList(clazz, null, null, manager, company);
        manager.close();
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
     * @deprecated
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<? extends CompanyEntity> queryList(
            Class<? extends CompanyEntity> clazz,
            SingularAttribute attr,
            Object value) {
        EntityManager manager = EM.createEntityManager();
        List<? extends CompanyEntity> entities = queryList(clazz, attr, value, manager);
        manager.close();

        return entities;
    }

    /**
     * Search for all entities with a specified attribute value.
     * Filtrato sulla azienda corrente.
     * <p>
     *
     * @param clazz   the entity class
     * @param attr    the searched attribute
     * @param value   the value to search for
     * @param manager the EntityManager to use
     * @return a list of entities corresponding to the specified criteria
     * @deprecated
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<? extends CompanyEntity> queryList(
            Class<? extends CompanyEntity> clazz,
            SingularAttribute attr,
            Object value,
            EntityManager manager) {

        return queryList(clazz, attr, value, manager, CompanySessionLib.getCompany());
    }// end of method

    /**
     * Search for all entities with a specified attribute value.
     * Filtrato sulla azienda passata come parametro.
     * <p>
     *
     * @param clazz   the entity class
     * @param attr    the searched attribute, null for no filter
     * @param value   the value to search for, null for no filter
     * @param manager the EntityManager to use
     * @param company azienda da filtrare
     * @return a list of entities corresponding to the specified criteria
     * @deprecated
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<? extends CompanyEntity> queryList(
            Class<? extends CompanyEntity> clazz,
            SingularAttribute attr,
            Object value,
            EntityManager manager,
            BaseCompany company) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<? extends BaseEntity> cq = cb.createQuery(clazz);
        Root<CompanyEntity> root = (Root<CompanyEntity>) cq.from(clazz);

        Predicate pred;
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (attr != null && value != null) {
            pred = cb.equal(root.get(attr), value);
            predicates.add(pred);
        }

        pred = cb.equal(root.get(CompanyEntity_.company), company);
        predicates.add(pred);

        cq.where(predicates.toArray(new Predicate[]{}));

        TypedQuery<? extends BaseEntity> query = manager.createQuery(cq);
        List<CompanyEntity> entities = (List<CompanyEntity>) query.getResultList();

        return entities;
    }// end of method

    /**
     * Search for the only entity with a specified attribute value.
     * Filtrato sulla azienda corrente.
     * Crea un EntityManager al volo
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
    public static BaseEntity queryOne(
            Class<? extends CompanyEntity> clazz,
            SingularAttribute attr,
            Object value) {
        EntityManager manager = EM.createEntityManager();
        BaseEntity bean = queryOne(clazz, attr, value, manager);
        manager.close();

        return bean;
    }// end of method

    /**
     * Search for the only entity with a specified attribute value.
     * Filtrato sulla azienda corrente.
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
    public static BaseEntity queryOne(
            Class<? extends CompanyEntity> clazz,
            SingularAttribute attr,
            Object value,
            EntityManager manager) {

        return queryOne(clazz, attr, value, manager, CompanySessionLib.getCompany());
    }// end of method

    /**
     * Search for the only entity with a specified attribute value.
     * Filtrato sulla azienda passata come parametro.
     * Crea un EntityManager al volo
     * <p>
     * If multiple entities exist, null is returned.
     *
     * @param clazz   the entity class
     * @param attr    the searched attribute
     * @param value   the value to search for
     * @param company azienda da filtrare
     * @return the only entity corresponding to the specified criteria, or null
     * @deprecated
     */
    @SuppressWarnings({"rawtypes"})
    public static BaseEntity queryOne(
            Class<? extends CompanyEntity> clazz,
            SingularAttribute attr,
            Object value,
            BaseCompany company) {
        EntityManager manager = EM.createEntityManager();
        BaseEntity bean = queryOne(clazz, attr, value, manager, company);
        manager.close();
        return bean;
    }// end of method

    /**
     * Search for the only entity with a specified attribute value.
     * Filtrato sulla azienda passata come parametro.
     * <p>
     * If multiple entities exist, null is returned.
     *
     * @param clazz   the entity class
     * @param attr    the searched attribute
     * @param value   the value to search for
     * @param manager the EntityManager to use
     * @param company azienda da filtrare
     * @return the only entity corresponding to the specified criteria, or null
     * @deprecated
     */
    @SuppressWarnings({"rawtypes"})
    public static BaseEntity queryOne(
            Class<? extends CompanyEntity> clazz,
            SingularAttribute attr,
            Object value,
            EntityManager manager,
            BaseCompany company) {
        BaseEntity bean = null;

        List<? extends CompanyEntity> entities = queryList(clazz, attr, value, manager, company);
        if (entities != null && entities.size() == 1) {
            bean = entities.get(0);
        }
        return bean;
    }// end of method

    /**
     * Search for the first entities with a specified attribute value.
     * <p>
     *
     * @param clazz the entity class
     * @param attr  the searched attribute
     * @param value the value to search for
     * @return the first entity corresponding to the specified criteria
     * @deprecated
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
     * @deprecated
     */
    @SuppressWarnings("unchecked")
    public static List<? extends CompanyEntity> getListOld(Class<? extends CompanyEntity> clazz) {
        return getListOld(clazz, null);
    }// end of method

    /**
     * Return a list of entities for a given domain class and filters.
     * <p>
     *
     * @param entityClass - the entity class
     * @param filters     - an array of filters (you can use FilterFactory
     *                    to build filters, or create them as Compare....)
     * @return the list with the entities found
     * @deprecated
     */
    public static List<? extends CompanyEntity> getListOld(Class<? extends CompanyEntity> entityClass, Filter... filters) {
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
            CompanyEntity entity = (CompanyEntity) container.getEntity(id);
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
     * @deprecated
     */
    public static CompanyEntity getEntity(Class<? extends CompanyEntity> entityClass, Filter... filters) {
        CompanyEntity entity = null;
        List<? extends CompanyEntity> list = getListOld(entityClass, filters);
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



    /**
     * Ritorna il numero di record della entity specificata
     * Filtrato sulla azienda corrente.
     *
     * @param clazz the Entity class
     * @return il numero di record
     * @deprecated -> return long, use count() instead
     */
    public static long getCount(Class<? extends CompanyEntity> clazz) {
        return getCount(clazz, CompanySessionLib.getCompany(), null);
    }// end of static method


    /**
     * Ritorna il numero di record della entity specificata
     * Filtrato sulla azienda corrente.
     * Use a specific manager (must be close by caller method)
     *
     * @param clazz   the Entity class
     * @param manager the EntityManager to use
     * @return il numero di record
     * @deprecated -> return long, use count() instead
     */
    public static long getCount(Class<? extends CompanyEntity> clazz, EntityManager manager) {
        return getCount(clazz, CompanySessionLib.getCompany(), manager);
    }// end of static method

    /**
     * Ritorna il numero di record della entity specificata
     * Filtrato sulla azienda passata come parametro.
     * Se la company è nulla, restituisce il numero di TUTTI i records
     *
     * @param clazz   the Entity class
     * @param company azienda da filtrare
     * @return il numero di record
     * @deprecated -> return long, use count() instead
     */
    public static long getCount(Class<? extends CompanyEntity> clazz, BaseCompany company) {
        return getCount(clazz, company, null);
    }// end of static method


    /**
     * Ritorna il numero di record della entity specificata
     * Filtrato sulla azienda passata come parametro.
     * Se il manager è nullo, costruisce al volo un manager standard (and close it)
     * Se il manager è valido, lo usa (must be close by caller method)
     * Se la company è nulla, restituisce il numero di TUTTI i records
     *
     * @param clazz   the Entity class
     * @param company azienda da filtrare
     * @param manager the EntityManager to use
     * @return il numero di record
     * @deprecated -> return long, use count() instead
     */
    @SuppressWarnings({"unchecked"})
    public static long getCount(Class<? extends CompanyEntity> clazz, BaseCompany company, EntityManager manager) {
        boolean usaManagerLocale = false;
        if (manager == null) {
            usaManagerLocale = true;
            manager = EM.createEntityManager();
        }// end of if cycle

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<CompanyEntity> root = (Root<CompanyEntity>) cq.from(clazz);

        if (company != null) {
            Predicate predicate = cb.equal(root.get(CompanyEntity_.company), company);
            cq.where(predicate);
        }// end of if cycle

        CriteriaQuery<Long> select = cq.select(cb.count(root));
        TypedQuery<Long> typedQuery = manager.createQuery(select);
        Long count = typedQuery.getSingleResult();
        if (count == null) {
            count = 0l;
        }// end of if cycle

        if (usaManagerLocale) {
            manager.close();
        }// end of if cycle

        return count;
    }// end of static method

}// end of abstract class

