package controllers.staticAuth;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import models.Cookie;
import models.Keystroke;
import models.User;
import mongoConnection.Connection;
import mongoConnection.ConnectionHandler;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import supportClass.KeyStrokeDataValue;
import views.Universal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TrainingWindowController {
    private ArrayList<Long> pressTimes = new ArrayList<>(0);
    private ArrayList<Long> releaseTimes = new ArrayList<>(0);
    private ArrayList<String> characters = new ArrayList<>(0);
    private ArrayList<Keystroke> keystrokesData = new ArrayList<>(0);
    private ArrayList<KeyStrokeDataValue> keyStrokeDataValues = new ArrayList<>(0);

    public void keyPressEvent(KeyEvent event, long pressTime) {
        pressTimes.add(pressTime);
    }

    public void keyReleaseEvent(KeyEvent event, long releaseTime) {
        characters.add(event.getText());
        releaseTimes.add(releaseTime);
    }

    private void compileKeyInformation()
    {
        Iterator<Long> pressIterator = pressTimes.iterator();
        Iterator<Long> releaseIterator = releaseTimes.iterator();
        Iterator<String> charsIterator = characters.iterator();

        while (pressIterator.hasNext() && releaseIterator.hasNext() && charsIterator.hasNext())
        {
            keyStrokeDataValues.add(
                    new KeyStrokeDataValue(charsIterator.next(), pressIterator.next(), releaseIterator.next())
            );
        }

        for (int i = 0; i < keyStrokeDataValues.size()-1; i++) {
            keystrokesData.add(
                    new Keystroke(
                            Universal.currentUser.getObjectId(),
                            keyStrokeDataValues.get(i),
                            keyStrokeDataValues.get(i+1)
                    )
            );
        }
    }

    public void submitButton(ActionEvent event, TextField textField) {
        Connection connection = ConnectionHandler.connection;
        compileKeyInformation();
        textField.clear();
//        Keystroke newData = new Keystroke(Universal.currentUser.getObjectId(), keyStrokeDataValues);
//        connection.getDatastore().save(newData);

        for (Keystroke newData : keystrokesData)
            connection.getDatastore().save(newData);
        System.out.println("Saved!");

//        -------------------- Coookie TESTING -----------------------------
//        Query<Cookie> query = connection.getDatastore().createQuery(Cookie.class)
//                .field("relatesTo").equal(Universal.currentUser.getObjectId());
//        List<Cookie> results = query.asList();
//        System.out.println(results.get(0).getRelatesTo());
    }

    public void clearButton(ActionEvent event, TextField textField) {
        textField.clear();
    }
}
