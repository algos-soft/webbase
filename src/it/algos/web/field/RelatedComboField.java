package it.algos.web.field;

import it.algos.web.entity.EM;
import it.algos.web.form.AForm;
import it.algos.web.lib.Lib;
import it.algos.web.query.AQuery;

import java.util.ArrayList;

import javax.persistence.metamodel.Attribute;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectConverter;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class RelatedComboField extends ComboBox implements FieldInterface<Object> {

	private ArrayList<RecordEditedListener> listeners = new ArrayList<RecordEditedListener>();


	@SuppressWarnings("rawtypes")
	private Class entityClass;

	private Component editComponent;

	@SuppressWarnings("rawtypes")
	public RelatedComboField(Class entityClass, String caption) {
		super(caption);
		this.entityClass = entityClass;
		setImmediate(true);
		init();
	}

	@SuppressWarnings("rawtypes")
	public RelatedComboField(Class entityClass) {
		this(entityClass, null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init() {
		initField();
		Container cont = createContainer();
		setContainerDataSource(cont);
		setItemCaptionMode(ItemCaptionMode.ITEM);
		setConverter(new SingleSelectConverter(this));
		setWidth("260px");
	}

	public void initField() {
		FieldUtil.initField(this);
	}

	/**
	 * Creates the container used as data source for the combo.
	 * <p>
	 * @return the container
	 */
	@SuppressWarnings("unchecked")
	protected Container createContainer(){
		return JPAContainerFactory.makeReadOnly(getEntityClass(), EM.createEntityManager());
	}


	/**
	 * Assigns a handler for new items. Switches on/off the feature of the combo if the handler is not null or null
	 */
	@Override
	public void setNewItemHandler(NewItemHandler newItemHandler) {
		setNewItemsAllowed(newItemHandler != null);
		super.setNewItemHandler(newItemHandler);
	}

	/**
	 * Creates a handler to handle new items and assigns it to this combo.
	 * 
	 * @param formClass
	 *            - the class for the form which will be used for editing the new item (must subclass AForm)
	 * @param attribute
	 *            - the attribute to fill with the text coming from the combo
	 */
	public void setNewItemHandler(Class<? extends AForm> formClass, Attribute attribute) {
		ComboNewItemHandler handler = new ComboNewItemHandler(this, formClass, attribute);
		handler.addRecordEditedListener(new ComboNewItemHandler.RecordEditedListener() {
			
			@Override
			public void save_(BeanItem bi, boolean newRecord) {
				fire(bi, newRecord);
			}
		});
		setNewItemHandler(handler);
	}
	
	

	/**
	 * Return a bean corresponding to the currently selected item
	 * <p>
	 * 
	 * @return the bean
	 */
	public Object getSelectedBean() {
		Object bean = null;
		long id = Lib.getLong(getValue());
		if (id > 0) {
			bean = AQuery.queryById(getEntityClass(), id);
		}
		return bean;
	}

	/**
	 * Sorts the container
	 * 
	 * @param sortList
	 *            array of attribute ids on which to sort
	 */
	@SuppressWarnings("rawtypes")
	public void sort(Object... sortList) {
		ArrayList<Object> attrIds = new ArrayList<Object>();
		for (Object obj : sortList) {
			Object key = obj;
			if (key instanceof Attribute) {
				key = ((Attribute) obj).getName();
			}
			attrIds.add(key);
		}
		Object[] attrArray = attrIds.toArray(new Object[0]);
		boolean[] attrOrders = new boolean[attrArray.length];
		for (int i = 0; i < attrOrders.length; i++) {
			attrOrders[i] = true;
		}
		getJPAContainer().sort(attrArray, attrOrders);
	}

	@SuppressWarnings("rawtypes")
	public JPAContainer getJPAContainer() {
		JPAContainer jpaCont=null;
		Container cont = getContainerDataSource();
		if ((cont!=null) && (cont instanceof JPAContainer)) {
			jpaCont=(JPAContainer)cont;
		}
		return jpaCont;
	}

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return entityClass;
	}

	public void setAlignment(FieldAlignment alignment) {
	}

	
	/**
	 * @return a Component containing this field and a edit button wrapped in a layout.
	 * <p>
	 * When the edit button is pressed, the selected item is edited in the edit form.
	 */
	public Component getEditComponent(){

		// lazily creates the edit component the first time is requested
		if (editComponent==null){
			HorizontalLayout layout = new HorizontalLayout();
			layout.setSpacing(true);
			layout.setCaption(this.getCaption());
			this.setCaption(null);
			layout.addComponent(this);
			Button button = new Button("Modifica");
			button.addClickListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					editButtonClicked();
				}
			});
			layout.addComponent(button);
			editComponent=layout;
		}

		return editComponent;
	}
	
	private void editButtonClicked(){
		NewItemHandler handler = getNewItemHandler();
		if ((handler!=null) && (handler instanceof ComboNewItemHandler)) {
			ComboNewItemHandler cHandler = (ComboNewItemHandler)handler;
			Object bean = getSelectedBean();
			if (bean!=null) {
				BeanItem bi = new BeanItem(bean);
				cHandler.edit(bi);
			}
		}
	}
	
	
	/**
	 * Adds a listener that gets notified when a 
	 * record is saved after being edited in the form.
	 */
	public void addRecordEditedListener(RecordEditedListener listener) {
		this.listeners.add(listener);
	}// end of method
	
	protected void fire(BeanItem bi, boolean newRecord) {
		for (RecordEditedListener l : listeners) {
			l.save_(bi, newRecord);
		}

	}// end of method

	
	public interface RecordEditedListener {
		/**
		 * The record has been saved after being edited in the form.
		 * <p>
		 * @param bi the saved bean
		 * @param newRecord true for new record, false for editing existing record
		 */
		public void save_(BeanItem bi, boolean newRecord);
	}

}
