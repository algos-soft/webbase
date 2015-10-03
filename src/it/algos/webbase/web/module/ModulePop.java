package it.algos.webbase.web.module;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Not;
import com.vaadin.data.util.filter.Or;
import com.vaadin.ui.Window;
import it.algos.webbase.web.dialog.ConfirmDialog;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.BaseEntity_;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.form.AForm;
import it.algos.webbase.web.form.AForm.FormListener;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.search.SearchManager;
import it.algos.webbase.web.table.ATable;
import it.algos.webbase.web.table.ATable.TableListener;
import it.algos.webbase.web.table.TablePortal;
import it.algos.webbase.web.toolbar.TableToolbar.TableToolbarListener;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Module displaying a table and allowing to edit the records in a form.
 */
@SuppressWarnings("serial")
public abstract class ModulePop extends Module {

    private static boolean MOSTRA_ID = false;
    protected Class<BaseEntity> entityClass;
    protected TablePortal tablePortal;

    // elenco dei campi da mostrare (ordinati) nel list, nel form e nel search
    protected Attribute<?, ?>[] fieldsList;
    protected Attribute<?, ?>[] fieldsForm;
    protected Attribute<?, ?>[] fieldsSearch;

    // titolo del dialogo nuovo
    private String titoloNew;

    // titolo del dialogo di modifica
    private String titoloEdit;

    // titolo del dialogo di ricerca
    private String titoloSearch;

    // indirizzo interno del modulo (serve nei menu)
    private String menuAddress;


    @SuppressWarnings({"unchecked", "rawtypes"})
    public ModulePop(Class entity) {
        this(entity, "");
    }// end of constructor

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ModulePop(Class entity, String menuAddress) {
        super();
        this.setMenuAddress(menuAddress);

        if (entity != null) {
            this.entityClass = (Class<BaseEntity>) entity;

            // spazzola tutti i campi della Entity
            this.setFieldsList(creaFieldsList());
            this.setFieldsForm(creaFieldsForm());
            this.setFieldsSearch(creaFieldsSearch());

            // regola i titoli (caption) dei dialogi specifici
            this.regolaCaptions();

            tablePortal = createTablePortal();
            tablePortal.setHeight("100%");

            tablePortal.addToolbarListener(new TableToolbarListener() {

                @Override
                public void edit_() {
                    edit();
                }// end of inner method

                @Override
                public void delete_() {
                    delete();
                }// end of inner method

                @Override
                public void create_() {
                    create();
                }// end of inner method

                @Override
                public void search_() {
                    search();
                }// end of inner method

                @Override
                public void selectedonly_() {
                    selectedonly();
                }// end of inner method

                @Override
                public void removeselected_() {
                    removeselected();
                }// end of inner method

                @Override
                public void showall_() {
                    showall();
                }// end of inner method

                @Override
                public void deselectall_() {
                    deselectall();
                }// end of inner method


            });// end of anonymous inner class

            // add listener to table events
            tablePortal.getTable().addTableListener(new TableListener() {

                @Override
                public void datachange_() {
                    tableDataChanged();
                }// end of inner method

                @Override
                public void created_() {
                }// end of inner method

                @Override
                public void attached_() {
                }// end of inner method
            });// end of anonymous inner class

            setCompositionRoot(tablePortal);
        }

    }// end of constructor

    /**
     * Crea i campi visibili nella lista (table)
     * <p>
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * Serve anche per l'ordine con cui vengono presentati i campi nella lista <br>
     */
    protected Attribute<?, ?>[] creaFieldsList() {
        return creaFieldsAll();
    }// end of method

    /**
     * Crea i campi visibili nella scheda (form)
     * <p>
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * Serve anche per l'ordine con cui vengono presentati i campi nella scheda <br>
     */
    protected Attribute<?, ?>[] creaFieldsForm() {
        return creaFieldsAll();
    }// end of method

    /**
     * Crea i campi visibili nella scheda (search)
     * <p>
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * Serve anche per l'ordine con cui vengono presentati i campi nella scheda <br>
     */
    protected Attribute<?, ?>[] creaFieldsSearch() {
        return creaFieldsAll();
    }// end of method

    /**
     * Crea i campi visibili
     * <p>
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * NON garantisce l'ordine con cui vengono presentati i campi nella scheda <br>
     * Può mostrare anche il campo ID, oppure no <br>
     * Se si vuole differenziare tra Table, Form e Search, <br>
     * sovrascrivere creaFieldsList, creaFieldsForm e creaFieldsSearch <br>
     */
    protected Attribute<?, ?>[] creaFieldsAll() {
        return getListaAttributi().toArray(new Attribute[0]);
    }// end of method

    /**
     * Crea i campi visibili
     * <p>
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * NON garantisce l'ordine con cui vengono presentati i campi nella scheda <br>
     * Può mostrare anche il campo ID, oppure no <br>
     * In ogni caso il campo ID viene posizionato a sinistra/per primo nel Form e nella Lista
     */
    public ArrayList<Attribute<?, ?>> getListaAttributi() {
        ArrayList<Attribute<?, ?>> lista = new ArrayList<Attribute<?, ?>>();
        EntityType<?> type = EM.getEntityType(getEntityClass());
        Set<?> attributes = type.getAttributes();
        Attribute<?, ?> attribute;

        if (MOSTRA_ID) {
            lista.add(BaseEntity_.id);
        }// fine del blocco if
        for (Object ogg : attributes) {
            if (ogg instanceof Attribute<?, ?>) {
                attribute = (Attribute<?, ?>) ogg;
                if (!attribute.getName().equals(BaseEntity_.id.getName())) {
                    lista.add(attribute);
                }// fine del blocco if
            }// fine del blocco if
        }// end of for cycle

        return lista;
    }// end of method

    /**
     * Regola i titoli (caption) dei dialoghi specifici.
     * <p>
     * Regola il titolo (caption) dei dialogo nuovo record. <br>
     * Regola il titolo (caption) dei dialogo di modifica. <br>
     * Regola il titolo (caption) dei dialogo di ricerca. <br>
     */
    protected void regolaCaptions() {
        this.setTitoloNew(getCaptionNew());
        this.setTitoloEdit(getCaptionEdit());
        this.setTitoloSearch(getCaptionSearch());
    }// end of method

    /**
     * Titolo (caption) dei dialogo nuovo record. <br>
     * Come default usa il titolo standard <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     */
    protected String getCaptionNew() {
        return "Nuovo";
    }// end of method

    /**
     * Titolo (caption) dei dialogo di modifica. <br>
     * Come default usa il titolo standard <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     */
    protected String getCaptionEdit() {
        return "Modifica";
    }// end of method

    /**
     * Titolo (caption) dei dialogo di ricerca. <br>
     * Come default usa il titolo standard <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     */
    protected String getCaptionSearch() {
        return "Ricerca";
    }// end of method

    /**
     * Returns the table used to shows the list. <br>
     * The concrete subclass must override for a specific Table.
     *
     * @return the Table
     */
    public ATable createTable() {
        return (new ATable(this));
    }// end of method

    /**
     * Returns the form used to edit an item. <br>
     * The concrete subclass must override for a specific Form.
     *
     * @param item singola istanza della classe
     * @return the Form
     */
    public AForm createForm(Item item) {
        return (new AForm(this, item));
    }// end of method

    /**
     * Create the Search Manager
     *
     * @return the SearchManager
     */
    public SearchManager createSearchManager() {
        SearchManager manager = new SearchManager(this);
        return manager;
    }// end of method

    /**
     * Create button pressed in table
     * <p>
     * Create a new item and edit it in a form
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void create() {
        Object bean = BaseEntity.createBean(getEntityClass());
        BeanItem item = new BeanItem(bean);
        postCreate(item);
        editItem(item, true, getTitoloNew());

    }// end of method

    /**
     * Post create / pre edit item
     */
    protected void postCreate(Item item) {
    }// end of method


    /**
     * Post save item.
     * A item has been saved. Chance for subclasses to do something.
     *
     * @param item      the saved item
     * @param newRecord if the saved item is a new record
     */
    protected void postSave(Item item, boolean newRecord) {
    }

    /**
     * Edit button pressed in table Display the item in a form
     */
    @SuppressWarnings("rawtypes")
    public void edit() {
        Object itemId = getTable().getSelectedId();
        if (itemId != null) {
            Item item = getTable().getItem(itemId);
            editItem(item, false, getTitoloEdit());
        }
    }// end of method

    // /**
    // * Edit button pressed in table Display the item in a form
    // */
    // @SuppressWarnings("rawtypes")
    // public void editOld() {
    // long rowId = getTable().getSelectedIdOld();
    // if (rowId >= 0) {
    // BeanItem item = getTable().getBeanItem(rowId);
    // editItem(item, getTitoloEdit());
    // }// end of if cycle
    // }// end of method

    /**
     * Edits an Item in a Form
     *
     * @param item the item
     * @param newRecord if the edited item is a new record
     */
    private void editItem(Item item, boolean newRecord) {
        editItem(item, newRecord, "");
    }// end of method

    /**
     * Edits an Item in a Form
     *
     * @param item      the item
     * @param newRecord if the edited item is a new record
     * @param caption   title for the window
     */
    private void editItem(Item item, boolean newRecord, String caption) {

        final AForm form = createForm(item);

        if (form != null) {

            final Window window = new Window(caption, form);
            window.setResizable(false);

            form.addFormListener(new FormListener() {

                @SuppressWarnings({"rawtypes", "unchecked"})
                @Override
                public void commit_() {

                    postSave(item, newRecord);

                    window.close();

//					// If the item is a BeanItem let's assume it is a new record and save it.
//					// the save operation is delegated to the table's JPAContainer
//					Item item = form.getItem();
//					if (item instanceof BeanItem) {
//						BeanItem<?> bi = (BeanItem<?>) item;
//						BaseEntity entity = (BaseEntity) bi.getBean();
//						if (entity.getId() == null) { // double checking just to be sure it's new
//							JPAContainer container = getTable().getJPAContainer();
//							container.addEntity(entity);
//						}
//					}

                    // This is needed to update generated columns in the table.
                    // (standard columns bound to properties are updated
                    // automatically when the item changes)
                    getTable().refreshRowCache();

                    // Item item = form.getItem();
                    // BaseEntity bean = itemToBean(item);
                    // bean.save();
                    // getTable().refreshRowCache();

                    // Collection<?> pids = item.getItemPropertyIds();
                    // for (Object pid : pids) {
                    // Property prop = item.getItemProperty(pid);
                    // if (prop instanceof MethodProperty) {
                    // MethodProperty mprop = (MethodProperty)prop;
                    // mprop.fireValueChange();
                    // }
                    // int a = 87;
                    // int b = a;
                    //
                    // }

                    // JPAContainer cont = (JPAContainer)getTable().getContainerDataSource();
                    // cont.f
                    // Property prop = item.getItemProperty(BaseEntity_.id);
                    // prop.setValue(prop.getValue());
                    // int a = 87;
                    // int b = a;

                }// end of inner method

                @Override
                public void cancel_() {
                    window.close();
                }// end of inner method

            });// end of anonymous inner class

            form.setHeightUndefined();

            window.center();

            this.getUI().addWindow(window);

        }// end of if cycle

    }// end of method

    /**
     * Invoked before any single deletion.
     * <p>
     *
     * @return true to proceed, false to abort
     */
    protected boolean preDelete(BaseEntity bEntity) {
        return true;
    }// end of method

    /**
     * Delete selected items button pressed
     */
    public void delete() {
        final Object[] ids = getTable().getSelectedIds();
        if (ids.length > 0) {
            showDeleteDialog(ids);
        }// end of if cycle
    }// end of method

    /**
     * Show delete confirmation dialog, delete if confirmed
     */
    public void showDeleteDialog(final Object[] ids) {
        String msg = "";
        if (ids.length == 1) {
            msg = "Sei sicuro?";
        } else {
            msg = "Vuoi eliminare " + ids.length + " records?";
        }// end of if/else cycle

        ConfirmDialog dialog = new ConfirmDialog("Eliminazione", msg, new ConfirmDialog.Listener() {

            @Override
            public void onClose(ConfirmDialog dialog, boolean confirmed) {
                if (confirmed) {
                    delete(ids);
                }// end of if cycle
            }// end of inner method
        });// end of anonymous inner class
        dialog.setConfirmButtonText("Elimina");
        dialog.show(getUI());

    }

    /**
     * Delete items from container
     */
    public void delete(Object[] ids) {
        for (Object id : ids) {
            delete(id);
        }// end of for cycle
        getTable().refreshRowCache();
    }

    public void delete(Object id) {
        // delegate to the table's JPAContainer
        getTable().getJPAContainer().removeItem(id);
    }

    /**
     * Search button pressed in table
     * <p>
     * Displays the Search dialog
     */
    public void search() {
        final SearchManager sm = createSearchManager();
        if (sm != null) {
            sm.setCloseListener(new ConfirmDialog.Listener() {

                @Override
                public void onClose(ConfirmDialog dialog, boolean confirmed) {
                    if (confirmed) {

                        JPAContainer<?> cont = getTable().getJPAContainer();

                        Filter filter = sm.getFilter();
                        if (filter != null) {

                            SearchManager.CombineSearchOptions option = sm.getCombineOption();
                            if (option != null) {

                                // concatenates the current filters with the And
                                // clause
                                List<Filter> currentFilters = cont.getFilters();
                                Filter[] aFilters = currentFilters.toArray(new Filter[0]);
                                Filter currentFilter = new And(aFilters);
                                Filter newFilter;
                                switch (option) {
                                    case addToList: // add to current records (or)

                                        // creates a new filter
                                        newFilter = new Or(currentFilter, filter);

                                        // sets the new filter in the container
                                        cont.removeAllContainerFilters();
                                        cont.refresh(); // refresh before applying
                                        // new filters
                                        if (filter != null) {
                                            cont.addContainerFilter(newFilter);
                                        }// end of if cycle
                                        break;

                                    case removeFromList: // subtract from current
                                        // (and not)

                                        // creates a new filter
                                        Filter notFilter = new Not(filter);
                                        newFilter = new And(currentFilter, notFilter);

                                        // sets the new filter in the container
                                        cont.removeAllContainerFilters();
                                        cont.refresh(); // refresh before applying
                                        // new filters
                                        if (filter != null) {
                                            cont.addContainerFilter(newFilter);
                                        }// end of if cycle
                                        break;

                                    case searchInList: // search in current (and)
                                        // add a new filter to the container
                                        cont.addContainerFilter(filter);
                                        break;
                                }// end of switch cycle

                            } else { // no combine option

                                cont.removeAllContainerFilters();
                                // cont.refresh(); // refresh before applying
                                // new filters -- disabled alex 24-09-2014
                                // too slow
                                if (filter != null) {
                                    cont.addContainerFilter(filter);
                                }// end of if cycle

                            }// end of if/else cycle

                        } else { // null filter
                            cont.removeAllContainerFilters();
                        }// end of if/else cycle

                    }// end of if cycle
                }// end of inner method
            });// end of anonymous inner class
            sm.show(getUI());
        }// end of if cycle

    }// end of method

    /**
     * Shows in the table only the selected rows
     */
    public void selectedonly() {
        JPAContainer<?> cont = getTable().getJPAContainer();
        if (cont != null) {
            Filter filter = createFilterForSelectedRows();
            cont.removeAllContainerFilters();
            cont.addContainerFilter(filter);
            cont.refresh();
        }// end of if cycle
    }// end of method

    /**
     * Removes the selected rows from the table
     */
    public void removeselected() {
        JPAContainer<?> cont = getTable().getJPAContainer();
        if (cont != null) {
            Filter filter = new Not(createFilterForSelectedRows());
            cont.addContainerFilter(filter);
            cont.refresh();
        }// end of if cycle
    }// end of method

    /**
     * Shows all the records in the table
     */
    public void showall() {
        JPAContainer<?> cont = getTable().getJPAContainer();
        if (cont != null) {
            cont.removeAllContainerFilters();
            cont.refresh();
        }// end of if cycle
    }// end of method

    /**
     * Deselects all the rows in the table
     */
    public void deselectall() {
        getTable().setValue(null);
    }// end of method


    /**
     * Creates a filter corresponding to the currently selected rows in the table
     * <p>
     */
    private Filter createFilterForSelectedRows() {
        Object[] ids = getTable().getSelectedIds();
        Filter[] filters = new Filter[ids.length];
        int idx = 0;
        for (Object id : ids) {
            filters[idx] = new Compare.Equal("id", id);
            idx++;
        }// end of for cycle
        Filter filter = new Or(filters);
        return filter;
    }// end of method

    /**
     * Create the Table Portal
     *
     * @return the TablePortal
     */
    public TablePortal createTablePortal() {
        return new TablePortal((ModulePop) this);
    }// end of method

    /**
     * Invoked when table data changes
     */
    protected void tableDataChanged() {
        String visibleTxt = "";
        String totalTxt = "";
        int visible = getTable().getVisibleRows();
        long total = getTable().getTotalRows();

        visibleTxt = LibNum.format(visible);
        totalTxt = LibNum.format(total);

        String text = visibleTxt + "/" + totalTxt + " records";
        getTablePortal().getToolbar().setInfoText(text);
    }// end of method

    /**
     * Returns a list of the visible columns
     *
     * @return the Form
     */
    public Object[] getVisibleColumns() {
        return null;
    }// end of method

    public TablePortal getTablePortal() {
        return tablePortal;
    }// end of method

    public ATable getTable() {
        return getTablePortal().getTable();
    }// end of method

    // get the selected rows id
    protected Long getValue() {
        Long rowId = null;

        if (getTable() != null && getTable().getValue() instanceof Long) {
            rowId = (Long) getTable().getValue();
        }// end of if cycle

        return rowId;
    }// end of method

    // get the selected item
    protected Item getTableItem() {
        Item item = null;
        Long rowId = getValue();

        if (rowId != null && rowId > 0) {
            item = getTable().getItem(rowId);
        }// end of if cycle

        return item;
    }// end of method

    @SuppressWarnings("rawtypes")
    private BaseEntity itemToBean(Item item) {
        BaseEntity entity = null;
        if (item instanceof BeanItem) {
            Object bean = ((BeanItem) item).getBean();
            if (bean instanceof BaseEntity) {
                entity = (BaseEntity) bean;
            }// end of if cycle

        }// end of if cycle
        return entity;
    }// end of method

    public Class<BaseEntity> getEntityClass() {
        return entityClass;
    }// end of method

    /**
     * @return the fieldsList
     */
    @SuppressWarnings("rawtypes")
    public Attribute[] getFieldsList() {
        return fieldsList;
    }// end of method

    /**
     * @param fieldsList the fieldsList to set
     */
    @SuppressWarnings("rawtypes")
    public void setFieldsList(Attribute[] fieldsList) {
        this.fieldsList = fieldsList;
    }// end of method

    /**
     * @return the fieldsForm
     */
    @SuppressWarnings("rawtypes")
    public Attribute[] getFieldsForm() {
        return fieldsForm;
    }// end of method

    /**
     * @param fieldsForm the fieldsForm to set
     */
    @SuppressWarnings("rawtypes")
    public void setFieldsForm(Attribute[] fieldsForm) {
        this.fieldsForm = fieldsForm;
    }// end of method

    /**
     * @return the fieldsSearch
     */
    @SuppressWarnings("rawtypes")
    public Attribute[] getFieldsSearch() {
        return fieldsSearch;
    }// end of method

    /**
     * @param fieldsSearch the fieldsSearch to set
     */
    @SuppressWarnings("rawtypes")
    public void setFieldsSearch(Attribute[] fieldsSearch) {
        this.fieldsSearch = fieldsSearch;
    }// end of method

    /**
     * @return the titoloSearch
     */
    public String getTitoloSearch() {
        return titoloSearch;
    }// end of method

    /**
     * @param titoloSearch the titoloSearch to set
     */
    public void setTitoloSearch(String titoloSearch) {
        this.titoloSearch = titoloSearch;
    }// end of method

    /**
     * @return the titoloNew
     */
    public String getTitoloNew() {
        return titoloNew;
    }

    /**
     * @param titoloNew the titoloNew to set
     */
    public void setTitoloNew(String titoloNew) {
        this.titoloNew = titoloNew;
    }

    /**
     * @return the titoloEdit
     */
    public String getTitoloEdit() {
        return titoloEdit;
    }

    /**
     * @param titoloEdit the titoloEdit to set
     */
    public void setTitoloEdit(String titoloEdit) {
        this.titoloEdit = titoloEdit;
    }

    public String getClassName() {
        String name = "";
        String tag = ".";

        if (entityClass != null) {
            name = this.entityClass.getCanonicalName();
        }// end of if cycle

        if (!name.equals("")) {
            if (name.contains(tag)) {
                name = name.substring(name.lastIndexOf(tag) + 1);
            }// end of if cycle
        }// end of if cycle

        return name;
    }// end of method

    public String getMenuAddress() {
        return menuAddress;
    }// end of method

    private void setMenuAddress(String menuAddress) {
        this.menuAddress = menuAddress;
    }// end of method


}// end of class
