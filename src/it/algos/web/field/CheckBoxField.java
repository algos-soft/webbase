package it.algos.web.field;

import com.vaadin.ui.CheckBox;

@SuppressWarnings("serial")
public class CheckBoxField extends CheckBox implements FieldInterface<Boolean> {

	public CheckBoxField() {
		super();
		init();
	}

	public CheckBoxField(String caption) {
		super(caption);
		init();
	}

	private void init() {
		initField();
	}

	public void initField() {
		FieldUtil.initField(this);
	}

	public void setAlignment(FieldAlignment alignment) {
	}

}
