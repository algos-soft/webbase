package it.algos.webbase.web.updown;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.server.StreamResource;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.importexport.ExportConfiguration;
import it.algos.webbase.web.importexport.ExportProvider;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * Stream source for an Export operation
 * Created by alex on 3-02-2016.
 */
public class ExportStreamSource implements StreamResource.StreamSource {

    private ExportConfiguration config;
    private CellStyle dateCellStyle;

    private Workbook workbook;
    private Sheet sheet;

    public ExportStreamSource(ExportConfiguration config) {
        this.config=config;
    }

    @Override
    public InputStream getStream() {

        createWorkbook();

        populateWorkbook();

        InputStream is = writeWorkbook();

        return is;

    }


    /**
     * Create and populate the workbook
     */
    protected Workbook createWorkbook() {
        workbook = new HSSFWorkbook();
        return workbook;
    }


    protected Sheet createSheet(){
        sheet = workbook.createSheet(config.getDomainClass().getSimpleName());
        return sheet;
    }


    protected void populateWorkbook(){
        // create Date cell style
        dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

        // create the worksheet and the header row
        createSheet();
        createFirstRow(); // create row 0

        // create or use the container
        EntityManager manager = EM.createEntityManager();
        Container container = config.getContainer();

        // iterate the container and create the rows
        if(container instanceof Container.Indexed){
            Container.Indexed iCont=(Container.Indexed)container;
            for (int i = 0; i < container.size(); i++) {
                Object id = iCont.getIdByIndex(i);
                Item item = iCont.getItem(id);
                Row row = createRow(i + 1);
                createCells(item, row);
            }
        }


        manager.close();

        // autosize the columns
        int noOfColumns = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i < noOfColumns; i++) {
            sheet.autoSizeColumn(i);
        }

    }


    protected InputStream writeWorkbook(){
        ByteArrayInputStream is = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
            is = new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }


    protected Row createRow(int i){
        Row row = sheet.createRow((short) i + 1);
        return row;
    }

    protected Row addRow(){
        Row row = sheet.createRow(sheet.getLastRowNum());
        return row;
    }



    /**
     * Create first row with titles
     */
    private void createFirstRow() {
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


    /**
     * Create the cells for a given Item
     */
    private void createCells(Item item, Row row) {
        if(item!=null){
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
                cell.setCellValue((String) value);
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

    public ExportConfiguration getExportConfiguration() {
        return config;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }
}
