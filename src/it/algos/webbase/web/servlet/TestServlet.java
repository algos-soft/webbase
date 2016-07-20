package it.algos.webbase.web.servlet;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import it.algos.webbase.web.AlgosApp;
import it.algos.webbase.web.ui.TestUI;

/**
 * Created by gac on 19 lug 2016.
 */
@WebServlet(urlPatterns = { "/test", "/VAADIN/*" }, asyncSupported = true, displayName = "Test")
@VaadinServletConfiguration(productionMode = false, ui = TestUI.class)
public class TestServlet extends VaadinServlet {
}// end of class
