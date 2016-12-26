package controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.User;
import mongoConnection.Connection;
import mongoConnection.ConnectionHandler;

import java.util.HashMap;
import java.util.Map;


public class SignupWindowController {

    private Boolean validationsOn = true;

    private static final String emailRegex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+" +
            "(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public void clearButtonAction(ActionEvent event, HashMap<String, Node> userFields) {
        for (Map.Entry<String, Node> node : userFields.entrySet()) {
            Node currentNode = node.getValue();
            if (currentNode instanceof TextField)
                ((TextField) currentNode).setText("");
            if (currentNode instanceof PasswordField)
                ((PasswordField) currentNode).setText("");
        }
    }

    public void signUpButtonAction(ActionEvent event, HashMap<String, Node> userFields) {
        Connection connection = ConnectionHandler.connection;
        String email = ((TextField) userFields.get("email")).getText();
        String username = ((TextField) userFields.get("username")).getText();
        String password = ((TextField) userFields.get("password")).getText();
        // TODO: Validation for Sign Up and make Email the primary key for this
        if (validationsOn) {
            Boolean emailIsValid = email.matches(emailRegex);
            Boolean passwordIsNull = password.isEmpty();
            Boolean usernameIsNull = username.isEmpty();
            if (emailIsValid && !passwordIsNull && !usernameIsNull) {
                User newUser = new User(email, username, password);
                connection.getDatastore().save(newUser);
                clearButtonAction(event, userFields);
                System.out.println("Sign Up Done!");
            } else {
                System.out.println("Invalid details!");
            }
        } else {
            User newUser = new User(email, username, password);
            connection.getDatastore().save(newUser);
            clearButtonAction(event, userFields);
            System.out.println("Sign Up Done!");
        }
    }
}
