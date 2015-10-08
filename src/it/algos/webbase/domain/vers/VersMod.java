package it.algos.webbase.domain.vers;
/**
 * Created by Gac on 17 lug 2015.
 * Using specific Templates (Entity, Domain, Modulo)
 */


import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.navigator.NavPlaceholder;

import javax.persistence.metamodel.Attribute;

@SuppressWarnings("serial")
public class VersMod extends ModulePop {


    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Vers";


    public VersMod() {
        super(Versione.class, MENU_ADDRESS);
        this.setIcon(FontAwesome.BARS);
    }// end of constructor


    /**
     * Titolo (caption) dei dialogo nuovo record. <br>
     * Come default usa il titolo standard <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     */
    @Override
    protected String getCaptionNew() {
        return "Nuova versione";
    }// end of method

    /**
     * Titolo (caption) dei dialogo di modifica. <br>
     * Come default usa il titolo standard <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     */
    @Override
    protected String getCaptionEdit() {
        return "Modifica versione";
    }// end of method

    /**
     * Titolo (caption) dei dialogo di ricerca. <br>
     * Come default usa il titolo standard <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     */
    @Override
    protected String getCaptionSearch() {
        return "Ricerca versione";
    }// end of method


    /**
     * Crea i campi visibili nella lista (table)
     * <p>
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * Serve anche per l'ordine con cui vengono presentati i campi nella lista <br>
     */
    @Override
    protected Attribute<?, ?>[] creaFieldsList() {
        return new Attribute[]{Versione_.ordine, Versione_.titolo, Versione_.descrizione, Versione_.timestamp};
    }// end of method

    /**
     * Crea i campi visibili nella scheda (form)
     * <p>
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * Serve anche per l'ordine con cui vengono presentati i campi nella scheda <br>
     */
    @Override
    protected Attribute<?, ?>[] creaFieldsForm() {
        return new Attribute[]{Versione_.ordine, Versione_.titolo, Versione_.descrizione};
    }// end of method


    /**
     * Post create / pre edit item.
     * Legge il massimo numero esistente nel DB per la property Numero
     * Ordina discendente per usare il primo record
     * Nel dialogo nuovo record, suggerisce il valore incrementato di 1
     * Riordina ascendente (default)
     */
    protected void postCreate(Item item) {
        String sortField;
        Property property;
        JPAContainer cont;
        Object idKey;
        int progressivo;

        if (item == null) {
            return;
        }// fine del blocco if

        cont = getTable().getJPAContainer();
        sortField = Versione_.ordine.getName();
        property = item.getItemProperty(sortField);

        if (property != null) {
            cont.sort(new String[]{sortField}, new boolean[]{false});
            idKey = cont.getIdByIndex(0);
            if (idKey != null && idKey instanceof Long) {
                Versione versione = Versione.find((long) idKey);
                if (versione != null) {
                    progressivo = versione.getOrdine();
                    progressivo = progressivo + 1;
                    property.setValue(progressivo);
                }// fine del blocco if
            }// fine del blocco if
            cont.sort(new String[]{sortField}, new boolean[]{true});
        }// fine del blocco if

    }// end of method

    /**
     * Create the MenuBar Item for this module
     * <p>
     * Invocato dal metodo AlgosUI.creaMenu()
     * PUO essere sovrascritto dalla sottoclasse
     *
     * @param menuBar     a cui agganciare il menuitem
     * @param placeholder in cui visualizzare il modulo
     * @return menuItem appena creato
     */
    @Override
    public MenuBar.MenuItem createMenuItem(MenuBar menuBar, NavPlaceholder placeholder) {
        return super.createMenuItem(menuBar, placeholder, FontAwesome.BARS);
    }// end of method


}// end of class
