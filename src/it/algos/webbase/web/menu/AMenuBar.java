package it.algos.webbase.web.menu;

import it.algos.webbase.web.security.LoginBar;
import it.algos.webbase.web.ui.ModulePlaceholder;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Barra di menu.
 * <p>
 * A sinistra i menu specifici dell'applicazione. <br>
 * A destra il menu (od i menu) della Security (login). <br>
 * Se l'applicazione non usa la security, il menu di destra non appare. <br>
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

	private MenuBar algosMenuBar;
	private LoginBar loginMenuBar;

	/**
	 * Regolazioni scss.
	 */
	public static String MENU_ABILITATO = "abilitato";
	public static String MENU_DISABILITATO = "disabilitato";

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
		this.setHeight("10%");
		this.setWidth("100%");

		algosMenuBar = createMenuBar();

		if (usaSecurity) {
			initConSecurity();
		} else {
			initSenzaSecurity();
		}// end of if/else cycle

	}// end of method

	/**
	 * Griglia di una riga con due componenti. <br>
	 * La griglia serve per posizionare i due menu, uno a sinistra e l'altro a destra. <br>
	 */
	private void initConSecurity() {
		final GridLayout grid = new GridLayout(2, 1);
		grid.setWidth("100%");
		loginMenuBar = createLoginMenuBar();

		grid.addComponent(algosMenuBar, 0, 0);
		grid.setComponentAlignment(algosMenuBar, Alignment.TOP_LEFT);
		grid.addComponent(loginMenuBar, 1, 0);
		grid.setComponentAlignment(loginMenuBar, Alignment.TOP_RIGHT);

		this.addComponent(grid);
	}// end of method

	/**
	 * Layput orizzontale semplice, dato che c'è solo una menu bar. <br>
	 */
	private void initSenzaSecurity() {
		final HorizontalLayout layout = new HorizontalLayout();
		layout.setWidth("100%");

		layout.addComponent(algosMenuBar);

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

	public void addMenu(String titolo, CustomComponent modulo, ModulePlaceholder placeholder) {
		MenuItem menuItem;
		MenuBar.Command comando = new ModuleCommand(modulo, placeholder, algosMenuBar);
		menuItem = algosMenuBar.addItem(titolo, null, comando);
		menuItem.setStyleName(MENU_DISABILITATO);
	}// end of method


}// end of class
