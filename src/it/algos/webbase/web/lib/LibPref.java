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
     * @param code        key code
     * @param descrizione dettagliata
     * @param value       di default
     */
    public static void newBool(String code, String descrizione, Boolean value) {
        newBase(false, code, descrizione, value, TypePref.booleano);
    }// end of method

    /**
     * Crea una nuova preferenza di tipo stringa
     * La crea solo se non esistente
     *
     * @param code        key code
     * @param descrizione dettagliata
     * @param value       di default
     */
    public static void newStr(String code, String descrizione, String value) {
        newBase(false, code, descrizione, value, TypePref.stringa);
    }// end of method

    /**
     * Crea una nuova preferenza di tipo intero
     * La crea solo se non esistente
     *
     * @param code        key code
     * @param descrizione dettagliata
     * @param value       di default
     */
    public static void newInt(String code, String descrizione, int value) {
        newBase(false, code, descrizione, value, TypePref.intero);
    }// end of method


    /**
     * Crea una nuova preferenza di tipo booleano
     * La crea solo se non esistente
     * Crea anche un record di log nella tavola Versione
     *
     * @param code        key code
     * @param descrizione dettagliata
     * @param value       di default
     */
    public static void newVersBool(String code, String descrizione, Boolean value) {
        newBase(true, code, descrizione, value, TypePref.booleano);
    }// end of method

    /**
     * Crea una nuova preferenza di tipo stringa
     * La crea solo se non esistente
     * Crea anche un record di log nella tavola Versione
     *
     * @param code        key code
     * @param descrizione dettagliata
     * @param value       di default
     */
    public static void newVersStr(String code, String descrizione, String value) {
        newBase(true, code, descrizione, value, TypePref.stringa);
    }// end of method

    /**
     * Crea una nuova preferenza di tipo intero
     * La crea solo se non esistente
     * Crea anche un record di log nella tavola Versione
     *
     * @param code        key code
     * @param descrizione dettagliata
     * @param value       di default
     */
    public static void newVersInt(String code, String descrizione, int value) {
        newBase(true, code, descrizione, value, TypePref.intero);
    }// end of method

    /**
     * Crea una nuova preferenza (solo se non esistente)
     *
     * @param logVersione flag per registrare una un record di Versione
     * @param code        key code
     * @param descrizione dettagliata
     * @param value       di default
     * @param type        di valore
     */
    private static void newBase(boolean logVersione, String code, String descrizione, Object value, TypePref type) {
        Pref pref;
        String commento = code + ", di default " + value;

        pref = Pref.findByCode(code);
        if (pref == null) {
            pref = new Pref();
            pref.setOrdine(Pref.count() + 1);
            pref.setCode(code);
            pref.setDescrizione(descrizione);
            pref.setType(type);
            if (type == TypePref.booleano) {
                try { // prova ad eseguire il codice
                    pref.setBool((Boolean) value);
                } catch (Exception unErrore) { // intercetta l'errore
                    Notification.show("La preferenza " + code + " non è di tipo booleano", Notification.Type.ERROR_MESSAGE);
                }// fine del blocco try-catch
            }// fine del blocco if
            if (type == TypePref.stringa) {
                try { // prova ad eseguire il codice
                    pref.setStringa((String) value);
                } catch (Exception unErrore) { // intercetta l'errore
                    Notification.show("La preferenza " + code + " non è di tipo stringa", Notification.Type.ERROR_MESSAGE);
                }// fine del blocco try-catch
            }// fine del blocco if
            if (type == TypePref.intero) {
                try { // prova ad eseguire il codice
                    pref.setIntero((Integer) value);
                } catch (Exception unErrore) { // intercetta l'errore
                    Notification.show("La preferenza " + code + " non è di tipo intero", Notification.Type.ERROR_MESSAGE);
                }// fine del blocco try-catch
            }// fine del blocco if
            pref.save();
        }// fine del blocco if

        if (logVersione) {
            LibVers.nuova("Preferenze", commento);
        }// fine del blocco if
    }// end of static method

}// end of static class
