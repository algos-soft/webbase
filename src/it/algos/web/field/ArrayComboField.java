package it.algos.web.field;

import com.vaadin.data.Item;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;

/**
 * Combo field driven by a Enum
 */
@SuppressWarnings("serial")
public class ArrayComboField extends ComboBox implements FieldInterface<Object> {

	private Object[] values;

	public ArrayComboField(Object[] values) {
		this(values, "");
	}// end of constructor

	public ArrayComboField(Object[] values, String caption) {
		super(caption);
		this.values = values;
		init();
	}// end of constructor


	private void init() {
		initField();
		for (Object value : values) {
			Item item = addItem(value);
			setItemCaption(item, value.toString());
		}
	}// end of constructor

	public void initField() {
		FieldUtil.initField(this);
	}// end of constructor

	public void setAlignment(FieldAlignment alignment) {
	}// end of constructor
	

}// end of class
