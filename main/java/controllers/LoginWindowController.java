package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import views.SignupWindow;

public class LoginWindowController {
    public void loginButtonAction(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hello There!");
        alert.setHeaderText("You clicked Login Button");
        alert.setContentText("Yes you've clicked it already, not don't!");
        alert.showAndWait();
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
