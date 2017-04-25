package com.jones.desktop;

import com.jones.desktop.util.PageEnum;
import com.jones.desktop.util.SceneCreator;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.stage.Stage;

public class JonesApplication extends Application {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;

    // Just a counter to create some delay while showing preloader.
    private static final int COUNT_LIMIT = 50000;

    private static int stepCount = 1;

    // Used to demonstrate step couns.
    public static String STEP() {
        return stepCount++ + ". ";
    }

    private Stage applicationStage;

    public static void main(String[] args) {
        LauncherImpl.launchApplication(JonesApplication.class, JonesPreloader.class, args);
    }

    public JonesApplication() {
        // Constructor is called after BEFORE_LOAD.
    }

    @Override
    public void init() throws Exception {

        //do initializing work
        for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress/100));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        applicationStage = primaryStage;
        SceneCreator.renderPage(primaryStage, PageEnum.PROCESS_SETUP);
    }
}
