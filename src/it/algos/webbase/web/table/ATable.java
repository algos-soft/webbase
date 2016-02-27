package it.algos.webbase.web.table;

import com.google.common.collect.Iterables;
import com.google.common.primitives.Primitives;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Not;
import com.vaadin.data.util.filter.Or;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.Table;
import it.algos.webbase.web.converter.StringToBigDecimalConverter;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.BaseEntity_;
import it.algos.webbase.web.entity.Entities;
import it.algos.webbase.web.entity.SortProperties;
import it.algos.webbase.web.lib.LibFilter;
import it.algos.webbase.web.query.AQuery;
import org.eclipse.persistence.internal.jpa.metamodel.SingularAttributeImpl;
import org.vaadin.addons.lazyquerycontainer.CompositeItem;
import org.vaadin.addons.lazyquerycontainer.LazyEntityContainer;
import org.vaadin.addons.lazyquerycontainer.NestingBeanItem;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base Table to list BaseEntity(es)
 */
public abstract class ATable extends Table {

    private final static Logger logger = Logger.getLogger(ATable.class.getName());
    protected Action actionEdit = new Action("Modifica", FontAwesome.PENCIL);
    protected Action actionDelete = new Action("Elimina", FontAwesome.TRASH_O);
    protected ArrayList<TotalizableColumn> totalizableColumns = new ArrayList();
    private EntityManager entityManager;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Class<?> entityClass;
    private ArrayList<TableListener> listeners = new ArrayList();
    private ArrayList<SelectionChangeListener> selectionChangeListeners = new ArrayList<>();


    /**
     * Creates a new table for a given module.
     *
     * @param entityClass   the Entity class
     * @param entityManager the Entity Manager
     */
    public ATable(Class<? extends BaseEntity> entityClass, EntityManager entityManager) {
        super();
        this.entityClass = entityClass;
        this.entityManager = entityManager;
//        init();
    }


    /**
     * Initializes the table.
     * Must be called from the costructor in each subclass
     */
    protected void init() {

        // setup the container
        Container container = createContainer();
        setContainerDataSource(container);
        addPropertiesToContainer();
        sortContainer();


        // adds a listener for data change to the container
        // (if supported by the container)
        Container cont = getContainerDataSource();
        if (cont != null && cont instanceof ItemSetChangeNotifier) {
            ItemSetChangeNotifier cNotifier = (ItemSetChangeNotifier) cont;

            cNotifier.addItemSetChangeListener(new ItemSetChangeListener() {

                @Override
                public void containerItemSetChange(Container.ItemSetChangeEvent event) {

                    updateTotals();

                    // fire table data changed
                    fire(TableEvent.datachange);

                }
            });

        }

        // adds a listener for mouse click to the table
        addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                getTable().itemClick(itemClickEvent);
            }
        });

        // adds a listener for user selection changes
        addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Property prop = event.getProperty();
                Set<Long> rows = (Set<Long>) prop.getValue();
                SelectionChangeEvent e = new SelectionChangeEvent(rows);
                fireSelectionChanged(e);
                selectionChanged(e);
            }
        });

        // create additional columns
        createAdditionalColumns();

        // set the visible columns
        setColumnVisibility();

        setAlignments();
        setSelectable(true);
        setMultiSelect(true);
        setMultiSelectMode(MultiSelectMode.DEFAULT);
        setEditable(false);
        setImmediate(true);
        setColumnCollapsingAllowed(true);
        setColumnReorderingAllowed(true);

        // setFooterVisible(true);

        // contextual click handlers
        addActionHandler(new Action.Handler() {

            public Action[] getActions(Object target, Object sender) {
                return getTable().getActions(target, sender);
            }

            public void handleAction(Action action, Object sender, Object target) {
                getTable().handleAction(action, sender, target);
            }
        });

        // fire table created
        fire(TableEvent.created);

    }

    /**
     * Return the Actions to display in contextual menu
     */
    protected Action[] getActions(Object target, Object sender) {
        Action[] actions = null;
        actions = new Action[2];
        actions[0] = actionEdit;
        actions[1] = actionDelete;
        return actions;
    }

    /**
     * Handle the Action
     */
    protected void handleAction(Action action, Object sender, Object target) {
    }


    /**
     * Action when a user click the table
     * Must be overridden on the subclass
     *
     * @param itemClickEvent the event
     */
    public void itemClick(ItemClickEvent itemClickEvent) {
    }


    /**
     * Recupera l'istanza di una riga
     *
     * @param itemId della riga
     */
    protected BaseEntity getBean(Object itemId) {
        BaseEntity bean;
        Container cont = this.getContainerDataSource();
        CompositeItem item = (CompositeItem) cont.getItem(itemId);
        NestingBeanItem beanItem = (NestingBeanItem) item.getItem("bean");
        bean = (BaseEntity) beanItem.getBean();
        return bean;
    }// end of method


    /**
     * Recupera l'istanza della riga selezionata col click
     *
     * @param itemClickEvent the event
     */
    protected BaseEntity getBean(ItemClickEvent itemClickEvent) {
        BaseEntity bean = null;
        Object oby = itemClickEvent.getItemId();
        Object oggetto = getContainerDataSource().getItem(oby);

        if (oggetto instanceof CompositeItem) {
            CompositeItem itemComp = (CompositeItem) oggetto;
            BeanItem beanItem = (BeanItem) itemComp.getItem("bean");
            bean = (BaseEntity) beanItem.getBean();
        }// fine del blocco if

        return bean;
    }// end of method


    /**
     * Recupera l'istanza della riga selezionata se il click è nella colonna indicata
     *
     * @param itemClickEvent the event
     * @param column         colonna 'sensibile'
     */
    protected BaseEntity getBeanClickOnColumn(ItemClickEvent itemClickEvent, String column) {
        BaseEntity bean = null;
        String titoloColonna = itemClickEvent.getPropertyId().toString();

        if (titoloColonna.equals(column)) {
            bean = getBean(itemClickEvent);
        }// fine del blocco if

        return bean;
    }// end of method

    /**
     * Invoked when the user selection changes
     */
    protected void selectionChanged(SelectionChangeEvent e) {
    }

    /**
     * Called when the component gets attached to the UI
     */
    @Override
    public void attach() {
        super.attach();

        // refresh the table (underlying data might have changed)
        refresh();

        // the first time is called when the table gets attached,
        // subsequently is called by the data change listener
        updateTotals();

        // fire table attached to UI
        fire(TableEvent.attached);

        // fire table data changed
        fire(TableEvent.datachange);

    }


    /**
     * Creates the container
     * <p>
     *
     * @return the container
     */
    public abstract Container createContainer();

    /**
     * Returns the paging size for the container.
     * Warning: above the size of 1.000 you start to get errors!!
     *
     * @return the container's paging size
     * vaadin.com/forum/#!/thread/186858/186857
     */
    protected int getContainerPageSize() {
        return 1000;
    }


    /**
     * Add the properties to the container.
     * By default, all the properties from the Entity class are added.
     * If a property whith the same name is already present it is not added again.
     */
    protected void addPropertiesToContainer() {
        Container cont = getContainerDataSource();
        Entities.addPropertiesToContainer(cont, getEntityClass());
    }


    /**
     * Sorts the container.
     * By default the container is sorted based on the default sort order declared
     * in the entity class via the @DefaultSort annotation.
     * If the annotation is not present the container is not sorted.
     * <p>
     * For a custom sort of the container in a RelatedCombo field you have 2 options:
     * 1) call the sort() method after the creation of the object passing the properties on which to sort
     * 2) override this method (needs subclassing).
     */
    protected void sortContainer() {
        Container cont = getContainerDataSource();
        if (cont instanceof com.vaadin.data.Container.Sortable) {
            com.vaadin.data.Container.Sortable csortable = (com.vaadin.data.Container.Sortable) cont;

            // retrieve the default sort properties from the class by annotation
            SortProperties props = BaseEntity.getSortProperties(getEntityClass());

            // sort the container on the sort properties
            if (!props.isEmpty()) {
                csortable.sort(props.getProperties(), props.getDirections());
            } else {
                String sortField = BaseEntity_.id.getName();
                csortable.sort(new String[]{sortField}, new boolean[]{true});
            }

        }
    }


    /**
     * Create additional columns
     * (add generated columns, nested properties...)
     * <p>
     * Override in the subclass
     */
    protected void createAdditionalColumns() {
    }

    /**
     * Sets the visibility of the columns
     */
    protected void setColumnVisibility() {

        // define the visible columns
        Object[] columns = getDisplayColumns();

        // if no visible columns, add the id column
        if (columns == null) {
            columns = new Object[]{BaseEntity_.id};
        }

        ArrayList<String> cNames = new ArrayList();
        for (Object obj : columns) {
            String cName = "";
            if (obj instanceof Attribute) {
                cNames.add(((Attribute<?, ?>) obj).getName());
            }
            if (obj instanceof String) {
                cNames.add((String) obj);
            }
            if (!cName.equals("")) {
                cNames.add(cName);
            }
        }

        Object[] outNames = cNames.toArray(new String[0]);
        this.setVisibleColumns(outNames);

    }

    /**
     * Set the default alignments based on item class
     */
    protected void setAlignments() {
        Object[] columns = getVisibleColumns();
        for (Object id : columns) {
            if (id instanceof String) {
                String sid = (String) id;
                Table.Align align = getAlignment(sid);
                if (align != null) {
                    setColumnAlignment(id, align);
                }
            }
        }
    }

    /**
     * Sets the column header for the specified column;
     *
     * @param propertyId the propertyId identifying the column.
     * @param header     the header to set.
     */
    public void setColumnHeader(Object propertyId, String header) {
        if (propertyId instanceof Attribute) {
            propertyId = ((Attribute<?, ?>) propertyId).getName();
        }
        super.setColumnHeader(propertyId, header);
    }

    @Override
    public void setColumnAlignment(Object propertyId, Align alignment) {
        if (propertyId instanceof Attribute) {
            propertyId = ((Attribute<?, ?>) propertyId).getName();
        }
        super.setColumnAlignment(propertyId, alignment);
    }

    @Override
    public void setColumnWidth(Object propertyId, int width) {
        if (propertyId instanceof Attribute) {
            propertyId = ((Attribute<?, ?>) propertyId).getName();
        }
        super.setColumnWidth(propertyId, width);
    }


    /**
     * Adds/removes a column to the list of totalizable columns
     * <p>
     *
     * @param propertyId    - the id of the column
     * @param useTotals     - to add or remove the column from the list
     * @param decimalPlaces - the number of decimal places, -1 for autodetect
     */
    public void setColumnUseTotals(Object propertyId, boolean useTotals, int decimalPlaces) {

        TotalizableColumn tcol = new TotalizableColumn(propertyId, decimalPlaces);

        if (useTotals) {
            if (!totalizableColumns.contains(tcol)) {
                totalizableColumns.add(tcol);
            }
        } else {
            totalizableColumns.remove(tcol);
        }

        // the first call with useTotals=true activates automatically the footer
        if (useTotals) {
            setFooterVisible(true);
        }
    }

    /**
     * Adds/removes a column to the list of totalizable columns<br>
     * with automatic number of decimal places
     * <p>
     *
     * @param propertyId - the id of the column
     * @param useTotals  - to add or remove the column from the list
     */
    public void setColumnUseTotals(Object propertyId, boolean useTotals) {
        setColumnUseTotals(propertyId, useTotals, -1);
    }


    /**
     * Returns an array of the visible columns ids. Ids might be of type String
     * or Attribute. This base implementations returns all the columns (no
     * order) Override for a custom implementation.
     *
     * @return the list
     */
    @SuppressWarnings("rawtypes")
    protected Object[] getDisplayColumns() {
//        Attribute[] fieldList = new Attribute[0];
//        if (modulo != null) {
//            fieldList = modulo.getFieldsList();
//        }
//        return fieldList;
        return null;
    }

    /**
     * Returns the alignment for a given column.
     *
     * @param columnId the column id
     * @return the alignment
     */
    protected Table.Align getAlignment(String columnId) {

        Table.Align align = Table.Align.LEFT;

        if (this.entityClass != null) {
            java.lang.reflect.Field field;
            try {
                field = getEntityClass().getDeclaredField(columnId);
                if (field != null) {
                    Class<?> clazz = field.getType();
                    clazz = Primitives.wrap(clazz);

                    if ((Boolean.class).isAssignableFrom(clazz)) {
                        align = Table.Align.CENTER;
                    }

                    if ((Number.class).isAssignableFrom(clazz)) {
                        align = Table.Align.RIGHT;
                    }

                }

            } catch (NoSuchFieldException | SecurityException e) {
            }
        }

        return align;

    }


    /**
     * Returns the ids of the single selected row
     * <p>
     * Usable for single-select or multi-select tables
     *
     * @return the selected row id (if a single row is selected, otherwise 0)
     */
    public long getSelectedKey() {
        long selectedId = 0;
        Object ids = getValue();
        if (ids != null) {

            // if multi select is enabled
            if (ids instanceof Collection) {
                Collection<Long> cIds = (Collection<Long>) ids;
                if (cIds.size() == 1) {
                    selectedId = Iterables.get(cIds, 0);
                }
            }

            // if multi select is disabled
            if (ids instanceof Long) {
                selectedId = (Long) ids;
            }

        }

        return selectedId;
    }

    /**
     * Returns the ids of the selected rows
     * <p>
     * Usable for single-select or multi-select tables
     *
     * @return the selected row ids, empty array if no selection
     */
    public Object[] getSelectedIds() {
        Object[] selected = new Object[0];

        Object ids = getValue();
        if (ids != null) {

            // if multi select is enabled
            if (ids instanceof Collection) {
                Collection<?> cIds = (Collection<?>) ids;
                selected = new Object[cIds.size()];
                int idx = 0;
                for (Object id : cIds) {
                    selected[idx] = id;
                    idx++;
                }
            } else {
                selected = new Object[1];
                selected[0] = ids;
            }

        }

        return selected;
    }


    /**
     * Returns the id of the single selected row
     * <p>
     * Usable for single-select or multi-select tables
     *
     * @return the selected row id (if a single row is selected, otherwise null)
     */
    public Object getSelectedId() {
        Object id = null;
        Object[] ids = getSelectedIds();
        if (ids.length == 1) {
            id = ids[0];
        }
        return id;
    }

    /**
     * Controlla se è selezionata una ed una sola riga
     *
     * @return vero se è selezionata una riga
     * falso se nessuna riga è selezionata
     * falso se sono selezionate due o più righe
     **/
    public boolean isSingleRowSelected() {
        Long idKey = this.getSelectedKey();
        return (idKey != null && idKey > 0);
    }


    /**
     * Return the selected entity.
     *
     * @return the selected entity, if one and only one entity if selected.
     * otherwise, null is returned.
     */
    public BaseEntity getSelectedEntity() {
        BaseEntity entity = null;
        BaseEntity[] entities = getSelectedEntities();
        if (entities.length == 1) {
            entity = entities[0];
        }
        return entity;
    }


    /**
     * Return the selected entities (multiple selection).
     *
     * @return an array containing the selected entities,
     * empty array if no entities are selected
     */
    public BaseEntity[] getSelectedEntities() {
        BaseEntity[] entities = new BaseEntity[0];
        Object[] ids = getSelectedIds();
        if (ids != null) {
            ArrayList<BaseEntity> objSel = new ArrayList();
            for (Object id : ids) {
                BaseEntity entity = getEntity((Long) id);
                if (entity != null) {
                    objSel.add(entity);
                }
            }
            entities = objSel.toArray(new BaseEntity[0]);
        }

        return entities;

    }


    /**
     * Returns the entity given a row id.
     *
     * @param rowId the row id
     * @return the entity
     */
    public BaseEntity getEntity(Object rowId) {
        BaseEntity entity = null;
        Container cont = getContainerDataSource();

        if (cont instanceof LazyEntityContainer) {
            LazyEntityContainer lec = (LazyEntityContainer) cont;
            entity = (BaseEntity) lec.getEntity(rowId);
        }

        if (cont instanceof JPAContainer) {
            Item item = getItem(rowId);
            if (item != null) {
                if (item instanceof JPAContainerItem) {
                    JPAContainerItem<?> jpaItem = (JPAContainerItem<?>) item;
                    entity = (BaseEntity) jpaItem.getEntity();
                }
            }
        }

        return entity;
    }


    /**
     * Refreshes the underlying container from the database
     */
    public void refresh() {
        Container cont = getContainerDataSource();
        if (cont != null) {
            // refresh() is not in any interface, so we have
            // to cast to any specific classes
            if (cont instanceof JPAContainer) {
                JPAContainer jpac = (JPAContainer) cont;
                jpac.refresh();
            }
            if (cont instanceof LazyEntityContainer) {
                LazyEntityContainer lec = (LazyEntityContainer) cont;
                lec.refresh();
            }
        }
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }


    /**
     * Returns the container as a Filterable container.
     *
     * @return the container as a Filterable, or null if it is not filterable
     */
    public Filterable getFilterableContainer() {
        Filterable filterable = null;
        Container cont = getContainerDataSource();
        if (cont != null && cont instanceof Filterable) {
            filterable = (Filterable) cont;
        }
        return filterable;
    }

    /**
     * Returns the container as a Sortable container.
     *
     * @return the container as a Sortable, or null if it is not sortable
     */
    public Sortable getSortableContainer() {
        Sortable sortable = null;
        Container cont = getContainerDataSource();
        if (cont != null && cont instanceof Sortable) {
            sortable = (Sortable) cont;
        }
        return sortable;
    }


    @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property<?> property) {
        String string = null;
        Object value;

        // Format for Dates
        if (property.getType() == Date.class) {
            value = property.getValue();
            if (value != null && value instanceof Date) {
                Date date = (Date) value;
                try {
                    string = this.dateFormat.format(date);
                } catch (Exception e) {
                    logger.log(Level.WARNING, "unable to format date: " + date);
                }
            }

        }

        // Format for Booleans
        if (property.getType() == Boolean.class) {
            string = "";
            value = property.getValue();

            if (value != null && value instanceof Boolean) {
                if ((boolean) value) {
                    string = "\u2714";
                }
            }

        }

        // none of the above
        if (string == null) {
            string = super.formatPropertyValue(rowId, colId, property);
        }

        return string;
    }


    /**
     * Updates the totals in the footer
     * <p>
     * Called when the container data changes
     */
    @SuppressWarnings("rawtypes")
    public void updateTotals() {

        // cycle the totalizable columns
        StringToBigDecimalConverter converter = new StringToBigDecimalConverter();
        for (TotalizableColumn column : totalizableColumns) {
            Object propertyId = column.getPropertyId();
            BigDecimal bd = getTotalForColumn(propertyId);
            int places = column.getDecimalPlaces();
            converter.setDecimalPlaces(places);
            String sTotal = converter.convertToPresentation(bd);
            setColumnFooter(propertyId, sTotal);
        }


    }


    /**
     * Calculates the total for a particular (numeric) column in the Container.
     * In this default implementation, totalizes the attribute corresponding to the propertyId
     * using the current filter.
     *
     * @param propertyId the property id
     * @return the total for the column
     */
    protected BigDecimal getTotalForColumn(Object propertyId) {
        BigDecimal bd = new BigDecimal(0);
        String sId = propertyId.toString();
        Attribute attr = getAttributeByName(sId);
        if (attr instanceof SingularAttribute) {
            SingularAttribute sAttr = (SingularAttribute) attr;
            bd = calcTotal(sAttr);
        }
        return bd;
    }


    /**
     * @param name the name of the attribute
     * @return the Attribute from the metamodel
     */
    private Attribute getAttributeByName(String name) {
        Metamodel metamodel = getEntityManager().getMetamodel();
        Set<EntityType<?>> entities = metamodel.getEntities();
        EntityType<?> entityType = null;
        for (EntityType<?> eType : entities) {
            Class<?> type = eType.getJavaType();
            if (type.equals(getEntityClass())) {
                entityType = eType;
                break;
            }
        }

        Attribute attr = null;
        if (entityType != null) {
            attr = entityType.getAttribute(name);
        }

        return attr;
    }


    /**
     * Calculate the total for a single column.
     *
     * @param attr the attribute
     * @return the total for the currently displayed rows
     */
    private BigDecimal calcTotal(SingularAttribute attr) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root root = cq.from(getEntityClass());

        Predicate pred = getFiltersPredicate(cb, cq, root);
        if (pred != null) {
            cq.where(pred);
        }

        Expression<java.lang.Float> expr = root.get(attr);
        Expression<java.lang.Double> e1 = cb.sumAsDouble(expr);
        cq.select(e1);

        Double num = getEntityManager().createQuery(cq).getSingleResult();

        BigDecimal retBd = new BigDecimal(0);
        if (num != null) {
            retBd = new BigDecimal(num);
        }
        return retBd;

    }

    /**
     * Returns a CriteriaQuery Predicate equivalent
     * to the condition expressed by the current Container Filters.
     *
     * @param cb   the CriteriaBuilder
     * @param cq   the CriteriaQuery
     * @param root the Root
     * @return the Predicate, or null if no filters are present
     */
    protected Predicate getFiltersPredicate(final CriteriaBuilder cb, final CriteriaQuery<?> cq, final Root<?> root) {
        Predicate pred = null;

        // retrieve an array of the current Container.Filter(s)
        Collection<Filter> filters = getContainerFilters();

        // do only if there are filters
        if (filters != null && filters.size() > 0) {

            // create a single Filter
            Filter singleFilter;
            if (filters.size() == 1) {
                singleFilter = Iterables.get(filters, 0);
            } else {
                Filter[] aFilters = filters.toArray(new Filter[filters.size()]);
                singleFilter = new And(aFilters);
            }



            // create the Predicate
            pred = LibFilter.getPredicate(singleFilter, cb, cq, root);

        }

        return pred;

    }


    /**
     * Return a collection of the current container filters.
     * Empty collection if no filters.
     * @return the current filters
     */
    public Collection<Filter> getContainerFilters(){

        Collection<Filter> filters = new ArrayList<Filter>();

        // retrieve an array of the current Container.Filter(s)
        Container cont = getContainerDataSource();
        if (cont instanceof LazyEntityContainer) {
            LazyEntityContainer lec = (LazyEntityContainer) cont;
            filters = lec.getContainerFilters();
        }
        if (cont instanceof JPAContainer) {
            JPAContainer jpac = (JPAContainer) cont;
            filters = jpac.getFilters();
        }

        return filters;

    }



    /**
     * Add a form listener to the form
     */
    public void addTableListener(TableListener listener) {
        listeners.add(listener);
    }

    protected void fire(TableEvent event) {
        for (TableListener l : listeners) {
            switch (event) {
                case created:
                    l.created_();
                    break;
                case attached:
                    l.attached_();
                    break;
                case datachange:
                    l.datachange_();
                    break;
                default:
                    break;
            }// end of switch cycle
        }
    }


    /**
     * Total number of rows in the table's domain database
     */
    public long getTotalRows() {
        Class<?> clazz = getEntityClass();
        return AQuery.getCount(clazz);
    }


    /**
     * Number of rows currently available in the table's container
     */
    public int getVisibleRows() {
        return getContainerDataSource().size();
    }

    /**
     * Deselects all the rows in the table
     */
    public void deselectAll() {
        setValue(null);
    }

    /**
     * Removes the selected rows from the table
     */
    public void removeSelected() {
        Container cont = getTable().getContainerDataSource();
        if (cont != null && cont instanceof Container.Filterable) {
            Container.Filterable cFilterable = (Container.Filterable) cont;
            Filter filter = new Not(createFilterForSelectedRows());
            cFilterable.addContainerFilter(filter);
            refresh();
        }
    }

    /**
     * Shows in the table only the selected rows
     */
    public void selectedOnly() {
        Container cont = getTable().getContainerDataSource();
        if (cont != null && cont instanceof Container.Filterable) {
            Container.Filterable cFilterable = (Container.Filterable) cont;
            Filter filter = createFilterForSelectedRows();
            cFilterable.removeAllContainerFilters();
            cFilterable.addContainerFilter(filter);
            refresh();
        }

    }


    /**
     * Displays all the records in the table
     */
    public void showAll() {
        Container cont = getContainerDataSource();
        if (cont != null && cont instanceof Container.Filterable) {
            Container.Filterable cFilterable = (Container.Filterable) cont;
            cFilterable.removeAllContainerFilters();
            refresh();
        }
    }


    /**
     * Creates a filter corresponding to the currently selected rows in the table
     * <p>
     */
    public Filter createFilterForSelectedRows() {
        Filter filter = null;
        Object[] ids = getSelectedIds();
        if (ids.length > 0) {
            Filter[] filters = new Filter[ids.length];
            int idx = 0;
            for (Object id : ids) {
                String propertyId = BaseEntity_.id.getName();
                filters[idx] = new Compare.Equal(propertyId, id);
                idx++;
            }

            if (filters.length > 1) {
                filter = new Or(filters);
            } else {
                filter = filters[0];
            }
        }
        return filter;
    }


    public void addSelectionChangeListener(SelectionChangeListener l) {
        selectionChangeListeners.add(l);
    }

    private void fireSelectionChanged(SelectionChangeEvent e) {
        for (SelectionChangeListener l : selectionChangeListeners) {
            l.selectionChanged(e);
        }
    }

    protected ATable getTable() {
        return this;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }


    /**
     * Enum di eventi previsti.
     */
    public enum TableEvent {
        created, attached, datachange
    }

    public interface SelectionChangeListener {
        void selectionChanged(SelectionChangeEvent e);
    }

    /**
     * Table high-level events
     */
    public interface TableListener {
        void created_(); // table created

        void attached_(); // table attached to UI

        void datachange_(); // table data changed
    }

    public class SelectionChangeEvent extends EventObject {
        private Set<Long> rows;

        public SelectionChangeEvent(Set<Long> rows) {
            super(getTable());
            this.rows = rows;
        }

        public boolean isSingleRowSelected() {
            return rows.size() == 1;
        }

        public boolean isMultipleRowsSelected() {
            return rows.size() >= 1;
        }

        public Set<Long> getSelectedRowIds() {
            return rows;
        }
    }

    /**
     * Wrapper for a totalizable column info
     */
    public class TotalizableColumn {
        private SingularAttribute attribute;
        private Object propertyId;
        private int decimalPlaces;

        /**
         * Constructor with container property id.
         *
         * @param propertyId    the property id
         * @param decimalPlaces the number of decimal digits to display (-1 for auto)
         */
        public TotalizableColumn(Object propertyId, int decimalPlaces) {
            super();

            // if is an Attribute register it and after this, resolve to the name
            if (propertyId instanceof SingularAttribute) {
                this.attribute = (SingularAttribute) propertyId;
                propertyId = attribute.getName();
            }

            this.propertyId = propertyId;
            this.decimalPlaces = decimalPlaces;
        }

        public SingularAttribute getAttribute() {
            return attribute;
        }

        public Object getPropertyId() {
            return propertyId;
        }// end of inner method

        /**
         * Returns the number of decimal digits to display for this column.
         * If set to auto, the cholice is based on the column class.
         *
         * @return the number of decimal digits
         */
        public int getDecimalPlaces() {
            int places = decimalPlaces;
            if (decimalPlaces == -1) { // auto
                places = getDefaultDecimalPlacesForColumn(propertyId);
            }
            return places;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TotalizableColumn that = (TotalizableColumn) o;

            return !(propertyId != null ? !propertyId.equals(that.propertyId) : that.propertyId != null);

        }


        /**
         * Returns a default number of decimal places for a given property.<br>
         * 0 for integers (int, long), 2 for decimals (double, float, BigDecimal)
         * <p>
         *
         * @param propertyId the property id
         * @return the number of decimal places
         */
        protected int getDefaultDecimalPlacesForColumn(Object propertyId) {
            Class<?> clazz = getContainerDataSource().getType(propertyId);
            if (clazz != null) {
                if (clazz.equals(Integer.class)) {
                    return 0;
                }
                if (clazz.equals(int.class)) {
                    return 0;
                }
                if (clazz.equals(Long.class)) {
                    return 0;
                }
                if (clazz.equals(long.class)) {
                    return 0;
                }
                if (clazz.equals(BigInteger.class)) {
                    return 0;
                }
                if (clazz.equals(Double.class)) {
                    return 2;
                }
                if (clazz.equals(double.class)) {
                    return 2;
                }
                if (clazz.equals(Float.class)) {
                    return 2;
                }
                if (clazz.equals(float.class)) {
                    return 2;
                }
                if (clazz.equals(BigDecimal.class)) {
                    return 2;
                }
            }

            return 0;
        }

    }

}
