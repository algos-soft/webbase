package it.algos.webbase.web.toolbar;

import java.util.ArrayList;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import it.algos.webbase.web.lib.LibResource;

@SuppressWarnings("serial")
public class FormToolbar extends Toolbar {

	private ArrayList<FormToolbarListener> listeners = new ArrayList<FormToolbarListener>();

	public FormToolbar() {
		super();
		addButtons();
		removeComponent(helperLayout);
	}// end of constructor

	protected void addButtons() {
			addButton("Annulla", new ThemeResource("img/action_cancel.png"), new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				fire(Events.cancel);
			}// end of method
		});// end of anonymous inner class

		// addButton("Reset", new ThemeResource("img/action_revert.png"), new MenuBar.Command() {
		// public void menuSelected(MenuItem selectedItem) {
		// fire(Events.reset);;
		// }// end of method
		// });// end of anonymous class

		addButton("Registra", new ThemeResource("img/action_save.png"), new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				fire(Events.save);
			}// end of method
		});// end of anonymous inner class

	}// end of method

	public void addToolbarListener(FormToolbarListener listener) {
		this.listeners.add(listener);
	}// end of method

	protected void fire(Events event) {
		for (FormToolbarListener l : listeners) {
			switch (event) {
			case cancel:
				l.cancel_();
				break;
			case reset:
				l.reset_();
				break;
			case save:
				l.save_();
				break;
			default:
				break;
			}// end of switch cycle
		}// end of for cycle

	}// end of method

	public interface FormToolbarListener {
		public void cancel_();

		public void save_();

		public void reset_();
	}// end of interface

	public enum Events {
		cancel, reset, save;
	}// end of enumeration

}// end of class
