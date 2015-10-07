package it.algos.webbase.web.login;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import it.algos.webbase.domain.utente.Utente;
import it.algos.webbase.web.AlgosApp;

import java.util.ArrayList;

/**
 * Fascia (barra UI) di controllo della security
 * <p>
 * Se si è loggati, mostra il nickname ed un popup per modificare/uscire <br>
 * Se non si è loggati, mostra un bottone per entrare <br>
 */
public class LoginBar extends HorizontalLayout implements LoginListener {

    private static String TESTO_NON_LOGGATO = "Loggato come Anonymous";
    private static boolean USA_TESTO = false;
    MenuBar menubar;
    private MenuBar.MenuItem loginItem; // il menuItem di login

    /**
     * La classe LoginBar gestisce il bottone/menu per il login
     * Lancia il fire di questo evento, se si clicca il bottone/menu Login.
     * Si registrano qui i listeners (istanze di classi che sono interessate all'evento)
     */
    private ArrayList<LogformListener> logformListeners = new ArrayList<>();

    /**
     * LoginBar gestisce il bottone/menu per il logout
     * Lancia il fire di questo evento, se si clicca il bottone/menu Esci/Logout.
     * Si registrano qui i listeners (istanze di classi che sono interessate all'evento)
     */
    private ArrayList<LogoutListener> logoutListeners = new ArrayList<>();

    /**
     * Constructor
     */
    public LoginBar() {
        init();
    }// end of constructor

    /**
     * Initialization
     */
    private void init() {
        this.setMargin(false);
        this.setSpacing(true);
        this.setHeight("40px");
        this.setWidth("100%");

        menubar = new MenuBar();
        loginItem = menubar.addItem("Login1", null, null);
        this.addComponent(menubar);
        this.setComponentAlignment(menubar, Alignment.MIDDLE_RIGHT);

        updateLoginUI();
    }// end of method

    /**
     * Aggiorna la UI di bottone/menu in base ai contenuti della session
     */
    private void updateLoginUI() {
        Utente user = Login.getLogin().getUser();
        Resource exitIcon;

        if (user == null) {

            loginItem.setText("Login");
            if (AlgosApp.USE_FONT_AWESOME) {
                loginItem.setIcon(FontAwesome.SIGN_IN);
            }// fine del blocco if
            loginItem.setCommand(new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    loginCommandSelected();
                }// end of method
            });// end of anonymous inner class

        } else {

            String username = user.getNickname();
            loginItem.setCommand(null);
            loginItem.setText(username);
            if (AlgosApp.USE_FONT_AWESOME) {
                loginItem.setIcon(FontAwesome.USER);
            }// fine del blocco if
            loginItem.removeChildren();
            if (AlgosApp.USE_FONT_AWESOME) {
                exitIcon = FontAwesome.SIGN_OUT;
            } else {
                exitIcon = null;
            }// end of if/else cycle
            loginItem.addItem("Logout", exitIcon, new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    logoutCommandSelected();
                }// end of method
            });// end of anonymous inner class

        }// end of if/else cycle

    }// end of method


    /**
     * Logout
     * Annulla l'oggetto Login nella sessione
     */
    private void logout() {
        Login.invalidateUser();
        updateLoginUI();
    }// end of method

    /**
     * Adds a LogformListener.
     */
    public void addLogformListener(LogformListener listener) {
        logformListeners.add(listener);
    }// end of method

    /**
     * Removes all the LogformListeners
     */
    public void removeAllLogformListener() {
        logformListeners.clear();
    }// end of method

    /**
     * Registers a unique LogformListener.
     * All the previous LogformListeners are deleted
     */
    public void setLogformListener(LogformListener listener) {
        removeAllLogformListener();
        addLogformListener(listener);
    }// end of method

    /**
     * Adds a LogoutListener.
     */
    public void addLogoutListener(LogoutListener listener) {
        logoutListeners.add(listener);
    }// end of method

    /**
     * Removes all the LogoutListeners
     */
    public void removeAllLogoutListeners() {
        logoutListeners.clear();
    }// end of method

    /**
     * Registers a unique LogoutListener.
     * All the previous LogoutListeners are deleted
     */
    public void setLogoutListener(LogoutListener listener) {
        removeAllLogoutListeners();
        addLogoutListener(listener);
    }// end of method

    /**
     * Evento generato dal bottone di comando <br>
     * Il bottone login è stato premuto, presenta il login form
     * Informa (tramite listener) chi è interessato <br>
     */
    protected void loginCommandSelected() {
        for (LogformListener listener : logformListeners) {
            listener.onLogFormRequest();
        }// end of for cycle
    }// end of method

    /**
     * Evento generato dal bottone di comando <br>
     * Il bottone logout è stato premuto
     * Informa (tramite listener) chi è interessato <br>
     */
    private void logoutCommandSelected() {
        logout();

        for (LogoutListener listener : logoutListeners) {
            listener.onUserLogout();
        }// end of for cycle
    }// end of method

    /**
     * Invoked after a successful login happened using the Login form.
     * <p>
     * La classe Login gestisce il form ed alla chiusura controlla la validità del nuovo utente
     * Lancia il fire di questo evento, se l'utente è valido.
     */
    @Override
    public void onUserLogin(Utente user, boolean remember) {
        updateLoginUI();
    }// end of method

}// end of class

