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
public class Login {

    // defaults
    private static final int DEFAULT_EXPIRY_TIME_SEC = 604800;    // 1 week
    private static final boolean DEFAULT_RENEW_COOKIES_ON_LOGIN = true;    // renews the cookies on login

    // key to store the Login object in the session
    public static String KEY_LOGIN = "login";
    public static String KEY_PASSWORD = "password";
    public static String KEY_REMEMBER = "rememberlogin";

    /**
     * Login gestisce il form ed alla chiusura controlla la validità del nuovo utente
     * Lancia il fire di questo evento, se l'utente è valido.
     * Si registrano qui i listeners (istanze di classi che sono interessate all'evento)
     */
    private ArrayList<LoginListener> loginListeners = new ArrayList<>();

    private Utente user;
    private LoginForm loginForm;

    private int expiryTime = DEFAULT_EXPIRY_TIME_SEC;
    private boolean renewCookiesOnLogin = DEFAULT_RENEW_COOKIES_ON_LOGIN;

    public Login() {
        setLoginForm(new BaseLoginForm());  // default login form
    }

    /**
     * Recupera l'oggetto Login dalla sessione.
     * Se manca lo crea ora e lo registra nella sessione.
     */
    public static Login getLogin() {
        Login login;
        Object obj = LibSession.getAttribute(Login.KEY_LOGIN);
        if (obj == null) {
            login = new Login();
            LibSession.setAttribute(Login.KEY_LOGIN, login);
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
            Cookie remCookie = LibCookie.getCookie(KEY_REMEMBER);
            if (remCookie != null) {
                String str = remCookie.getValue();
                if (str.equalsIgnoreCase("true")) {

                    Cookie userCookie = LibCookie.getCookie(KEY_LOGIN);
                    if (userCookie != null) {
                        username = userCookie.getValue();
                        if (!username.equals("")) {

                            Cookie passCookie = LibCookie.getCookie(KEY_PASSWORD);
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
            LibCookie.setCookie(KEY_LOGIN, user.getNickname(), expiryTime);
            LibCookie.setCookie(KEY_PASSWORD, user.getPassword(), expiryTime);
            LibCookie.setCookie(KEY_REMEMBER, "true", expiryTime);
        } else {
            // delete the cookies
            LibCookie.deleteCookie(KEY_LOGIN);
            LibCookie.deleteCookie(KEY_PASSWORD);
            LibCookie.deleteCookie(KEY_REMEMBER);
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
     * Assigns a new LoginForm.
     *
     * @param loginForm the new LoginForm
     */
    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
        this.loginForm.setLoginListener(new LoginListener() {
            @Override
            public void onUserLogin(Utente user, boolean remember) {
                userLogin(user, remember);
            }
        });
    }

    /**
     * Attempts a login from the cookies.
     *
     * @return true if success
     */
    public boolean loginFromCookies() {
        boolean success = false;
        String username = LibCookie.getCookieValue(KEY_LOGIN);
        String password = LibCookie.getCookieValue(KEY_PASSWORD);

        user = Utente.validate(username, password);
        if (user != null) {
            success = true;
        }// end of if cycle

        // if success, renew the cookies (if the option is on)
        // if failed, delete the cookies (if existing)
        if (success) {
            if (renewCookiesOnLogin) {
                Cookie cookie;
                cookie = LibCookie.getCookie(KEY_LOGIN);
                LibCookie.setCookie(KEY_LOGIN, cookie.getValue(), expiryTime);
                cookie = LibCookie.getCookie(KEY_PASSWORD);
                LibCookie.setCookie(KEY_PASSWORD, cookie.getValue(), expiryTime);
                cookie = LibCookie.getCookie(KEY_REMEMBER);
                LibCookie.setCookie(KEY_REMEMBER, cookie.getValue(), expiryTime);
            }
        } else {
            LibCookie.deleteCookie(KEY_LOGIN);
            LibCookie.deleteCookie(KEY_PASSWORD);
            LibCookie.deleteCookie(KEY_REMEMBER);
        }// end of if/else cycle

        return success;
    }// end of method

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


//    private void writeCookie(){
//
//
////        String value = this.user.getNickname();
////        createCookie("login", value, 600);
//
////        byte[] pass = "www.javacodegeeks.com".getBytes();
////
////        byte[] pKey = new byte[]{0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd,(byte) 0xef};
////
////        ObjectCrypter crypter = new ObjectCrypter(pass, pKey);
////
////        String userpass = user.getPassword();
////        String encpass="";
////        try {
////            byte[] bytes = crypter.encrypt(userpass);
////            encpass = new String(bytes, StandardCharsets.UTF_8);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////        Cookies.setCookie("login", user.getNickname());
//////        Cookies.setCookie("password", encpass);
//    }

    /**
     * Whether the cookies are renewed after a successful login.
     *
     * @param renewCookiesOnLogin true to renew the expiry time on each login
     */
    public void setRenewCookiesOnLogin(boolean renewCookiesOnLogin) {
        this.renewCookiesOnLogin = renewCookiesOnLogin;
    }


}
