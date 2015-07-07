package it.algos.web.updown;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.EntityItemProperty;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.server.StreamResource.StreamSource;
import it.algos.web.entity.EM;
import it.algos.web.importexport.ExportProvider;
import it.algos.web.importexport.ExportConfiguration;
import it.algos.web.updown.OnDemandFileDownloader.OnDemandStreamResource;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("serial")
public class ExportStreamResource implements OnDemandStreamResource, StreamSource {

	private ExportConfiguration config;
	private CellStyle dateCellStyle;

	public ExportStreamResource(ExportConfiguration config) {
		super();
		this.config = config;
	}

	@Override
	public InputStream getStream() {
		ByteArrayInputStream is = null;
		Workbook wb = createWorkbook();

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
			is = new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

	@Override
	public String getFilename() {
		return config.getFilename();
	}

	private Workbook createWorkbook() {
		Workbook wb = new HSSFWorkbook();

		// create Date cell style
		dateCellStyle = wb.createCellStyle();
		CreationHelper createHelper = wb.getCreationHelper();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

		Sheet sheet = wb.createSheet(config.getDomainClass().getSimpleName());
		createFirstRow(sheet); // create row 0

		// create container
		EntityManager manager = EM.createEntityManager();
		JPAContainer<?> container;
		if (config.isExportAll()) {
			container = JPAContainerFactory.makeNonCachedReadOnly(config.getDomainClass(), manager);
		} else {
			container = config.getContainer();
		}

		for (int i = 0; i < container.size(); i++) {
			Row row = sheet.createRow((short) i + 1);
			createCells(container, row, i);
		}
		
		manager.close();

		// autosize the columns
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		for (int i = 0; i < noOfColumns; i++) {
			sheet.autoSizeColumn(i);
		}

		return wb;
	}

//	/**
//	 * Create first row with titles
//	 */
//	private void createFirstRowOld(Sheet sheet) {
//		Row row = sheet.createRow((short) 0);
//		CellStyle style = sheet.getWorkbook().createCellStyle();
//		Font font = sheet.getWorkbook().createFont();
//		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
//		style.setFont(font);
//		int attrIdx = 0;
//
//		for (ExportAttribute eattr : config.getColumnProviders()) {
//			String title = eattr.getTitle();
//			Cell cell = row.createCell(attrIdx);
//			cell.setCellStyle(style);
//			attrIdx++;
//			cell.setCellValue(title);
//		}
//	}


	/**
	 * Create first row with titles
	 */
	private void createFirstRow(Sheet sheet) {
		Row row = sheet.createRow((short) 0);
		CellStyle style = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		int columnIdx = 0;
		for(String title : config.getExportProvider().getTitles()){
			Cell cell = row.createCell(columnIdx);
			cell.setCellStyle(style);
			setCellValue(cell, title);
			columnIdx++;
		}
	}




//	/**
//	 * Create the cells for a given row and index
//	 */
//	private void createCellsOld(JPAContainer<?> cont, Row row, int idx) {
//		Object id = cont.getIdByIndex(idx);
//		if (id != null) {
//			EntityItem<?> item = cont.getItem(id);	// the row
//			if (item != null) {
//				ArrayList<ExportAttribute> attributes = config.getColumnProviders();
//				int attrIdx = 0;
//				for (ExportAttribute eattr : attributes) {
//
//					//Object value = eattr.getValue();
//
//					EntityItemProperty property = item.getItemProperty(eattr.getAttribute().getName());
//					if (property != null) {
//						Object value = property.getValue();
//						if (value == null) {
//							value = "";
//						}
//						Cell cell = row.createCell(attrIdx);
//						attrIdx++;
//						cell.setCellValue(value.toString());
//					}
//				}
//			}
//		}
//
//	}

	/**
	 * Create the cells for a given row and index
	 */
	private void createCells(JPAContainer<?> cont, Row row, int idx) {
		Object id = cont.getIdByIndex(idx);
		if (id != null) {
			EntityItem<?> item = cont.getItem(id);	// the row
			if (item != null) {
				int columnIdx = 0;
				ExportProvider provider = config.getExportProvider();
				Object[] values = provider.getExportValues(item);
				for(Object obj : values){
					Cell cell = row.createCell(columnIdx);
					setCellValue(cell, obj);
					columnIdx++;
				}
			}
		}

	}



	/**
	 * Write a value to a cell.
	 * Allows only the types supported by Excel
	 * @param cell the cell
	 * @param value the value
	 */
	private void setCellValue(Cell cell, Object value){
		if(value!=null){
			if(value instanceof String){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue((String)value);
				return;
			}
			if(value instanceof Number){
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				Number num = (Number)value;
				cell.setCellValue(num.doubleValue());
				return;
			}
			if(value instanceof Boolean){
				cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
				cell.setCellValue((Boolean) value);
				return;
			}
			if (value instanceof Date){
				cell.setCellStyle(dateCellStyle);
				cell.setCellValue((Date)value);
				return;
			}
			if(value instanceof Calendar){
				cell.setCellValue((Calendar)value);
				return;
			}
			if(value instanceof RichTextString){
				cell.setCellValue((RichTextString)value);
				return;
			}

			// tipo non riconosciuto, usa toString()
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(value.toString());

		}
	}


}
