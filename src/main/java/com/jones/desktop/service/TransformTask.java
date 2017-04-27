package com.jones.desktop.service;

import com.jones.desktop.controller.ProcessDetailsController;
import com.jones.desktop.entity.nfe.Nfe;
import javafx.concurrent.Task;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.Random;

/**
 * User: bbonfim
 * Date: 4/24/17
 * Time: 9:21 PM
 */
public class TransformTask extends Task<Void> {

  final static Logger logger = Logger.getLogger(TransformTask.class);

  ProcessDetailsController processDetailsController;
  File template;
  File xmlFolder;
  Boolean NFe;

  XLSManager xlsManager = new XLSManager();
  XMLManager xmlManager = new XMLManager();

  public TransformTask(ProcessDetailsController processDetailsController) {
    this.processDetailsController = processDetailsController;
    this.template = new File(processDetailsController.getTemplateFolder());
    this.xmlFolder = new File(processDetailsController.getFolder());
    this.NFe = processDetailsController.getTypeNFe();
  }

  @Override
  protected Void call() {
    String processId = String.valueOf(new Random().nextInt(90000) + 10000);
    //if the base spreadsheet is not uploaded, uses the template
    InputStream in = this.getClass().getClassLoader().getResourceAsStream("template.xlsx");
    File generatedFile = new File("jones_" + processId + ".xlsx");
    try {
      if (template != null && template.exists()) {
        in = new FileInputStream(template);
      }

      XSSFWorkbook workbook = null;
      try {
        workbook = new XSSFWorkbook(in);
      } catch (IOException e) {
        logger.error("Error while opening spreadsheet to insert the data: " + e.getMessage());
        e.printStackTrace();
      }
      XSSFSheet sheet = workbook.getSheetAt(0);

      //make sure to start writing to first empty row in the sheet
      int startFromRow = sheet.getLastRowNum() + 1;

      final FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("XML files", "xml");
      for (final File child : xmlFolder.listFiles()) {
        if (child.isFile() && extensionFilter.accept(child)) {

          //control variables
          Nfe nfe = null;
          Boolean statusOK = true;
          String message = null;

          try {
            //marshal xml file
            nfe = (Nfe) xmlManager.readNFe(child);

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

          } catch (JAXBException e) {
            statusOK = false;
            message = e.getMessage();
            logger.error("Error while transforming XML file: " + e.getCause());
            e.printStackTrace();
          } catch (IOException e) {
            statusOK = false;
            message = e.getMessage();
            logger.error("Error while transforming XML file: " + e.getCause());
            e.printStackTrace();
          } finally {
            //update user interface with the current progress
            processDetailsController.updateDetails(child, nfe, statusOK, message);
          }

        }
      }

      FileOutputStream outFile = new FileOutputStream(generatedFile);
      workbook.write(outFile);
      outFile.close();
      logger.info("Process Completed. Generated File: " + generatedFile.getAbsolutePath());
      processDetailsController.finishProcess(generatedFile);
    } catch (IOException e) {
      logger.error("Error while writing XLS file to disk: " + e.getMessage());
      e.printStackTrace();
    }finally {
      try {
        in.close();
      } catch (IOException e) {
        logger.error("Error processing XLS file: " + e.getMessage());
        e.printStackTrace();
      }
    }
    return null;
  }


}