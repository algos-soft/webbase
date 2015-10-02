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
public class Login implements LogformListener, LoginListener {

    // defaults
    private static final int DEFAULT_EXPIRY_TIME_SEC = 604800;    // 1 week
    private static final boolean DEFAULT_RENEW_COOKIES_ON_LOGIN = true;    // renews the cookies on login

    // key to store the Login object in the session
    public static final String LOGIN_KEY_IN_SESSION = "login";

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
    private BaseLoginForm loginForm;

    // prefix for the cookie names (optional)
    private String cookiePrefix="";

    private int expiryTime = DEFAULT_EXPIRY_TIME_SEC;
    private boolean renewCookiesOnLogin = DEFAULT_RENEW_COOKIES_ON_LOGIN;

    public Login() {
//        loginForm = new BaseLoginForm();
//        loginForm.setLoginListener(this);
//        setLoginForm(loginForm);  // default login form
    }

    /**
     * Recupera l'oggetto Login dalla sessione.
     * Se manca lo crea ora e lo registra nella sessione.
     */
    public static Login getLogin() {
        Login login;
        Object obj = LibSession.getAttribute(Login.LOGIN_KEY_IN_SESSION);
        if (obj == null) {
            login = new Login();
            LibSession.setAttribute(Login.LOGIN_KEY_IN_SESSION, login);
        } else {
            login = (Login) obj;
        }
        return login;
    }

    // displays the Login form
    public void showLoginForm(UI ui) {

        if (loginForm != null) {


            String username = "";
            String password = "";
            boolean remember = false;

            // retrieve login data from the cookies
            Cookie remCookie = LibCookie.getCookie(getRememberKey());
            if (remCookie != null) {
                String str = remCookie.getValue();
                if (str.equalsIgnoreCase("true")) {

                    Cookie userCookie = LibCookie.getCookie(getLoginKey());
                    if (userCookie != null) {
                        username = userCookie.getValue();
                        if (!username.equals("")) {

                            Cookie passCookie = LibCookie.getCookie(getPasswordKey());
                            if (passCookie != null) {
                                password = passCookie.getValue();
                            }

                            remember = true;

                        }
                    }

                }
            }

            loginForm.setUsername(username);
            loginForm.setPassword(password);
            loginForm.setRemember(remember);

            Window window = loginForm.getWindow();
            window.center();
            ui.addWindow(window);
        }
    }

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
            LibCookie.setCookie(getLoginKey(), user.getNickname(), expiryTime);
            LibCookie.setCookie(getPasswordKey(), user.getPassword(), expiryTime);
            LibCookie.setCookie(getRememberKey(), "true", expiryTime);
        } else {
            // delete the cookies
            LibCookie.deleteCookie(getLoginKey());
            LibCookie.deleteCookie(getPasswordKey());
            LibCookie.deleteCookie(getRememberKey());
        }


        // notify all the listeners
        for (LoginListener listener : loginListeners) {
            listener.onUserLogin(user, remember);
        }// end of for cycle

    }// end of method

    public Utente getUser() {
        return user;
    }

    /**
     * Open a new LoginForm.
     */
    public void openLoginForm() {
        loginForm = new BaseLoginForm();
        loginForm.setLoginListener(this);
        loginForm.show(UI.getCurrent());

//        this.loginForm = loginForm;
//        this.loginForm.setLoginListener(new LoginListener() {
//            @Override
//            public void onUserLogin(Utente user, boolean remember) {
//                userLogin(user, remember);
//            }
//        } );
    }// end of method

    /**
     * Attempts a login from the cookies.
     *
     * @return true if success
     */
    public boolean loginFromCookies() {
        boolean success = false;
        String username = LibCookie.getCookieValue(getLoginKey());
        String password = LibCookie.getCookieValue(getPasswordKey());

        user = Utente.validate(username, password);
        if (user != null) {
            success = true;
        }// end of if cycle

        // if success, renew the cookies (if the option is on)
        // if failed, delete the cookies (if existing)
        if (success) {
            if (renewCookiesOnLogin) {
                Cookie cookie;
                cookie = LibCookie.getCookie(getLoginKey());
                LibCookie.setCookie(getLoginKey(), cookie.getValue(), expiryTime);
                cookie = LibCookie.getCookie(getPasswordKey());
                LibCookie.setCookie(getPasswordKey(), cookie.getValue(), expiryTime);
                cookie = LibCookie.getCookie(getRememberKey());
                LibCookie.setCookie(getRememberKey(), cookie.getValue(), expiryTime);
            }
        } else {
            LibCookie.deleteCookie(getLoginKey());
            LibCookie.deleteCookie(getPasswordKey());
            LibCookie.deleteCookie(getRememberKey());
        }// end of if/else cycle

        return success;
    }// end of method


    /**
     * @return true if a user is logged
     */
    public boolean isLogged(){
        return (getUser()!=null);
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
    }


    /**
     * Whether the cookies are renewed after a successful login.
     *
     * @param renewCookiesOnLogin true to renew the expiry time on each login
     */
    public void setRenewCookiesOnLogin(boolean renewCookiesOnLogin) {
        this.renewCookiesOnLogin = renewCookiesOnLogin;
    }

    public void setCookiePrefix(String cookiePrefix) {
        this.cookiePrefix = cookiePrefix;
    }

    private String getLoginKey(){
        String name="";
        if(!cookiePrefix.equals("")){
            name+=cookiePrefix+".";
        }
        return name+=COOKIENAME_LOGIN;
    }

    private String getPasswordKey(){
        String name="";
        if(!cookiePrefix.equals("")){
            name+=cookiePrefix+".";
        }
        return name+=COOKIENAME_PASSWORD;
    }

    private String getRememberKey(){
        String name="";
        if(!cookiePrefix.equals("")){
            name+=cookiePrefix+".";
        }
        return name+=COOKIENAME_REMEMBER;
    }



    /**
     * Adds a LoginListener.
     */
    public void addLoginListener(LoginListener l) {
        loginListeners.add(l);
    }// end of method

    /**
     * Removes all the login listeners
     */
    public void removeAllListeners() {
        loginListeners.clear();
    }// end of method

    /**
     * Registers a unique LoginListener.
     * All the previous LoginListeners are deleted
     */
    public void setLoginListener(LoginListener l) {
        removeAllListeners();
        addLoginListener(l);
    }// end of method


    /**
     * Evento ricevuto dalla classe LoginBar quando si clicca il bottone Login <br>
     */
    @Override
    public void onLogFormRequest() {
        openLoginForm();
    }// end of method

    /**
     * Evento ricevuto dalla classe LoginForm quando si modifica l'utente loggato <br>
     * <p>
     * Registra l'utente
     * Rilancia l'evento ed informa (tramite listener) chi è interessato e registrato presso questa classe <br>
     */
    @Override
    public void onUserLogin(Utente user, boolean remember) {
        this.user = user;

        if (loginListeners != null) {
            for (LoginListener listener : loginListeners) {
                listener.onUserLogin(user, remember);
            }// end of for cycle
        }// end of if cycle
    }// end of method


}
