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
    private KeyStrokeDataValue firstKey;
    private KeyStrokeDataValue secondKey;

    public Keystroke() {
    }

    public Keystroke(ObjectId relatesTo, KeyStrokeDataValue firstKey, KeyStrokeDataValue secondKey) {
        this.relatesTo = relatesTo;
        this.firstKey = firstKey;
        this.secondKey = secondKey;
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

    public KeyStrokeDataValue getFirstKey() {
        return firstKey;
    }

    public void setFirstKey(KeyStrokeDataValue firstKey) {
        this.firstKey = firstKey;
    }

    public KeyStrokeDataValue getSecondKey() {
        return secondKey;
    }

    public void setSecondKey(KeyStrokeDataValue secondKey) {
        this.secondKey = secondKey;
    }
}
