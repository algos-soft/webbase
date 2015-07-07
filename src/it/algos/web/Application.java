package it.algos.web;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

/**
 * Contenitore di costanti della applicazione
 */
public abstract class Application {

	/**
	 * Servlet context<br>
	 * registered as soon as possible to make it available to every component
	 */
	private static ServletContext servletContext;

	/**
	 * Name of the folder for temporary uploaded files<br>
	 * The folder is located in the context folder of the container
	 */
	public static final String UPLOAD_FOLDER_NAME = "uploads";

	/**
	 * Name of the folder for the files to download<br>
	 * The folder is located in the context folder of the container
	 */
	public static final String DOWNLOAD_FOLDER_NAME = "downloads";

	/**
	 * Name of the base folder for demo data.<br>
	 * Demo data is loaded at bootstrap to populate empty tables.<br>
	 */
	public static final String DEMODATA_FOLDER_NAME = "/WEB-INF/data/demo/";

	/**
	 * Name of the base folder for images.<br>
	 */
	public static final String IMG_FOLDER_NAME = "/WEB-INF/data/img/";

	/**
	 * Use security access.<br>
	 * Not final<br>
	 */
	public static boolean USE_SECURITY = false;

	/**
	 * Use company.<br>
	 * Not final<br>
	 */
	public static boolean USE_COMPANY = false;

	/**
	 * Code of the company.<br>
	 * Not final<br>
	 */
	public static String COMPANY_CODE = "";

	/**
	 * Returns the path to the Uploads folder.
	 * 
	 * @return the path to the Uploads folder
	 */
	public static Path getUploadPath() {
		ServletContext sc = Application.getServletContext();
		return Paths.get(sc.getRealPath("/" + Application.UPLOAD_FOLDER_NAME));
	}// end of method

	public static Path getDownloadPath() {
		ServletContext sc = Application.getServletContext();
		return Paths.get(sc.getRealPath("/" + Application.DOWNLOAD_FOLDER_NAME));
	}

	public static Path getDemoDataFolderPath() {
		ServletContext sc = Application.getServletContext();
		return Paths.get(sc.getRealPath("/" + Application.DEMODATA_FOLDER_NAME));
	}

	public static Path getImgFolderPath() {
		ServletContext sc = Application.getServletContext();
		return Paths.get(sc.getRealPath("/" + Application.IMG_FOLDER_NAME));
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}// end of method

	public static void setServletContext(ServletContext servletContext) {
		Application.servletContext = servletContext;
	}// end of method

}// end of class