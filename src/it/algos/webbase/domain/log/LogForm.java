package it.algos.webbase.domain.log;

import com.vaadin.data.Item;
import it.algos.webbase.web.field.ArrayComboField;
import it.algos.webbase.web.form.AForm;
import it.algos.webbase.web.module.ModulePop;

/**
 * Created by gac on 16 set 2015.
 * .
 */
public class LogForm extends AForm {

    /**
     * Constructor
     *
     * @param module the referenced module
     */
    public LogForm(ModulePop module) {
        this(module, (Item) null);
    }// end of constructor

    /**
     * Constructor
     *
     * @param module the reference module
     * @param item   the item with the properties
     */
    public LogForm(ModulePop module, Item item) {
        super(module, item);
    }// end of constructor


    /**
     * Populate the map to bind item properties to fields.
     * <p>
     * Crea e aggiunge i campi.<br>
     * Implementazione di default nella superclasse.<br>
     * I campi vengono recuperati dal Modello.<br>
     * I campi vengono creti del tipo grafico previsto nella Entity.<br>
     * Se si vuole aggiungere un campo (solo nel form e non nel Modello),<br>
     * usare il metodo sovrascritto nella sottoclasse
     * richiamando prima (o dopo) il metodo della superclasse.
     */
    @Override
    @SuppressWarnings("rawtypes")
    protected void createFields() {
        ArrayComboField beta = new ArrayComboField(Livello.values(), "Type");
        beta.setNullSelectionAllowed(false);
        addField(Log_.livello, beta);

        super.createFields();
    }// end of method

}// end of class
