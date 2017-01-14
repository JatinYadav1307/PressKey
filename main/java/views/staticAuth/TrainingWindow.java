package views.staticAuth;

import controllers.staticAuth.TrainingWindowController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TrainingWindow {
    private TrainingWindowController trainingWindowController = new TrainingWindowController();
    private Stage trainingStage;

    public Stage getTrainingStage() {
        return trainingStage;
    }

    public void launch()
    {
        trainingStage =  new Stage();
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane, 600, 300);
//        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("css/staticAuth/TrainingWindowDesign.css");

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        Label trainingText = new Label("The quick brown fox jumps over the lazy dog  \n and dropped " +
                "my box with five dozen liquor jugs.");
        trainingText.setId("training-text");
        gridPane.add(trainingText, 0, 1);

        TextField trainingArea = new TextField();
        trainingArea.setId("training-area");
        gridPane.add(trainingArea, 0, 2);

        Button submitButton = new Button("Submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setPadding(new Insets(15, 0, 0, 0));
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        Hyperlink clearAll = new Hyperlink("Clear All");

        hbBtn.getChildren().add(clearAll);
        hbBtn.getChildren().add(submitButton);

        gridPane.add(hbBtn, 0, 3);

        // Logo Preview on Top
        Image image = new Image("images/logo.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(104);
        imageView.setFitWidth(114);
        HBox hboxLogo = new HBox(0);
        hboxLogo.setAlignment(Pos.CENTER);
        hboxLogo.getChildren().addAll(imageView);
        gridPane.add(hboxLogo, 0, 0);

        stackPane.getChildren().addAll(gridPane);

        trainingArea.setOnKeyPressed(event -> trainingWindowController.keyPressEvent(event, System.nanoTime()));
        trainingArea.setOnKeyReleased(event -> trainingWindowController.keyReleaseEvent(event, System.nanoTime()));
        submitButton.setOnAction(event -> trainingWindowController.submitButton(event, trainingArea));
        clearAll.setOnAction(event -> trainingWindowController.clearButton(event, trainingArea));

        trainingStage.setScene(scene);
        trainingStage.show();
    }
}
