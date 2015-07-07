package it.algos.web.lib;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;

/**
 * Created by gac on 26/05/15.
 */
public abstract class LibSession {

    /**
     * Recupera dalla request l'attributo developer (se esiste)
     * Regola la variabile statica
     */
    public static void checkDeveloper(VaadinRequest request) {
        LibSession.checkBool(request, Attribute.developer);
    }// end of method

    /**
     * Recupera dalla request l'attributo debug (se esiste)
     * Regola la variabile statica
     */
    public static void checkDebug(VaadinRequest request) {
        LibSession.checkBool(request, Attribute.debug);
    }// end of method

    /**
     * Recupera dalla sessione l'attributo developer
     */
    public static boolean isDeveloper() {
        return isBool(Attribute.developer);
    }// end of method

    /**
     * Regola per la sessione corrente l'attributo developer
     */
    public static void setDeveloper(boolean status) {
        setBool(Attribute.developer, status);
    }// end of method

    /**
     * Recupera dalla sessione l'attributo debug
     */
    public static boolean isDebug() {
        return isBool(Attribute.debug);
    }// end of method

    /**
     * Regola per la sessione corrente l'attributo debug
     */
    public static void setDebug(boolean status) {
        setBool(Attribute.debug, status);
    }// end of method


    /**
     * Recupera dalla sessione l'attributo specifico
     */
    public static boolean isBool(Attribute attributo) {
        boolean status = false;
        Object devObj = null;
        VaadinSession sessione = VaadinSession.getCurrent();

        if (attributo != null && sessione != null) {
            devObj = sessione.getAttribute(attributo.toString());
            if (devObj != null) {
                if (devObj instanceof Boolean) {
                    status = (Boolean) devObj;
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        return status;
    }// end of method


    /**
     * Regola lo specifico attributo
     */
    public static void setBool(Attribute attributo, boolean status) {
        Object devObj = null;
        VaadinSession sessione = VaadinSession.getCurrent();

        if (attributo != null && sessione != null) {
            sessione.setAttribute(attributo.toString(), status);
        }// fine del blocco if
    }// end of method

    /**
     * Regola lo specifico attributo.
     * Qualsiasi applicazione può registrare dei propri attributi nella session.
     */
    public static void setAttribute(String name, Object value) {
        VaadinSession session = VaadinSession.getCurrent();
        if (session != null) {
            session.setAttribute(name, value);
        }
    }// end of method

    /**
     * Recupera un attributo.
     */
    public static Object getAttribute(String name) {
        Object attr = null;
        VaadinSession session = VaadinSession.getCurrent();
        if (session != null) {
            attr = session.getAttribute(name);
        }
        return attr;
    }// end of method

    /**
     * Recupera dalla request l'attributo booleano indicato (se esiste)
     * Regola la variabile statica
     */
    public static void checkBool(VaadinRequest request, Attribute attribute) {
        boolean status = LibSession.getBool(request, attribute);
        LibSession.setBool(attribute, status);
    }// end of method

    /**
     * Recupera dalla request l'attributo booleano indicato (se esiste)
     * Restituisce il valore booleano
     */
    public static boolean getBool(VaadinRequest request, Attribute attribute) {
        boolean status = false;
        Object obj = null;
        String objValue = "";
        String attributeTxt = attribute.toString();

        if (request != null) {
            obj = request.getParameter(attributeTxt);
        }// fine del blocco if

        if (obj != null) {
            objValue = (String) obj;
            if (objValue.equals("False") || objValue.equals("Falso") || objValue.equals("false") || objValue.equals("falso)")) {
                status = false;
            } else {
                status = true;
            }// fine del blocco if-else
        }// fine del blocco if

        return status;
    }// end of method


    public enum Attribute {developer, debug}


}// end of static abstract class
