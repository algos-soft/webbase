package it.algos.webbase.web.login;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import it.algos.webbase.domain.utente.Utente;
import it.algos.webbase.web.lib.LibCookie;
import it.algos.webbase.web.lib.LibCrypto;
import it.algos.webbase.web.lib.LibSession;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Main Login object (Login logic).
 * An instance of this object is created and stored in the current session
 * when getLogin() in invoked. Subsequent calls to getLogin() return the same object
 * from the session.
 * <p>
 * <p>
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

    // listeners notificati al logout
    private ArrayList<LogoutListener> logoutListeners = new ArrayList<>();

    private UserIF user;

    private String cookiePrefix = "";
    private int expiryTime = DEFAULT_EXPIRY_TIME_SEC;
    private boolean renewCookiesOnLogin = DEFAULT_RENEW_COOKIES_ON_LOGIN;


    /**
     * Recupera l'oggetto Login dalla sessione.
     * Se manca lo crea ora e lo registra nella sessione.
     */
    public static Login getLogin() {
        Login login = null;
        Object obj = LibSession.getAttribute(Login.LOGIN_KEY_IN_SESSION);
        if (obj == null) {
            login = new Login();
            LibSession.setAttribute(Login.LOGIN_KEY_IN_SESSION, login);
        } else {
            try {
                login = (Login) obj;
            } catch (Exception e) {
            }
        }

        return login;
    }


    /**
     * Registra un oggetto Login nella sessione.
     */
    public static void setLogin(Login login) {
        LibSession.setAttribute(Login.LOGIN_KEY_IN_SESSION, login);
    }

    /**
     * Retrieve the Login form
     * @return the Login form to show
     */
    protected AbsLoginForm getLoginForm(){
        return new DefaultLoginForm();
    }

    /**
     * Displays the Login form
     */
    public void showLoginForm() {

        AbsLoginForm loginForm = getLoginForm();
        loginForm.setLoginListener(this);

        if (loginForm != null) {

            // retrieve login data from the cookies
            String username = LibCookie.getCookieValue(getLoginKey());
            String encPass = LibCookie.getCookieValue(getPasswordKey());
            String clearPass = LibCrypto.decrypt(encPass);
            String rememberStr = LibCookie.getCookieValue(getRememberKey());
            boolean remember = (rememberStr.equalsIgnoreCase("true"));

            loginForm.setUsername(username);
            loginForm.setPassword(clearPass);
            loginForm.setRemember(remember);

            Window window = loginForm.getWindow();
            window.center();
            UI.getCurrent().addWindow(window);
        }

    }


    /**
     * Logout the current user
     */
    public void logout() {
        UserIF oldUser = user;
        user = null;
        LogoutEvent e = new LogoutEvent(this, oldUser);
        for (LogoutListener l : logoutListeners) {
            l.onUserLogout(e);
        }

        deleteCookies();

    }


    /**
     * Invoked after a successful login happened using the Login form.
     *
     * @param e the LoginEvent object
     */
    protected void userLogin(LoginEvent e) {

        // register user
        this.user = e.getUser();

        if (e.isRememberOption()) {

            // create/update the cookies
            LibCookie.setCookie(getLoginKey(), user.getNickname(), expiryTime);
            LibCookie.setCookie(getPasswordKey(), user.getEncryptedPassword(), expiryTime);
            LibCookie.setCookie(getRememberKey(), "true", expiryTime);

        } else {
            // delete the cookies
            deleteCookies();
        }

    }


    /**
     * Renew the expiry time for all the cookies
     */
    private void renewCookies() {
        Cookie cookie;
        cookie = LibCookie.getCookie(getLoginKey());
        LibCookie.setCookie(getLoginKey(), cookie.getValue(), expiryTime);
        cookie = LibCookie.getCookie(getPasswordKey());
        LibCookie.setCookie(getPasswordKey(), cookie.getValue(), expiryTime);
        cookie = LibCookie.getCookie(getRememberKey());
        LibCookie.setCookie(getRememberKey(), cookie.getValue(), expiryTime);
    }

    /**
     * Delete all the cookies
     */
    private void deleteCookies() {
        LibCookie.deleteCookie(getLoginKey());
        LibCookie.deleteCookie(getPasswordKey());
        LibCookie.deleteCookie(getRememberKey());
    }


    /**
     * Attempts a login directly from the cookies without showing the login form.
     *
     * @return true if success
     */
    public boolean loginFromCookies() {
        boolean success = false;
        String username = LibCookie.getCookieValue(getLoginKey());
        String encPassword = LibCookie.getCookieValue(getPasswordKey());
        String clearPass = LibCrypto.decrypt(encPassword);

        UserIF user = userFromNick(username);

        if(user!=null){
            if(user.validatePassword(clearPass)){
                this.user=user;
                success = true;
            }
        }

        // if success, renew the cookies (if the option is on)
        // if failed, delete the cookies
        if (success) {
            if (renewCookiesOnLogin) {
                renewCookies();
            }

            LoginEvent e = new LoginEvent(this, user, LoginTypes.TYPE_COOKIES, false);

            // use Iterator instead foreach to avoid ConcurrentModificationException
            for (Iterator<LoginListener> it = loginListeners.iterator(); it.hasNext(); ) {
                LoginListener l = it.next();
                l.onUserLogin(e);
            }

//            for(LoginListener l : loginListeners){
//                l.onUserLogin(e);
//            }

        } else {
            deleteCookies();
        }


        return success;
    }


    /**
     * Retrieves the user from a given username
     * @return the user corresponding to a given username
     */
    protected UserIF userFromNick(String username){
        Utente aUser = Utente.read(username);
        return aUser;
    }


    /**
     * @return the expiry time of the cookies in seconds
     */
    public int getExpiryTime() {
        return expiryTime;
    }

    /**
     * Sets the expiry time for the cookies
     *
     * @param expiryTime the expiry time of the cookies in seconds
     */
    public void setExpiryTime(int expiryTime) {
        this.expiryTime = expiryTime;
    }

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
     * Adds a LogoutListener
     */
    public void addLogoutListener(LogoutListener l) {
        logoutListeners.add(l);
    }

    /**
     * Evento ricevuto dalla classe LoginBar quando si clicca il bottone Login <br>
     */
    @Override
    public void onLogFormRequest() {
        showLoginForm();
    }

    /**
     * @return true if a user is logged
     */
    public boolean isLogged() {
        return (getUser() != null);
    }

    public UserIF getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    /**
     * Evento ricevuto dalla classe LoginForm quando si modifica l'utente loggato <br>
     * <p>
     * Registra l'utente
     * Rilancia l'evento ed informa (tramite listener) chi è interessato e registrato presso questa classe <br>
     */
    @Override
    public void onUserLogin(LoginEvent e) {
        userLogin(e);

        // notify all the listeners
        // use Iterator instead of foreach to avoid ConcurrentModificationException
        if (loginListeners != null) {
            for (Iterator<LoginListener> it = loginListeners.iterator(); it.hasNext(); ) {
                LoginListener l = it.next();
                l.onUserLogin(e);
            }
        }

//        if (loginListeners != null) {
//            for (LoginListener listener : loginListeners) {
//                listener.onUserLogin(e);
//            }
//        }

    }


}
