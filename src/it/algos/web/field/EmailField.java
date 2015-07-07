package it.algos.web.field;

import com.vaadin.data.Validator;

public class EmailField extends TextField implements FieldInterface<String> {

	public EmailField(String caption) {
		super(caption);
		init();
	}

	public EmailField() {
		this("");
	}

	private void init() {
		setWidth("260px");
		Validator validator = new com.vaadin.data.validator.EmailValidator("indirizzo email non valido");
		this.addValidator(validator);
	}

}
