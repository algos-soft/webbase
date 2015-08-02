package it.algos.webbase.web.field;

import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.form.AForm;
import it.algos.webbase.web.form.AForm.FormListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.metamodel.Attribute;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * Handler for new creating new items from a RelatedComboField. <br>
 * When a new string is typed in the combo, the addNewItem method is called and the string is received. <br>
 * Then, a form is displayed to fill the data for the new item. <br>
 * When the for is confirmed, the new item is saved and the combo is updated.
 */
@SuppressWarnings("serial")
public class ComboNewItemHandler implements NewItemHandler {

	private ArrayList<RecordEditedListener> listeners = new ArrayList<RecordEditedListener>();

	private final static Logger logger = Logger.getLogger(ComboNewItemHandler.class.getName());

	private RelatedComboField field;
	private Class<? extends AForm> formClass;
	@SuppressWarnings("rawtypes")
	private Attribute attribute;

	/**
	 * Creates a new NewItemHandler
	 * 
	 * @param field
	 *            the referenced field
	 * @param formClass
	 *            - the class for the form which will be used for editing the new item (must subclass AForm)
	 * @param attribute
	 *            - the attribute to fill with the text coming from the combo
	 */
	@SuppressWarnings({ "rawtypes" })
	public ComboNewItemHandler(RelatedComboField field, Class<? extends AForm> formClass, Attribute attribute) {
		super();
		this.field = field;
		this.formClass = formClass;
		this.attribute = attribute;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addNewItem(String newItemCaption) {

		// create the new item
		Object bean = BaseEntity.createBean(this.field.getEntityClass());
		BeanItem item = new BeanItem(bean);

		// write the caption to the appropriate field
		if (attribute != null) {
			Property prop = item.getItemProperty(attribute.getName());
			if (prop != null) {
				prop.setValue(newItemCaption);
			}
		}
		
		// edit the item in the form
		editItem(item, true);


	}
	
	
	private void editItem(BeanItem item, final boolean newRecord){
		
		// check that the form class exists
		if (formClass == null) {
			logger.log(Level.WARNING, "The class for the form is null.");
			return;
		}

		// retrieves the constructor with Item
		Constructor constr;
		try {
			constr = formClass.getConstructor(Item.class);
		} catch (NoSuchMethodException | SecurityException e) {
			logger.log(Level.WARNING, "Can't find Constructor with Item.", e);
			return;
		}


		// instantiate the form class with the item
		Object[] args = new Object[] { item };
		final AForm form;
		try {
			form = (AForm) constr.newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			logger.log(Level.WARNING, "Can't instantiate the Form.", e);
			return;
		}

		// create the window
		final Window window = new Window();

		// adds listeners to the form
		form.addFormListener(new FormListener() {

			@Override
			public void commit_() {
				Item item = form.getItem();
				BaseEntity bean = itemToBean(item);
				bean.save();
				window.close();
				field.getJPAContainer().refresh();
				long id = bean.getId();
				field.setValue(id);
				fire(new BeanItem(bean), newRecord);
			}

			@Override
			public void cancel_() {
				window.close();
			}

		});

		// show the UI
		window.setCaption(""+field.getEntityClass().getSimpleName());
		window.setResizable(false);
		window.setModal(true);
		form.setWidth("100%");
		form.setHeightUndefined();
		window.setContent(form);
		window.center();
		UI ui = UI.getCurrent();
		ui.addWindow(window);
	}
	
	public void edit(BeanItem bi){
		editItem(bi, false);
	}

	@SuppressWarnings("rawtypes")
	private BaseEntity itemToBean(Item item) {
		BaseEntity entity = null;
		if (item instanceof BeanItem) {
			Object bean = ((BeanItem) item).getBean();
			if (bean instanceof BaseEntity) {
				entity = (BaseEntity) bean;
			}

		}
		return entity;
	}

	public void addRecordEditedListener(RecordEditedListener listener) {
		this.listeners.add(listener);
	}// end of method
	
	protected void fire(BeanItem bi, boolean newRecord) {
		for (RecordEditedListener l : listeners) {
			l.save_(bi, newRecord);
		}
	}// end of method

	
	public interface RecordEditedListener {
		public void save_(BeanItem bi, boolean newRecord);
	}

}
