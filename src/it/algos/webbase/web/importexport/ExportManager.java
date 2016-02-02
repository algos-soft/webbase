package it.algos.webbase.web.importexport;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.OptionGroup;
import it.algos.webbase.web.dialog.ConfirmDialog;
import it.algos.webbase.web.updown.ExportStreamResource;
import it.algos.webbase.web.updown.OnDemandFileDownloader;
import it.algos.webbase.web.updown.OnDemandFileDownloader.OnDemandStreamResource;

import java.io.File;

@SuppressWarnings("serial")
public class ExportManager extends ConfirmDialog {

	private ExportConfiguration config;
	private OptionGroup exportOptions;
	private String itemTutti = "Esporta tutti";
	private String itemSoli = "Solo quelli presenti in lista";

	public ExportManager(final ExportConfiguration config) {
		super(null);
		this.config = config;
		setTitle("Esportazione");
		setMessage("Tabella: <b>" + config.getDomainClass().getSimpleName() + "</b>");

		exportOptions = new OptionGroup();
		exportOptions.setImmediate(true);
		exportOptions.addItem(itemTutti);
		exportOptions.addItem(itemSoli);
		addComponent(exportOptions);
		exportOptions.select(itemTutti);
		exportOptions.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				config.setExportAll(!exportOptions.isSelected(itemSoli));
			}
		});
		config.setExportAll(!exportOptions.isSelected(itemSoli)); // set initial value in config

		createDownloader();
	}

	private void createDownloader() {

		OnDemandStreamResource streamRes = new ExportStreamResource(config);
		OnDemandFileDownloader downloader = new OnDemandFileDownloader(streamRes);
		downloader.extend(getConfirmButton());

//		Resource res = new FileResource(new File("/tmp/CRIF_Algos_feb-2013.pdf"));
//		FileDownloader fd = new FileDownloader(res);
//		fd.extend(getConfirmButton());

	}

	@Override
	protected void onConfirm() {
		super.onConfirm();
	}

	/**
	 * Helper method to create a new ExportConfiguration object for a given Entity.<br>
	 * Contains all the fields with defaul titles.
	 */
	public static ExportConfiguration createExportConfiguration(Class clazz) {
		ExportProvider provider=new EntityExportProvider(clazz);
		ExportConfiguration config = new ExportConfiguration(clazz, clazz.getSimpleName() + ".xls", null, provider);
		return config;
	}

}
