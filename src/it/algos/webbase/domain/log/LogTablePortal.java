package it.algos.webbase.domain.log;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Or;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import it.algos.webbase.web.AlgosApp;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.TablePortal;
import it.algos.webbase.web.toolbar.TableToolbar;

/**
 * Created by gac on 23 set 2015.
 * .
 */
public class LogTablePortal extends TablePortal {
    private TableToolbar toolbar;

    public LogTablePortal(ModulePop modulo) {
        super(modulo);
    }// end of constructor

    public TableToolbar createToolbar() {
        toolbar = super.createToolbar();
        final String msg = "Messaggio di controllo";

//        addSelect();

//        // bottone New Ciclo
//        toolbar.addButton("Ciclo new", FontAwesome.COG, new MenuBar.Command() {
//            public void menuSelected(MenuBar.MenuItem selectedItem) {
//                if (Pref.getBool(CostBio.USA_DIALOGHI_CONFERMA)) {
//                    int maxDowloadNew = Pref.getInt(CostBio.MAX_DOWNLOAD_BIO_CICLO_NEW);
//                    String newMsg = "Esegue un ciclo di sincronizzazione tra le pagine della categoria TAG_BIO ed i records della tavola Wikibio";
//                    newMsg += "<br>Esegue un ciclo di controllo e creazione di nuovi records esistenti nella categoria e mancanti nel database";
//                    newMsg += "<br>Esegue un ciclo di controllo e cancellazione di records esistenti nel database e mancanti nella categoria";
//                    newMsg += "<br>Le preferenze prevedono di scaricare ";
//                    newMsg += LibNum.format(maxDowloadNew);
//                    newMsg += " voci dal server";
//                    newMsg += "<br>Occorre diverso tempo";
//                    ConfirmDialog dialog = new ConfirmDialog(msg, newMsg,
//                            new ConfirmDialog.Listener() {
//                                @Override
//                                public void onClose(ConfirmDialog dialog, boolean confirmed) {
//                                    if (confirmed) {
//                                        new CicloNew();
//                                    }// end of if cycle
//                                }// end of inner method
//                            });// end of anonymous inner class
//                    dialog.show(getUI());
//                } else {
//                    new CicloNew();
//                }// fine del blocco if-else
//            }// end of method
//        });// end of anonymous class


        return toolbar;
    }// end of method


    /**
     * Bottone selection.
     */
    protected void addSelect() {
        MenuBar.MenuItem item = null;

        item = toolbar.addButton("Filtro", FontAwesome.NAVICON, null);

        item.addItem("Debug", FontAwesome.FILE_TEXT, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                filtroDebug();
            }// end of inner method
        });// end of anonymous inner class

        item.addItem("Info", FontAwesome.FILE_TEXT, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                filtroInfo();
            }// end of inner method
        });// end of anonymous inner class

    }// end of method


    /**
     * Shows in the table only the debug level
     */
    public void filtroDebug() {
        JPAContainer<?> cont = getTable().getJPAContainer();
        if (cont != null) {
            Container.Filter filter = createFilterForDebugRows();
            cont.removeAllContainerFilters();
            cont.addContainerFilter(filter);
            cont.refresh();
        }// end of if cycle
    }// end of method

    /**
     * Shows in the table only the debug level
     */
    public void filtroInfo() {
        JPAContainer<?> cont = getTable().getJPAContainer();
        if (cont != null) {
            Container.Filter filter = createFilterForInfoRows();
            cont.removeAllContainerFilters();
            cont.addContainerFilter(filter);
            cont.refresh();
        }// end of if cycle
    }// end of method

    /**
     * Creates a filter corresponding to the debug level rows in the table
     * <p>
     */
    private Container.Filter createFilterForDebugRows() {
        Container.Filter filter = new Compare.Equal(Log_.livello,Livello.debug);
        return filter;
    }// end of method

    /**
     * Creates a filter corresponding to the debug level rows in the table
     * <p>
     */
    private Container.Filter createFilterForInfoRows() {
        Container.Filter filter = new Compare.Equal(Log_.livello,Livello.info);
        return filter;
    }// end of method


}// end of class

