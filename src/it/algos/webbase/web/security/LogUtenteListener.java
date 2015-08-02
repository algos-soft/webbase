package it.algos.webbase.web.security;

import it.algos.webbase.domain.utente.Utente;

/**
 * Interface per la modifica dell'utente loggato nella LoginLogic <br>
 */
public interface LogUtenteListener {
	public void setUtenteLogin(Utente utente);
}// end of interface
