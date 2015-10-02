package it.algos.webbase.web.login;

/**
 * Listener invoked for open a form
 * <p>
 * La classe LoginBar gestisce il bottone/menu per il login
 * Lancia il fire di questo evento, se si clicca il bottone/menu Login.
 * Nella classe LoginBar si registrano i listeners (istanze di classi che sono interessate all'evento)
 * Le classi che si registrano devono implementare questa interfaccia
 * I listeners verrano informati dell'evento tramite invocazione del metodo di questa interfaccia
 */
public interface LogformListener {
    public void onLogFormRequest();
}// end of interface
