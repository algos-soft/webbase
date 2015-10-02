package it.algos.webbase.web.login;

/**
 * Listener invoked on logout.
 * <p>
 * La classe LoginBar gestisce il bottone/menu per il logout
 * Lancia il fire di questo evento, se si clicca il bottone/menu Esci/Logout.
 * Nella classe LoginBar si registrano i listeners (istanze di classi che sono interessate all'evento)
 * Le classi che si registrano devono implementare questa interfaccia
 * I listeners verrano informati dell'evento tramite invocazione del metodo di questa interfaccia
 */
public interface LogoutListener {
    public void onUserLogout();
}// end of interface
