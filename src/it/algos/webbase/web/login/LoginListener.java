package it.algos.webbase.web.login;

import it.algos.webbase.domain.utente.Utente;

/**
 * Listener invoked on a successful login.
 */
public interface LoginListener {
    public void onUserLogin(Utente user, boolean remember);
}

