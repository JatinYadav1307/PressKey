package controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import models.User;
import mongoConnection.Connection;

import java.util.ArrayList;


public class SignupWindowController {

    private Connection connection;

    private Connection initializeDatabase(String databaseToConnect)
    {
        return new Connection(databaseToConnect);
    }

    public void clearButtonAction(ActionEvent event, GridPane gridPane)
    {
        for (Node node :
                gridPane.getChildren()) {
            if (node instanceof TextField)
            {
                ((TextField) node).setText("");
            }
            if (node instanceof PasswordField)
            {
                ((PasswordField) node).setText("");
            }
        }
        gridPane.getChildren().get(1).requestFocus();
    }

    public void signUpButtonAction(ActionEvent event, GridPane gridPane)
    {
        Thread databaseInit = new Thread(() -> connection = initializeDatabase("pressKeyUsers"));
        databaseInit.start();
        try {
            databaseInit.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // TODO: Validation for Sign Up and make Email the primary key for this
        ArrayList<String> objectData = new ArrayList<>(0);
        for (Node node :
                gridPane.getChildren()) {
            if (node instanceof TextField)
            {
                objectData.add(((TextField) node).getText());
            }
            if (node instanceof PasswordField)
            {
                objectData.add(((PasswordField) node).getText());
            }
        }
        User newUser = new User(objectData.get(0), objectData.get(1), objectData.get(2));
        Thread databaseFunctions = new Thread(() -> {
            connection.getDatastore().save(newUser);
            connection.getMongoClient().close();
        });

        databaseFunctions.start();
        try {
            databaseFunctions.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clearButtonAction(event, gridPane);
        System.out.println("DONE");
    }
}
