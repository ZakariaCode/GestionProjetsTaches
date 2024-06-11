package persistance;


import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class connexion {

    private static connexion instance = null;
    private final String mongoDB_URI ="mongodb://localhost:27017/";
    private final String DatabaseName="ToDoList";
    private MongoClient mongoClient;
    private MongoDatabase database;

    private connexion(){
        mongoClient=new MongoClient(new MongoClientURI(mongoDB_URI));
        database=mongoClient.getDatabase(DatabaseName);
    }

    public static connexion getInstance() {
        if (instance == null) {
           instance = new connexion();
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void closeConnection() {
        mongoClient.close();
    }
    
    public MongoCollection<Document> GetCollection(String Collection){
        MongoCollection <Document> retrievedCollection = this.getDatabase().getCollection(Collection);
        return retrievedCollection;
    }
}
