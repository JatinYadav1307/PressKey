package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

@Entity(value = "cookies", noClassnameStored = true)
public class Cookie {
    @Id
    private ObjectId objectId;
    private ObjectId relatesTo;
    private Date creationTime;

    public Cookie() {
    }

    public Cookie(ObjectId relatesTo, Date creationTime) {
        this.relatesTo = relatesTo;
        this.creationTime = creationTime;
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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
