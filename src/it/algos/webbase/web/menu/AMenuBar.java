package it.algos.webbase.web.menu;

import com.vaadin.ui.*;
import com.vaadin.ui.MenuBar.MenuItem;
import it.algos.webbase.web.lib.LibPath;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.navigator.NavPlaceholder;
import it.algos.webbase.web.security.LoginBar;

import java.util.List;

/**
 * Contenitore grafico per la barra di menu principale e per il menu/bottone del Login
 * <p>
 * A sinistra i menu specifici dell'applicazione. <br>
 * A destra il bottone (od i menu) della Security (login). <br>
 * Se l'applicazione non usa la security, il bottone di destra non appare. <br>
 * Può essere sovrascritta dall'applicazione per utilizzi specifici. <br>
 * L'implementazione con GridLayout può essere modificata all'interno di questa classe. <br>
 * Se non usa la security, il GridLayout viene sostituito con HorizontalLayout. <br
 * Le due barre di menu (destra e sinistra) hanno due stili diversi. <br>
 * Per adesso sono implementati uguali nel scss, ma potrebbero essere differenziati. <br>
 * Il costruttore seleziona già se l'applicazione usa la security e regola di conseguenza il menu di destra. <br>
 * I menu specifici dell'applicazione possono essere passati nel costruttore od aggiunti dopo. <br>
 */
@SuppressWarnings("serial")
public class AMenuBar extends HorizontalLayout {

    /**
     * Regolazioni scss.
     */
    public static String MENU_ABILITATO = "abilitato";
    public static String MENU_DISABILITATO = "disabilitato";
    private MenuBar algosMenuBar = new MenuBar();
    private LoginBar loginMenuBar;

    /**
     * Constructor senza security
     */
    public AMenuBar() {
        this(false);
    }// end of constructor


    /**
     * Constructor
     */
    public AMenuBar(boolean usaSecurity) {
        init(usaSecurity);
    }// end of constructor

    /**
     * Initialization
     * <p>
     * Niente margine che c'è già nel contenitore parente <br>
     * ˙
     */
    private void init(boolean usaSecurity) {
        this.setMargin(false);
        this.setSpacing(true);
        this.setHeight("40px");
        this.setWidth("100%");

        algosMenuBar = createMenuBar();
        this.addComponent(algosMenuBar);

        if (usaSecurity) {
            loginMenuBar = createLoginMenuBar();
            this.addComponent(loginMenuBar);
        }// fine del blocco if

    }// end of method

    /**
     * Griglia di una riga con due componenti. <br>
     * La griglia serve per posizionare i due menu, uno a sinistra e l'altro a destra. <br>
     */
    private void initConSecurity() {
        final GridLayout grid = new GridLayout(2, 1);
        grid.setWidth("100%");
        algosMenuBar = createMenuBar();
        loginMenuBar = createLoginMenuBar();

        // aggiunge la menubar principale e la menubar login
        HorizontalLayout menuLayout = new HorizontalLayout();
        menuLayout.setHeight("40px");
        menuLayout.setWidth("100%");
        menuLayout.addComponent(algosMenuBar);
//        mainBar.setWidth("95%");
        algosMenuBar.setHeight("100%");
        menuLayout.setExpandRatio(algosMenuBar, 1.0f);
        menuLayout.addComponent(loginMenuBar);
        loginMenuBar.setHeight("100%");
        this.addComponent(menuLayout);
        this.addComponent(new Button("Pippozbelloneterzo"));

//		grid.addComponent(algosMenuBar, 0, 0);
//		grid.setComponentAlignment(algosMenuBar, Alignment.TOP_LEFT);
//		grid.addComponent(loginMenuBar, 1, 0);
//		grid.setComponentAlignment(loginMenuBar, Alignment.TOP_RIGHT);
//
//		this.addComponent(grid);
    }// end of method

    /**
     * Layout orizzontale semplice, dato che c'è solo una menu bar. <br>
     */
    private void initSenzaSecurity() {
        final HorizontalLayout layout = new HorizontalLayout();
        layout.setHeight("40px");
        layout.setWidth("100%");

        algosMenuBar = createMenuBar();
        algosMenuBar.addItem("alfa", null);
        algosMenuBar.addItem("beta", null);
        algosMenuBar.addItem("gamma", null);

        layout.addComponent(algosMenuBar);
        algosMenuBar.setHeight("100%");
        layout.setExpandRatio(algosMenuBar, 1.0f);
        this.addComponent(layout);
    }// end of method

    /**
     * Menu bar specifico dell'applicazione
     * <p>
     * I singoli menu vengono aggiunti dall'applicazione specifica col metodo addMenu. <br>
     */
    private MenuBar createMenuBar() {
        MenuBar menubar = new MenuBar();
        menubar.addStyleName("algosmenubar");

        return menubar;
    }// end of method

    /**
     * Menu bar specifico della security
     * <p>
     */
    private LoginBar createLoginMenuBar() {
        MenuBar menubar = new MenuBar();
        menubar.addStyleName("loginmenubar");
        menubar.addItem("Login", null, null);

        return new LoginBar();
//		return menubar;
    }// end of method

    public void addMenu(String titolo, CustomComponent modulo, NavPlaceholder placeholder) {
        MenuItem menuItem;
        MenuBar.Command comando = new ModuleCommand(modulo, placeholder, algosMenuBar);
        menuItem = algosMenuBar.addItem(titolo, null, comando);
        menuItem.setStyleName(MENU_DISABILITATO);
    }// end of method

//    public void addMenu(String titolo, ModulePop modulo) {
//        MenuItem menuItem;
//        MenuBar.Command comando = new ModuleCommand(modulo, placeholder, algosMenuBar);
//        menuItem = algosMenuBar.addItem(titolo, null, comando);
//        menuItem.setStyleName(MENU_DISABILITATO);
//    }// end of method


    /**
     * Aggiunge alla barra di menu principale il comando per lanciare il modulo indicatoi
     * Aggiunge il singolo menu (item) alla barra principale di menu
     *
     * @param modulo da visualizzare nel placeholder alla pressione del bottone di menu
     */
    public void addModulo(ModulePop modulo, NavPlaceholder placeholder) {
        String address = "";
        address = modulo.getMenuAddress();

        if (address.equals("")) {
            address = LibPath.getClassName(modulo.getEntityClass());
        }// fine del blocco if

        this.addMenu(address, modulo, placeholder);
    }// end of method

    /**
     * Returns a list with all the MenuItem objects in the menu bar
     *
     * @return a list containing the MenuItem objects in the menu bar
     */
    public List<MenuItem> getItems() {
        return algosMenuBar.getItems();
    }// end of method

    public MenuBar.MenuItem addItem(String caption, MenuBar.Command command) {
        return algosMenuBar.addItem(caption, null, command);
    }

}// end of class
