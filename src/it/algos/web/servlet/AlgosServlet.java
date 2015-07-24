package it.algos.web.servlet;

import com.vaadin.server.*;
import it.algos.web.lib.LibSession;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
    }// end of method

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        // Do session end stuff here
    }// end of method

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);

//        HttpServletRequest pippo = (HttpServletRequest)req;
//        String a = pippo.getRemoteAddr();
//        String b = pippo.getLocalAddr();
//        String ddd=pippo.getServerName();
//        StringBuffer pippos= pippo.getRequestURL();
//        String nome= pippo.getQueryString();
//        String betta=pippo.getContextPath();
//        String ss= pippo.getSession().toString();
//
//        String c="alfa";
    }// end of method


}// end of abstract class
