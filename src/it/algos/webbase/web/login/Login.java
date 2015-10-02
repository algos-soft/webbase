package it.algos.webbase.web.login;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import it.algos.webbase.domain.utente.Utente;
import it.algos.webbase.web.lib.LibCookie;
import it.algos.webbase.web.lib.LibSession;

import javax.servlet.http.Cookie;
import java.util.ArrayList;

/**
 * Main Login object (Login logic).
 * An instance of this object is created and stored in the current session
 * when getLogin() in invoked. Subsequent calls to getLogin() return the same object
 * from the session.
 */

/**
 * Eventi inerenti il login.
 * <p>
 * LOGIN CLICK  -> Open Form
 * LoginBar regista i listeners: addLogformListener(), removeAllLogformListener(), setLogformListener()
 * LoginBar fire evento in loginCommandSelected()
 * 1.Login si registra presso LoginBar, col metodo AlgosUI.regolaListenerLog()
 * 1.Login implementa l'interfaccia LogformListener
 * 1.Login ascolta e riceve l'evento in onLogFormRequest()
 * <p>
 * FORM CONFIRM  -> Convalida utente
 * LoginForm regista un solo listener: setLoginListener()
 * LoginForm fire evento in utenteLoggato()
 * 1.Login si registra presso LoginForm, col metodo Login.openLoginForm()
 * 1.Login implementa l'interfaccia LoginListener
 * 1.Login ascolta e riceve l'evento in onUserLogin()
 * Login regista i listeners: addLoginListener(), removeAllLoginListeners(), setLoginListener()
 * LoginBar fire evento in onUserLogin() - Rilancia quello ricevuto
 * 2.LoginBar si registra presso login, col metodo AlgosUI.regolaListenerLog()
 * 2.LoginBar implementa l'interfaccia LoginListener
 * 2.LoginBar ascolta e riceve l'evento in onUserLogin()
 * 3.AlgosUI si registra presso login, col metodo AlgosUI.regolaListenerLog()
 * 3.AlgosUI implementa l'interfaccia LoginListener
 * 3.AlgosUI ascolta e riceve l'evento in onUserLogin()
 * <p>
 * LOGOUT CLICK  -> Disabilita utente
 * LoginBar regista i listeners: addLogoutListener(), removeAllLogoutListeners(), setLogoutListener()
 * LoginBar fire evento in logoutCommandSelected()
 * 1.AlgosUI si registra presso LoginBar, col metodo AlgosUI.regolaListenerLog()
 * 1.AlgosUI implementa l'interfaccia LogoutListener
 * 1.AlgosUI ascolta e riceve l'evento in onUserLogout()
 */

public class Login implements LogformListener, LoginListener {

    // key to store the Login object in the session
    public static final String LOGIN_KEY_IN_SESSION = "login";
    // defaults
    private static final int DEFAULT_EXPIRY_TIME_SEC = 604800;    // 1 week
    private static final boolean DEFAULT_RENEW_COOKIES_ON_LOGIN = true;    // renews the cookies on login
    // default cookie names
    private static final String COOKIENAME_LOGIN = "login_username";
    private static final String COOKIENAME_PASSWORD = "login_password";
    private static final String COOKIENAME_REMEMBER = "login_remember";

    /**
     * Login gestisce il form ed alla chiusura controlla la validità del nuovo utente
     * Lancia il fire di questo evento, se l'utente è valido.
     * Si registrano qui i listeners (istanze di classi che sono interessate all'evento)
     */
    private ArrayList<LoginListener> loginListeners = new ArrayList<>();

    private Utente user;

    private String cookiePrefix = "";
    private int expiryTime = DEFAULT_EXPIRY_TIME_SEC;
    private boolean renewCookiesOnLogin = DEFAULT_RENEW_COOKIES_ON_LOGIN;


    public Login() {
    }// end of constructor

    /**
     * Recupera l'oggetto Login dalla sessione.
     * Se manca lo crea ora e lo registra nella sessione.
     */
    public static Login getLogin() {
        Login login;
        Object obj = LibSession.getAttribute(Login.COOKIENAME_LOGIN);
        if (obj == null) {
            login = new Login();
            LibSession.setAttribute(Login.COOKIENAME_LOGIN, login);
        } else {
            login = (Login) obj;
        }// end of if/else cycle

        return login;
    }// end of method

    public static void invalidateUser() {
        Login login = getLogin();

        if (login != null) {
            login.user = null;
        }// end of if cycle

    }// end of method

    /**
     * Displays the Login form
     */
    public void showLoginForm() {
        BaseLoginForm loginForm = new BaseLoginForm();
        loginForm.setLoginListener(this);

        if (loginForm != null) {

            // retrieve login data from the cookies
            String username = LibCookie.getCookieValue(COOKIENAME_LOGIN);
            String password = LibCookie.getCookieValue(COOKIENAME_PASSWORD);
            String rememberStr = LibCookie.getCookieValue(COOKIENAME_REMEMBER);
            boolean remember = (rememberStr.equalsIgnoreCase("true"));

            loginForm.setUsername(username);
            loginForm.setPassword(password);
            loginForm.setRemember(remember);

            Window window = loginForm.getWindow();
            window.center();
            UI.getCurrent().addWindow(window);
        }// end of if cycle

    }// end of method

    /**
     * Invoked after a successful login happened using the Login form.
     *
     * @param user     the logged user
     * @param remember the value for the Remember option
     */
    protected void userLogin(Utente user, boolean remember) {

        // register user
        this.user = user;

        if (remember) {
            // create/update the cookies
            LibCookie.setCookie(COOKIENAME_LOGIN, user.getNickname(), expiryTime);
            LibCookie.setCookie(COOKIENAME_PASSWORD, user.getPassword(), expiryTime);
            LibCookie.setCookie(COOKIENAME_REMEMBER, "true", expiryTime);
        } else {
            // delete the cookies
            LibCookie.deleteCookie(COOKIENAME_LOGIN);
            LibCookie.deleteCookie(COOKIENAME_PASSWORD);
            LibCookie.deleteCookie(COOKIENAME_REMEMBER);
        }// end of if/else cycle

    }// end of method

    /**
     * Attempts a login from the cookies.
     *
     * @return true if success
     */
    public boolean loginFromCookies() {
        boolean success = false;
        String username = LibCookie.getCookieValue(COOKIENAME_LOGIN);
        String password = LibCookie.getCookieValue(COOKIENAME_PASSWORD);

        user = Utente.validate(username, password);
        if (user != null) {
            success = true;
        }// end of if cycle

        // if success, renew the cookies (if the option is on)
        // if failed, delete the cookies (if existing)
        if (success) {
            if (renewCookiesOnLogin) {
                Cookie cookie;
                cookie = LibCookie.getCookie(COOKIENAME_LOGIN);
                LibCookie.setCookie(COOKIENAME_LOGIN, cookie.getValue(), expiryTime);
                cookie = LibCookie.getCookie(COOKIENAME_PASSWORD);
                LibCookie.setCookie(COOKIENAME_PASSWORD, cookie.getValue(), expiryTime);
                cookie = LibCookie.getCookie(COOKIENAME_REMEMBER);
                LibCookie.setCookie(COOKIENAME_REMEMBER, cookie.getValue(), expiryTime);
            }
        } else {
            LibCookie.deleteCookie(COOKIENAME_LOGIN);
            LibCookie.deleteCookie(COOKIENAME_PASSWORD);
            LibCookie.deleteCookie(COOKIENAME_REMEMBER);
        }// end of if/else cycle

        return success;
    }// end of method

    /**
     * @return the expiry time of the cookies in seconds
     */
    public int getExpiryTime() {
        return expiryTime;
    }// end of method

    /**
     * Sets the expiry time for the cookies
     *
     * @param expiryTime the expiry time of the cookies in seconds
     */
    public void setExpiryTime(int expiryTime) {
        this.expiryTime = expiryTime;
    }// end of method

    /**
     * Whether the cookies are renewed after a successful login.
     *
     * @return true if the cookies are renewed
     */
    public boolean isRenewCookiesOnLogin() {
        return renewCookiesOnLogin;
    }// end of method

    /**
     * Whether the cookies are renewed after a successful login.
     *
     * @param renewCookiesOnLogin true to renew the expiry time on each login
     */
    public void setRenewCookiesOnLogin(boolean renewCookiesOnLogin) {
        this.renewCookiesOnLogin = renewCookiesOnLogin;
    }// end of method

    public void setCookiePrefix(String cookiePrefix) {
        this.cookiePrefix = cookiePrefix;
    }

    private String getLoginKey() {
        String name = "";
        if (!cookiePrefix.equals("")) {
            name += cookiePrefix + ".";
        }
        return name += COOKIENAME_LOGIN;
    }// end of method

    private String getPasswordKey() {
        String name = "";
        if (!cookiePrefix.equals("")) {
            name += cookiePrefix + ".";
        }
        return name += COOKIENAME_PASSWORD;
    }

    private String getRememberKey() {
        String name = "";
        if (!cookiePrefix.equals("")) {
            name += cookiePrefix + ".";
        }
        return name += COOKIENAME_REMEMBER;
    }

    /**
     * Removes all the login listeners
     */
    public void removeAllLoginListeners() {
        loginListeners.clear();
    }// end of method

    /**
     * Adds a LoginListener
     */
    public void addLoginListener(LoginListener l) {
        loginListeners.add(l);
    }// end of method

    /**
     * Registers a unique LoginListener.
     * All the previous LoginListeners are deleted
     */
    public void setLoginListener(LoginListener l) {
        removeAllLoginListeners();
        addLoginListener(l);
    }// end of method

    /**
     * Evento ricevuto dalla classe LoginBar quando si clicca il bottone Login <br>
     */
    @Override
    public void onLogFormRequest() {
        showLoginForm();
    }// end of method

    /**
     * @return true if a user is logged
     */
    public boolean isLogged() {
        return (getUser() != null);
    }// end of method

    public Utente getUser() {
        return user;
    }// end of method

    /**
     * Evento ricevuto dalla classe LoginForm quando si modifica l'utente loggato <br>
     * <p>
     * Registra l'utente
     * Rilancia l'evento ed informa (tramite listener) chi è interessato e registrato presso questa classe <br>
     */
    @Override
    public void onUserLogin(Utente user, boolean remember) {
        userLogin(user, remember);

        // notify all the listeners
        if (loginListeners != null) {
            for (LoginListener listener : loginListeners) {
                listener.onUserLogin(user, remember);
            }// end of for cycle
        }// end of if cycle
    }// end of method

}// end of class
