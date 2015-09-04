package it.algos.webbase.web.ui;

import com.vaadin.server.*;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import it.algos.webbase.domain.log.LogMod;
import it.algos.webbase.domain.pref.PrefMod;
import it.algos.webbase.domain.ruolo.RuoloModulo;
import it.algos.webbase.domain.utente.UtenteModulo;
import it.algos.webbase.domain.utenteruolo.UtenteRuoloModulo;
import it.algos.webbase.domain.vers.VersMod;
import it.algos.webbase.web.AlgosApp;
import it.algos.webbase.web.lib.Cost;
import it.algos.webbase.web.lib.LibSession;
import it.algos.webbase.web.menu.AMenuBar;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.navigator.AlgosNavigator;
import it.algos.webbase.web.navigator.NavPlaceholder;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
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

    protected String menuAddressModuloPartenza;
    protected ArrayList<ModulePop> moduli;

    /**
     * Initializes this UI. This method is intended to be overridden by subclasses to build the view and configure
     * non-component functionality. Performing the initialization in a constructor is not suggested as the state of the
     * UI is not properly set up when the constructor is invoked.
     * <p>
     * The {@link VaadinRequest} can be used to get information about the request that caused this UI to be created.
     * </p>
     * Se viene sovrascritto dalla sottoclasse, deve (DEVE) richiamare anche il metodo della superclasse
     * di norma DOPO aver effettuato alcune regolazioni <br>
     * Nella sottoclasse specifica viene eventualmente regolato il nome del modulo di partenza <br>
     *
     * @param request the Vaadin request that caused this UI to be created
     */
    @Override
    protected void init(VaadinRequest request) {

        // Legge eventuali parametri passati nella request
        this.checkParams(request);

        // Legge i cookies dalla request
        this.checkCookies(request);

        // Controlla il login della security
        this.checkSecurity(request);

        // Crea l'interfaccia utente (User Interface) iniziale dell'applicazione
        this.startUI();
    }// end of method

    /**
     * Legge eventuali parametri passati nella request
     * Legge i cookies dalla request
     * <p>
     */
    protected void checkParams(VaadinRequest request) {
        // legge il parametro "developer" (se esiste) e regola la variabile statica
        LibSession.checkDeveloper(request);

        // legge il parametro "debug" (se esiste) e regola la variabile statica
        LibSession.checkDebug(request);
    }// end of method


    /**
     * Controlla i cookies esistenti
     * <p>
     */
    protected void checkCookies(VaadinRequest request) {
        // Fetch all cookies
        Cookie[] cookies = request.getCookies();

        // Store the current cookies in the service session
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Cost.COOKIE_LOGIN_NICK)) {
                    LibSession.setAttribute(Cost.COOKIE_LOGIN_NICK, cookie.getValue());
                }// fine del blocco if
                if (cookie.getName().equals(Cost.COOKIE_LOGIN_PASS)) {
                    LibSession.setAttribute(Cost.COOKIE_LOGIN_PASS, cookie.getValue());
                }// fine del blocco if
                if (cookie.getName().equals(Cost.COOKIE_LOGIN_ROLE)) {
                    LibSession.setAttribute(Cost.COOKIE_LOGIN_ROLE, cookie.getValue());
                }// fine del blocco if
            } // fine del ciclo for-each
        }// fine del blocco if


//         Create a new cookie
//        Cookie myCookie = new Cookie(Cost.COOKIE_LOGIN_NICK, "cookie-value");
//        myCookie.setMaxAge(300);
//        myCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
//        VaadinService.getCurrentResponse().addCookie(myCookie);

//        Cookies.setCookie("pippo", "Some-other-value");

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
     * Può essere sovrascritto per gestire la UI in maniera completamente diversa
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

        // Crea i menu per la gestione dei moduli (standard e specifici)
        this.addAllModuli();

        // crea un Navigator e lo configura in base ai contenuti della MenuBar
        this.creaPartenza();

        // set browser window title
        Page.getCurrent().setTitle("Vaadin");

        // assegna la UI
        setContent(mainLayout);
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
     * Crea i menu per la gestione dei moduli (standard e specifici)
     * Alcuni moduli sono già definiti per tutte le applicazioni (LogMod, VersMod, PrefMod)
     */
    private void addAllModuli() {
        moduli = new ArrayList<ModulePop>();
        this.addModuliStandard();
        this.addModuli();
    }// end of method


    /**
     * Crea i menu per la gestione dei moduli standar definiti nel progetto webbase
     * Vengono usati o meno secondo i flags (sovrascrivibili): AlgosApp.USE_LOG, AlgosApp.USE_VERS, AlgosApp.USE_PREF
     */
    private void addModuliStandard() {
        if (LibSession.isDeveloper()) {
            this.addModulo(new RuoloModulo());
            this.addModulo(new UtenteModulo());
            this.addModulo(new UtenteRuoloModulo());
        }// fine del blocco if

        if (AlgosApp.USE_LOG) {
            this.addModulo(new LogMod());
        }// fine del blocco if
        if (AlgosApp.USE_VERS) {
            this.addModulo(new VersMod());
        }// fine del blocco if
        if (AlgosApp.USE_PREF) {
            this.addModulo(new PrefMod());
        }// fine del blocco if
    }// end of method

    /**
     * Crea i menu specifici per la gestione dei moduli
     * Deve (DEVE) essere sovrascritto dalla sottoclasse per aggiungere i moduli alla menubar dell'applicazione <br>
     */
    protected void addModuli() {
    }// end of method

    /**
     * Aggiunge alla barra di menu principale il comando per lanciare il modulo indicato
     * Aggiunge il modulo alla lista interna
     * Aggiunge il singolo menu (item) alla barra principale di menu
     * Rimanda al metodo omonimo della classe AMenuBar (dedicata per il menu algosMenuBar e loginMenuBar)
     * Invocato dalla sottoclasse
     *
     * @param modulo da visualizzare nel placeholder alla pressione del bottone di menu
     */
    protected void addModulo(ModulePop modulo) {
        moduli.add(modulo);
        if (topLayout != null) {
            topLayout.addModulo(modulo, placeholder);
        }// fine del blocco if
    }// end of method

    /**
     * Crea e aggiunge uno spaziatore verticale di altezza fissa
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

    /**
     * crea un Navigator e lo configura in base ai contenuti della MenuBar
     */
    private void creaPartenza() {
        AlgosNavigator nav = new AlgosNavigator(getUI(), placeholder);
        nav.configureFromMenubar(topLayout);
//        nav.navigateTo(menuAddressModuloPartenza);
    }// end of method

}// end of class
