package it.algos.webbase.web.security;

import it.algos.webbase.web.lib.Cost;
import it.algos.webbase.web.lib.LibSession;

/**
 * Created by gac on 01 set 2015.
 * .
 */
public class Login {

    /**
     * Controllo <br>
     */
    public static boolean isCollegato() {
        boolean collegato = false;
        String cookieLoginNick;
        String cookieLoginPass;
        String cookieLoginRole;

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

}// end of class
