package views;

import controllers.LoginWindowController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class LoginWindow{

    private LoginWindowController loginWindowController = new LoginWindowController();

    public void launch()
    {

        // Initialize Items
        Stage loginStage = new Stage();

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
        Label userNameLbl = new Label("Username");
        userNameLbl.setId("username-label");
        TextField userNameTxt = new TextField();

        Label passwordLbl = new Label("Password");
        passwordLbl.setId("password-label");
        PasswordField passwordTxt = new PasswordField();

        Button submitBtn = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setPadding(new Insets(15, 0, 0, 0));
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);

        Hyperlink signUp = new Hyperlink("Sign Up!");
        hbBtn.getChildren().add(signUp);


        // Adding items to the pane
        gridPane.add(userNameLbl, 0, 1);
        gridPane.add(userNameTxt, 0, 2);
        gridPane.add(passwordLbl, 0, 3);
        gridPane.add(passwordTxt, 0, 4);

        hbBtn.getChildren().add(submitBtn);
        gridPane.add(hbBtn, 0, 6);

        // Logo Preview on Top
        URL url = getClass().getResource("../images/logo.png");
        String img = "";
        File file =  new File(url.getPath());
        try {
            img = file.toURI().toURL().toString();
        }
        catch (Exception e)
        {
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

        stackPane.getChildren().addAll(gridPane);
        submitBtn.setOnAction(event -> loginWindowController.loginButtonAction(event));
        signUp.setOnAction(event -> loginWindowController.signUpButtonAction(event, loginStage));

        loginStage.setScene(scene);
        loginStage.show();
    }
}
