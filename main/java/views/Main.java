package views;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private SplashWindow splashWindow = new SplashWindow();

    @Override
    public void start(Stage primaryStage) throws Exception {
        splashWindow.splashScreen(primaryStage);
        System.out.println("Hello World to GIT!");
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
