package it.algos.webbase.web.servlet;

import com.vaadin.server.*;
import it.algos.webbase.web.lib.Cost;
import it.algos.webbase.web.lib.LibSession;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

/**
 * Servlet 3.0 introduces a @WebServlet annotation which can be used to replace the traditional web.xml.
 * <p>
 * The straightforward approach to create a Vaadin application using servlet 3.0 annotations,
 * is to simply move whatever is in web.xml to a custom servlet class (extends VaadinServlet)
 * and annotate it using @WebServlet and add @WebInitParams as needed.
 * <p><
 * Vaadin 7.1 introduces two features which makes this a lot easier, @VaadinServletConfiguration
 * and automatic UI finding.
 * VaadinServletConfiguration is a type safe, Vaadin version of @WebInitParam
 * which provides you with the option to select UI by referring the UI class
 * directly toggle productionMode using a boolean and more
 */
public abstract class AlgosServlet extends VaadinServlet implements SessionInitListener, SessionDestroyListener {

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
        getService().addSessionDestroyListener(this);
    }// end of method

    /**
     * Invoked when a new Vaadin service session is initialized for that service.
     * <p>
     * Because of the way different service instances share the same session, the listener is not necessarily notified immediately
     * when the session is created but only when the first request for that session is handled by a specific service.
     *
     * @param event the initialization event
     * @throws ServiceException a problem occurs when processing the event
     */
    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        // Do session start stuff here
        LibSession.setDeveloper(false);

        // Controlla i cookies esistenti
        this.checkCookies();
    }// end of method

    /**
     * Called when a Vaadin service session is no longer used.
     *
     * @param event the event with details about the destroyed session
     */
    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        // Do session end stuff here
    }// end of method


    /**
     * Controlla i cookies esistenti
     * <p>
     */
    protected void checkCookies() {
        // Fetch all cookies
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

        // Store the current cookies in the service session
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Cost.COOKIE_LOGIN_NICK)) {
                    LibSession.setAttribute(Cost.COOKIE_LOGIN_NICK, cookie.getValue());
                }// fine del blocco if
                if (cookie.getName().equals(Cost.COOKIE_LOGIN_PASS)) {
                    LibSession.setAttribute(Cost.COOKIE_LOGIN_PASS, cookie.getValue());
                }// fine del blocco if
                if (cookie.getName().equals(Cost.COOKIE_LOGIN_ROLE)) {
                    LibSession.setAttribute(Cost.COOKIE_LOGIN_ROLE, cookie.getValue());
                }// fine del blocco if
            } // fine del ciclo for-each
        }// fine del blocco if


//         Create a new cookie
//        Cookie myCookie = new Cookie(Cost.COOKIE_LOGIN_NICK, "cookie-value");
//        myCookie.setMaxAge(300);
//        myCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
//        VaadinService.getCurrentResponse().addCookie(myCookie);

//        Cookies.setCookie("pippo", "Some-other-value");

    }// end of method

}// end of abstract class
