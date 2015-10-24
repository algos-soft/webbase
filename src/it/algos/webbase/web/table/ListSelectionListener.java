package it.algos.webbase.web.table;

import javax.swing.event.ListSelectionEvent;
import java.util.EventListener;

/**
 * Listener invoked on every change in selection list
 * <p>
 * La classe ATable lancia il fire di questo evento ogni volta che cambia la selezione delle righe
 * La classe ATable deve prevedere i metodi
 * Nella classe ATable si registrano i listeners (istanze di classi che sono interessate all'evento)
 * Le classi che si registrano devono implementare questa interfaccia
 * I listeners verrano informati dell'evento tramite invocazione del metodo di questa interfaccia
 */
public interface ListSelectionListener extends EventListener {
    public void valueChanged(ListSelectionEvent e);
}// end of interface
