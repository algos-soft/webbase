package it.algos.webbase.web.module;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import it.algos.webbase.web.lib.LibSession;
import it.algos.webbase.web.navigator.AlgosNavigator;

@SuppressWarnings("serial")
public abstract class Module extends CustomComponent {

    public Module() {
    }// end of constructor

    /**
     * Crea una sola istanza di un modulo per sessione.
     * Tutte le finestre e i tab di un browser sono nella stessa sessione.
     * Usare la Reflection per costruire un'istanza di xxxModulo
     * La classe xxxModulo ha un costruttore pubblico (deprecato) SOLO per questo uso
     */
    public static Module getInstanceOld(Class clazz) {
        Module istanza = null;

        String key = clazz.getName();

        Object obj = LibSession.getAttribute(key);
        if (obj == null) {

            try {
                istanza = (Module) clazz.newInstance();
                LibSession.setAttribute(key, istanza);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        } else {
            istanza = (Module) obj;
        }

        // rimuove il componente dal suo parente se presente
        // (un componente può avere un solo parente)
        Component comp = istanza.getParent();
        if (comp != null) {
            if (comp instanceof AlgosNavigator.NavigatorView) {
                AlgosNavigator.NavigatorView aLayout = (AlgosNavigator.NavigatorView) comp;
                aLayout.removeComponent();
            }
        }

        return istanza;
    }



	/**
	 * Crea una nuova istanza (test alex)
	 */
	public static Module getInstance(Class clazz) {
		Module istanza = null;

		try {
			istanza = (Module) clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return istanza;
	}




}// end of class
