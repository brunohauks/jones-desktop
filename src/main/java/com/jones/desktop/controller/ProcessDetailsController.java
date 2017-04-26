package com.jones.desktop.controller;

import com.jones.desktop.entity.nfe.Nfe;
import com.jones.desktop.service.TransformTask;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.io.File;

/**
 * User: bbonfim
 * Date: 4/25/17
 * Time: 4:43 PM
 */
public class ProcessDetailsController {

  //process input
  private String folder;
  private String templateFolder;
  private Boolean typeNFe;

  @FXML
  Text totalNumberOfXmlFiles;
  @FXML
  Text totalNumberOfXmlWithError;
  @FXML
  Text numberOfXmlProcessedWithNoErrors;
  @FXML
  TableView detailsDataTable;

  public void startProcess() {
    TransformTask transformTask = new TransformTask(this);
    new Thread(transformTask).start();
  }

  public void updateDetails(File child, Nfe nfe, Boolean statusOK, String message){
    //figure out number of processed xml
    String totalNumberOfXmlFilesText = totalNumberOfXmlFiles.getText();
    Integer totalNumberOfProcessedXml = totalNumberOfXmlFilesText.isEmpty() ? 1 : Integer.parseInt(totalNumberOfXmlFilesText) + 1;
    totalNumberOfXmlFiles.setText(totalNumberOfProcessedXml.toString());

    if(statusOK){
      //figure out number of successfully processed xml files
      String totalNumberOfXmlWithNoErrorText = numberOfXmlProcessedWithNoErrors.getText();
      Integer totalNumberOfXmlWithNoError = totalNumberOfXmlWithNoErrorText.isEmpty() ? 1 : Integer.parseInt(totalNumberOfXmlWithNoErrorText) + 1;
      numberOfXmlProcessedWithNoErrors.setText(totalNumberOfXmlWithNoError.toString());
    }else{
      //figure out number of successfully processed xml files
      String totalNumberOfXmlWithErrorText = totalNumberOfXmlWithError.getText();
      Integer totalNumberOfXmlWithErrorValue = totalNumberOfXmlWithErrorText.isEmpty() ? 1 : Integer.parseInt(totalNumberOfXmlWithErrorText) + 1;
      totalNumberOfXmlWithError.setText(totalNumberOfXmlWithErrorValue.toString());
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