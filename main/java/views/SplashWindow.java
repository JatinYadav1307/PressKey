package views;

import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mongoConnection.ConnectionHandler;

class SplashWindow {

    private LoginWindow loginWindow = new LoginWindow();


    void splashScreen(Stage splashStage)
    {
        splashStage.initStyle(StageStyle.TRANSPARENT);
        splashStage.setTitle("PressKey");
        StackPane stackPane = new StackPane();

        Scene scene = new Scene(stackPane, 610, 231);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().addAll("/css/SplashWindowDesign.css");
        splashStage.setScene(scene);
        splashStage.show();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        splashStage.setX((screenBounds.getWidth() - splashStage.getWidth()) / 2);
        splashStage.setY((screenBounds.getHeight() - splashStage.getHeight()) / 2);

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try{Thread.sleep(1500);}
                catch (InterruptedException e) {e.printStackTrace();}
                return null;
            }
        };

        Thread connection = new Thread(() -> ConnectionHandler.generateConnection());
        connection.start();

        sleeper.setOnSucceeded(event -> {
            splashStage.close();
            loginWindow.launch();
        });
        new Thread(sleeper).start();
    }
}
