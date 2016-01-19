package it.algos.webbase.web.importexport;

import com.vaadin.data.Container;

public class ExportConfiguration {
	private Class<?> domainClass;
	private Container container;
	private boolean exportAll;
	private ExportProvider provider;
	private String filename;

	/**
	 * Constructor for one ExportConfiguration.
	 * 
	 * @param domainClass
	 *            the reference domain class
	 * @param filename
	 *            the filename for export
	 * @param container
	 *            a Container to read the data from (used only if exportAll=false)
	 */
	public ExportConfiguration(Class<?> domainClass, String filename, Container container, ExportProvider provider) {
		super();
		this.domainClass = domainClass;
		this.filename = filename;
		this.container = container;
		this.provider=provider;
	}

//	public void addAttribute(Attribute<?, ?> attr) {
//		addAttribute(attr, null);
//	}

//	public void addAttributeOld(Attribute<?, ?> attr, String title) {
//		ExportAttribute eattr = new ExportAttribute(attr, title);
//		providers.add(eattr);
//	}

//	public void addAttribute(Attribute<?, ?> attr, String title) {
//		ExportProvider provider = new EntityExportProvider(attr, title);
//		addColumnProvider(provider);
//	}

//	public void setExportProvider(ExportProvider provider){
//		this.provider=provider;
//	}

	public Class<?> getDomainClass() {
		return domainClass;
	}

	public String getFilename() {
		return filename;
	}

	public ExportProvider getExportProvider() {
		return provider;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public boolean isExportAll() {
		return exportAll;
	}

	public void setExportAll(boolean exportAll) {
		this.exportAll = exportAll;
	}

//	public class ExportAttribute {
//		private Attribute<?, ?> attr;
//		private String title;
//
//		public ExportAttribute(Attribute<?, ?> attr, String title) {
//			super();
//			this.attr = attr;
//			this.title = title;
//			if (this.title == null) {
//				this.title = attr.getName();
//			}
//		}
//
//		public Attribute<?, ?> getAttribute() {
//			return attr;
//		}
//
//		public String getTitle() {
//			return title;
//		}
//
//	}

}
