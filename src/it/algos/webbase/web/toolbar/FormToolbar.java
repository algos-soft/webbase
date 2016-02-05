package it.algos.webbase.web.toolbar;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import it.algos.webbase.web.AlgosApp;
import it.algos.webbase.web.component.Spacer;
import it.algos.webbase.web.form.AForm;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class FormToolbar extends Toolbar {

    private AForm form;

    private ArrayList<FormToolbarListener> listeners = new ArrayList<FormToolbarListener>();

    /**
     * Constructor.
     * @param form the reference form
     */
    public FormToolbar(AForm form) {
        super();
        this.form=form;

        removeComponent(helperLayout);

        // regola il layout: aggiunge per primo un componente espandibile in modo che
        // i bottoni vadano ad allinearsi a destra
        Spacer spc = new Spacer();
        spc.setWidth("100%");
        commandLayout.setWidth("100%");
        commandLayout.addComponent(spc);
        commandLayout.setExpandRatio(spc, 1);

        if (DEBUG_GUI) {
            spc.addStyleName("blueBg");
        }// end of if cycle

        addButtons();

    }// end of constructor


    /**
     * Empty Constructor.
     * @deprecated, use the constructor with Form instead.
     */
    public FormToolbar() {
        this(null);
    }// end of constructor


    /**
     * A seconda del flag, usa il Font Awesome oppure l'icona del Theme corrente
     */
    protected void addButtons() {
        addButton("Annulla", FontAwesome.TIMES, new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                fire(Events.cancel);
            }// end of method
        });// end of anonymous inner class

        addButton("Registra", FontAwesome.CHECK, new MenuBar.Command() {
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

    public enum Events {
        cancel, reset, save;
    }// end of enumeration

    public interface FormToolbarListener {
        public void cancel_();

        public void save_();

        public void reset_();
    }// end of interface

    public AForm getForm() {
        return form;
    }

}// end of class
