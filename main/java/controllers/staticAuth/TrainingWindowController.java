package controllers.staticAuth;

import com.mongodb.operation.UpdateOperation;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import models.Cookie;
import models.Keystroke;
import models.Training;
import models.User;
import mongoConnection.Connection;
import mongoConnection.ConnectionHandler;
import org.joda.time.DateTime;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import supportClass.KeyStrokeDataValue;
import views.Universal;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TrainingWindowController {
    private ArrayList<Long> pressTimes = new ArrayList<>(0);
    private ArrayList<Long> releaseTimes = new ArrayList<>(0);
    private ArrayList<String> characters = new ArrayList<>(0);
    private ArrayList<Training> trainingData = new ArrayList<>(0);
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
        Date currentTime = Date.from(Instant.now());

        for (int i = 0; i < keyStrokeDataValues.size()-1; i++) {
            trainingData.add(
                    new Training(
                            Universal.currentUser.getObjectId(),
                            currentTime,
                            Universal.currentUser.getNextSessionNumber(),
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

        Query<User> query = connection.getDatastore().createQuery(User.class)
                .field("_id").equal(Universal.currentUser.getObjectId());
        UpdateOperations<User> updateTrainingNumber =  connection.getDatastore().createUpdateOperations(User.class)
                .set("nextSessionNumber", Universal.currentUser.getNextSessionNumber() + 1);
        UpdateOperations<User> updateLastTrainingSession =  connection.getDatastore().createUpdateOperations(User.class)
                .set("lastTrainingSession", Date.from(Instant.now()));
        UpdateOperations<User> updateNextTrainingSession =  connection.getDatastore().createUpdateOperations(User.class)
                .set("nextTrainingSession", Date.from(Instant.now().plusSeconds(150 * Universal.currentUser.getNextSessionNumber())));

        connection.getDatastore().update(query, updateTrainingNumber);
        connection.getDatastore().update(query, updateLastTrainingSession);
        connection.getDatastore().update(query, updateNextTrainingSession);
        for (Training newData : trainingData)
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
