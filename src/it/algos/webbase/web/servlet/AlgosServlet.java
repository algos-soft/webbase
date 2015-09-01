package it.algos.webbase.web.servlet;

import com.google.gwt.user.client.Cookies;
import com.vaadin.server.*;
import it.algos.webbase.web.AlgosApp;
import it.algos.webbase.web.lib.Cost;
import it.algos.webbase.web.lib.LibSession;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AlgosServlet extends VaadinServlet implements SessionInitListener, SessionDestroyListener {

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
        getService().addSessionDestroyListener(this);
    }// end of method

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        // Do session start stuff here
        LibSession.setDeveloper(false);

        // Controlla i cookies esistenti
        this.checkCookies();
    }// end of method

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        // Do session end stuff here
    }// end of method

    /**
     * Dispatches client requests to the protected
     * <code>service</code> method. There's no need to
     * override this method.
     *
     * @param req the {@link HttpServletRequest} object that
     * contains the request the client made of the servlet
     *
     * @param res the {@link HttpServletResponse} object that
     * contains the response the servlet returns to the client
     *
     * @exception IOException if an input or output error occurs
     * while the servlet is handling the HTTP request
     *
     * @exception ServletException if the HTTP request cannot
     * be handled
     *
     * @see javax.servlet.Servlet#service
     */
    @Override
    public void service(ServletRequest request, ServletResponse res) throws ServletException, IOException {
        super.service(request, res);
    }// end of method

    /**
     * Controlla i cookies esistenti
     * <p>
     */
    protected void checkCookies() {
        // Fetch all cookies
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

        // Store the current cookies in the service session
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


//         Create a new cookie
//        Cookie myCookie = new Cookie(Cost.COOKIE_LOGIN_NICK, "cookie-value");
//        myCookie.setMaxAge(300);
//        myCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
//        VaadinService.getCurrentResponse().addCookie(myCookie);

//        Cookies.setCookie("pippo", "Some-other-value");

    }// end of method

}// end of abstract class
