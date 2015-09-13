package it.algos.webbase.domain.pref;

import com.vaadin.data.Item;
import it.algos.webbase.web.form.AForm;
import it.algos.webbase.web.module.ModulePop;

/**
 * Created by gac on 13 set 2015.
 * .
 */
public class PrefForm extends AForm {

    public PrefForm(ModulePop modulo) {
        super(modulo);
    }// end of constructor

    public PrefForm(ModulePop modulo, Item item) {
        super(modulo, item);
    }// end of constructor

}// end of class

