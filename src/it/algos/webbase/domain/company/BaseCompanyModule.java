package it.algos.webbase.domain.company;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import it.algos.webbase.web.form.ModuleForm;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.query.AQuery;

import javax.persistence.metamodel.Attribute;

@SuppressWarnings("serial")
public class BaseCompanyModule extends ModulePop implements View {

    public BaseCompanyModule() {
        this(BaseCompany.class);
    }// end of constructor

    public BaseCompanyModule(Class entityClass) {
        super(entityClass);
    }// end of constructor

    // come default spazzola tutti i campi della Entity
    // non garantisce l'ordine con cui vengono presentati i campi
    // può essere sovrascritto nelle sottoclassi specifiche (garantendo l'ordine)
    // può mostrare anche il campo ID, oppure no
    // se si vuole differenziare tra Table, Form e Search, sovrascrivere
    // creaFieldsList, creaFieldsForm e creaFieldsSearch
    protected Attribute<?, ?>[] creaFieldsAll() {
        return new Attribute[]{BaseCompany_.name, BaseCompany_.contractType, BaseCompany_.contractEnd};
    }// end of method


    @Override
    protected Attribute<?, ?>[] creaFieldsList() {
        return new Attribute[]{BaseCompany_.companyCode, BaseCompany_.name, BaseCompany_.contractType, BaseCompany_.contractEnd};
    }

    @Override
    public ModuleForm createForm(Item item) {
        return (new BaseCompanyForm(this, item));
    }// end of method


    public void delete(Object id) {

        // cancella prima tutti i dati
        BaseCompany company = (BaseCompany) AQuery.queryById(BaseCompany.class, id);
        company.deleteAllData();

        // poi cancella la company
        super.delete(id);

    }


}// end of class
