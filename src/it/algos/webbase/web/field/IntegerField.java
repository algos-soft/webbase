package it.algos.webbase.web.field;

import java.util.Locale;

import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

@SuppressWarnings({ "serial" })
public class IntegerField extends CustomField<Integer> implements FieldInterface<Integer> {

	private TextField textField;
	private StringToIntegerConverter converter = new StringToIntegerConverter();

	public IntegerField(String caption) {
		super();
		textField = new TextField();
		setCaption(caption);
		setWidth("5em");

		textField.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				String text = textField.getValue();
				try{	// if the value is invalid then set it to 0
					Integer.parseInt(text);
				}catch (Exception e){
					text="0";
					textField.setValue(text);
				}
				Integer integer = converter.convertToModel(text, Integer.class, Locale.getDefault());
				writeValue(integer);
			}
		});
	}

	public IntegerField() {
		this(null);
	}

	@Override
	protected Component initContent() {
		return textField;
	}

	@Override
	public void initField() {
		FieldUtil.initField(this);
	}

	private void writeValue(Integer integer) {
		setValue(integer);
	}

	@Override
	protected void setInternalValue(Integer integer) {
		super.setInternalValue(integer);
		String text = converter.convertToPresentation(integer, String.class, Locale.getDefault());
		textField.setValue(text);
	}

	@Override
	public Class<? extends Integer> getType() {
		return Integer.class;
	}

	@Override
	public void setWidth(String width) {
		textField.setWidth(width);
	}

	@Override
	public boolean isReadOnly() {
		return textField.isReadOnly();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		textField.setReadOnly(readOnly);
	}

	public void setAlignment(FieldAlignment alignment) {
		FieldUtil.setAlignment(textField, alignment);
	}

}
