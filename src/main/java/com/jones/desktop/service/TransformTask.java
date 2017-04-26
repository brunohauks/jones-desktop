package com.jones.desktop.service;

import com.jones.desktop.controller.ProcessDetailsController;
import javafx.concurrent.Task;

import java.io.File;

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

  public TransformTask(ProcessDetailsController processDetailsController) {
    this.processDetailsController = processDetailsController;
    this.template = new File (processDetailsController.getTemplateFolder());
    this.xmlFolder = new File (processDetailsController.getFolder());
    this.NFe = processDetailsController.getTypeNFe();
  }

  @Override
  protected Void call() throws Exception {


    return null;
  }



}