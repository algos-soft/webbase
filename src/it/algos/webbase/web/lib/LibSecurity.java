package it.algos.webbase.web.lib;

import it.algos.webbase.domain.ruolo.Ruolo;
import it.algos.webbase.domain.ruolo.TipoRuolo;
import it.algos.webbase.domain.utente.Utente;
import it.algos.webbase.domain.utenteruolo.UtenteRuolo;

import java.util.ArrayList;

/**
 * Created by gac on 03 set 2015.
 * .
 */
public abstract class LibSecurity {

    /**
     * Controllo esistenza dei ruoli standard <br>
     * Se mancano, li crea <br>
     */
    public static void checkRuoli() {
        Ruolo ruolo = null;
        ArrayList<String> listaNomiRuoli = TipoRuolo.getAllNames();

        for (String nome : listaNomiRuoli) {
            ruolo = Ruolo.read(nome);

            if (ruolo == null) {
                ruolo = new Ruolo(nome);
                ruolo.save();
            }// end of if cycle
        }// fine del ciclo for
    }// end of method


    /**
     * Crea un programmatore/developer
     */
    public static void creaDeveloper(String nickName, String password) {
        creaUtente(nickName, password, TipoRuolo.developer.toString());
    }// end of method


    /**
     * Crea un programmatore/developer
     */
    public static void creaAdmin(String nickName, String password) {
        creaUtente(nickName, password, TipoRuolo.admin.toString());
    }// end of method


    /**
     * Crea un programmatore/developer
     */
    public static void creaUser(String nickName, String password) {
        creaUtente(nickName, password, TipoRuolo.user.toString());
    }// end of method


    /**
     * Crea un programmatore/developer
     */
    public static void creaGuest(String nickName, String password) {
        creaUtente(nickName, password, TipoRuolo.guest.toString());
    }// end of method


    /**
     * Crea un utente con ruolo connesso <br>
     */
    public static void creaUtente(String nickName, String password, String nomeRuolo) {
        Ruolo ruolo = Ruolo.read(nomeRuolo);

        if (ruolo != null) {
            creaUtente(nickName, password, ruolo);
        }// end of if cycle

    }// end of method


    /**
     * Crea un utente con ruolo connesso <br>
     */
    public static void creaUtente(String nickName, String password, Ruolo ruolo) {
        Utente newUtente = Utente.read(nickName);
        UtenteRuolo newUserRole;

        if (newUtente == null) {
            newUtente = new Utente(nickName, password);
            newUtente.save();
            newUserRole = UtenteRuolo.read(newUtente, ruolo);
            if (newUserRole == null) {
                newUserRole = new UtenteRuolo(newUtente, ruolo);
                newUserRole.save();
            }// end of if cycle
        }// end of if cycle

    }// end of method

}// end of static abstract class
