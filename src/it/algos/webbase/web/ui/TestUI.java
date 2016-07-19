package it.algos.webbase.web.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import it.algos.webbase.domain.pref.PrefMod;
import it.algos.webbase.web.AlgosApp;
import it.algos.webbase.web.login.LoginEvent;

/**
 * Created by gac on 19 lug 2016.
 * .
 */
@Theme("algos")
public class TestUI extends AlgosUI {

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

        AlgosApp.USE_VERS = true;
        AlgosApp.USE_PREF = true;
        AlgosApp.USE_LOG = true;
        menuAddressModuloPartenza = PrefMod.class.getName();

        super.init(request);
    }// end of method


    @Override
    public void onUserLogin(LoginEvent e) {
    }// end of method

}// end of class
