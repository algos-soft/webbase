package it.algos.webbase.domain.utente;

import com.vaadin.data.Item;
import com.vaadin.ui.*;
import it.algos.webbase.web.dialog.ConfirmDialog;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.field.CheckBoxField;
import it.algos.webbase.web.field.EmailField;
import it.algos.webbase.web.field.TextField;
import it.algos.webbase.web.form.AForm;
import it.algos.webbase.web.form.AFormLayout;
import it.algos.webbase.web.module.ModulePop;

/**
 * Created by alex on 29-09-2015.
 */
public class UtenteForm extends AForm {



    public UtenteForm(ModulePop modulo, Item item) {
        super(modulo, item);
        doInit();
    }// end of constructor

    private void doInit(){
        setWidth("500px");
    }

    @Override
    protected void createFields() {
        @SuppressWarnings("rawtypes")
        Field field;

        field = new TextField("Nome");
        addField(Utente_.nickname, field);

        field = new PasswordField("Password");
        addField(Utente_.password, field);

        field = new CheckBoxField("Abilitato");
        addField(Utente_.enabled, field);

    }

}
