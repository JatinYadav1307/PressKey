package views;

import controllers.LoginWindowController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

public class LoginWindow {

    private LoginWindowController loginWindowController = new LoginWindowController();
    private Stage loginStage;

    public Stage getLoginStage() {
        return loginStage;
    }

    public void launch() {

        // Initialize Items
        loginStage = new Stage();

        // Stacking Pane
        StackPane stackPane = new StackPane();
        // TODO: Uncomment this once done with the Login UI
//        loginStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(stackPane, 610, 350);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("css/LoginWindowDesign.css");

        // GridPane with overlay items
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Items in GridPane for LoginWindow
        Label emailLabel = new Label("Email");
        emailLabel.setId("username-label");
        TextField emailText = new TextField();

        Label passwordLabel = new Label("Password");
        passwordLabel.setId("password-label");
        PasswordField passwordText = new PasswordField();

        Button submitButton = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setPadding(new Insets(15, 0, 0, 0));
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);

        Hyperlink signUp = new Hyperlink("Sign Up!");
        hbBtn.getChildren().add(signUp);


        // Adding items to the pane
        gridPane.add(emailLabel, 0, 1);
        gridPane.add(emailText, 0, 2);
        gridPane.add(passwordLabel, 0, 3);
        gridPane.add(passwordText, 0, 4);

        hbBtn.getChildren().add(submitButton);
        gridPane.add(hbBtn, 0, 6);

        // Logo Preview on Top

        Image image = new Image("images/logo.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(104);
        imageView.setFitWidth(114);
        HBox hboxLogo = new HBox(0);
        hboxLogo.setAlignment(Pos.CENTER);
        hboxLogo.getChildren().addAll(imageView);
        gridPane.add(hboxLogo, 0, 0);

        HashMap<String, Node> userFields = new HashMap<>();
        userFields.put("email", emailText);
        userFields.put("password", passwordText);

        stackPane.getChildren().addAll(gridPane);
        submitButton.setOnAction(event -> loginWindowController.loginButtonAction(event, userFields));
        signUp.setOnAction(event -> loginWindowController.signUpButtonAction(event));

        loginStage.setScene(scene);
        loginStage.show();
    }
}
