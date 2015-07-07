package it.algos.web.menu;

import it.algos.web.ui.ModulePlaceholder;

import java.util.List;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Command for main modules
 */
@SuppressWarnings("serial")
class ModuleCommand implements MenuBar.Command {

	private CustomComponent component;
	private ModulePlaceholder placeholder;
	private MenuBar menuBar;

	/**
	 * Regolazioni scss.
	 */
	private static String MENU_ABILITATO = AMenuBar.MENU_ABILITATO;
	private static String MENU_DISABILITATO = AMenuBar.MENU_DISABILITATO;

	/**
	 * Constructor
	 */
	public ModuleCommand(CustomComponent component, ModulePlaceholder placeholder, MenuBar menuBar) {
		this.component = component;
		this.placeholder = placeholder;
		this.menuBar = menuBar;
	}// end of constructor

	/**
	 * Put the component in the placeholder <br>
	 * Regola l'aspetto del menu selezionato <br>
	 */
	@Override
	public void menuSelected(MenuItem selectedItem) {
		placeholder.setContent(this.component);
		deselezionaAllItemButOne(selectedItem);
	}// end of method

	/**
	 * Regola l'aspetto di tutti i menu <br>
	 */
	public void deselezionaAllItemButOne(MenuItem selectedItem) {
		List<MenuItem> lista = menuBar.getItems();

		for (MenuItem menuItem : lista) {
			if (menuItem != selectedItem) {
				menuItem.setStyleName(MENU_DISABILITATO);
			}// end of if cycle
		}// fine del ciclo for

		if (selectedItem != null) {
			selectedItem.setStyleName(MENU_ABILITATO);
		}// end of if cycle

	}// end of method
}// end of class
