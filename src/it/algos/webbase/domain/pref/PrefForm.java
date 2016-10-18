package it.algos.webbase.domain.pref;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.VerticalLayout;
import it.algos.webbase.web.AlgosApp;
import it.algos.webbase.web.field.ArrayComboField;
import it.algos.webbase.web.form.AForm;
import it.algos.webbase.web.form.AFormLayout;
import it.algos.webbase.web.form.ModuleForm;
import it.algos.webbase.web.lib.LibField;
import it.algos.webbase.web.module.ModulePop;

import javax.persistence.metamodel.Attribute;

/**
 * Created by gac on 13 set 2015.
 * .
 */
public class PrefForm extends ModuleForm {

    protected FormLayout body; //usato per i fields aggiuntivi che vengono visualizzati a comando sotto gli altri

//    public PrefForm(ModulePop modulo, Container cont) {
//        super(modulo, null, cont);
//    }// end of constructor

    public PrefForm(Item item, ModulePop modulo) {
        super(item, modulo);
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
        verticalLayout.setMargin(true);

        FormLayout formLayoutTop = new AFormLayout();
        formLayoutTop.setMargin(false);

//        if (bindMap != null) {
//            for (Object key : bindMap.keySet()) {
//                formLayoutTop.addComponent(this.getField(key));
//            }// end of for cycle
//        }// end of if cycle

        for(Field field : getFields()){
            formLayoutTop.addComponent(field);
        }
        verticalLayout.addComponent(formLayoutTop);

        FormLayout formLayoutBottom = new AFormLayout();
        body = new AFormLayout();
        body.setSpacing(false);
        body.setMargin(false);
        formLayoutBottom.addComponent(body);
        verticalLayout.addComponent(formLayoutBottom);

        return verticalLayout;
    }// end of method


    protected Field createField(Attribute attr) {
        Field field = super.createField(attr);

        // se è un ArrayComboField aggiunge un listener
        if(field instanceof ArrayComboField){
            ArrayComboField acf = (ArrayComboField)field;
            acf.setNullSelectionAllowed(false);
//            if (AlgosApp.COMBO_BOX_NULL_SELECTION_ALLOWED) {
//                ((ArrayComboField) field).setNullSelectionAllowed(true);
//            } else {
//                ((ArrayComboField) field).setNullSelectionAllowed(false);
//            }// fine del blocco if-else
            field.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    arrayComboBoxValueChanged(event);
                }// end of method
            });// end of anonymous class
        }
        return field;
    }

    /**
     * Intercetta i cambiamenti nel combobox.
     * <p>
     * Sovrascritto nella classe specifica
     */
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


    /**
     * Aggiunge al volo un fields
     * <p>
     * Il fields viene aggiunto in un body di tipo FormLayout posizionato SOTTO gli altri campi
     * Viene usato un FormLayout per avere la caption a sinistra
     * Il body viene viene (eventualmente) costruito nella sottoclasse (sovrascrivendo il metodo createComponent), solo se serve.
     * La sottoclasse decide se inserire più di un field nel body, oppure di svuotarlo ogni volta che cambia il field
     * <p>
     * Metodo invocato dalla sottoclasse
     */
    protected void addBodyField(Attribute attr) {
        String fieldName = attr.getName();
//        Field field = bindMap.get(fieldName);
        Field field = getField(fieldName);

        if (field == null) {
            field = createField(attr);
            if (field != null) {
                addField(attr, field);
            }// end of if cycle
        }// fine del blocco if

        if (body != null) {
            body.addComponent(field);
        }// fine del blocco if
    }// end of method


}// end of class

