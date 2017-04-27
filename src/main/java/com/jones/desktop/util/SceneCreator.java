package com.jones.desktop.util;

import com.jones.desktop.entity.Page;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * User: bbonfim
 * Date: 4/22/17
 * Time: 10:29 AM
 */
public class SceneCreator {

  public static void renderPage(Stage stage, PageEnum displayPage) throws Exception {
    stage.setTitle("Jones - Desktop");

    //create scene
    Scene scene = createScene(displayPage);

    //set stage
    stage.setScene(scene);
    stage.show();
  }

  public static Scene createScene(PageEnum displayPage) throws IOException {
    VBox templatePage;

    // get main page
    Pane main = new Page(displayPage).getPane();

    // get reference of Pane to be added to the template.
    Node mainNode = main.lookup("#content");

    // get template
    templatePage = (VBox) new Page(PageEnum.MAIN).getPane();

    // get reference of the "main" section in template i.e. where the new contents will be added
    AnchorPane origMain = (AnchorPane) templatePage.lookup("#main");

    // set contents of "mainPage" into the template
    origMain.getChildren().setAll(mainNode);

    // create scene
    Scene scene = new Scene(templatePage);
    return scene;
  }

  /**
   * updates scene from an alreayd created page object
   * @param currentScene
   * @param pageToDisplay
   * @return
   * @throws IOException
   */
  public static void updateScene(Scene currentScene, Page pageToDisplay) throws IOException {
    ((Stage) currentScene.getWindow()).setTitle("Jones - Desktop");

    // get reference of Pane to be added to the template.
    Node mainNode = pageToDisplay.getPane().lookup("#content");

    AnchorPane origMain = (AnchorPane) currentScene.lookup("#main");
    // set contents of "mainPage" into the template
    origMain.getChildren().setAll(mainNode);
  }

}