package com.jones.desktop.controller;

import com.jones.desktop.entity.nfe.Nfe;
import com.jones.desktop.service.TransformTask;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * User: bbonfim
 * Date: 4/25/17
 * Time: 4:43 PM
 */
public class ProcessDetailsController implements Initializable{

  final static Logger logger = Logger.getLogger(ProcessSetUpController.class);

  //process input
  private String folder;
  private String templateFolder;
  private Boolean typeNFe;
  private Double totalXmlNumber;
  private ResourceBundle bundle;
  private File generatedFile;

  @FXML Text totalNumberOfXmlFiles;
  @FXML Text totalNumberOfXmlWithError;
  @FXML Text numberOfXmlProcessedWithNoErrors;
  @FXML TableView detailsDataTable;
  @FXML ProgressBar progressBar;
  @FXML Button openGeneratedFile;

  public void startProcess(Double expectedNumberOfFilesToProcess) {
    TransformTask transformTask = new TransformTask(this);
    totalXmlNumber = expectedNumberOfFilesToProcess;
    initializeTableView();
    new Thread(transformTask).start();
  }

  public void updateDetails(File file, Nfe nfe, Boolean statusOK, String message){
    //figure out number of processed xml
    String totalNumberOfXmlFilesText = totalNumberOfXmlFiles.getText();
    Integer totalNumberOfProcessedXml = totalNumberOfXmlFilesText.isEmpty() ? 1 : Integer.parseInt(totalNumberOfXmlFilesText.split("/")[0].trim()) + 1;
    totalNumberOfXmlFiles.setText(totalNumberOfProcessedXml.toString() +" / " + String.valueOf(totalXmlNumber.intValue()));

    if(statusOK){
      //figure out number of successfully processed xml files
      String totalNumberOfXmlWithNoErrorText = numberOfXmlProcessedWithNoErrors.getText();
      Integer totalNumberOfXmlWithNoError = totalNumberOfXmlWithNoErrorText.isEmpty() ? 1 : Integer.parseInt(totalNumberOfXmlWithNoErrorText) + 1;
      numberOfXmlProcessedWithNoErrors.setText(totalNumberOfXmlWithNoError.toString());
    }else{
      //figure out number of xml with errors
      String totalNumberOfXmlWithErrorText = totalNumberOfXmlWithError.getText();
      Integer totalNumberOfXmlWithErrorValue = totalNumberOfXmlWithErrorText.isEmpty() ? 1 : Integer.parseInt(totalNumberOfXmlWithErrorText) + 1;
      totalNumberOfXmlWithError.setText(totalNumberOfXmlWithErrorValue.toString());
    }
    addRowToTableView(statusOK, message, nfe, file.getName());
    progressBar.setProgress((100 * totalNumberOfProcessedXml) / totalXmlNumber / 100);
  }

  public void finishProcess(File generatedFile){
    openGeneratedFile.setDisable(false);
    this.generatedFile = generatedFile;
  }

  public void setOpenGeneratedFile(){

    if( Desktop.isDesktopSupported() )
    {
      new Thread(() -> {
        try {
          Desktop.getDesktop().open(generatedFile);
        } catch (IOException e) {
          logger.error("Error while opening generated file: " + e.getMessage());
        }
      }).start();
    }
  }

  private void initializeTableView() {
    TableColumn fileName = new TableColumn(bundle.getString("label.process.table.header.file"));
    fileName.setCellValueFactory(new PropertyValueFactory<DetailsObject,String>("fileName"));

    TableColumn cnpj = new TableColumn(bundle.getString("label.process.table.header.cnpj"));
    cnpj.setCellValueFactory(
            new PropertyValueFactory<DetailsObject,String>("cnpj")
    );

    TableColumn status = new TableColumn(bundle.getString("label.process.table.header.status"));
    status.setCellValueFactory(
            new PropertyValueFactory<DetailsObject,String>("status")
    );

    TableColumn message = new TableColumn(bundle.getString("label.process.table.header.status"));
    message.setCellValueFactory(
            new PropertyValueFactory<DetailsObject,String>("message")
    );
    detailsDataTable.setItems(FXCollections.observableArrayList());
    detailsDataTable.getColumns().addAll(fileName, cnpj, status, message);
  }

  public void addRowToTableView(Boolean statusOK, String message, Nfe nfe, String fileName){
    DetailsObject rowData = new DetailsObject();
    rowData.setFileName(fileName);
    if (nfe instanceof com.jones.desktop.entity.nfe.v310.TNfeProc) {
      rowData.setCnpj(((com.jones.desktop.entity.nfe.v310.TNfeProc) nfe).getNFe().getInfNFe().getEmit().getCNPJ());
    }else {
      rowData.setCnpj(((com.jones.desktop.entity.nfe.v200.TNfeProc) nfe).getNFe().getInfNFe().getEmit().getCNPJ());
    }
    rowData.setStatus(statusOK ? "OK" : "ERRO");
    rowData.setMessage(statusOK ? bundle.getString("process.details.success.xml.message") : message);
    detailsDataTable.getItems().add(rowData);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    bundle = resources;
  }

  public class DetailsObject {
    String fileName;
    String cnpj;
    String status;
    String message;

    public String getFileName() {
      return fileName;
    }

    public void setFileName(String fileName) {
      this.fileName = fileName;
    }

    public String getCnpj() {
      return cnpj;
    }

    public void setCnpj(String cnpj) {
      this.cnpj = cnpj;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }

  public void setFolder(String folder) {
    this.folder = folder;
  }

  public void setTemplateFolder(String templateFolder) {
    this.templateFolder = templateFolder;
  }

  public void setTypeNFe(Boolean typeNFe) {
    this.typeNFe = typeNFe;
  }

  public String getFolder() {
    return folder;
  }

  public String getTemplateFolder() {
    return templateFolder;
  }

  public Boolean getTypeNFe() {
    return typeNFe;
  }
}