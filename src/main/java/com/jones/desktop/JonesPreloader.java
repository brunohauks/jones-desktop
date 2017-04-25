package com.jones.desktop;

/**
 * User: bbonfim
 * Date: 4/24/17
 * Time: 2:41 PM
 */

import com.jones.desktop.entity.Page;
import com.jones.desktop.util.PageEnum;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class JonesPreloader extends Preloader {

  private static final double WIDTH = 400;
  private static final double HEIGHT = 400;

  private Stage preloaderStage;
  private Scene scene;

  private ProgressBar progress = new ProgressBar();

  public JonesPreloader() {
  }

  @Override
  public void init() throws Exception {
    Platform.runLater(() -> {
      try {
        Pane splashScreen = new Page(PageEnum.SPLASH).getPane();
        progress = (ProgressBar) splashScreen.lookup("#progressBar");
        scene = new Scene(splashScreen, WIDTH, HEIGHT);
      } catch (IOException e) {
        e.printStackTrace();
        Label title = new Label("Loading, please wait...");
        title.setTextAlignment(TextAlignment.CENTER);
        progress = new ProgressBar(0);
        VBox root = new VBox(title, progress);
        root.setAlignment(Pos.CENTER);
        scene = new Scene(root, WIDTH, HEIGHT);
      }
    });
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.preloaderStage = primaryStage;

    // Set preloader scene and show stage.
    preloaderStage.setScene(scene);
    preloaderStage.show();
    preloaderStage.setTitle("Jones - Desktop");
  }

  @Override
  public void handleApplicationNotification(PreloaderNotification info) {
    // Handle application notification in this point (see MyApplication#init).
    if (info instanceof ProgressNotification) {
      progress.setProgress(((ProgressNotification) info).getProgress());
    }
  }

  @Override
  public void handleStateChangeNotification(StateChangeNotification info) {
    // Handle state change notifications.
    StateChangeNotification.Type type = info.getType();
    switch (type) {
      case BEFORE_START:
        // Called after JonesApplication#init and before MyApplication#start is called.
        preloaderStage.hide();
        break;
    }
  }

}
