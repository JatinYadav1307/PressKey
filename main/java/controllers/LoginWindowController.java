package controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.User;
import mongoConnection.Connection;
import mongoConnection.ConnectionHandler;
import org.mongodb.morphia.query.Query;
import views.SignupWindow;

import java.util.ArrayList;
import java.util.List;

public class LoginWindowController {
    private Connection connection;

    public void loginButtonAction(ActionEvent event, GridPane gridPane)
    {
        connection = ConnectionHandler.connection;
        ArrayList<String> objectData = new ArrayList<>(0);
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField)
                objectData.add(((TextField) node).getText());
            if (node instanceof PasswordField)
                objectData.add(((PasswordField) node).getText());
        }

        // TODO: Validation for checking user login information
        Query<User> query = connection.getDatastore().createQuery(User.class)
                .field("userName").equal(objectData.get(0));
        List<User> result = query.asList();

        try {
            if (result.get(0).getPassword().equals(objectData.get(1))) {
                System.out.println("Login Pass!");
            } else {
                System.out.println("Login failed!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void signUpButtonAction(ActionEvent event, Stage inputStage)
    {
        SignupWindow signupWindow = new SignupWindow();
        inputStage.hide();
        signupWindow.launch();
        signupWindow.getSignupStage().setOnCloseRequest(event1 -> {
            inputStage.show();
        });
    }
}
