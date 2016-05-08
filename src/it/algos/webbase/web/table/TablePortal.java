package it.algos.webbase.web.table;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.toolbar.TableToolbar;
import it.algos.webbase.web.toolbar.TableToolbar.TableToolbarListener;

/**
 * Portal hosting a table and a toolbar
 */
@SuppressWarnings("serial")
public class TablePortal extends VerticalLayout implements ATable.SelectionChangeListener {

    protected ATable table;
    protected TableToolbar toolbar;
    protected TableFooter footer;
    private ModulePop module;

    public TablePortal(ATable table) {
        super();
        this.table = table;
        init();
    }

    public TablePortal(ModulePop modulo) {
        super();
        this.module = modulo;
        this.table = modulo.createTable();
        init();
    }

    protected void init() {

        // creates the table
        // this.table = createTable();
        this.table.setWidth("100%");
        this.table.setHeight("100%");

        // creates the table footer
        this.footer = createFooter();
        this.footer.setWidth("100%");

        // creates the toolbar
        this.toolbar = createToolbar();
        this.toolbar.setWidth("100%");

        // adds them to the portal
        addComponent(this.table);
        // addComponent(this.footer);
        addComponent(this.toolbar);
        setExpandRatio(this.table, 1f);

        // aggiunge un double click listener alla table.
        // Quando lo riceve, invoca un apposito metodo fireEdit()
        // sulla toolbar, simulando la pressione del bottone edit
        table.addItemClickListener(new ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    getToolbar().fireEdit();
                }
            }
        });

        // The toolbar listens to table selection changes
        getTable().addSelectionChangeListener(this.getToolbar());

        // The portal listens to table selection changes
        //@todo aggiunto da gac - 7.5.16
        getTable().addSelectionChangeListener(this);

    }// end of method

    public TableToolbar createToolbar() {
        TableToolbar toolbar = new TableToolbar();
        return toolbar;
    }

    public TableFooter createFooter() {
        return new TableFooter();
    }

//    /**
//     * Sincronizzazione dei listener per il funzionamento della ATable
//     * <p>
//     * Registra la TableToolBar nei selectionListeners della ATable per gli eventi di tipo ListSelectionEvent
//     */
//    protected void regolaListenerTable() {
//        ATable table = this.getTable();
//        TableToolbar tableToolBar = this.getToolbar();
//        table.addSelectionChangeListener(tableToolBar);
//    }


    public void addToolbarListener(TableToolbarListener listener) {
        TableToolbar tb = getToolbar();
        if (tb != null) {
            tb.addToolbarListener(listener);
        }
    }


    public ATable getTable() {
        return table;
    }

    public TableToolbar getToolbar() {
        return toolbar;
    }

    public ModulePop getModule() {
        return module;
    }

    @Override
    public void selectionChanged(ATable.SelectionChangeEvent e) {
    }// end of method


    /*
     * Elimina un comando dalla GUI.
     */
    public void delCmd(String label) {
        toolbar.delCmd(label);
    }// end of method

    /**
     * Recupera il componente grafico corrispondente al comando indicato.
     */
    public Component getComp(MenuBar.MenuItem item) {
        return getComp(item.getText());
    }// end of method

    /**
     * Recupera il componente grafico corrispondente al comando indicato.
     */
    public Component getComp(String label) {
        return toolbar.getComp(label);
    }// end of method

}// end of class
