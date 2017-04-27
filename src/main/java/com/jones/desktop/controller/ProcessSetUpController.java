package com.jones.desktop.controller;

import com.jones.desktop.entity.Page;
import com.jones.desktop.util.PageEnum;
import com.jones.desktop.util.SceneCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * User: bbonfim
 * Date: 4/21/17
 * Time: 11:57 PM
 */
public class ProcessSetUpController {

  final static Logger logger = Logger.getLogger(ProcessSetUpController.class);

  //variable for the first step
  @FXML
  Label labelNoFilesFound;
  @FXML
  Label labelTotalNumberOfXml;
  @FXML
  Label labelTotalNumberOfNFEXml;
  @FXML
  Label labelSelectedFolder;
  @FXML
  Label labelTotalNumberOfCTEXml;
  @FXML
  Text totalNumberOfXml;
  @FXML
  Text totalNumberOfNFEXml;
  @FXML
  Text totalNumberOfCTEXml;
  @FXML
  Text selectedFolder;


  //variables for the second step
  @FXML
  AnchorPane chooseSetUp;
  @FXML
  Label labelSelectedTemplate;
  @FXML
  Text selectedTemplate;
  @FXML
  RadioButton NFe;

  //variables for the 3rd step
  @FXML
  Button startProcessButton;
  @FXML
  AnchorPane startProcess;



  /**
   * handles the selection of the folder with the xml files. Enables the next step panel
   * once the calculation of xml files is done.
   * @param event
   * @throws IOException
   */
  @FXML
  protected void selectFolder(ActionEvent event) throws IOException {
    //create director chooser
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Select Folder");
    Stage stage = (Stage) totalNumberOfXml.getScene().getWindow();
    File dir = directoryChooser.showDialog(stage);

    if (dir != null && dir.isDirectory()) {
      logger.info("selected folder: " + dir.getAbsolutePath());

      //calculate xml files
      File[] xmlFiles = dir.listFiles(new FilenameFilter() {
        public boolean accept(File folder, String name) {
          return name.toLowerCase().endsWith(".xml");
        }
      });

      if(xmlFiles.length > 0){
        //remove message of no files found
        labelNoFilesFound.setVisible(false);

        //set selected folder
        selectedFolder.setText(dir.getAbsolutePath());
        labelSelectedFolder.setVisible(true);

        totalNumberOfXml.setText(String.valueOf(xmlFiles.length));
        labelTotalNumberOfXml.setVisible(true);

        chooseSetUp.setDisable(false);
        startProcess.setDisable(false);
      }else{
        labelNoFilesFound.setVisible(true);
      }
      logger.info("total number of xml files found: " + xmlFiles.length);
    }
  }

  /**
   * selects the template file to be used in the transformation
   * @param event
   * @throws IOException
   */
  @FXML
  protected void selectTemplate(ActionEvent event) throws IOException {
    //create director chooser
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Selecionar Template");
    Stage stage = (Stage) totalNumberOfXml.getScene().getWindow();
    File templateFile = fileChooser.showOpenDialog(stage);

    if (templateFile != null && templateFile.isFile()) {
      //set selected file
      selectedTemplate.setText(templateFile.getAbsolutePath());
      labelSelectedTemplate.setVisible(true);
      logger.info("selected template File: " + templateFile.getAbsolutePath());
    }
  }

  /**
   * start the transformation process
   * @param event
   * @throws IOException
   */
  @FXML
  protected void startProcess(ActionEvent event) throws Exception {
    Scene scene = labelNoFilesFound.getScene();
    Page processDetailsPage = new Page(PageEnum.PROCESS_DETAILS);

    //set the input parameters in the controller
    ProcessDetailsController controller = (ProcessDetailsController) processDetailsPage.getController();
    controller.setFolder(selectedFolder.getText());
    controller.setTemplateFolder(selectedTemplate.getText());
    controller.setTypeNFe(NFe.isSelected());

    //updates scene with process details
    SceneCreator.updateScene(scene, processDetailsPage);

    controller.startProcess(new Double(totalNumberOfXml.getText()));
  }
}