package it.algos.webbase.web.table;

import com.google.common.collect.Iterables;
import com.google.common.primitives.Primitives;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Container;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.And;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.Table;
import it.algos.webbase.web.converter.StringToBigDecimalConverter;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.BaseEntity_;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.entity.SortProperties;
import it.algos.webbase.web.lib.LibFilter;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.query.AQuery;
import org.vaadin.addons.lazyquerycontainer.LazyEntityContainer;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import javax.swing.event.ListSelectionEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("serial")
public class ATable extends Table implements ListSelection {

    protected ModulePop modulo;
    protected EntityManager entityManager;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Class<?> entityClass;
    protected Action actionEdit = new Action("Modifica", FontAwesome.PENCIL);
    protected Action actionDelete = new Action("Elimina", FontAwesome.TRASH_O);
    protected ArrayList<TotalizableColumn> totalizableColumns = new ArrayList<TotalizableColumn>();
    private ArrayList<TableListener> listeners = new ArrayList<TableListener>();

    /**
     * Si registrano qui i listeners (istanze di classi che sono interessate all'evento)
     */
    private ArrayList<ListSelectionListener> selectionListeners = new ArrayList<>();


    /**
     * Creates a new table for a given Entity class.
     *
     * @param entityClass the Entity class
     */
    public ATable(Class<?> entityClass) {
        super();
        this.entityClass = entityClass;
        init();
    }// end of constructor

    /**
     * Creates a new table for a given module.
     *
     * @param module the Module
     */
    public ATable(ModulePop module) {
        super();
        this.modulo = module;
        this.entityClass = module.getEntityClass();
        init();
    }// end of constructor

    protected void init() {

        //create and register the EntityManager
        entityManager = EM.createEntityManager();

        // create and set the container - read-only and cached
        // all the columns are added automatically to the table
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

                }// end of inner method
            });// end of anonymous inner class

        }


        // adds a listener for mouse click to the table
        this.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                getTable().itemClick(itemClickEvent);
                selectionChanged(itemClickEvent);
            }// end of inner method
        });// end of anonymous inner class

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
        addActionHandler(new Action.Handler() {
            public Action[] getActions(Object target, Object sender) {
                Action[] actions = null;
                if (modulo != null) {
                    actions = new Action[2];
                    actions[0] = actionEdit;
                    actions[1] = actionDelete;
                }// end of if cycle
                return actions;
            }// end of inner method

            public void handleAction(Action action, Object sender, Object target) {
                if (action.equals(actionEdit)) {
                    if (modulo != null) {
                        modulo.edit();
                    }// end of if cycle
                }// end of if cycle
                if (action.equals(actionDelete)) {
                    if (modulo != null) {
                        modulo.delete();
                    }// end of if cycle
                }// end of if cycle
            }// end of inner method
        });// end of anonymous inner class

        // fire table created
        fire(TableEvent.created);
    }// end of method

    /**
     * Called when the component gets attached to the UI
     */
    @Override
    public void attach() {
        super.attach();

        // the first time is called when the table gets attached,
        // subsequently is called by the data change listener
        updateTotals();

        // fire table attached to UI
        fire(TableEvent.attached);

        // fire table dta changed
        fire(TableEvent.datachange);

    }// end of method


    public ModulePop getModule() {
        return modulo;
    }

    /**
     * Creates the container
     * <p/>
     * JPAContainerFactory creates a container with all the properties
     *
     * @return the container Override in the subclass to use a different container
     */
    protected Container createContainer() {
        JPAContainer cont = JPAContainerFactory.makeNonCached(getEntityClass(), EM.createEntityManager());
        return cont;
    }// end of method


    /**
     * Add the properties to the container.
     * By default, all the properties from the Entity class are added.
     * If a property whith the same name is already present it is not added again.
     */
    protected void addPropertiesToContainer() {
        Container cont = getContainerDataSource();
        EntityType<?> type = EM.getEntityType(getEntityClass());
        Set<?> attributes = type.getAttributes();
        Attribute<?, ?> attribute;

        Collection coll = cont.getContainerPropertyIds();

        for (Object ogg : attributes) {
            if (ogg instanceof Attribute<?, ?>) {
                attribute = (Attribute<?, ?>) ogg;
                String name = attribute.getName();

                if (!coll.contains(name)) {
                    Class clazz = attribute.getJavaType();
                    Object defaultValue = null;
                    try {
                        defaultValue = clazz.newInstance();
                    } catch (Exception e) {
                    }

                    // specific handling for LazyQueryContainer
                    if (cont instanceof LazyQueryContainer) {
                        LazyQueryContainer lqCont = (LazyQueryContainer) cont;
                        lqCont.addContainerProperty(name, clazz, defaultValue, true, true);
                    } else {
                        cont.addContainerProperty(name, clazz, defaultValue);
                    }

                }

            }
        }

    }


    /**
     * Sorts the container.
     * By default the container is sorted based on the default sort order declared
     * in the entity class via the @DefaultSort annotation.
     * If the annotation is not present the container is not sorted.
     * <p/>
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
    }// end of method


    /**
     * Create additional columns
     * (add generated columns, nested properties...)
     * <p/>
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

        ArrayList<String> cNames = new ArrayList();
        for (Object obj : columns) {
            String cName = "";
            if (obj instanceof Attribute) {
                cNames.add(((Attribute<?, ?>) obj).getName());
            }// end of if cycle
            if (obj instanceof String) {
                cNames.add((String) obj);
            }// end of if cycle
            if (!cName.equals("")) {
                cNames.add(cName);
            }// end of if cycle
        }// end of for cycle

        Object[] outNames = cNames.toArray(new String[0]);
        this.setVisibleColumns(outNames);

    }// end of method

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
                }// end of if cycle
            }// end of if cycle
        }// end of for cycle
    }// end of method

    /**
     * Sets the column header for the specified column;
     *
     * @param propertyId the propertyId identifying the column.
     * @param header     the header to set.
     */
    public void setColumnHeader(Object propertyId, String header) {
        if (propertyId instanceof Attribute) {
            propertyId = ((Attribute<?, ?>) propertyId).getName();
        }// end of if cycle
        super.setColumnHeader(propertyId, header);
    }// end of method

    @Override
    public void setColumnAlignment(Object propertyId, Align alignment) {
        if (propertyId instanceof Attribute) {
            propertyId = ((Attribute<?, ?>) propertyId).getName();
        }// end of if cycle
        super.setColumnAlignment(propertyId, alignment);
    }// end of method

    @Override
    public void setColumnWidth(Object propertyId, int width) {
        if (propertyId instanceof Attribute) {
            propertyId = ((Attribute<?, ?>) propertyId).getName();
        }// end of if cycle
        super.setColumnWidth(propertyId, width);
    }// end of method


//    /**
//     * Adds/removes a column to the list of totalizable columns
//     * <p/>
//     *
//     * @param propertyId    - the id of the column
//     * @param useTotals     - to add or remove the column from the list
//     * @param decimalPlaces - the number of decimal places, -1 for autodetect
//     */
//    public void setColumnUseTotals(SingularAttribute attr, boolean useTotals, int decimalPlaces) {
//
//        TotalizableColumn tcol = new TotalizableColumn(attr, decimalPlaces);
//
//        if (useTotals) {
//            if (!totalizableColumns.contains(tcol)) {
//                totalizableColumns.add(tcol);
//            }// end of if cycle
//        } else {
//            totalizableColumns.remove(tcol);
//        }// end of if/else cycle
//
//        // the first call with useTotals=true activates automatically the footer
//        if (useTotals) {
//            setFooterVisible(true);
//        }// end of if cycle
//    }// end of method


    /**
     * Adds/removes a column to the list of totalizable columns
     * <p/>
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
            }// end of if cycle
        } else {
            totalizableColumns.remove(tcol);
        }// end of if/else cycle

        // the first call with useTotals=true activates automatically the footer
        if (useTotals) {
            setFooterVisible(true);
        }// end of if cycle
    }// end of method

    /**
     * Adds/removes a column to the list of totalizable columns<br>
     * with automatic number of decimal places
     * <p/>
     *
     * @param propertyId - the id of the column
     * @param useTotals  - to add or remove the column from the list
     */
    public void setColumnUseTotals(Object propertyId, boolean useTotals) {
        setColumnUseTotals(propertyId, useTotals, -1);
    }// end of method


    /**
     * Returns an array of the visible columns ids. Ids might be of type String
     * or Attribute. This base implementations returns all the columns (no
     * order) Override for a custom implementation.
     *
     * @return the list
     */
    @SuppressWarnings("rawtypes")
    protected Object[] getDisplayColumns() {
        Attribute[] fieldList = new Attribute[0];
        if (modulo != null) {
            fieldList = modulo.getFieldsList();
        }// end of if cycle
        return fieldList;
    }// end of method

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
                    }// end of if cycle

                    if ((Number.class).isAssignableFrom(clazz)) {
                        align = Table.Align.RIGHT;
                    }// end of if cycle

                }// end of if cycle

            } catch (NoSuchFieldException | SecurityException e) {
            }
        }// end of if cycle

        return align;

    }// end of method

    /**
     * Returns the ids of the single selected row
     * <p/>
     * Usable for single-select or multi-select tables
     *
     * @return the selected row id (if a single row is selected, otherwise null)
     * @deprecated
     */
    public Object getSelectedId() {
        Object selectedId = null;
        Object ids = getValue();
        if (ids != null) {

            // if multi select is enabled
            if (ids instanceof Collection) {
                Collection<Long> cIds = (Collection<Long>) ids;
                if (cIds.size() == 1) {
                    selectedId = Iterables.get(cIds, 0);
                }// end of if cycle
            }// end of if cycle

            // if multi select is disabled
            if (ids instanceof Long) {
                selectedId = ids;
            }// end of if cycle

        }// end of if cycle

        return selectedId;
    }// end of method

    /**
     * Returns the ids of the single selected row
     * <p/>
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
                }// end of if cycle
            }// end of if cycle

            // if multi select is disabled
            if (ids instanceof Long) {
                selectedId = (Long) ids;
            }// end of if cycle

        }// end of if cycle

        return selectedId;
    }// end of method

    /**
     * Returns the ids of the selected rows
     * <p/>
     * Usable for single-select or multi-select tables
     *
     * @return the selected row ids
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

        }// end of if cycle

        return selected;
    }// end of method

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
    }// end of method

    // /**
    // * Returns the ids of the selected row
    // * <p>
    // * Use for single-select tables
    // * @return the selected row id (if a single row is selected, otherwise -1)
    // */
    // public long getSelectedIdOld() {
    // long rowId = -1;
    // Object ids = getValue();
    // if (ids != null) {
    //
    // // if multi select is enabled
    // if (ids instanceof Collection) {
    // Collection<Long> cIds = (Collection<Long>) ids;
    // if (cIds.size() == 1) {
    // rowId = (long) Iterables.get(cIds, 0);
    // }// end of if cycle
    // }// end of if cycle
    //
    // // if multi select is disabled
    // if (ids instanceof Long) {
    // rowId = (long) ids;
    // }// end of if cycle
    //
    // }// end of if cycle
    //
    // return rowId;
    // }// end of method

//    /**
//     * Return the selected bean
//     */
//    public Object getSelectedBean() {
//        Object bean = null;
//        Object id = getSelectedId();
//        if (id != null) {
//            BeanItem<?> bi = getBeanItem(id);
//            if (bi != null) {
//                bean = bi.getBean();
//            }// end of if cycle
//        }
//        return bean;
//    }// end of method

    /**
     * Return the selected entity
     */
    public BaseEntity getSelectedEntity() {
        BaseEntity entity=null;
        Long id = (Long)getSelectedId();
        if (id != null) {
            entity=getEntity(id);
        }
        return entity;
    }// end of method


//    /**
//     * Return the selected beans (multiple selection)
//     */
//    public BeanItem[] getSelectedBeans() {
//        BeanItem[] selected = new BeanItem[0];
//        Object[] ids = getSelectedIds();
//        if (ids != null) {
//            ArrayList<BeanItem> objSel = new ArrayList<BeanItem>();
//            for (Object id : ids) {
//                BeanItem<?> bi = getBeanItem(id);
//                if (bi != null) {
//                    objSel.add(bi);
//                }
//            }
//            selected = objSel.toArray(new BeanItem[0]);
//        }
//        return selected;
//    }// end of method


    /**
     * Return the selected entities (multiple selection)
     */
    public BaseEntity[] getSelectedEntities() {
        BaseEntity[] entities = new BaseEntity[0];
        Object[] ids = getSelectedIds();
        if (ids != null) {
            ArrayList<BaseEntity> objSel = new ArrayList();
            for (Object id : ids) {
                BaseEntity entity=getEntity((Long)id);
                if (entity != null) {
                    objSel.add(entity);
                }
            }
            entities = objSel.toArray(new BaseEntity[0]);
        }

        return  entities;

    }



//    /**
//     * @param rowId the row id
//     * @return the new BeanItem
//     */
//    public BeanItem<Object> getBeanItem(Object rowId) {
//        BeanItem<Object> bi = null;
//        Item item = getItem(rowId);
//        if (item != null) {
//
//            Container cont = getContainerDataSource();
//            if(cont instanceof LazyEntityContainer){
//                LazyEntityContainer lec = (LazyEntityContainer)cont;
//                lec.getEntity(rowId);
//            }
//
//            if (item instanceof JPAContainerItem) {
//                JPAContainerItem<?> jpaItem = (JPAContainerItem<?>) item;
//                Object entity = jpaItem.getEntity();
//                bi = new BeanItem<Object>(entity);
//            }// end of if cycle
//        }// end of if cycle
//
//        return bi;
//    }// end of method
//

    /**
     * Returns the entity given a row id.
     * @param rowId the row id
     * @return the entity
     */
    public BaseEntity getEntity(Long rowId){
        BaseEntity entity=null;
        Container cont = getContainerDataSource();

        if(cont instanceof LazyEntityContainer){
            LazyEntityContainer lec = (LazyEntityContainer)cont;
            entity=(BaseEntity)lec.getEntity(rowId);
        }

        if(cont instanceof JPAContainer){
            Item item = getItem(rowId);
            if(item!=null){
                if (item instanceof JPAContainerItem) {
                    JPAContainerItem<?> jpaItem = (JPAContainerItem<?>) item;
                    entity = (BaseEntity) jpaItem.getEntity();
                }// end of if cycle
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
    }// end of method

    public Class<?> getEntityClass() {
        return entityClass;
    }

//    public JPAContainer<?> getJPAContainer() {
//        JPAContainer<?> jpaCont = null;
//        Container cont = getContainerDataSource();
//        if (cont instanceof JPAContainer) {
//            jpaCont = (JPAContainer<?>) cont;
//        }// end of if cycle
//
//        return jpaCont;
//    }// end of method


    /**
     * Returns the container as a Filterable container.
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
        Object value = null;

        // Format for Dates
        if (property.getType() == Date.class) {

            try {
                string = this.dateFormat.format((Date) property.getValue());
            } catch (Exception e) {
                // TODO: handle exception
            }
        }// end of if cycle

        // Format for Booleans
        if (property.getType() == Boolean.class) {
            string = "";
            value = property.getValue();

            if (value != null && value instanceof Boolean) {
                if ((boolean) value) {
                    string = "\u2714";
                }// end of if cycle
            }

        }// end of if cycle

        // none of the above
        if (string == null) {
            string = super.formatPropertyValue(rowId, colId, property);
        }// end of if cycle

        return string;
    }// end of method

    /**
     * Updates the totals in the footer
     * <p/>
     * Called when the container data changes
     * todo - se si usa un database questa implementazione è micidiale, spazzola tutte le righe!!!
     * todo - va riscritta usando delle JPQ CriteriaQuery
     * todo - attualmente il problema è che i filtri sono oggetti Container.Filter e non
     * todo - sono compatibili con CriteriaQuery
     */
    @SuppressWarnings("rawtypes")
    protected void updateTotals() {
        // Collection ids = getJPAContainer().getItemIds();

        if (totalizableColumns.size() > 0) {

            // maps the totals to the property ids
            HashMap<Object, BigDecimal> totalsMap = new HashMap<Object, BigDecimal>();

            Collection<?> ids = getContainerDataSource().getItemIds();

            // cycle all the rows
            for (Object itemId : ids) {

                // cycle the totalizable columns
                for (TotalizableColumn column : totalizableColumns) {
                    Object propertyId = column.getPropertyId();
                    Property<?> prop = getContainerDataSource().getContainerProperty(itemId, propertyId);
                    if (prop != null) {
                        addToTotals(totalsMap, propertyId, prop);
                    }// end of if cycle
                }// end of for cycle
            }// end of for cycle

            // places the totals in the target columns
            StringToBigDecimalConverter converter = new StringToBigDecimalConverter();
            for (Object propertyId : totalsMap.keySet()) {
                BigDecimal total = totalsMap.get(propertyId);
                int places = genNumDecimalPlacesForTotalColumn(propertyId);
                converter.setDecimalPlaces(places);
                String sTotal = converter.convertToPresentation(total);
                setColumnFooter(propertyId, sTotal);
            }// end of for cycle

        }


    }// end of method


    /**
     * Adds a total to the Totals map
     *
     * @param map        the totals map
     * @param propertyId the id of the Property
     * @param prop       ???
     */
    protected void addToTotals(HashMap<Object, BigDecimal> map, Object propertyId, Property<?> prop) {

        // add the key if absent
        if (!map.containsKey(propertyId)) {
            map.put(propertyId, new BigDecimal(0));
        }

        BigDecimal currentValue = map.get(propertyId);

        // retrieve the value as BigDecimal
        Object value = prop.getValue();
        BigDecimal valueToAdd = new BigDecimal(0);
        if (value != null) {
            Class<?> type = prop.getType();
            if (type.equals(Integer.class)) {
                valueToAdd = new BigDecimal((Integer) value);
            }// end of if cycle
            if (type.equals(Long.class)) {
                valueToAdd = new BigDecimal((Long) value);
            }// end of if cycle
            if (type.equals(Double.class)) {
                valueToAdd = new BigDecimal((Double) value);
            }// end of if cycle
            if (type.equals(BigDecimal.class)) {
                valueToAdd = (BigDecimal) value;
            }// end of if cycle
        }// end of if cycle

        // add the new value and re-put the value in the map
        currentValue = currentValue.add(valueToAdd);
        map.put(propertyId, currentValue);

    }// end of method

    /**
     * Returns the number of decimal places in totalizable columns for a given
     * totalizable column
     * <p/>
     *
     * @param propertyId the property id
     * @return the number of decimal places
     */
    protected int genNumDecimalPlacesForTotalColumn(Object propertyId) {
        int places = 0;
        for (TotalizableColumn t : totalizableColumns) {
            if (t.getPropertyId().equals(propertyId)) {
                places = t.getDecimalPlaces();
                break;
            }// end of if cycle
        }// end of for cycle
        return places;
    }// end of method




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
        Metamodel metamodel = entityManager.getMetamodel();
        Set<EntityType<?>> entities = metamodel.getEntities();
        EntityType<?> entityType = null;
        for (EntityType<?> eType : entities) {
            Class<?> type = eType.getJavaType();
            if (type.equals(getEntityClass())) {
                entityType = eType;
                break;
            }// end of if cycle
        }// end of for cycle

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

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root root = cq.from(getEntityClass());

        Predicate pred = getFiltersPredicate(cb, cq, root);
        if (pred != null) {
            cq.where(pred);
        }

        Expression<Number> e1 = cb.sum(root.get(attr));
        cq.select(e1);

        Number num = entityManager.createQuery(cq).getSingleResult();
        BigDecimal bd = new BigDecimal(0);
        if (num != null) {
            if (num instanceof BigDecimal) {
                bd = (BigDecimal) num;
            }
            if (num instanceof Integer) {
                Integer i = (Integer) num;
                bd = new BigDecimal(i);
            }
            if (num instanceof Long) {
                Long l = (Long) num;
                bd = new BigDecimal(l);
            }
        }

        return bd;

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
        Collection<Filter> filters = null;

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
     * Action when a user click the table
     * Must be overridden on the subclass
     *
     * @param itemClickEvent the event
     */
    public void itemClick(ItemClickEvent itemClickEvent) {

    }// end of method

    /**
     * Add a form listener to the form
     */
    public void addTableListener(TableListener listener) {
        listeners.add(listener);
    }// end of method

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
        }// end of for cycle
    }// end of method


    /**
     * Total number of rows in the table's domain database
     */
    public long getTotalRows() {
        Class<?> clazz = getEntityClass();
        long rows = AQuery.getCount(clazz);
        return rows;
    }// end of method


    /**
     * Number of rows currently available in the table's container
     */
    public int getVisibleRows() {
        return getContainerDataSource().size();
    }// end of method

    @Override
    public void addListListener(ListSelectionListener list) {
        selectionListeners.add(list);
    }// end of method

    @Override
    public void removeAllListListeners() {
        selectionListeners.clear();
    }// end of method


    @Override
    public void setListListener(ListSelectionListener list) {
        removeAllListListeners();
        addListListener(list);
    }// end of method


    /**
     * Evento generato quando si modifica la selezione delle righe
     * <p/>
     * Informa (tramite listener) chi è interessato <br>
     */
    public void selectionChanged(ItemClickEvent itemClickEvent) {
        ListSelectionEvent evento;
        Object id;
        Long longKey;
        int intKeyOld;
        int intKeyNew = 0;
        Object[] selez;
        int firstIndex = 0;
        int lastIndex = 0;

        if (itemClickEvent == null) {
            evento = new ListSelectionEvent(this, firstIndex, lastIndex, false);
            fireSelectionListener(evento);
            return;
        }// end of if cycle

        id = itemClickEvent.getItemId();
        if (id instanceof Long) {
            intKeyNew = ((Long) id).intValue();
        }// end of if cycle

        if (itemClickEvent.isDoubleClick()) {
            return;
        }// end of if cycle

        if (itemClickEvent.isCtrlKey()) {
            return;
        }// end of if cycle

        // selezione discontinua
        if (itemClickEvent.isMetaKey()) {
            return;
        }// end of if cycle

        // selezione continua
        if (itemClickEvent.isShiftKey()) {
            // c'era una sola riga selezionata precedentemente
            if (isSingleRowSelected()) {
                longKey = getSelectedKey();
                intKeyOld = longKey.intValue();

                if (intKeyNew > intKeyOld) {
                    firstIndex = intKeyOld;
                    lastIndex = intKeyNew;
                } else {
                    firstIndex = intKeyNew;
                    lastIndex = intKeyOld;
                }// end of if/else cycle
            } else {
//                selez = getSelectedIds();
//                if (selez != null && selez.length > 0) {
//                    firstIndex = (Integer) selez[0];
//                    lastIndex = (Integer) selez[selez.length - 1];
//                }// end of if cycle
            }// end of if/else cycle
        }// end of if cycle

        // selezione singola
        if (!itemClickEvent.isDoubleClick() && !itemClickEvent.isCtrlKey() && !itemClickEvent.isMetaKey() && !itemClickEvent.isShiftKey()) {
            id = itemClickEvent.getItemId();
            if (id instanceof Long) {
                firstIndex = ((Long) id).intValue();
                lastIndex = ((Long) id).intValue();
            }// end of if cycle
        }// end of if cycle

        // crea l'evento
        evento = new ListSelectionEvent(this, firstIndex, lastIndex, false);

        // notify all the listeners
        fireSelectionListener(evento);
    }// end of method

    /**
     * Evento generato quando si modifica la selezione delle righe
     * <p/>
     * Informa (tramite listener) chi è interessato <br>
     */
    public void fireSelectionListener(ListSelectionEvent evento) {
        // notify all the listeners
        if (selectionListeners != null) {
            for (ListSelectionListener listener : selectionListeners) {
                listener.valueChanged(evento);
            }// end of for cycle
        }// end of if cycle
    }// end of method

    public enum TableEvent {
        created, attached, datachange;
    }// end of enumeration

    /**
     * Table high-level events
     */
    public interface TableListener {
        public void created_(); // table created

        public void attached_(); // table attached to UI

        public void datachange_(); // table data changed
    }// end of interface

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
        }// end of inner method

        public SingularAttribute getAttribute() {
            return attribute;
        }

        public Object getPropertyId() {
            return propertyId;
        }// end of inner method

        /**
         * Returns the number of decimal digits to display for this column.
         * If set to auto, the cholice is based on the column class.
         * @return the number of decimal digits
         */
        public int getDecimalPlaces() {
            int places =decimalPlaces;
            if (decimalPlaces == -1) { // auto
                places = getDefaultDecimalPlacesForColumn(propertyId);
            }
            return places;
        }// end of inner method

        @Override
        public boolean equals(Object obj) {
            TotalizableColumn other = (TotalizableColumn) obj;
            return propertyId.equals(other.getPropertyId());
        }// end of inner method

        /**
         * Returns a default number of decimal places for a given property.<br>
         * 0 for integers (int, long), 2 for decimals (double, float, BigDecimal)
         * <p/>
         *
         * @param propertyId the property id
         * @return the number of decimal places
         */
        protected int getDefaultDecimalPlacesForColumn(Object propertyId) {
            Class<?> clazz = getContainerDataSource().getType(propertyId);
            if (clazz.equals(Integer.class)) {
                return 0;
            }// end of if cycle
            if (clazz.equals(int.class)) {
                return 0;
            }// end of if cycle
            if (clazz.equals(Long.class)) {
                return 0;
            }// end of if cycle
            if (clazz.equals(long.class)) {
                return 0;
            }// end of if cycle
            if (clazz.equals(BigInteger.class)) {
                return 0;
            }// end of if cycle
            if (clazz.equals(Double.class)) {
                return 2;
            }// end of if cycle
            if (clazz.equals(double.class)) {
                return 2;
            }// end of if cycle
            if (clazz.equals(Float.class)) {
                return 2;
            }// end of if cycle
            if (clazz.equals(float.class)) {
                return 2;
            }// end of if cycle
            if (clazz.equals(BigDecimal.class)) {
                return 2;
            }// end of if cycle
            return 0;
        }// end of method

    }// end of inner class


    protected ATable getTable() {
        return this;
    }

}// end of class
