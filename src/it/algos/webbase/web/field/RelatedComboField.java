package it.algos.webbase.web.field;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectConverter;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.entity.SortProperties;
import it.algos.webbase.web.form.AForm;
import it.algos.webbase.web.lib.Lib;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.query.AQuery;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("serial")
public class RelatedComboField extends ComboBox implements FieldInterface<Object> {

    // constants to identify the edit types allowed (new/edit buttons)
    public static final int EDIT_TYPE_NEW = 1;
    public static final int EDIT_TYPE_EDIT = 2;
    public static final int EDIT_TYPE_BOTH = 3;

    private ArrayList<RecordEditedListener> listeners = new ArrayList();

    @SuppressWarnings("rawtypes")
    private Class entityClass;
    protected EntityManager entityManager;
    private Component editComponent;
    private String filterString;

    @SuppressWarnings("rawtypes")
    public RelatedComboField(Class entityClass, String caption) {
        super(caption);
        this.entityClass = entityClass;
        setImmediate(true);
        init();
    }

    @SuppressWarnings("rawtypes")
    public RelatedComboField(Class entityClass) {
        this(entityClass, null);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void init() {
        initField();

        //create and register the EntityManager
        entityManager = EM.createEntityManager();

        Container cont = createContainer();
        setContainerDataSource(cont);
        sortContainer(cont);
        setItemCaptionMode(ItemCaptionMode.ITEM);
        setConverter(new SingleSelectConverter(this));
        setWidth("260px");
    }

    public void initField() {
        FieldUtil.initField(this);
    }

    /**
     * Creates the container used as data source for the combo.
     * <p>
     *
     * @return the container
     */
    @SuppressWarnings("unchecked")
    protected Container createContainer() {
        JPAContainer cont = JPAContainerFactory.makeNonCached(getEntityClass(), getEntityManager());
        return cont;
    }

    /**
     * Sorts the container.
     * By default the container is sorted based on the default sort order declared
     * in the entity class via the @DefaultSort annotation.
     * If the annotation is not present the container is not sorted.
     *
     * For a custom sort of the container in a RelatedCombo field you have 2 options:
     * 1) call the sort() method after the creation of the object passing the properties on which to sort
     * 2) override this method (needs subclassing).
     * @param cont the container to be sorted.
     */
    protected void sortContainer(Container cont){

        // retrieve the default sort properties from the class by annotation
        SortProperties props = BaseEntity.getSortProperties(getEntityClass());

        // sort the container on the sort properties
        if(cont instanceof Sortable){
            Sortable sortable=(Sortable)cont;
            sortable.sort(props.getProperties(), props.getDirections());
        }

    }

    /**
     * Returns the container as a JPAContainer.
     *
     * @return the container as a Filterable, or null if it is not filterable
     */
    public JPAContainer getJPAContainer() {
        JPAContainer jpac = null;
        Container cont = getContainerDataSource();
        if (cont != null && cont instanceof JPAContainer) {
            jpac = (JPAContainer) cont;
        }
        return jpac;
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
     * Assigns a handler for new items. Switches on/off the feature of the combo if the handler is not null or null
     */
    @Override
    public void setNewItemHandler(NewItemHandler newItemHandler) {
        setNewItemsAllowed(newItemHandler != null);
        super.setNewItemHandler(newItemHandler);
    }

    /**
     * Creates a handler to handle new items and assigns it to this combo.
     *
     * @param formClass - the class of the form which will be used for editing the new item (must subclass AForm)
     * @param attribute - the attribute to fill with the text coming from the combo
     */
    public void setNewItemHandler(Class<? extends AForm> formClass, Attribute attribute) {
        ComboNewItemHandler handler = new ComboNewItemHandler(this, formClass, attribute);
        setNewItemHandler(handler);
    }


    /**
     * Return a bean corresponding to the currently selected item
     * <p>
     *
     * @return the bean
     */
    public Object getSelectedBean() {
        Object bean = null;
        long id = Lib.getLong(getValue());
        if (id > 0) {
            bean = AQuery.queryById(getEntityClass(), id);
        }
        return bean;
    }

    /**
     * Sorts the container
     *
     * @param sortList array of attribute ids on which to sort
     */
    @SuppressWarnings("rawtypes")
    public void sort(Object... sortList) {
        ArrayList<Object> attrIds = new ArrayList<Object>();
        for (Object obj : sortList) {
            Object key = obj;
            if (key instanceof Attribute) {
                key = ((Attribute) obj).getName();
            }
            attrIds.add(key);
        }
        Object[] attrArray = attrIds.toArray(new Object[0]);
        boolean[] attrOrders = new boolean[attrArray.length];
        for (int i = 0; i < attrOrders.length; i++) {
            attrOrders[i] = true;
        }

        Container cont = getContainerDataSource();
        if(cont instanceof Sortable){
            Sortable sCont=(Sortable)cont;
            sCont.sort(attrArray, attrOrders);
        }

    }


    @SuppressWarnings("rawtypes")
    public Class getEntityClass() {
        return entityClass;
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setAlignment(FieldAlignment alignment) {
    }


    /**
     * @return a Component containing this field and the new/edit buttons,
     * all wrapped in a layout.
     * <p>
     * When the new/edit buttons are pressed, the edit form is presented.
     * @param type EDIT_TYPE_NEW to show tne NEW button, EDIT_TYPE_EDIT to
     *             show the EDIT button, EDIT_TYPE_BOTH to show both buttons
     */
    public Component getEditComponent(int type) {

        // lazily creates the edit component the first time is requested
        if (editComponent == null) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setSpacing(true);

            // moves the caption to the external layout
            layout.setCaption(this.getCaption());
            this.setCaption(null);
            layout.addComponent(this);

            // "new" button
            if ((type == EDIT_TYPE_NEW) | (type == EDIT_TYPE_BOTH)) {
                Button bNew = new Button("Nuovo");
                bNew.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        newButtonClicked();
                    }
                });
                layout.addComponent(bNew);
            }

            // "edit" button
            if ((type == EDIT_TYPE_EDIT) | (type == EDIT_TYPE_BOTH)) {
                Button bEdit = new Button("Modifica");
                bEdit.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        editButtonClicked();
                    }
                });
                layout.addComponent(bEdit);
            }

            editComponent = layout;
        }

        return editComponent;
    }

    /**
     * @return a Component containing this field and the edit button,
     * all wrapped in a layout.
     */
    public Component getEditComponent() {
        return getEditComponent(EDIT_TYPE_EDIT);
    }


    protected void editButtonClicked() {
        NewItemHandler handler = getNewItemHandler();
        if ((handler != null) && (handler instanceof ComboNewItemHandler)) {
            ComboNewItemHandler cHandler = (ComboNewItemHandler) handler;
            Object bean = getSelectedBean();
            if (bean != null) {
                BeanItem bi = new BeanItem(bean);
                cHandler.edit(bi);
            }
        }
    }

    protected void newButtonClicked() {
        NewItemHandler handler = getNewItemHandler();
        if ((handler != null) && (handler instanceof ComboNewItemHandler)) {
            ComboNewItemHandler cHandler = (ComboNewItemHandler) handler;
            cHandler.addNewItem(filterString);
            filterString=null;
        }
    }


    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {
        super.changeVariables(source, variables);

        // save the filter string for later use
        filterString= (String) variables.get("filter");
    }




    /**
     * Adds a listener that gets notified when a
     * record is saved after being edited in the form.
     */
    public void addRecordEditedListener(RecordEditedListener listener) {
        this.listeners.add(listener);
    }// end of method

    protected void fire(Item item, boolean newRecord) {
        for (RecordEditedListener l : listeners) {
            l.save_(item, newRecord);
        }

    }// end of method


    public interface RecordEditedListener {
        /**
         * The record has been saved after being edited in the form.
         * <p>
         *
         * @param item        the saved item
         * @param newRecord true for new record, false for editing existing record
         */
        public void save_(Item item, boolean newRecord);
    }

}
