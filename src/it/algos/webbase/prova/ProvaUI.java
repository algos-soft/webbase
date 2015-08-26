package it.algos.webbase.prova;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import it.algos.webbase.domain.versione.VersioneModulo;
import it.algos.webbase.web.ui.AlgosUI;

//@Theme("base")        // VAADIN.themes
//@Theme("chamelon")    // VAADIN.themes
//@Theme("liferay")     // VAADIN.themes
//@Theme("reindeer")    // VAADIN.themes
//@Theme("runo")        // VAADIN.themes
@Theme("valo")          // VAADIN.themes
public class ProvaUI extends AlgosUI {

    @Override
    protected void init(VaadinRequest request) {
        super.init(request);

        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        // modulo di partenza - utilizza un modulo già esistente in webbase come dimostrazione iniziale
        // tipicamente viene modificato secondo le necessità specifiche dell'applicazione
        layout.addComponent(new VersioneModulo());

    }// end of method

}//end of UI class
