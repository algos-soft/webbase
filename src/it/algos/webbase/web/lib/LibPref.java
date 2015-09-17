package it.algos.webbase.web.lib;

import com.vaadin.ui.Notification;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.domain.pref.TypePref;

/**
 * Created by gac on 05 set 2015.
 * .
 */
public abstract class LibPref {

    /**
     * Crea una nuova preferenza di tipo booleano
     * La crea solo se non esistente
     *
     * @param code     key code
     * @param value    di default
     * @param descPref dettagliata (obbligatoria per Pref)
     */
    public static void newBool(String code, Boolean value, String descPref) {
        newBase(false, code, value, descPref, TypePref.booleano);
    }// end of method


    /**
     * Crea una nuova preferenza di tipo stringa
     * La crea solo se non esistente
     *
     * @param code     key code
     * @param value    di default
     * @param descPref dettagliata (obbligatoria per Pref)
     */
    public static void newStr(String code, String value, String descPref) {
        newBase(false, code, value, descPref, TypePref.stringa);
    }// end of method


    /**
     * Crea una nuova preferenza di tipo intero
     * La crea solo se non esistente
     *
     * @param code     key code
     * @param value    di default
     * @param descPref dettagliata (obbligatoria per Pref)
     */
    public static void newInt(String code, int value, String descPref) {
        newBase(false, code, value, descPref, TypePref.intero);
    }// end of method


    /**
     * Crea una nuova preferenza di tipo booleano
     * La crea solo se non esistente
     * Crea anche un record di log nella tavola Versione
     *
     * @param code     key code
     * @param value    di default
     * @param descPref dettagliata (obbligatoria per Pref)
     */
    public static void newVersBool(String code, boolean value, String descPref) {
        newBase(true, code, value, descPref, "", TypePref.booleano);
    }// end of method

    /**
     * Crea una nuova preferenza di tipo booleano
     * La crea solo se non esistente
     * Crea anche un record di log nella tavola Versione
     *
     * @param code     key code
     * @param value    di default
     * @param descPref dettagliata (obbligatoria per Pref)
     * @param descVers dettagliata (facoltativa e aggiuntiva a quella automatica di Versione)
     */
    public static void newVersBool(String code, boolean value, String descPref, String descVers) {
        newBase(true, code, value, descPref, descVers, TypePref.booleano);
    }// end of method

    /**
     * Crea una nuova preferenza di tipo stringa
     * La crea solo se non esistente
     * Crea anche un record di log nella tavola Versione
     *
     * @param code     key code
     * @param value    di default
     * @param descPref dettagliata (obbligatoria per Pref)
     */
    public static void newVersStr(String code, String value, String descPref) {
        newBase(true, code, value, descPref, "", TypePref.stringa);
    }// end of method

    /**
     * Crea una nuova preferenza di tipo stringa
     * La crea solo se non esistente
     * Crea anche un record di log nella tavola Versione
     *
     * @param code     key code
     * @param value    di default
     * @param descPref dettagliata (obbligatoria per Pref)
     * @param descVers dettagliata (facoltativa e aggiuntiva a quella automatica di Versione)
     */
    public static void newVersStr(String code, String value, String descPref, String descVers) {
        newBase(true, code, value, descPref, descVers, TypePref.stringa);
    }// end of method

    /**
     * Crea una nuova preferenza di tipo intero
     * La crea solo se non esistente
     * Crea anche un record di log nella tavola Versione
     *
     * @param code     key code
     * @param value    di default
     * @param descPref dettagliata (obbligatoria per Pref)
     */
    public static void newVersInt(String code, int value, String descPref) {
        newBase(true, code, value, descPref, "", TypePref.intero);
    }// end of method

    /**
     * Crea una nuova preferenza di tipo intero
     * La crea solo se non esistente
     * Crea anche un record di log nella tavola Versione
     *
     * @param code     key code
     * @param value    di default
     * @param descPref dettagliata (obbligatoria per Pref)
     * @param descVers dettagliata (facoltativa e aggiuntiva a quella automatica di Versione)
     */
    public static void newVersInt(String code, int value, String descPref, String descVers) {
        newBase(true, code, value, descPref, descVers, TypePref.intero);
    }// end of method


    /**
     * Crea una nuova preferenza (solo se non esistente)
     *
     * @param logVersione flag per registrare una un record di Versione
     * @param code        key code
     * @param value       di default
     * @param descPref    dettagliata (obbligatoria per Pref)
     * @param type        di valore
     */
    private static void newBase(boolean logVersione, String code, Object value, String descPref, TypePref type) {
        newBase(logVersione, code, value, descPref, "", type);
    }// end of method

    /**
     * Crea una nuova preferenza (solo se non esistente)
     *
     * @param logVersione flag per registrare una un record di Versione
     * @param code        key code
     * @param value       di default
     * @param descPref    dettagliata (obbligatoria per Pref)
     * @param descVers    dettagliata (facoltativa e aggiuntiva a quella automatica di Versione)
     * @param type        di valore
     */
    private static void newBase(boolean logVersione, String code, Object value, String descPref, String descVers, TypePref type) {
        Pref pref;
        String commento;
        String strValue = "";

        pref = Pref.findByCode(code);
        if (pref == null) {
            pref = new Pref();
            pref.setOrdine(Pref.count() + 1);
            pref.setCode(code);
            pref.setDescrizione(descPref);
            pref.setType(type);
            if (type == TypePref.booleano) {
                try { // prova ad eseguire il codice
                    pref.setBool((Boolean) value);
                    strValue = value.toString();
                } catch (Exception unErrore) { // intercetta l'errore
                    Notification.show("La preferenza " + code + " non è di tipo booleano", Notification.Type.ERROR_MESSAGE);
                }// fine del blocco try-catch
            }// fine del blocco if
            if (type == TypePref.stringa) {
                try { // prova ad eseguire il codice
                    pref.setStringa((String) value);
                    strValue = (String) value;
                } catch (Exception unErrore) { // intercetta l'errore
                    Notification.show("La preferenza " + code + " non è di tipo stringa", Notification.Type.ERROR_MESSAGE);
                }// fine del blocco try-catch
            }// fine del blocco if
            if (type == TypePref.intero) {
                try { // prova ad eseguire il codice
                    pref.setIntero((Integer) value);
                    strValue = LibNum.format(value);
                } catch (Exception unErrore) { // intercetta l'errore
                    Notification.show("La preferenza " + code + " non è di tipo intero", Notification.Type.ERROR_MESSAGE);
                }// fine del blocco try-catch
            }// fine del blocco if
            pref.save();
        }// fine del blocco if

        if (logVersione) {
            commento = code + ", di default " + strValue + ".";
            if (!descVers.equals("")) {
                commento += " " + descVers;
            }// fine del blocco if
            LibVers.nuova("Preferenze", commento);
        }// fine del blocco if
    }// end of static method

}// end of static class
