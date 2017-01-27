package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import supportClass.KeyStrokeDataValue;

import java.util.Date;

@Entity(value = "training", noClassnameStored = true)
public class Training {
    @Id
    private ObjectId objectId;
    private ObjectId relatesTo;
    private Date timeStamp;
    private int sessionNumber;
    private KeyStrokeDataValue firstKey;
    private KeyStrokeDataValue secondKey;

    public Training(ObjectId relatesTo, Date timeStamp, int sessionNumber, KeyStrokeDataValue firstKey, KeyStrokeDataValue secondKey) {
        this.relatesTo = relatesTo;
        this.timeStamp = timeStamp;
        this.sessionNumber = sessionNumber;
        this.firstKey = firstKey;
        this.secondKey = secondKey;
    }

    public int getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(int sessionNumber) {
        this.sessionNumber = sessionNumber;
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

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
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

    public Training(ObjectId relatesTo, Date timeStamp, KeyStrokeDataValue firstKey, KeyStrokeDataValue secondKey) {

        this.relatesTo = relatesTo;
        this.timeStamp = timeStamp;
        this.firstKey = firstKey;
        this.secondKey = secondKey;
    }

    public Training() {

    }
}
