package it.algos.webbase.web.security;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import it.algos.webbase.domain.utente.Utente;
import it.algos.webbase.web.lib.Cost;
import it.algos.webbase.web.lib.LibSession;
import it.algos.webbase.web.module.Module;

import javax.servlet.http.Cookie;
import java.util.ArrayList;

/**
 * Logica di gestione della security
 * <p>
 * Viene creata dalla LoginBar <br>
 * Viene attivata dal bottone di comando della LoginBar <br>
 * Crea un dialogo LoginForm per il controllo/inserimento dei dati <br>
 * Controlla la correttezza dei dati <br>
 * Registra una property globale con l'utente abilitato <br>
 * Modifica lo stato della LoginBar <br>
 */
@SuppressWarnings("serial")
public class LoginLogic extends Module implements LogUtenteListener, LogBottoniListener {

    private static String TAG_COOKIE = "algoscookie";
    private ArrayList<LogUtenteListener> listeners = new ArrayList<LogUtenteListener>();
    private LoginForm loginForm;
    private Utente utenteCollegato;

    /**
     * Constructor
     */
    public LoginLogic() {
        this(null);
    }// end of constructor

    /**
     * Constructor
     */
    public LoginLogic(UI gui) {
        init(gui);
    }// end of constructor


    /**
     * Initialization <br>
     * Crea un dialogo LoginForm per il controllo/inserimento dei dati <br>
     */
    private void init(UI gui) {
        LoginForm loginForm = new LoginForm();
        loginForm.addUtenteLoginListener(this);
        gui.addWindow(loginForm);
    }// end of method

    /**
     * Azione effettuata quando si modifica l'utente loggato <br>
     * <p>
     * Registra l'utente nella property di questa classe (per controlli)
     * Registra l'utente (nome?) nella sessione
     * Scrive (modificando) un cookie dedicato
     * Fire un evento ai listener registrati
     */
    @Override
    public void setUtenteLogin(Utente utente) {
        Object loginObj;
        Login login = null;

        // Registra l'utente nella property di questa classe (per controlli)
        utenteCollegato = utente;

//        // Registra l'utente (nome?) nella sessione
//        LibSession.setAttribute(Cost.COOKIE_LOGIN_NICK, utente.getNickname());

        // Registra nella sessione le informazioni del login
        loginObj = LibSession.getAttribute(Cost.LOGIN_INFO);
        if (loginObj instanceof Login) {
            login = (Login) loginObj;
            login.setUtente(utente);
        }// fine del blocco if

        // Scrive (modificando) un cookie dedicato
        writeCookie(utente);

        // Fire un evento ai listener registrati
        for (LogUtenteListener listener : listeners) {
            listener.setUtenteLogin(utente);
        }// end of for cycle
    }// end of method

    /**
     * Azione effettuata quando si preme il bottone esci <br>
     */
    @Override
    public void esciLogin() {
        utenteCollegato = null;
        LibSession.setAttribute(Cost.COOKIE_LOGIN_NICK, null);
    }// end of method

    @Override
    public void openLogin() {
    }// end of method

    /**
     * Controllo <br>
     */
    public boolean isCollegato() {
        boolean collegato = false;

        if (utenteCollegato != null) {
            collegato = true;
        }// end of if/else cycle

        return collegato;
    }// end of method

    /**
     * Controllo <br>
     */
    public Utente getUtente() {
        return utenteCollegato;
    }// end of method


    /**
     * Controllo <br>
     * Vero se esiste un utente collegato <br>
     */
    @Deprecated
    public boolean isUtente() {
        return (utenteCollegato != null);
    }// end of method


    /**
     * @return the loginForm
     */
    public LoginForm getLoginForm() {
        return loginForm;
    }

    /**
     * @param loginForm the loginForm to set
     */
    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }


    /**
     * Controllo dei cookies
     * <p>
     */
    public boolean checkCookies() {
        boolean status = false;

        return status;
    }// end of method

    /**
     * Scrive il cookie del login
     * <p>
     */
    public boolean writeCookie(Utente utente) {
        boolean status = false;

        // Create a new cookie
        Cookie myCookie = new Cookie(Cost.COOKIE_LOGIN_NICK, utente.getNickname());
        myCookie.setMaxAge(300);
        myCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
        VaadinService.getCurrentResponse().addCookie(myCookie);

        return status;
    }// end of method

    /**
     * Legge il cookie del login
     * <p>
     */
    public boolean readCookie() {
        boolean status = false;

        return status;
    }// end of method


    /**
     * Listener notificato quando si modifica l'utente loggato
     */
    public void addUtenteLoginListener(LogUtenteListener listener) {
        listeners.add(listener);
    }// end of method

}// end of class
