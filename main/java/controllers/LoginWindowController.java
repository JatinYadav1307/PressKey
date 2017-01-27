package controllers;

import com.sun.glass.ui.View;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Cookie;
import models.User;
import mongoConnection.Connection;
import mongoConnection.ConnectionHandler;
import org.mongodb.morphia.query.Query;
import views.SignupWindow;
import views.Universal;
import views.ViewHandler;

import java.time.Instant;
import java.util.Date;
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
                        .field("email").equal(email);
                List<User> result = query.asList();

                try {
                    if (result.get(0).getPassword().equals(password)) {
                        System.out.println("Login Pass!");
//                        Cookie currentCookie = new Cookie(result.get(0).getObjectId(), new Date());
//                        connection.getDatastore().save(currentCookie);
                        Universal.currentUser = result.get(0);
                        if (Universal.currentUser.getNextTrainingSession() != null) {
                            if (Universal.currentUser.getNextTrainingSession().before(Date.from(Instant.now()))) {
                                ViewHandler.loginWindow.getLoginStage().close();
                                ViewHandler.trainingWindow.launch();
                            } else {
                                // OTHER WINDOW IS TO BE USED HERE
                                ViewHandler.loginWindow.getLoginStage().close();
                            }
                        }
                        else
                        {
                            ViewHandler.loginWindow.getLoginStage().close();
                            ViewHandler.trainingWindow.launch();
                        }
                    } else {
                        System.out.println("Login failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("User not registered yet!");
                }
            } else {
                System.out.println("Enter valid details!");
            }
        }
    }

    public void signUpButtonAction(ActionEvent event) {
        ViewHandler.loginWindow.getLoginStage().hide();
        ViewHandler.signupWindow.launch();
        ViewHandler.signupWindow.getSignupStage().setOnCloseRequest(event1 -> {
            ViewHandler.loginWindow.getLoginStage().show();
        });
    }
}
