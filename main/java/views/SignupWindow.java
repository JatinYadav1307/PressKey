package views;

import controllers.SignupWindowController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SignupWindow {
    private Stage signupStage;
    private Scene mainScene;
    private SignupWindowController signupWindowController = new SignupWindowController();

    public Stage getSignupStage() {
        return signupStage;
    }

    public void setSignupStage(Stage signupStage) {
        this.signupStage = signupStage;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void launch() {
        signupStage = new Stage();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);
        mainScene = new Scene(gridPane, 610, 400);
//        signupStage.initStyle(StageStyle.TRANSPARENT);
        mainScene.setFill(Color.TRANSPARENT);
        mainScene.getStylesheets().add("css/SignupWindowDesign.css");

        Label emailLabel = new Label("Email");
        Label userNameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");
        Button signupButton = new Button("Sign Up");
        Button clearButton = new Button("Clear All");
        HBox hboxButtons = new HBox(10);
        hboxButtons.setAlignment(Pos.BASELINE_RIGHT);
        TextField emailText = new TextField();
        TextField userNameText = new TextField();
        PasswordField passwordText = new PasswordField();
        Tooltip emailTooltip = new Tooltip("Enter a valid email address!");
        emailText.setTooltip(emailTooltip);

        gridPane.add(emailLabel, 0, 1);
        gridPane.add(emailText, 0, 2);
        gridPane.add(userNameLabel, 0, 3);
        gridPane.add(userNameText, 0, 4);
        gridPane.add(passwordLabel, 0, 5);
        gridPane.add(passwordText, 0, 6);
        hboxButtons.getChildren().addAll(clearButton, signupButton);
        gridPane.add(hboxButtons, 0, 8);

        // Logo Preview on Top
        URL url = getClass().getResource("../images/logo.png");
        String img = "";
        File file = new File(url.getPath());
        try {
            img = file.toURI().toURL().toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        Image image = new Image(img);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(104);
        imageView.setFitWidth(114);
        HBox hboxLogo = new HBox(0);
        hboxLogo.setAlignment(Pos.CENTER);
        hboxLogo.getChildren().addAll(imageView);
        gridPane.add(hboxLogo, 0, 0);

        HashMap<String, Node> userFields = new HashMap<>();
        userFields.put("email", emailText);
        userFields.put("username", userNameText);
        userFields.put("password", passwordText);

        clearButton.setOnAction(event -> signupWindowController.clearButtonAction(event, userFields));
        signupButton.setOnAction(event -> signupWindowController.signUpButtonAction(event, userFields));

        signupStage.setScene(mainScene);
        signupStage.show();
    }
}
