package com.jones.desktop.entity;

import com.jones.desktop.util.PageEnum;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * User: bbonfim
 * Date: 4/22/17
 * Time: 10:40 AM
 */
public class Page {

  PageEnum pageResource;
  Pane pane;
  FXMLLoader loader;
  Object controller;

  public Page() {
  }

  public Page(PageEnum pageResource) throws IOException {

    Locale locale = new Locale("en");
    ResourceBundle bundle = ResourceBundle.getBundle("scenes/main/main", locale);

    this.pageResource = pageResource;
    loader = new FXMLLoader(this.getClass().getClassLoader().getResource(this.pageResource.getLocation()), bundle);
//    loader.setBuilderFactory(new JavaFXBuilderFactory());
//    loader.setLocation(this.getClass().getClassLoader().getResource(this.pageResource.getLocation()));
    pane = loader.load();
    controller = loader.getController();
  }

  public Pane getPane() {
    return pane;
  }

  public void setPane(Pane pane) {
    this.pane = pane;
  }

  public Object getController() {
    return controller;
  }

  public void setController(Object controller){
    loader.setController(controller);
  }

  public void setController(Initializable controller) {
    this.controller = controller;
  }


}