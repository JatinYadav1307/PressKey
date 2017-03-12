package controllers.staticAuth;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import models.Training;
import models.User;
import mongoConnection.Connection;
import mongoConnection.ConnectionHandler;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import supportClass.KeyStrokeDataValue;
import views.Universal;
import views.ViewHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class TrainingWindowController {
    private int submitButtonCount = 1;
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
                .set("nextTrainingSession", Date.from(Instant.now().plusSeconds(15 * Universal.currentUser.getNextSessionNumber())));
        // UPDATE THE MULTIPLIER TO 150 for 15 MINUTES gap between training!!
        connection.getDatastore().update(query, updateTrainingNumber);
        connection.getDatastore().update(query, updateLastTrainingSession);
        connection.getDatastore().update(query, updateNextTrainingSession);
        for (Training newData : trainingData)
            connection.getDatastore().save(newData);
        System.out.println("Saved!");
        submitButtonCount++;
        if (submitButtonCount > 2)
        {
            ViewHandler.trainingWindow.getTrainingStage().close();
            submitButtonCount = 1;
        }
    }

    public void clearButton(ActionEvent event, TextField textField) {
        textField.clear();
    }
}
