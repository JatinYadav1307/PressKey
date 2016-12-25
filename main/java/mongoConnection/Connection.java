package mongoConnection;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import models.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class Connection {
    private Morphia morphia;
    private Datastore datastore;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public Connection(String dataBaseToConnect)
    {
        mongoClient = new MongoClient("localhost", 27017);
        mongoDatabase = mongoClient.getDatabase(dataBaseToConnect);
        morphia = new Morphia();
        morphia.mapPackage("models");
        datastore = morphia.createDatastore(mongoClient, mongoDatabase.getName());
        datastore.ensureIndexes();
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public void setMongoDatabase(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Morphia getMorphia() {
        return morphia;
    }

    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }
}
