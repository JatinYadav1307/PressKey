package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import supportClass.KeyStrokeDataValue;

import java.util.ArrayList;

@Entity(value = "keystrokes", noClassnameStored = true)
public class Keystroke {
    @Id
    private ObjectId objectId;
    private ObjectId relatesTo;
    private ArrayList<KeyStrokeDataValue> keyStrokeDataValues;

    public Keystroke() {
    }

    public Keystroke(ObjectId relatesTo, ArrayList<KeyStrokeDataValue> keyStrokeDataValues) {
        this.relatesTo = relatesTo;
        this.keyStrokeDataValues = keyStrokeDataValues;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ObjectId getRelatesTo() {

        return relatesTo;
    }

    public void setRelatesTo(ObjectId relatesTo) {
        this.relatesTo = relatesTo;
    }

    public ArrayList<KeyStrokeDataValue> getKeyStrokeDataValues() {
        return keyStrokeDataValues;
    }

    public void setKeyStrokeDataValues(ArrayList<KeyStrokeDataValue> keyStrokeDataValues) {
        this.keyStrokeDataValues = keyStrokeDataValues;
    }
}
