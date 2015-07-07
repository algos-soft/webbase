package it.algos.web.security;

import it.algos.domain.utente.Utente;

/**
 * Interface per la modifica dell'utente loggato nella LoginLogic <br>
 */
public interface LogUtenteListener {
	public void setUtenteLogin(Utente utente);
}// end of interface
