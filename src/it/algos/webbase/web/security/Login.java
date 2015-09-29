package it.algos.webbase.web.security;

import it.algos.webbase.domain.ruolo.Ruolo;
import it.algos.webbase.domain.ruolo.TipoRuolo;
import it.algos.webbase.domain.utente.Utente;
import it.algos.webbase.domain.utenteruolo.UtenteRuolo;
import it.algos.webbase.web.lib.Cost;
import it.algos.webbase.web.lib.LibSession;

import java.util.ArrayList;

/**
 * Created by gac on 01 set 2015.
 * Wrapper di informazioni
 */
public class Login {

    private Utente utente;

    /**
     * Controllo <br>
     *
     * @deprecated
     */
    public static boolean isCollegatoOld() {
        boolean collegato = false;
        String cookieLoginNick;
        String cookieLoginPass;
        String cookieLoginRole;
        LoginLogic alfa= new LoginLogic();

        cookieLoginNick = LibSession.getAttributeStr(Cost.COOKIE_LOGIN_NICK);
        cookieLoginPass = LibSession.getAttributeStr(Cost.COOKIE_LOGIN_PASS);
        cookieLoginRole = LibSession.getAttributeStr(Cost.COOKIE_LOGIN_ROLE);

        if (!cookieLoginNick.equals("")) {
            collegato = true;
        }// fine del blocco if

//        if (utenteCollegato != null) {
//            collegato = true;
//        }// end of if/else cycle
//
        return collegato;
    }// end of method

    /**
     * Controllo <br>
     */
    public boolean isCollegato() {
        boolean status = false;

        if (getUtente() != null) {
            status = true;
        }// fine del blocco if

        return status;
    }// end of method

    /**
     * Controllo <br>
     * Vero se esiste un utente collegato ed ha il ruolo di developer <br>
     */
    public boolean isDeveloper() {
        return isRuoloBase(TipoRuolo.developer);
    }// end of method

    /**
     * Controllo <br>
     * Vero se esiste un utente collegato ed ha il ruolo di admin <br>
     */
    public boolean isAdmin() {
        return isRuoloBase(TipoRuolo.admin);
    }// end of method

    /**
     * Controllo <br>
     * Vero se esiste un utente collegato ed ha il ruolo di user <br>
     */
    public boolean isUser() {
        return isRuoloBase(TipoRuolo.user);
    }// end of method

    /**
     * Controllo guest
     */
    public boolean isGuest() {
        return isRuoloBase(TipoRuolo.guest);
    }// end of method


    /**
     * Controllo <br>
     * Vero se esiste un utente collegato ed ha il ruolo di admin o superiore<br>
     */
    public boolean isAdminOrMore() {
        return (isDeveloper() || isAdmin());
    }// end of method

    /**
     * Controllo <br>
     * Vero se esiste un utente collegato ed ha il ruolo di utente o superiore<br>
     */
    public boolean isUserOrMore() {
        return (isAdmin() || isUser());
    }// end of method


    /**
     * Controllo base del ruolo
     */
    public boolean isRuoloBase(TipoRuolo ruoloPrevisto) {
        boolean status = false;
        UtenteRuolo userRole = null;
        Ruolo ruolo = Ruolo.read(ruoloPrevisto.toString());

        if (utente != null) {
            userRole = UtenteRuolo.read(utente, ruolo);
            if (userRole != null) {
                status = true;
            }// end of if cycle
        }// end of if cycle

        return status;
    }// end of method


    /**
     * Controllo base del ruolo
     *
     * @deprecated
     */
    private boolean isRuoloBaseOld(TipoRuolo ruoloPrevisto) {
        boolean status = false;
        Utente utente = this.getUtente();
        ArrayList<UtenteRuolo> lista = UtenteRuolo.findUtente(utente);
        Ruolo ruolo;
        String ruoloTxt;

        if (lista != null && lista.size() > 0) {
            for (UtenteRuolo utenteRuolo : lista) {
                ruolo = utenteRuolo.getRuolo();
                ruoloTxt = ruolo.getNome();
                if (ruoloTxt.equals(ruoloPrevisto.toString())) {
                    status = true;
                }// fine del blocco if
            } // fine del ciclo for-each
        }// fine del blocco if

        return status;
    }// end of method

    public Utente getUtente() {
        return utente;
    }// end of getter method


    public void setUtente(Utente utente) {
        this.utente = utente;
    }//end of setter method

    public String getNickName() {
        String nick = "";
        Utente utente = this.getUtente();

        if (utente != null) {
            nick = utente.getNickname();
        }// fine del blocco if

        return nick;
    }//end of setter method


}// end of class
