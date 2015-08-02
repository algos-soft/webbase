package it.algos.webbase.web.field;

@SuppressWarnings("serial")
public class TextField extends com.vaadin.ui.TextField implements FieldInterface<String> {

	public TextField(String caption) {
		super(caption);
		init();
	}

	public TextField() {
		this("");
	}

	private void init() {
		initField();
		setWidth("180px");
		setNullRepresentation("");
	}

	public void initField() {
		FieldUtil.initField(this);
	}

	public void setAlignment(FieldAlignment alignment) {
		FieldUtil.setAlignment(this, alignment);
	}

}
