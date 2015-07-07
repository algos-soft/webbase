package it.algos.web.field;

import it.algos.web.dialog.AlertDialog;
import it.algos.web.dialog.ConfirmDialog;
import it.algos.web.lib.LibResource;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings({ "serial" })
public class ImageField extends CustomField<byte[]> implements FieldInterface<byte[]> {

	private static final boolean DEBUG_GUI=false;
	private Image image;
	private CommandToolbar cmdToolbar;

	public ImageField(String caption) {
		super();
		
		setCaption(caption);
		
		// create the image
		image = new Image();
		image.setImmediate(true);
		image.setVisible(false);
		
		// create the command toolbar
		cmdToolbar = new CommandToolbar();

		if (DEBUG_GUI) {
			addStyleName("yellowBg");
			image.addStyleName("pinkBg");
			cmdToolbar.addStyleName("greenBg");
		}


	}

	@Override
	public void initField() {
	}

	@Override
	public void setAlignment(FieldAlignment alignment) {
	}

	@Override
	protected Component initContent() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.addComponent(image);
		layout.addComponent(cmdToolbar);
		return layout;
	}

	@Override
	public Class<? extends byte[]> getType() {
		return byte[].class;
	}


	/**
	 * Toolbar with commands
	 */
	private class CommandToolbar extends HorizontalLayout{
		CommandToolbar() {
			super();
			
			setSpacing(true);
			
			Button bAdd = new Button(new ThemeResource("img/action_arrow_up.png"));
			bAdd.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					new UploadDialog().show(UI.getCurrent());
				}
			});
			
			Button bDel = new Button(new ThemeResource("img/action_delete.png"));
			bDel.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					removeImage();
				}
			});
			
			addComponent(bAdd);
			addComponent(bDel);

		}// end of constructor

	}
	
	
	private void removeImage(){
		ConfirmDialog dialog = new ConfirmDialog("Elimina immagine", "Vuoi eliminare l'immagine?", new ConfirmDialog.Listener() {
			
			@Override
			public void onClose(ConfirmDialog dialog, boolean confirmed) {
				if (confirmed) {
					setValue(new byte[0]);
				}
			}
		});
		
		dialog.show(UI.getCurrent());
	}

	
	private class UploadDialog extends AlertDialog{


		public UploadDialog() {
			super("Carica file", "");
			
			// create upload component
			ImageUploader receiver = new ImageUploader();
			Upload upload = new Upload(null, receiver);
			upload.setButtonCaption("Carica");
			upload.addSucceededListener(receiver);
			addComponent(upload);
			
			// close the dialog when finished
			upload.addFinishedListener(new FinishedListener() {
				@Override
				public void uploadFinished(FinishedEvent event) {
					close();
				}
			});
		}

		
	}

	/**
	 * Upload an image to an output stream,<br>
	 * create a resource with the stream and assign it to the image
	 */
	class ImageUploader implements Receiver, SucceededListener {
		
		private ByteArrayOutputStream stream;

		public OutputStream receiveUpload(String filename, String mimeType) {
			stream = new ByteArrayOutputStream();
			return stream; // Return the output stream to write to
		}

		public void uploadSucceeded(SucceededEvent event) {
			setValue(stream.toByteArray());
	    }
	}

	/**
	 * Sets the internal field value.
	 * <p>
	 * It can be overridden by the inheriting classes to update all dependent variables.
     * @param newValue- the new value to be set.
     */
	@Override
	protected void setInternalValue(final byte[] newValue) {
		super.setInternalValue(newValue);

		// create a StreamResource as data source for the image
        StreamResource resource = LibResource.getStreamResource(newValue);
        image.setSource(resource);
        
        // the image is visible only if there is some data to show
        if ((newValue==null) || (newValue.length==0)) {
    		image.setVisible(false);
		} else {
			image.setVisible(true);
		}
	}


	
	
}
