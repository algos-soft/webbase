package it.algos.webbase.web.field;

import com.vaadin.addon.jpacontainer.metadata.PropertyKind;
import com.vaadin.data.Property;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.DefaultSort;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.entity.SortProperties;
import it.algos.webbase.web.form.AForm;
import it.algos.webbase.web.lib.Lib;
import it.algos.webbase.web.query.AQuery;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import javax.persistence.metamodel.Attribute;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectConverter;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class RelatedComboField extends ComboBox implements FieldInterface<Object> {

    // constants to identify the edit types allowed (new/edit buttons)
    public static final int EDIT_TYPE_NEW = 1;
    public static final int EDIT_TYPE_EDIT = 2;
    public static final int EDIT_TYPE_BOTH = 3;

    private ArrayList<RecordEditedListener> listeners = new ArrayList<RecordEditedListener>();


    @SuppressWarnings("rawtypes")
    private Class entityClass;

    private Component editComponent;

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
        JPAContainer cont = JPAContainerFactory.makeReadOnly(getEntityClass(), EM.createEntityManager());
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
        if(cont instanceof JPAContainer){
            JPAContainer jpaCont=(JPAContainer)cont;
            jpaCont.sort(props.getProperties(), props.getDirections());
        }

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
     * @param formClass - the class for the form which will be used for editing the new item (must subclass AForm)
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
        getJPAContainer().sort(attrArray, attrOrders);
    }

    @SuppressWarnings("rawtypes")
    public JPAContainer getJPAContainer() {
        JPAContainer jpaCont = null;
        Container cont = getContainerDataSource();
        if ((cont != null) && (cont instanceof JPAContainer)) {
            jpaCont = (JPAContainer) cont;
        }
        return jpaCont;
    }

    @SuppressWarnings("rawtypes")
    public Class getEntityClass() {
        return entityClass;
    }

//    public BaseEntity getBaseEntityClass() {
//        if(entityClass instanceof BaseEntity){
//
//        }
//        return null;
//    }



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
            cHandler.addNewItem(null);
        }
    }


    /**
     * Adds a listener that gets notified when a
     * record is saved after being edited in the form.
     */
    public void addRecordEditedListener(RecordEditedListener listener) {
        this.listeners.add(listener);
    }// end of method

    protected void fire(BeanItem bi, boolean newRecord) {
        for (RecordEditedListener l : listeners) {
            l.save_(bi, newRecord);
        }

    }// end of method


    public interface RecordEditedListener {
        /**
         * The record has been saved after being edited in the form.
         * <p>
         *
         * @param bi        the saved bean
         * @param newRecord true for new record, false for editing existing record
         */
        public void save_(BeanItem bi, boolean newRecord);
    }

}
