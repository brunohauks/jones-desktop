package com.jones.desktop.util;

/**
 * Utility to have all scenes resources in one place and to reuse the resource loading code
 *
 * User: bbonfim
 * Date: 4/22/17
 * Time: 9:45 AM
 */
public enum PageEnum {
  MAIN ("scenes/main/main.fxml"),
  SPLASH ("scenes/splash/SplashScreen.fxml"),
  PROCESS_SETUP ("scenes/process/Process.fxml"),
  PROCESS_DETAILS ("scenes/process/ProcessDetails.fxml");

  private String location;

  PageEnum(String resourceLocation) {
    this.location = resourceLocation;
  }

  public String getLocation() {
    return this.location;
  }
}
