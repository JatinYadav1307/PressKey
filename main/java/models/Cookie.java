package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;

@Entity(value = "cookies", noClassnameStored = true)
public class Cookie {
    @Id
    private ObjectId objectId;
    @Reference
    private User relatesTo;
    private Date creationTime;

    public Cookie(User relatesTo, Date creationTime) {
        this.relatesTo = relatesTo;
        this.creationTime = creationTime;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public User getRelatesTo() {
        return relatesTo;
    }

    public void setRelatesTo(User relatesTo) {
        this.relatesTo = relatesTo;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
