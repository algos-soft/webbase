package it.algos.webbase.web.security;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickListener;
import it.algos.webbase.domain.utente.Utente;
import it.algos.webbase.web.login.LoginListener;
import it.algos.webbase.web.login.LogoutListener;

import java.util.ArrayList;

/**
 * Fascia (barra UI) di controllo della security
 * <p>
 * Se si è loggati, mostra il nickname ed un popup per modificare/uscire <br>
 * Se non si è loggati, mostra un bottone per entrare <br>
 */
@SuppressWarnings("serial")
public class LoginBar extends HorizontalLayout implements LogBottoniListener, LogUtenteListener {

    private static String TESTO_NON_LOGGATO = "Loggato come Anonymous";
    private static boolean USA_TESTO = false;
    private ArrayList<LogBottoniListener> listeners = new ArrayList<LogBottoniListener>();
    private Utente utente;
    private Label label;
    private Button buttonLogin;
    private Button buttonEsci;

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
        this.addLogBottoneListener(this);

        creaLabelAndBottoni();

        elaboraComponenti();
    }// end of method

    /**
     * Creazione dei componenti grafici
     */
    private void creaLabelAndBottoni() {
        this.label = new Label();
        this.label.setSizeUndefined();
        buttonLogin = creaBottoneLogin();
        buttonEsci = creaBottoneEsci();
    }// end of method

    /**
     * Visualizzazione dei componenti
     */
    private void barraLoggato(Button bottoneAttivo) {
        HorizontalLayout layout;

        if (USA_TESTO) {
            layout = new HorizontalLayout();
            layout.setSpacing(true);
            layout.addComponent(label);
            layout.addComponent(bottoneAttivo);
            layout.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
            layout.setComponentAlignment(bottoneAttivo, Alignment.MIDDLE_RIGHT);
            this.addComponent(layout);
            this.setComponentAlignment(layout, Alignment.MIDDLE_RIGHT);
        } else {
            this.addComponent(bottoneAttivo);
            this.setComponentAlignment(bottoneAttivo, Alignment.MIDDLE_RIGHT);
        }// end of if/else cycle

    }// end of method

    /**
     * Visualizzazione da loggato
     */
    private void barraLoggato() {
        this.barraLoggato(buttonEsci);
    }// end of method

    /**
     * Visualizzazione da sloggato
     */
    private void barraNonLoggato() {
        this.barraLoggato(buttonLogin);
    }// end of method

    /**
     * Evento generato dal bottone di comando <br>
     * <p>
     * Informa (tramite listener) chi è interessato <br>
     */
    protected void bottoneLoginPremuto() {
        for (LogBottoniListener listener : listeners) {
            listener.openLogin();
        }// end of for cycle
    }// end of method

    /**
     * Evento generato dal bottone di comando <br>
     * <p>
     * Informa (tramite listener) chi è interessato <br>
     */
    protected void bottoneEsciPremuto() {
        for (LogBottoniListener listener : listeners) {
            listener.esciLogin();
        }// end of for cycle
    }// end of method

    /**
     * Visualizza un bottone di comando <br>
     */
    private Button creaBottoneLogin() {
        Button button = new Button("Login");

        button.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
                bottoneLoginPremuto();
            }// end of inner method
        });// end of anonymous class

        button.setSizeUndefined();
        return button;
    }// end of method

    /**
     * Visualizza un bottone di comando <br>
     */
    private Button creaBottoneEsci() {
        Button button = new Button("Esci");

        button.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
                bottoneEsciPremuto();
            }// end of inner method
        });// end of anonymous class

        button.setSizeUndefined();
        return button;
    }// end of method

    /**
     * Listener notificato quando si preme il bottone login
     */
    public void addLogBottoneListener(LogBottoniListener listener) {
        listeners.add(listener);
    }// end of method

    /**
     * Azione effettuata quando si preme il bottone login <br>
     * Passa il controllo a LoginLogic <br>
     */
    @Override
    public void openLogin() {
        LoginLogic loginLogic = new LoginLogic(UI.getCurrent());
        loginLogic.addUtenteLoginListener(this);
    }// end of method

    /**
     * Azione effettuata quando si preme il bottone esci <br>
     */
    @Override
    public void esciLogin() {
        this.utente = null;
        this.reset();
    }// end of method

    /**
     * Crea la componente grafica <br>
     */
    public void elaboraComponenti() {
        if (utente != null) {
            label.setCaption("Loggato come: " + utente.getNickname());
            barraLoggato();
        } else {
            label.setCaption(TESTO_NON_LOGGATO);
            barraNonLoggato();
        }// end of if/else cycle
    }// end of method

    /**
     * Azzera l'aspetto grafico e lo ricrea <br>
     */
    public void reset() {
        this.removeAllComponents();
        elaboraComponenti();
    }// end of method

    /**
     * Azione effettuata quando si modifica l'utente loggato <br>
     */
    public void setUtenteLogin(Utente utente) {
        this.utente = utente;
        this.reset();
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

}// end of class

