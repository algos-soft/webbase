package it.algos.webbase.web.form;

import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import it.algos.webbase.web.AlgosApp;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.field.*;
import it.algos.webbase.web.field.DateField;
import it.algos.webbase.web.field.TextField;
import it.algos.webbase.web.lib.Lib;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.toolbar.FormToolbar;
import it.algos.webbase.web.toolbar.FormToolbar.FormToolbarListener;
import it.algos.webbase.web.toolbar.Toolbar;

import javax.persistence.metamodel.Attribute;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class AForm extends VerticalLayout {

    @SuppressWarnings("rawtypes")
    protected LinkedHashMap<Object, Field> bindMap;
    protected FormLayout body; //usato per i fields aggiuntivi che vengono visualizzati a comando sotto gli altri
    private ModulePop module;
    private Item item;
    private FieldGroup binder;
    private Toolbar toolbar;
    private ArrayList<FormListener> listeners = new ArrayList<FormListener>();
    private boolean newRecord; // turned on if the edited bean is a new record.
    // Never turned off.

    /**
     * Constructor
     *
     * @param item the item
     */
    public AForm(Item item) {
        this((ModulePop) null, item);
    }// end of constructor

    /**
     * Constructor
     *
     * @param module the referenced module
     */
    public AForm(ModulePop module) {
        this(module, (Item) null);
    }// end of constructor

    /**
     * Constructor
     *
     * @param module the reference module
     * @param item   the item with the properties
     */
    public AForm(ModulePop module, Item item) {
        super();
        this.module = module;
        setItem(item);

        init();
    }// end of constructor

    /**
     * Initialization
     */
    protected void init() {

        // create a new BeanItem if no item is present
        if (this.item == null) {
            setItem(createBeanItem());
        }// end of if cycle

        // determine if the edited bean is new record
        BaseEntity entity = getBaseEntity();
        if (entity != null) {
            if (entity.getId() == null) {
                this.newRecord = true;
            }
        }

        // create the binder
        this.binder = new FieldGroup(this.item);

        // create the (empty) map
        this.bindMap = new LinkedHashMap<Object, Field>();

        // field.commit() is needed to update the item data source.
        // validation is run on commit.
        this.binder.setBuffered(true);

        // create the fields
        createFields();

        // create the layout
        Component detail = createComponent();
        this.addComponent(detail);
        detail.setHeight("100%");
        this.setExpandRatio(detail, 1f);

        this.toolbar = createToolBar();
        this.addComponent(this.toolbar);
        toolbar.setWidth("100%");

    }// end of method

    /**
     * Creates a new empty BeanItem from the module's bean
     */
    private BeanItem createBeanItem() {
        BeanItem<BaseEntity> item = null;
        try {
            Class<BaseEntity> entityClass = module.getEntityClass();
            Object obj = entityClass.newInstance();
            item = new BeanItem<BaseEntity>((BaseEntity) obj);
        } catch (Exception e) {
        }
        return item;
    }// end of method

    /**
     * @return the BaseEntity managed by this form
     */
    @SuppressWarnings("rawtypes")
    protected BaseEntity getBaseEntity() {
        BaseEntity entity = null;

        Item item = getItem();
        if (item instanceof BeanItem) {
            BeanItem beanItem = getBeanItem();
            entity = (BaseEntity) beanItem.getBean();
        }
        if (item instanceof JPAContainerItem) {
            JPAContainerItem jpaItem = (JPAContainerItem) getItem();
            entity = (BaseEntity) jpaItem.getEntity();
        }

        return entity;
    }// end of method

    /**
     * Populate the map to bind item properties to fields.
     * <p>
     * Crea e aggiunge i campi.<br>
     * Implementazione di default nella superclasse.<br>
     * I campi vengono recuperati dal Modello.<br>
     * I campi vengono creti del tipo grafico previsto nella Entity.<br>
     * Se si vuole aggiungere un campo (solo nel form e non nel Modello),<br>
     * usare il metodo sovrascritto nella sottoclasse
     * invocando prima (o dopo) il metodo della superclasse.
     * Se si vuole un layout completamente diverso sovrascrivere
     * senza invocare il metodo della superclasse
     */
    protected void createFields() {
        Field field = null;

        // create a field for each property
        Attribute[] attributes = getModule().getFieldsForm();
        for (Attribute attr : attributes) {
            field = creaField(attr);
            if (field != null) {
                addField(attr, field);
            }// end of if cycle
        }// end of for cycle
    }// end of method

    /**
     * Crea i campi. I campi vengono creati del tipo grafico previsto nella Entity.
     */
    protected Field creaField(Attribute attr) {
        Field field = null;
        Class clazz = null;
        Object[] values;
        String caption = "";

        if (attr != null) {
            clazz = attr.getJavaType();

            if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
                field = new CheckBoxField();
            }// fine del blocco if

            if (clazz.equals(String.class)) {
                field = new TextField();
            }// fine del blocco if

            if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
                field = new IntegerField();
            }// fine del blocco if

            if (clazz.equals(Date.class)) {
                field = new DateField();
            }// fine del blocco if

            if (clazz.equals(BigDecimal.class)) {
                field = new DecimalField();
            }// fine del blocco if

            if (clazz.equals(Timestamp.class)) {
                field = new DateField();
            }// fine del blocco if

            if (attr.isAssociation()) {
                field = new RelatedComboField(clazz);
            }// fine del blocco if

            if (clazz.isEnum()) {
                values = clazz.getEnumConstants();
                field = new ArrayComboField(values);
                if (AlgosApp.COMBO_BOX_NULL_SELECTION_ALLOWED) {
                    ((ArrayComboField) field).setNullSelectionAllowed(true);
                } else {
                    ((ArrayComboField) field).setNullSelectionAllowed(false);
                }// fine del blocco if-else
                field.addValueChangeListener(new Property.ValueChangeListener() {
                    @Override
                    public void valueChange(Property.ValueChangeEvent event) {
                        arrayComboBoxValueChanged(event);
                    }// end of method
                });// end of anonymous class
            }// fine del blocco if

            if (field == null) {
                field = new TextField();
            }// fine del blocco if
        }// fine del blocco if

        if (field != null) {
            caption = DefaultFieldFactory.createCaptionByPropertyId(attr.getName());
            field.setCaption(caption);
        }// fine del blocco if

        return field;
    }// end of method


    /**
     * Intercetta i cambiamenti nei combobox.
     * <p>
     * Sovrascritto nella classe specifica
     */
    protected void arrayComboBoxValueChanged(Property.ValueChangeEvent event) {
    }// end of method


    /**
     * Aggiunge al volo un fields
     * <p>
     * Il fields viene aggiunto in un body di tipo FormLayout posizionato SOTTO gli altri campi
     * Viene usato un FormLayout per avere la caption a sinistra
     * Il body viene viene (eventualmente) costruito nella sottoclasse (sovrascrivendo il metodo createComponent), solo se serve.
     * La sottoclasse decide se inserire più di un field nel body, oppure di svuotarlo ogni volta che cambia il field
     * <p>
     * Metodo invocato dalla sottoclasse
     */
    protected void addBodyField(Attribute attr) {
        String fieldName = attr.getName();
        Field field = bindMap.get(fieldName);

        if (field == null) {
            field = creaField(attr);
            if (field != null) {
                addField(attr, field);
            }// end of if cycle
        }// fine del blocco if

        if (body != null) {
            body.addComponent(field);
        }// fine del blocco if
    }// end of method

    /**
     * Create the UI component.
     * <p>
     * Retrieve the fields from the map and place them in the UI. Implementazione di default nella superclasse. I campi
     * vengono allineati verticalmente. Se si vuole aggiungere un campo, usare il metodo sovrascritto nella sottoclasse
     * richiamando prima il metodo della superclasse. Se si vuole un layout completamente differente, implementare il
     * metodo sovrascritto da solo.
     */
    protected Component createComponent() {

        FormLayout layout = new AFormLayout();

        if (bindMap != null) {
            for (Object key : bindMap.keySet()) {
                layout.addComponent(this.getField(key));
            }// end of for cycle
        }// end of if cycle

        return incapsulaPerMargine(layout);
    }// end of method

    protected Toolbar createToolBar() {
        // create the toolbar
        FormToolbar toolbar = new FormToolbar();
        toolbar.addToolbarListener(new FormToolbarListener() {

            @Override
            public void save_() {
                if (onPreSave(bindMap, isNewRecord())) {
                    if (save(bindMap, isNewRecord())) {
                        onPostSave(getItem(), isNewRecord());
                    }
                }
            }// end of method

            @Override
            public void reset_() {
                // TODO Auto-generated method stub
            }// end of method

            @Override
            public void cancel_() {
                fire(FormEvent.cancel);
            }// end of method

        });// end of anonymous class

        return toolbar;
    }// end of method

    /**
     * Adds a field with a given key to the fields map and binds the field to the property
     * <p>
     * If the key is of type Attribute, the attribute's name is used as key
     * and a Validator is added for attribute's type.
     *
     * @param key   - the key
     * @param field - the field
     */
    @SuppressWarnings("rawtypes")
    protected void addField(Object key, Field field) {

        if (key instanceof Attribute) {

            Attribute attr = (Attribute) key;

            // remove any existing validators
            // (if you want more validators, add them after the field is added to the form)
            // The reason to remove validators here, is that some fields (e.g. the Email field)
            // can already have their pre-built validator, and in this case we would end up
            // having multiple validators.
            field.removeAllValidators();

            // Add the bean validator
            Class clazz = attr.getJavaMember().getDeclaringClass();
            BeanValidator validator = new BeanValidator(clazz, attr.getName());
            field.addValidator(validator);

            // reassign the the key as the member name
            key = attr.getName();

        }// end of if cycle

        this.binder.bind(field, key);
        this.bindMap.put(key, field);

    }// end of method

    /**
     * Return a field
     *
     * @param key the key for the field if the key is Attribute, the attribute's name is used.
     * @return the field
     */
    protected Field getField(Object key) {

        if (bindMap != null) {
            if (key instanceof Attribute) {
                key = ((Attribute) key).getName();
            }// end of if cycle
        }// end of if cycle

        return this.bindMap.get(key);
    }// end of method


    /**
     * Saves the current values to the storage.
     * <p>
     *
     * @return true if saved successfully
     */
    protected boolean save() {
        return save(bindMap, isNewRecord());
    }


    /**
     * Saves the current values to the storage.
     * <p>
     *
     * @param fieldMap  bindMap map of the fields, the key is the field name
     * @param newRecord true if it is a new record
     * @return true if saved successfully
     */
    protected boolean save(LinkedHashMap<Object, Field> fieldMap, boolean newRecord) {
        boolean saved = false;
        ArrayList<String> reasons = isValid();
        if (reasons.size() == 0) {
            try {

                // This call validates all the fields.
                // If the validation is successfull the item is updated.
                // If the validation fails a CommitException is thrown.
                // If the item is managed by a writable container the changes
                // are persisted in the storage.
                binder.commit();

                // if the item is a new record we have to persist it now
                if (newRecord) {
                    if (item instanceof BeanItem) {
                        BeanItem<?> bi = (BeanItem<?>) item;
                        BaseEntity entity = (BaseEntity) bi.getBean();
                        entity.save();
                    }
                }

                // fire a commit event for who might be interested
                fire(FormEvent.commit);
                saved = true;

            } catch (CommitException e) {
                Notification.show("La scheda non è registrabile", Notification.Type.WARNING_MESSAGE);
            }

        } else {    // the form is invalid due to some specific reason (not covered by the validators)

            // show the reasons
            String string = "";
            for (String reason : reasons) {
                if (!string.equals("")) {
                    string += "<br>";
                }
                string += reason;
            }
            Notification notification = new Notification("La scheda non è valida", string, Notification.Type.HUMANIZED_MESSAGE);
            notification.setDelayMsec(-1);
            notification.show(Page.getCurrent());

        }

        return saved;
    }// end of method


    /**
     * Invoked before saving the item.
     * Chance for subclasses to override.
     *
     * @param fieldMap  bindMap map of the fields, the key is the field name
     * @param newRecord true if it is a new record
     * @return true to continue saving, false to stop saving
     */
    protected boolean onPreSave(LinkedHashMap<Object, Field> fieldMap, boolean newRecord) {
        return true;
    }

    /**
     * Invoked after the item has been saved.
     * Chance for subclasses to override.
     *
     * @param item      the saved item
     * @param newRecord true if it was a new record
     */
    protected void onPostSave(Item item, boolean newRecord) {
    }


    /**
     * Checks if the current values are valid and ready to be persisted.
     * <p>
     *
     * @return a list of strings containing the reasons if not valid, empty list if valid.
     */
    protected ArrayList<String> isValid() {
        ArrayList<String> reasons = new ArrayList<String>();
        return reasons;
    }


    /**
     * @return the Item
     */
    public Item getItem() {
        return item;
    }// end of method

    /**
     * Assigns an item to the form
     *
     * @PARAM the Item to assign
     */
    public void setItem(Item item) {
        this.item = item;
    }

    public ModulePop getModule() {
        return module;
    }

    public FieldGroup getBinder() {
        return binder;
    }

    /**
     * Indicates if the form is editing a new record
     * <p>
     * (the BeanItem has been created by the form and not passed in the constructor)
     */
    public boolean isNewRecord() {
        return newRecord;
    }

    /**
     * Return the form item as BeanItem
     *
     * @return the BeanItem
     */
    public BeanItem getBeanItem() {
        BeanItem bi = null;
        Item item = getItem();
        if ((item != null) && (item instanceof BeanItem)) {
            bi = (BeanItem) item;
        }
        return bi;
    }

    // /**
    // * Return the currently edited Bean
    // * @return the BeanItem
    // */
    // public Object getBean(){
    // Object bean = null;
    // BeanItem bi = getBeanItem();
    // if (bi!=null) {
    // bean = bi.getBean();
    // }
    // return bean;
    // }

    /**
     * Return the id of the item currently edited
     *
     * @return the item id
     */
    public long getItemId() {
        long id = 0;
        Item item = getItem();
        Property prop = item.getItemProperty("id");
        if (prop != null) {
            Object value = prop.getValue();
            if (value != null) {
                id = (long) value;
            }
        }
        return id;
    }

    /**
     * Retrieve the value of a given field
     *
     * @param key the key (String or Attribute)
     * @return the value
     */
    public Object getFieldValue(Object key) {
        Object value = null;
        if (key instanceof Attribute) {
            key = ((Attribute) key).getName();
        }
        Field field = this.bindMap.get(key);
        if (field != null) {
            value = field.getValue();
        }

        // Item item = getItem();
        // Property prop=item.getItemProperty(key);
        // if (prop!=null) {
        // value=prop.getValue();
        // }

        return value;
    }

    /**
     * Retrieve the value of a given field as String
     *
     * @param key the key
     * @return the value
     */
    public String getStringValue(Object key) {
        return Lib.getString(getFieldValue(key));
    }

    /**
     * Retrieve the value of a given field as int
     *
     * @param key the key
     * @return the value
     */
    public int getIntValue(Object key) {
        return Lib.getInt(getFieldValue(key));
    }

    /**
     * Retrieve the value of a given field as BigDecimal
     *
     * @param key the key
     * @return the value
     */
    public BigDecimal getBigDecimalValue(Object key) {
        return Lib.getBigDecimal(getFieldValue(key));
    }


    /**
     * Retrieve the value of a given field as long
     *
     * @param key the key
     * @return the value
     */
    public long getLongValue(Object key) {
        return Lib.getLong(getFieldValue(key));
    }

    /**
     * Add a form listener to the form
     */
    public void addFormListener(FormListener listener) {
        listeners.add(listener);
    }// end of method

    protected void fire(FormEvent event) {
        for (FormListener l : listeners) {
            switch (event) {
                case cancel:
                    l.cancel_();
                    break;
                case commit:
                    l.commit_();
                    break;
                default:
                    break;
            }// end of switch cycle
        }// end of for cycle
    }// end of method

    // incapsula tutto in un layout con margine perché setMargin
    // sembra non funzionare con Valo in FormLayout
    protected Component incapsulaPerMargine(Component comp) {
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.setMargin(true);
        vLayout.addComponent(comp);
        return vLayout;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }


    /**
     * @return the Window the component is attached to
     */
    public Window getWindow() {
        return findAncestor(Window.class);
    }

    public enum FormEvent {
        cancel, commit;
    }// end of inner enumeration

    /**
     * Form high-level events
     */
    public interface FormListener {
        public void cancel_();

        public void commit_();
    }// end of inner interface


}// end of class
