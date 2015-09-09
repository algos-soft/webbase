package it.algos.webbase.web.toolbar;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import it.algos.webbase.web.AlgosApp;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class FormToolbar extends Toolbar {

    private ArrayList<FormToolbarListener> listeners = new ArrayList<FormToolbarListener>();

    public FormToolbar() {
        super();
        addButtons();
        removeComponent(helperLayout);
    }// end of constructor

    /**
     * A seconda del flag, usa il Font Awesome oppure l'icona del Theme corrente
     */
    protected void addButtons() {
        if (AlgosApp.USE_FONT_AWESOME) {
            addButton("Annulla", FontAwesome.TIMES, new MenuBar.Command() {
                public void menuSelected(MenuItem selectedItem) {
                    fire(Events.cancel);
                }// end of method
            });// end of anonymous inner class
        } else {
            addButton("Annulla", new ThemeResource("img/action_cancel.png"), new MenuBar.Command() {
                public void menuSelected(MenuItem selectedItem) {
                    fire(Events.cancel);
                }// end of method
            });// end of anonymous inner class
        }// fine del blocco if-else

        if (AlgosApp.USE_FONT_AWESOME) {
            addButton("Registra", FontAwesome.CHECK, new MenuBar.Command() {
                public void menuSelected(MenuItem selectedItem) {
                    fire(Events.save);
                }// end of method
            });// end of anonymous inner class
        } else {
            addButton("Registra", new ThemeResource("img/action_save.png"), new MenuBar.Command() {
                public void menuSelected(MenuItem selectedItem) {
                    fire(Events.save);
                }// end of method
            });// end of anonymous inner class
        }// fine del blocco if-else
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

    public enum Events {
        cancel, reset, save;
    }// end of enumeration

    public interface FormToolbarListener {
        public void cancel_();

        public void save_();

        public void reset_();
    }// end of interface

}// end of class
