package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

import java.util.Date;

@Entity(value = "users", noClassnameStored = true)
public class User {
    @Id
    private ObjectId objectId;
    private String username;
    @Indexed(value= IndexDirection.ASC, name="email", unique=true)
    private String email;
    private String password;
    private Date lastTrainingSession;
    private Date nextTrainingSession;
    private int nextSessionNumber;

    public Date getLastTrainingSession() {
        return lastTrainingSession;
    }

    public void setLastTrainingSession(Date lastTrainingSession) {
        this.lastTrainingSession = lastTrainingSession;
    }

    public Date getNextTrainingSession() {
        return nextTrainingSession;
    }

    public void setNextTrainingSession(Date nextTrainingSession) {
        this.nextTrainingSession = nextTrainingSession;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public User(String email, String username, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nextSessionNumber = 1;
        this.lastTrainingSession = null;
        this.nextTrainingSession = null;
    }

    public int getNextSessionNumber() {
        return nextSessionNumber;
    }

    public void setNextSessionNumber(int nextSessionNumber) {
        this.nextSessionNumber = nextSessionNumber;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {

    }
}
