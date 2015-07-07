package it.algos.web.ui;

import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import it.algos.web.Application;
import it.algos.web.lib.LibSession;
import it.algos.web.menu.AMenuBar;
import it.algos.web.module.ModulePop;

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
    protected ModulePlaceholder placeholder;
    protected AMenuBar barraMenu;

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

        // Controlla i cookies esistenti
        this.checkCookies(request);

        // Controlla il login della security
        this.checkSecurity(request);

        // Regola il menu
        this.regolaMenu();
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
     * Controlla i cookies esistenti
     * <p>
     */
    protected void checkCookies(VaadinRequest request) {
//		// Fetch all cookies
//		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
//
//		// Create a new cookie
//		Cookie myCookie = new Cookie("pippo", "cookie-value");
//		myCookie.setMaxAge(300);
//		myCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
//		VaadinService.getCurrentResponse().addCookie(myCookie);
//		
//		Cookies.setCookie("pippo", "Some other value");

    }// end of method

    /**
     * Controlla il login della security
     * <p>
     */
    protected void checkSecurity(VaadinRequest request) {
    }// end of method

    /**
     * Regola il menu
     * <p>
     */
    protected void regolaMenu() {
        mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        // mainLayout.setHeight("100%");
        // mainLayout.setWidth("100%");

        // crea e aggiunge il placeholder
        placeholder = new ModulePlaceholder();

        if (Application.USE_SECURITY) {
            barraMenu = new AMenuBar(true);
        } else {
            barraMenu = new AMenuBar();
        }// end of if/else cycle

        mainLayout.addComponent(barraMenu);
        mainLayout.addComponent(placeholder);
        mainLayout.setExpandRatio(barraMenu, 1.0f);

        setContent(mainLayout);
    }// end of method

    /**
     * Legge il parametro "azienda" e regola la variabile statica
     */
    protected void leggeAzienda(VaadinRequest request) {
        String company = "";
        Map<String, String[]> mappa = null;

        // legge il parametro "azienda" e regola la variabile statica
        if (Application.USE_COMPANY) {
            mappa = request.getParameterMap();

            // ci sono 17 valori di sistema
            if (mappa.size() == 18) {
                company = "demo";
            }// end of if cycle
        }// end of if cycle

        Application.COMPANY_CODE = company;
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
