package it.algos.webbase.web.login;

import com.vaadin.ui.*;
import it.algos.webbase.domain.utente.Utente;
import it.algos.webbase.web.lib.LibSession;

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
    //    private ArrayList<LogBottoniListener> listeners = new ArrayList<LogBottoniListener>();
    private Utente utente;
    private Label label;
    private Button buttonLogin;
    private Button buttonEsci;
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

        // this.setMargin(true);
        // this.setSpacing(true);
//		this.setHeight("10%");
//		this.setWidth("100%");

        // si autoregistra per rispondere al menu/bottone
//        this.addLogBottoneListener(this);
        menubar = new MenuBar();
        loginItem = menubar.addItem("Login1", null, null);
        this.addComponent(menubar);
        this.setComponentAlignment(menubar, Alignment.MIDDLE_RIGHT);
//        creaLabelAndBottoni();

//        elaboraComponenti();
        updateLoginUI();
    }// end of method

//    /**
//     * Creazione dei componenti grafici
//     */
//    private void creaLabelAndBottoni() {
//        this.label = new Label();
//        this.label.setSizeUndefined();
//        buttonLogin = creaBottoneLogin();
//        buttonEsci = creaBottoneEsci();
//    }// end of method

//    /**
//     * Visualizzazione dei componenti
//     */
//    private void barraLoggato(Button bottoneAttivo) {
//        HorizontalLayout layout;
//
//        if (USA_TESTO) {
//            layout = new HorizontalLayout();
//            layout.setSpacing(true);
//            layout.addComponent(label);
//            layout.addComponent(bottoneAttivo);
//            layout.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
//            layout.setComponentAlignment(bottoneAttivo, Alignment.MIDDLE_RIGHT);
//            this.addComponent(layout);
//            this.setComponentAlignment(layout, Alignment.MIDDLE_RIGHT);
//        } else {
//            this.addComponent(bottoneAttivo);
//            this.setComponentAlignment(bottoneAttivo, Alignment.MIDDLE_RIGHT);
//        }// end of if/else cycle
//
//    }// end of method


    /**
     * Aggiorna la UI di bottone/menu in base ai contenuti della session
     */
    private void updateLoginUI() {
        Utente user = Login.getLogin().getUser();

        if (user == null) {

            loginItem.setText("Login2");
            loginItem.setCommand(new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    loginCommandSelected();
                }
            });

        } else {

            String username = user.getNickname();
            loginItem.setCommand(null);
            loginItem.setText(username);
            loginItem.removeChildren();
            loginItem.addItem("Logout", new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    logoutCommandSelected();
                }
            });

        }

    }// end of method

//    /**
//     * Visualizzazione da loggato
//     */
//    private void barraLoggato() {
//        this.barraLoggato(buttonEsci);
//    }// end of method
//
//    /**
//     * Visualizzazione da sloggato
//     */
//    private void barraNonLoggato() {
//        this.barraLoggato(buttonLogin);
//    }// end of method


    /**
     * Evento generato dal bottone di comando <br>
     * <p>
     * Informa (tramite listener) chi è interessato <br>
     */
    protected void bottoneEsciPremuto() {
//        for (LogBottoniListener listener : listeners) {
//            listener.esciLogin();
//        }// end of for cycle
    }// end of method

//    /**
//     * Visualizza un bottone di comando <br>
//     */
//    private Button creaBottoneLogin() {
//        Button button = new Button("Login");
//
//        button.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
//                bottoneLoginPremuto();
//            }// end of inner method
//        });// end of anonymous class
//
//        button.setSizeUndefined();
//        return button;
//    }// end of method

//    /**
//     * Visualizza un bottone di comando <br>
//     */
//    private Button creaBottoneEsci() {
//        Button button = new Button("Esci");
//
//        button.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
//                bottoneEsciPremuto();
//            }// end of inner method
//        });// end of anonymous class
//
//        button.setSizeUndefined();
//        return button;
//    }// end of method

//    /**
//     * Listener notificato quando si preme il bottone login
//     */
//    public void addLogBottoneListener(LogBottoniListener listener) {
//        listeners.add(listener);
//    }// end of method
//
//    /**
//     * Azione effettuata quando si preme il bottone login <br>
//     * Passa il controllo a LoginLogic <br>
//     */
//    @Override
//    public void openLogin() {
//        LoginLogic loginLogic = new LoginLogic(UI.getCurrent());
//        loginLogic.addUtenteLoginListener(this);
//    }// end of method
//
//    /**
//     * Azione effettuata quando si preme il bottone esci <br>
//     */
//    @Override
//    public void esciLogin() {
//        this.utente = null;
//        this.reset();
//    }// end of method

//    /**
//     * Crea la componente grafica <br>
//     */
//    public void elaboraComponenti() {
//        if (utente != null) {
//            label.setCaption("Loggato come: " + utente.getNickname());
//            barraLoggato();
//        } else {
//            label.setCaption(TESTO_NON_LOGGATO);
//            barraNonLoggato();
//        }// end of if/else cycle
//    }// end of method

//    /**
//     * Azzera l'aspetto grafico e lo ricrea <br>
//     */
//    public void reset() {
//        this.removeAllComponents();
//        elaboraComponenti();
//    }// end of method

    /**
     * Azione effettuata quando si modifica l'utente loggato <br>
     */
    public void setUtenteLogin(Utente utente) {
        this.utente = utente;
//        this.reset();
    }// end of method

    /**
     * Logout
     * Annulla l'oggetto Login nella sessione
     */
    private void logout() {
        LibSession.setAttribute(Login.LOGIN_KEY_IN_SESSION, null);
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

