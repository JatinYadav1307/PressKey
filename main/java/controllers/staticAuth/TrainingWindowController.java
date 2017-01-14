package controllers.staticAuth;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import supportClass.KeyStrokeDataValue;

import java.util.ArrayList;
import java.util.Iterator;

public class TrainingWindowController {
    private ArrayList<Long> pressTimes = new ArrayList<>(0);
    private ArrayList<Long> releaseTimes = new ArrayList<>(0);
    private ArrayList<String> characters = new ArrayList<>(0);
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
    }

    public void submitButton(ActionEvent event, TextField textField) {
        compileKeyInformation();
        textField.clear();
        System.out.println(keyStrokeDataValues);
    }

    public void clearButton(ActionEvent event, TextField textField) {
        textField.clear();
    }
}
