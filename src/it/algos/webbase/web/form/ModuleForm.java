package it.algos.webbase.web.form;

import com.vaadin.data.Buffered;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Field;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.BaseEntity_;
import it.algos.webbase.web.module.ModulePop;
import org.vaadin.addons.lazyquerycontainer.CompositeItem;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;
import java.util.Collection;

/**
 * A Form for creating and editing records in a Module.
 * Created by alex on 06/01/16.
 */
public class ModuleForm extends AForm {

    private ModulePop module;
    private boolean newRecord; // true if the form is editing a new record.

    public ModuleForm(Item item, ModulePop module) {
        super(item);
        this.module = module;
        init();
    }

    @Override
    protected void init() {

        // if the item is not present, then it is a new record.
        // create a temporary BeanItem of the proper class
        if (getItem() == null) {
            Object bean = BaseEntity.createBean(getModule().getEntityClass());
            setItem(new BeanItem(bean));
            getBinder().setItemDataSource(getItem());   // link the binder to the new item before adding the fields
            this.newRecord = true;
        }

        super.init();
    }

    // create a field for each property declared in the module
    public void createFields(){
        Attribute[] attributes = getModule().getFieldsForm();
        for (Attribute attr : attributes) {
            Field field = createField(attr);
            if (field != null) {
                addField(attr, field);
            }
        }
    }


    @Override
    public void postCommit() {

//        // only for new records, let the container create a new item
//        // and copy the data from the temporary BeanItem to the container's item
//        if (isNewRecord()) {
//            Object itemId = getContainer().addItem();
//            Item newItem = getContainer().getItem(itemId);
//            Collection ids = newItem.getItemPropertyIds();
//            for (Object id : ids) {
//                if (!id.equals(BaseEntity_.id.getName())) {   // don't copy the id property!
//                    Property pTemp = getItem().getItemProperty(id);
//                    Property pNew = newItem.getItemProperty(id);
//                    pNew.setValue(pTemp.getValue());
//                }
//            }
//        }
//
//        // make the container persist the changes
//        Container cont = getContainer();
//        if (cont instanceof Buffered) {
//            Buffered buff = (Buffered) cont;
//            buff.commit();
//        }

        // retrieve the bean
        BaseEntity bean;
        if (isNewRecord()) {
            BeanItem item = (BeanItem)getItem();
            bean = (BaseEntity)item.getBean();
        }else{
            CompositeItem compItem = (CompositeItem) getItem();
            BeanItem beanItem = (BeanItem) compItem.getItem("bean");
            bean = (BaseEntity)beanItem.getBean();
        }

        // merge the bean (creates or updates the record(s) in the db)
        bean.save(getModule().getEntityManager());
//        EntityManager em = getModule().getEntityManager();
//        em.getTransaction().begin();
//        em.merge(bean);
//        em.getTransaction().commit();

    }

    /**
     * Returns the container
     * (uses the same container of the table)
     */
    public Container getContainer() {
        return getModule().getTable().getContainerDataSource();
    }

    public ModulePop getModule() {
        return module;
    }

    public boolean isNewRecord() {
        return newRecord;
    }

}
