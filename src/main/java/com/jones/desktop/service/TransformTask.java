package com.jones.desktop.service;

import com.jones.desktop.controller.ProcessDetailsController;
import com.jones.desktop.entity.nfe.Nfe;
import javafx.concurrent.Task;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * User: bbonfim
 * Date: 4/24/17
 * Time: 9:21 PM
 */
public class TransformTask extends Task<Void> {

  ProcessDetailsController processDetailsController;
  File template;
  File xmlFolder;
  Boolean NFe;

  XLSManager xlsManager = new XLSManager();
  XMLManager xmlManager = new XMLManager();

  public TransformTask(ProcessDetailsController processDetailsController) {
    this.processDetailsController = processDetailsController;
    this.template = new File (processDetailsController.getTemplateFolder());
    this.xmlFolder = new File (processDetailsController.getFolder());
    this.NFe = processDetailsController.getTypeNFe();
  }

  @Override
  protected Void call() throws Exception {

    //if the base spreadsheet is not uploaded, uses the template
    InputStream in;
    Integer startFromRow=0;
    if(template == null){
      in = this.getClass().getClassLoader().getResourceAsStream("template.xlsx");
      //starting from row 3 since its the first line for data in the template
      startFromRow = 3;
    }else{
      in = new FileInputStream(template);
    }

    XSSFWorkbook workbook = new XSSFWorkbook(in);
    XSSFSheet sheet = workbook.getSheetAt(0);

    //make sure to start writing to first empty row in the sheet
    if(startFromRow==0){
      startFromRow = sheet.getLastRowNum()+1;
    }

    final FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("xml");
    for (final File child : xmlFolder.listFiles()) {
      if(extensionFilter.accept(child)) {
        //marshal xml file
        Nfe nfe = (Nfe) xmlManager.readNFe(child);

        //build array with the values for the xls columns
        Object[] nfeValues = null;
        if (nfe instanceof com.jones.desktop.entity.nfe.v310.TNfeProc) {
          nfeValues = xlsManager.buildRow310(nfe);
        }
        if (nfe instanceof com.jones.desktop.entity.nfe.v200.TNfeProc) {
          nfeValues = xlsManager.buildRow200(nfe);
        }

        //creates the new row
        XSSFRow row = sheet.createRow(startFromRow++);

        //writes the data to the new row
        int cellId = 1;
        for (Object obj : nfeValues) {
          Cell cell = row.createCell(cellId++);
          cell.setCellValue((String) obj);
        }

        //update user interface with the current progress
        processDetailsController.updateDetails();
      }
    }

    in.close();
    FileOutputStream outFile = new FileOutputStream(new File("test.xlsx"));
    workbook.write(outFile);
    outFile.close();


    return null;
  }


}