package controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import mongoConnection.Connection;
import mongoConnection.ConnectionHandler;
import org.mongodb.morphia.query.Query;
import views.SignupWindow;

import java.util.HashMap;
import java.util.List;

public class LoginWindowController {

    private Boolean validationsOn = true;

    public void loginButtonAction(ActionEvent event, HashMap<String, Node> userFields) {
        Connection connection = ConnectionHandler.connection;
        String email = ((TextField) userFields.get("email")).getText();
        String password = ((TextField) userFields.get("password")).getText();

        if (validationsOn) {
            Boolean passwordIsNull = password.isEmpty();
            Boolean usernameIsNull = email.isEmpty();
            // TODO: Validation for checking user login information
            if (!passwordIsNull && !usernameIsNull) {
                Query<User> query = connection.getDatastore().createQuery(User.class)
                        .field("emailAddress").equal(email);
                List<User> result = query.asList();

                try {
                    if (result.get(0).getPassword().equals(password)) {
                        System.out.println("Login Pass!");
                    } else {
                        System.out.println("Login failed!");
                    }
                } catch (Exception e) {
                    System.out.println("User not registered yet!");
                }
            } else {
                System.out.println("Enter valid details!");
            }
        }
    }

    public void signUpButtonAction(ActionEvent event, Stage inputStage) {
        SignupWindow signupWindow = new SignupWindow();
        inputStage.hide();
        signupWindow.launch();
        signupWindow.getSignupStage().setOnCloseRequest(event1 -> {
            inputStage.show();
        });
    }
}
