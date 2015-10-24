package it.algos.webbase.web.table;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.VerticalLayout;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.toolbar.TableToolbar;
import it.algos.webbase.web.toolbar.TableToolbar.TableToolbarListener;

/**
 * Portal hosting a table and a toolbar
 */

@SuppressWarnings("serial")
public class TablePortal extends VerticalLayout {

    protected ATable table;
    protected TableToolbar toolbar;
    protected TableFooter footer;
    private ModulePop module;

    public TablePortal(ATable table) {
        super();
        this.table = table;
        init();
    }// end of constructor

    public TablePortal(ModulePop modulo) {
        super();
        this.module = modulo;
        this.table = modulo.createTable();
        init();
    }// end of constructor

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
                }// end of if cycle
            }// end of method
        });// end of anonymous class

        // Sincronizzazione dei listener per il funzionamento della ATable
        regolaListenerTable();
    }// end of method

    public TableToolbar createToolbar() {
        TableToolbar toolbar = new TableToolbar();
        return toolbar;
    }// end of method

    public TableFooter createFooter() {
        return new TableFooter();
    }// end of method

    /**
     * Sincronizzazione dei listener per il funzionamento della ATable
     * <p>
     * Registra la TableToolBar nei selectionListeners della ATable per gli eventi di tipo ListSelectionEvent
     */
    protected void regolaListenerTable() {
        ATable table = this.getTable();
        TableToolbar tableToolBar = this.getToolbar();

        table.addListListener(tableToolBar);
        table.selectionChanged(null); // regolazione iniziale con nessuna riga selezionata
    }// end of method


    public void addToolbarListener(TableToolbarListener listener) {
        getToolbar().addToolbarListener(listener);
    }// end of method

    public ATable getTable() {
        return table;
    }// end of method

    public TableToolbar getToolbar() {
        return toolbar;
    }// end of method

    public ModulePop getModule() {
        return module;
    }
}// end of class
