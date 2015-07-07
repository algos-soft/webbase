package it.algos.web;

import it.algos.domain.ruolo.Ruolo;
import it.algos.domain.ruolo.TipoRuolo;
import it.algos.domain.utente.Utente;
import it.algos.domain.utenteruolo.UtenteRuolo;

import java.util.ArrayList;

/**
 * Security BootStrap.<br>
 */
public abstract class SecurityBootStrap {

	/**
	 * Controllo esistenza dei ruoli standard <br>
	 * Se mancano, li crea <br>
	 */
	public static void bootStrapRuoli() {
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
	 * Crea un utente con ruolo connesso <br>
	 */
	public static void creaUtente(String nickName, String password, String nomeRuolo) {
		Utente newUtente = Utente.read(nickName);
		UtenteRuolo userRole;
		Ruolo ruolo = Ruolo.read(nomeRuolo);

		if (newUtente == null) {
			newUtente = new Utente(nickName, password);
			newUtente.save();
		}// end of if cycle

		if (newUtente != null) {
			userRole = UtenteRuolo.read(newUtente, ruolo);
			if (userRole == null) {
				userRole = new UtenteRuolo(newUtente, ruolo);
				userRole.save();
			}// end of if cycle
		}// end of if cycle

	}// end of method

}// end of abstract class

