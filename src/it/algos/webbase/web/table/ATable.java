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
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Table;
import it.algos.webbase.web.converter.StringToBigDecimalConverter;
import it.algos.webbase.web.entity.BaseEntity_;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.query.AQuery;

import javax.persistence.metamodel.Attribute;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

@SuppressWarnings("serial")
public class ATable extends Table {

    protected ModulePop modulo;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Class<?> entityClass;
    private Action actionEdit = new Action("Modifica", new ThemeResource("img/action_edit18.png"));
    private Action actionDelete = new Action("Modifica", new ThemeResource("img/action_delete18.png"));
    private ArrayList<TotalizableColumn> totalizableColumns = new ArrayList<TotalizableColumn>();
    private ArrayList<TableListener> listeners = new ArrayList<TableListener>();

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
        modulo = module;
        this.entityClass = module.getEntityClass();
        init();
    }// end of constructor

    private void init() {

        // create and set the container - read-only and cached
        // all the columns are added automatically to the table
        Container container = createContainer();
        sortContainer(container);
        setContainerDataSource(container);

        // adds a listener for data change to the container (JPAContainer only)
        JPAContainer<?> jpacont = getJPAContainer();
        if (jpacont != null) {
            jpacont.addItemSetChangeListener(new ItemSetChangeListener() {

                @Override
                public void containerItemSetChange(ItemSetChangeEvent event) {
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
                ATable.this.itemClick(itemClickEvent);
            }// end of inner method
        });// end of anonymous inner class

        // create additional columns
        createAdditionalColumns();

        // set the visible columns
        setColumnVisibility();

        setAlignments();
        setSelectable(true);
        setEditable(false);
        setImmediate(true);
        setColumnCollapsingAllowed(true);
        setColumnReorderingAllowed(true);
        setMultiSelect(true);

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

    /**
     * Creates the container
     * <p>
     *
     * @return the container Override in the subclass to use a different container
     */
    protected Container createContainer() {
        JPAContainer cont = JPAContainerFactory.makeNonCached(getEntityClass(), EM.createEntityManager());
//		JPAContainer cont = JPAContainerFactory.make(getEntityClass(),EM.createEntityManager());

        return cont;
    }// end of method

    /**
     * Initial sort order for the container
     * <p>
     *
     * @param cont the container to be sorted
     */
    protected void sortContainer(Container cont) {
        if (cont instanceof JPAContainer) {
            sortJPAContainer((JPAContainer) cont);
        }
    }// end of method

    /**
     * Initial sort order for the JPA container
     * <p>
     *
     * @param cont the container to be sorted
     */
    protected void sortJPAContainer(JPAContainer cont) {
        String sortField = BaseEntity_.id.getName();
        cont.sort(new String[]{sortField}, new boolean[]{true});
    }// end of method


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
    private void setColumnVisibility() {

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
    private void setAlignments() {
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

    /**
     * Adds/removes a column to the list of totalizable columns
     * <p>
     *
     * @param propertyId    - the id of the column
     * @param useTotals     - to add or remove the column from the list
     * @param decimalPlaces - the number of decimal places, -1 for autodetect
     */
    public void setColumnUseTotals(Object propertyId, boolean useTotals, int decimalPlaces) {
        if (propertyId instanceof Attribute) {
            propertyId = ((Attribute<?, ?>) propertyId).getName();
        }// end of if cycle

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
     * <p>
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
     * <p>
     * Usable for single-select or multi-select tables
     *
     * @return the selected row id (if a single row is selected, otherwise null)
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
     * Returns the ids of the selected rows
     * <p>
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

    /**
     * Return the selected bean
     */
    public Object getSelectedBean() {
        Object bean = null;
        Object id = getSelectedId();
        if (id != null) {
            BeanItem<?> bi = getBeanItem(id);
            if (bi != null) {
                bean = bi.getBean();
            }// end of if cycle
        }
        return bean;
    }// end of method

    // /**
    // * @return the selected row ids
    // */
    // public long[] getSelectedIdsOld() {
    // long[] selected = new long[0];
    //
    // Object ids = getValue();
    // if (ids != null) {
    //
    // // if multi select is enabled
    // if (ids instanceof Collection) {
    // Collection<Long> cIds = (Collection<Long>) ids;
    // selected = new long[cIds.size()];
    // int idx = 0;
    // for (Iterator<Long> iterator = cIds.iterator(); iterator.hasNext();) {
    // long id = (long) iterator.next();
    // selected[idx] = id;
    // idx++;
    // }// end of for cycle
    // }// end of if cycle
    //
    // // if multi select is disabled
    // if (ids instanceof Long) {
    // selected = new long[1];
    // selected[0] = (long) ids;
    // }// end of if cycle
    //
    // }// end of if cycle
    //
    // return selected;
    // }// end of method

    /**
     * @param rowId the row id
     * @return the new BeanItem
     */
    public BeanItem<Object> getBeanItem(Object rowId) {
        BeanItem<Object> bi = null;
        Item item = getItem(rowId);
        if (item != null) {
            if (item instanceof JPAContainerItem) {
                JPAContainerItem<?> jpaItem = (JPAContainerItem<?>) item;
                Object entity = jpaItem.getEntity();
                bi = new BeanItem<Object>(entity);
            }// end of if cycle
        }// end of if cycle

        return bi;
    }// end of method

    public void refresh() {
        JPAContainer<?> cont = getJPAContainer();
        if (cont != null) {
            cont.refresh();
        }// end of if cycle
    }// end of method

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public JPAContainer<?> getJPAContainer() {
        JPAContainer<?> jpaCont = null;
        Container cont = getContainerDataSource();
        if (cont instanceof JPAContainer) {
            jpaCont = (JPAContainer<?>) cont;
        }// end of if cycle

        return jpaCont;
    }// end of method

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
     * <p>
     * Called when the container data changes
     */
    @SuppressWarnings("rawtypes")
    protected void updateTotals() {
        // Collection ids = getJPAContainer().getItemIds();

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
            if (places == -1) { // auto
                places = getDefaultDecimalPlacesForColumn(propertyId);
            }// end of if cycle
            converter.setDecimalPlaces(places);
            String sTotal = converter.convertToPresentation(total);
            setColumnFooter(propertyId, sTotal);
        }// end of for cycle

    }// end of method

    private void addToTotals(HashMap<Object, BigDecimal> map, Object propertyId, Property<?> prop) {

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
     * <p>
     *
     * @param propertyId the property id
     * @return the number of decimal places
     */
    private int genNumDecimalPlacesForTotalColumn(Object propertyId) {
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
     * Returns a default number of decimal places for a given property.<br>
     * 0 for integers (int, long), 2 for decimals (double, float, BigDecimal)
     * <p>
     *
     * @param propertyId the property id
     * @return the number of decimal places
     */
    private int getDefaultDecimalPlacesForColumn(Object propertyId) {
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

    /**
     * Action when a user click the table
     * Must be overridden on the subclass
     *
     * @param itemClickEvent the event
     */
    protected void itemClick(ItemClickEvent itemClickEvent) {
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
        long rows = -1;
        JPAContainer<?> cont = getJPAContainer();
        if (cont != null) {
            Class<?> clazz = cont.getEntityClass();
            rows = (long) AQuery.getCount(clazz);
        }// end of if cycle

        return rows;
    }// end of method

    /**
     * Number of rows currently available in the table's container
     */
    public int getVisibleRows() {
        return getContainerDataSource().size();
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
    private class TotalizableColumn {
        private Object propertyId;
        private int decimalPlaces;

        public TotalizableColumn(Object propertyId, int decimalPlaces) {
            super();
            this.propertyId = propertyId;
            this.decimalPlaces = decimalPlaces;
        }// end of inner method

        public Object getPropertyId() {
            return propertyId;
        }// end of inner method

        public int getDecimalPlaces() {
            return decimalPlaces;
        }// end of inner method

        @Override
        public boolean equals(Object obj) {
            TotalizableColumn other = (TotalizableColumn) obj;
            return propertyId.equals(other.getPropertyId());
        }// end of inner method
    }// end of inner class

}// end of class
