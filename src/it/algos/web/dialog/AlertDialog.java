package it.algos.web.dialog;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class AlertDialog extends BaseDialog {

	public AlertDialog(String title, String message) {
		super(title, message);
		init();
	}

	public AlertDialog(String title) {
		this(title, "");
	}

	private void init() {
		Button cancelButton = new Button("Chiudi");
		cancelButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		getToolbar().addComponent(cancelButton);

	}

}
