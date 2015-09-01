package it.algos.webbase.web.ui;

import com.vaadin.server.*;
import com.vaadin.ui.*;
import it.algos.webbase.web.AlgosApp;
import it.algos.webbase.web.Command.MenuCommand;
import it.algos.webbase.web.lib.LibPath;
import it.algos.webbase.web.lib.LibSession;
import it.algos.webbase.web.menu.AMenuBar;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.navigator.AlgosNavigator;
import it.algos.webbase.web.navigator.NavPlaceholder;

import java.util.Map;

/**
 * The topmost component in any component hierarchy. There is one UI for every Vaadin instance in a browser window. A UI
 * may either represent an entire browser window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is the server side entry point for various client side features that are not represented as components added
 * to a layout, e.g notifications, sub windows, and executing javascript in the browser.
 * </p>
 * <p>
 * When a new UI instance is needed, typically because the user opens a URL in a browser window which points to e.g.
 * {@link VaadinServlet}, all {@link UIProvider}s registered to the current {@link VaadinSession} are queried for the UI
 * class that should be used. The selection is by default based on the <code>UI</code> init parameter from web.xml.
 * </p>
 * <p>
 * After a UI has been created by the application, it is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden by the developer to add components to the user interface and initialize non-component
 * functionality. The component hierarchy must be initialized by passing a {@link Component} with the main layout or
 * other content of the view to {@link #setContent(Component)} or to the constructor of the UI.
 * </p>
 *
 * @see #init(VaadinRequest)
 * @see UIProvider
 * @since 7.0
 */
@SuppressWarnings("serial")
public class AlgosUI extends UI {

    protected VerticalLayout mainLayout;
    protected AMenuBar barraMenu;
    protected NavPlaceholder placeholder;
    private MenuBar mainBar = new MenuBar();

    /**
     * Initializes this UI. This method is intended to be overridden by subclasses to build the view and configure
     * non-component functionality. Performing the initialization in a constructor is not suggested as the state of the
     * UI is not properly set up when the constructor is invoked.
     * <p>
     * The {@link VaadinRequest} can be used to get information about the request that caused this UI to be created.
     * </p>
     * Viene normalmente sovrascitta dalla sottoclasse per aggiungere i moduli alla menubar dell'applicazione <br>
     * Deve (DEVE) richiamare anche il metodo della superclasse (questo) <br>
     *
     * @param request the Vaadin request that caused this UI to be created
     */
    @Override
    protected void init(VaadinRequest request) {

        // Legge eventuali parametri passati nella request
        this.checkParams(request);

        // Controlla il login della security
        this.checkSecurity(request);

        // Regola il menu
        startUI();
//        this.regolaMenu();
    }// end of method

    /**
     * Legge eventuali parametri passati nella request
     * <p>
     */
    protected void checkParams(VaadinRequest request) {

        // legge il parametro "developer" (se esiste) e regola la variabile statica
        LibSession.checkDeveloper(request);

        // legge il parametro "debug" (se esiste) e regola la variabile statica
        LibSession.checkDebug(request);

    }// end of method


    /**
     * Controlla il login della security
     * <p>
     */
    protected void checkSecurity(VaadinRequest request) {
    }// end of method

//    /**
//     * Regola il menu
//     * <p>
//     */
//    protected void regolaMenu() {
//        mainLayout = new VerticalLayout();
//        mainLayout.setMargin(true);
//        mainLayout.setSpacing(true);
//        // mainLayout.setHeight("100%");
//        // mainLayout.setWidth("100%");
//
//        // crea e aggiunge il placeholder
//        placeholder = new ModulePlaceholder();
//
//        if (AlgosApp.USE_SECURITY) {
//            barraMenu = new AMenuBar(true);
//        } else {
//            barraMenu = new AMenuBar();
//        }// end of if/else cycle
//
//        mainLayout.addComponent(barraMenu);
//        mainLayout.addComponent(placeholder);
////        mainLayout.setExpandRatio(barraMenu, 1.0f);
//        mainLayout.setExpandRatio(placeholder, 1.0f);
//
//        setContent(mainLayout);
//    }// end of method

    /**
     * Mostra la UI
     */
    private void startUI() {

        // crea la UI di base, un VerticalLayout
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.setMargin(true);
        vLayout.setSpacing(false);
        vLayout.setSizeFull();

        // crea la MenuBar principale
//        vLayout.addComponent(mainBar);

        // aggiunge la menubar principale e la menubar login
        HorizontalLayout menuLayout = new HorizontalLayout();
        menuLayout.setHeight("40px");
        menuLayout.setWidth("100%");
        menuLayout.addComponent(mainBar);
        mainBar.setHeight("100%");
        menuLayout.setExpandRatio(mainBar, 1.0f);

        if (AlgosApp.USE_SECURITY) {
            MenuBar loginBar = createLoginMenuBar();
            loginBar.setHeight("100%");
            menuLayout.addComponent(loginBar);
        }// fine del blocco if

        vLayout.addComponent(menuLayout);

        // crea e aggiunge uno spaziatore verticale
        HorizontalLayout spacer = new HorizontalLayout();
        spacer.setMargin(false);
        spacer.setSpacing(false);
        spacer.setHeight("5px");
        vLayout.addComponent(spacer);
//
        // crea e aggiunge il placeholder dove il Navigator inserirà le varie pagine
        // a seconda delle selezioni di menu
        // crea e aggiunge il placeholder
        placeholder = new NavPlaceholder(null);
        placeholder.setSizeFull();
        vLayout.addComponent(placeholder);
        vLayout.setExpandRatio(placeholder, 1.0f);

        // assegna la UI
        setContent(vLayout);

        // crea un Navigator e lo configura in base ai contenuti della MenuBar
        AlgosNavigator nav = new AlgosNavigator(getUI(), placeholder);
        nav.configureFromMenubar(mainBar);
        nav.navigateTo("Bolla");

        this.addAllModuli();

//        // set browser window title
//        Page.getCurrent().setTitle("Sistemare");

    }// end of method

    /**
     * Legge il parametro "azienda" e regola la variabile statica
     */
    protected void leggeAzienda(VaadinRequest request) {
        String company = "";
        Map<String, String[]> mappa = null;

        // legge il parametro "azienda" e regola la variabile statica
        if (AlgosApp.USE_COMPANY) {
            mappa = request.getParameterMap();

            // ci sono 17 valori di sistema
            if (mappa.size() == 18) {
                company = "demo";
            }// end of if cycle
        }// end of if cycle

        AlgosApp.COMPANY_CODE = company;
    }// end of method


    /**
     * Crea la menubar di Login
     */
    private MenuBar createLoginMenuBar() {
        MenuBar menubar = new MenuBar();
        MenuBar.MenuItem loginItem; // il menuItem di login


        ThemeResource icon = new ThemeResource("img/action_user.png");
//        MenuBar.Command command = new MenuBar.Command() {
//
//            @Override
//            public void menuSelected(MenuItem selectedItem) {
//                loginCommandSelected();
//            }
//        };

        loginItem = menubar.addItem("Login", null, null);
//        updateLoginUI();

//        loginItem.addItem("Logout", new MenuBar.Command() {
//            @Override
//            public void menuSelected(MenuItem selectedItem) {
//                logout();
//            }
//        });

        return menubar;
    }

    /**
     * Crea i menu specifici
     * Sovrascritto nella sottoclasse
     */
    protected void addAllModuli() {
    }// end of method

    /**
     * Crea il singolo menu
     */
    protected void addModulo(ModulePop modulo) {
        UI ui = this.getUI();
        String address = "";

        address = LibPath.getClassName(modulo.getEntityClass());

        MenuCommand command = new MenuCommand(ui, mainBar, address, modulo);
        mainBar.addItem(address, null, command);
    }// end of method

    /**
     * Aggiunge un modulo alla barra di menu.
     * <p>
     * Il modulo viene visualizzato nel placeholder <br>
     */
    protected void addModule(CustomComponent module) {
        ModulePop modulo = null;
        String titoloMenu = "";

        if (module != null) {
            modulo = (ModulePop) module;
        }// end of if cycle

        if (modulo != null) {
            titoloMenu = modulo.getClassName();
        }// end of if cycle

        if (!titoloMenu.equals("")) {
            barraMenu.addMenu(titoloMenu, module, placeholder);
        }// end of if cycle

    }// end of method

}// end of class
