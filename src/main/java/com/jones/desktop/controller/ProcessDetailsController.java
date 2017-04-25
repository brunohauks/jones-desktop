package com.jones.desktop.controller;

import com.jones.desktop.service.TransformTask;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

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

  public void updateDetails(){
    totalNumberOfXmlFiles.setText("121212");
    numberOfXmlProcessedWithNoErrors.setText("4444");
    totalNumberOfXmlWithError.setText("233333");
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