package it.algos.webbase.web.dialog;

import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Abstract base class for modal dialogs. <br>
 * To add Components (buttons, etc.) to the toolbar, call: getToolbar().addComponent(Component comp). <br>
 * To add Components to the detail area, call: addComponent(Component comp) (the detail area is a Vertical layout, you
 * can add one or more components.)
 * <p>
 * You can also set a message to be displayed in the detail area calling <code>setMessage(String message)</code>. The
 * message string can contain simple HTML.
 * <p>
 * Once the dialog is prepared, show it by calling <code>show(UI ui)</code>
 * <p>
 * Sizing: By default the dialog is not resizable and its size depends on the content.<br>
 * You can set it to be resizable with <code>setResizable(true)</code> but in this case a initial size is also needed.
 * Failing to provide initial size will cause incorrect sizing. e.g.<br>
 * <code>
 * dialog.setResizable(true);
 * dialog.setWidth("300px");
 * dialog.setHeight("200px");
 * </code>
 */
@SuppressWarnings("serial")
public abstract class BaseDialog extends Window {
	protected VerticalLayout detail;
	private DialogToolbar toolbar;
	private VerticalLayout mainLayout;
	private Label label;
	String messageText;

	public BaseDialog(String title, String message) {
		super();
		setTitle(title);
		setMessageText(message);
		init();
	}

	public BaseDialog() {
		this(null, null);
	}

	protected void init() {

		setModal(true);
		setClosable(false);
		setResizable(false);

		toolbar = createToolbarComponent();
		detail = createDetailComponent();
		detail.setMargin(true);
		label = new Label();
		label.setContentMode(ContentMode.HTML);
		setMessage(messageText);

		mainLayout = new VerticalLayout();
		syncResizable();

		mainLayout.addComponent(detail);
		detail.addComponent(label);
		detail.setHeight("100%");
		mainLayout.setExpandRatio(detail, 1f);

		mainLayout.addComponent(toolbar);
		toolbar.setWidth("100%");

		setContent(mainLayout);

		center();

	}

	/**
	 * The component shown in the detail area.
	 */
	protected VerticalLayout createDetailComponent() {
		return new VerticalLayout();
	}

	/**
	 * The component shown in the toolbar area.
	 */
	protected DialogToolbar createToolbarComponent() {
		return new DialogToolbar();
	}

	@Override
	public void setResizable(boolean resizable) {
		super.setResizable(resizable);
		syncResizable();
	}

	private void syncResizable() {
		if (mainLayout != null) {
			if (isResizable()) {
				mainLayout.setSizeFull();
			} else {
				mainLayout.setWidth("100%");
				mainLayout.setHeightUndefined();
			}
		}
	}

	/**
	 * Adds a component to the Detail area.<br>
	 * (the detail area is a Vertical layout)
	 * 
	 * @param comp
	 *            the component to add
	 */
	public void addComponent(Component comp) {
		if (detail != null) {
			detail.addComponent(comp);
		}
	}

	/**
	 * Sets the message text
	 */
	public void setMessage(String message) {
		if (label != null) {
			label.setValue(message);
		}
	}

	/**
	 * Sets the visibility of the detail area
	 * 
	 * @param visible
	 *            the visibility
	 */
	public void setDetailAreaVisible(boolean visible) {
		if (detail != null) {
			detail.setVisible(visible);
		}
	}

	public void show(UI ui) {
		ui.addWindow(this);
	}

	public DialogToolbar getToolbar() {
		return toolbar;
	}

	public void setTitle(String title) {
		setCaption(title);
	}

	public String getMessageText() {
		return messageText;
	}

	private void setMessageText(String messageText) {
		this.messageText = messageText;
	}

}
