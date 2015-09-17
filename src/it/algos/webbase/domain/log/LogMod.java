package it.algos.webbase.domain.log;

import com.vaadin.data.Item;
import it.algos.webbase.web.form.AForm;
import it.algos.webbase.web.module.ModulePop;

import javax.persistence.metamodel.Attribute;

@SuppressWarnings("serial")
public class LogMod extends ModulePop {

    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Logo";

    public LogMod() {
        super(Log.class, MENU_ADDRESS);
    }// end of constructor

    /**
     * Titolo (caption) dei dialogo nuovo record. <br>
     * Come default usa il titolo standard <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     */
    @Override
    protected String getCaptionNew() {
        return "Nuovo log";
    }// end of method

    /**
     * Titolo (caption) dei dialogo di modifica. <br>
     * Come default usa il titolo standard <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     */
    @Override
    protected String getCaptionEdit() {
        return "Modifica log";
    }// end of method

    /**
     * Titolo (caption) dei dialogo di ricerca. <br>
     * Come default usa il titolo standard <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     */
    @Override
    protected String getCaptionSearch() {
        return "Ricerca log";
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
        return new Attribute[]{Log_.livello, Log_.code, Log_.descrizione, Log_.timestamp};
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
        return new Attribute[]{Log_.code, Log_.descrizione};
    }// end of method

    /**
     * Returns the form used to edit an item. <br>
     * The concrete subclass must override for a specific Form.
     *
     * @return the Form
     */
    @Override
    public AForm createForm(Item item) {
        return (new LogForm(this, item));
    }// end of method

}// end of class
