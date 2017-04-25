package com.jones.desktop.service;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.File;

/**
 * User: bbonfim
 * Date: 4/24/17
 * Time: 9:21 PM
 */
public class TransformTask extends Task<Void> {

  Text processedXMLNumber;
  Text processedXMLErrorNumber;
  TextArea processInfoTextArea;
  ProgressBar progressBar;
  File template;
  File processFolder;
  Boolean NFe;

  public TransformTask(Text processedXMLNumber, Text processedXMLErrorNumber, TextArea processInfoTextArea, ProgressBar progressBar, File template, File processFolder, Boolean NFe) {
    this.processedXMLNumber = processedXMLNumber;
    this.processedXMLErrorNumber = processedXMLErrorNumber;
    this.processInfoTextArea = processInfoTextArea;
    this.progressBar = progressBar;
    this.template = template;
    this.processFolder = processFolder;
    this.NFe = NFe;
  }

  @Override
  protected Void call() throws Exception {

    return null;
  }
}