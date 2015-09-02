package it.algos.webbase.web.ui;

import com.vaadin.server.*;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import it.algos.webbase.domain.versione.VersioneModulo;
import it.algos.webbase.web.AlgosApp;
import it.algos.webbase.web.lib.LibSession;
import it.algos.webbase.web.menu.AMenuBar;
import it.algos.webbase.web.module.ModulePop;
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

    protected static boolean DEBUG_GUI = false;

    protected VerticalLayout mainLayout;        // main
    protected AMenuBar topLayout;               // top
    protected NavPlaceholder placeholder;       // body
    protected HorizontalLayout footerLayout;    // footer

    protected ModulePop moduloPartenza;
//    private MenuBar mainBar = new MenuBar();

    /**
     * Initializes this UI. This method is intended to be overridden by subclasses to build the view and configure
     * non-component functionality. Performing the initialization in a constructor is not suggested as the state of the
     * UI is not properly set up when the constructor is invoked.
     * <p>
     * The {@link VaadinRequest} can be used to get information about the request that caused this UI to be created.
     * </p>
     *
     * @param request the Vaadin request that caused this UI to be created
     */
    @Override
    protected void init(VaadinRequest request) {

        // Legge eventuali parametri passati nella request
        this.checkParams(request);

        // Controlla il login della security
        this.checkSecurity(request);

        // Crea l'interfaccia utente (User Interface) iniziale dell'applicazione
        this.startUI();
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


    /**
     * Crea l'interfaccia utente (User Interface) iniziale dell'applicazione
     * Crea i menu specifici
     * Layout standard composto da:
     * Top      - una barra composita di menu e login
     * Body     - un placeholder per il portale della tavola/modulo
     * Footer   - un striscia per eventuali informazioni (Algo, copyright, ecc)
     * <p>
     * Se le applicazioni specifiche vogliono una UI differente, possono sovrascrivere questo metodo nella sottoclasse
     */
    protected void startUI() {

        // crea la UI di base, un VerticalLayout
        mainLayout = this.creaMain();

        // crea ed aggiunge la menubar principale e la menubar login tramite una classe dedicata
        topLayout = this.creaTop();
        mainLayout.addComponent(topLayout);

        // crea e aggiunge uno spaziatore verticale
        this.addSpacer();

        // crea e aggiunge il placeholder dove il Navigator inserirà le varie pagine a seconda delle selezioni di menu
        placeholder = this.creaBody();
        mainLayout.addComponent(placeholder);
        mainLayout.setExpandRatio(placeholder, 1.0f);

        // crea ed aggiunge il footer
        footerLayout = this.creaFooter();
        mainLayout.addComponent(footerLayout);

        // assegna la UI
        setContent(mainLayout);

//        // crea un Navigator e lo configura in base ai contenuti della MenuBar
//        AlgosNavigator nav = new AlgosNavigator(getUI(), placeholder);
//        nav.configureFromMenubar(topLayout);
//        nav.navigateTo("Bolla");
//
        moduloPartenza = new VersioneModulo();
        this.addAllModuli();

        // set browser window title
        Page.getCurrent().setTitle("Vaadin");

    }// end of method

    /**
     * Crea l'interfaccia utente (User Interface) iniziale dell'applicazione
     * Crea la UI di base, un VerticalLayout
     * Le applicazioni specifiche, possono sovrascrivere questo metodo nella sottoclasse
     *
     * @return layout -  un VerticalLayout
     */
    protected VerticalLayout creaMain() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(false);
        layout.setSizeFull();

        if (DEBUG_GUI) {
            layout.addStyleName("blueBg");
        }// fine del blocco if

        return layout;
    }// end of method

    /**
     * Crea l'interfaccia utente (User Interface) iniziale dell'applicazione
     * Top  - una barra composita di menu e login
     * Le applicazioni specifiche, possono sovrascrivere questo metodo nella sottoclasse
     *
     * @return layout - normalmente un AMenuBar
     */
    protected AMenuBar creaTop() {
        AMenuBar menubar = new AMenuBar(AlgosApp.USE_SECURITY);

        if (DEBUG_GUI) {
            menubar.addStyleName("yellowBg");
        }// fine del blocco if

        return menubar;
    }// end of method

    /**
     * Crea l'interfaccia utente (User Interface) iniziale dell'applicazione
     * Body - un placeholder per il portale della tavola/modulo
     * Le applicazioni specifiche, possono sovrascrivere questo metodo nella sottoclasse
     *
     * @return layout - normalmente un NavPlaceholder
     */
    protected NavPlaceholder creaBody() {
        NavPlaceholder placeholder = new NavPlaceholder(null);
        placeholder.setSizeFull();

        if (DEBUG_GUI) {
            placeholder.addStyleName("greenBg");
        }// fine del blocco if

        return placeholder;
    }// end of method

    /**
     * Crea l'interfaccia utente (User Interface) iniziale dell'applicazione
     * Footer - un striscia per eventuali informazioni (Algo, copyright, ecc)
     * Le applicazioni specifiche, possono sovrascrivere questo metodo nella sottoclasse
     *
     * @return layout - normalmente un HorizontalLayout
     */
    protected HorizontalLayout creaFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setMargin(new MarginInfo(false, false, false, false));
        footer.setSpacing(true);
        footer.setHeight("30px");

        if (DEBUG_GUI) {
            footer.addStyleName("redBg");
        }// fine del blocco if

        footer.addComponent(new Label("Algos s.r.l."));

        return footer;
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
            }// fine del blocco if
        }// fine del blocco if

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
     * Viene normalmente sovrascritto dalla sottoclasse per aggiungere i moduli alla menubar dell'applicazione <br>
     * Deve (DEVE) richiamare anche il metodo della superclasse (questo), DOPO le regolazioni specifiche <br>
     * La property moduloPartenza (già regolata su VersioneModulo di default),
     * può essere modificata nella sottoclasse PRIMA di invocare super.addAllModuli()
     */
    protected void addAllModuli() {
        this.addModulo(moduloPartenza);
    }// end of method

    /**
     * Aggiunge alla barra di menu principale il comando per lanciare il modulo indicatoi
     * Aggiunge il singolo menu (item) alla barra principale di menu
     * Invocato dalla sottoclasse
     *
     * @param modulo da visualizzare nel placeholder alla pressione del bottone di menu
     */
    protected void addModulo(ModulePop modulo) {
        if (topLayout!=null) {
            topLayout.addModulo(modulo, placeholder);
        }// fine del blocco if

//        UI ui = this.getUI();
//        String address = "";
//
//        address = LibPath.getClassName(modulo.getEntityClass());
//
//        MenuCommand command = new MenuCommand(ui, mainBar, address, modulo);
//        mainBar.addItem(address, null, command);
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
            topLayout.addMenu(titoloMenu, module, placeholder);
        }// end of if cycle

    }// end of method

    /**
     * Crea e aggiunge uno spaziatore verticale.
     */
    private void addSpacer() {
        HorizontalLayout spacer = new HorizontalLayout();
        spacer.setMargin(false);
        spacer.setSpacing(false);
        spacer.setHeight("5px");

        if (DEBUG_GUI) {
            spacer.addStyleName("yellowBg");
        }// fine del blocco if

        mainLayout.addComponent(spacer);
    }// end of method

}// end of class
