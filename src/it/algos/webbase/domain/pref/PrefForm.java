package it.algos.webbase.domain.pref;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.VerticalLayout;
import it.algos.webbase.web.form.AForm;
import it.algos.webbase.web.form.AFormLayout;
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


    /**
     * Create the UI component.
     * <p>
     * Retrieve the fields from the map and place them in the UI. Implementazione di default nella superclasse. I campi
     * vengono allineati verticalmente. Se si vuole aggiungere un campo, usare il metodo sovrascritto nella sottoclasse
     * richiamando prima il metodo della superclasse. Se si vuole un layout completamente differente, implementare il
     * metodo sovrascritto da solo.
     */
    protected Component createComponent() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSpacing(false);
        verticalLayout.setMargin(false);

        FormLayout formLayoutTop = new AFormLayout();
        formLayoutTop.setMargin(false);

        if (bindMap != null) {
            for (Object key : bindMap.keySet()) {
                formLayoutTop.addComponent(this.getField(key));
            }// end of for cycle
        }// end of if cycle
        verticalLayout.addComponent(formLayoutTop);

        FormLayout formLayoutBottom = new AFormLayout();
        body = new AFormLayout();
        body.setSpacing(false);
        body.setMargin(false);
        formLayoutBottom.addComponent(body);
        verticalLayout.addComponent(formLayoutBottom);

        return incapsulaPerMargine(verticalLayout);
    }// end of method


    /**
     * Intercetta i cambiamenti nel combobox.
     * <p>
     * Sovrascritto nella classe specifica
     */
    @Override
    protected void arrayComboBoxValueChanged(Property.ValueChangeEvent event) {
        String value = "";
        Property property = event.getProperty();
        Object objValue = property.getValue();

        if (objValue instanceof TypePref) {
            value = objValue.toString();
            if (body != null) {
                if (body.getComponentCount() > 0) {
                    body.removeAllComponents();
                }// fine del blocco if
            }// fine del blocco if
        } else {
            return;
        }// fine del blocco if-else

        if (value.equals(TypePref.booleano.toString())) {
            addBodyField(Pref_.bool);
        }// fine del blocco if

        if (value.equals(TypePref.stringa.toString())) {
            addBodyField(Pref_.stringa);
        }// fine del blocco if

        if (value.equals(TypePref.intero.toString())) {
            addBodyField(Pref_.intero);
        }// fine del blocco if

    }// end of method

}// end of class

