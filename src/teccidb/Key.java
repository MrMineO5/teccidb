package teccidb;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Database {
    JSONObject maindb = TecciDB.db;
    JSONObject db;
    public int size = 0;
    public Database(String databaseName) {
        this.db = new JSONObject();
        db.put("databaseName", databaseName);
        try (FileWriter file = new FileWriter(databaseName + "_" + TecciDB.projectID + ".json")) {
            file.write(db.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TecciDB.addDatabase(this);
    }
    public Object get(Object dataName) {
        return db.get(dataName);
    }
    public void set(Key dataName, Object variable) {
        db.put(dataName, variable);
        size += 1;
    }
    public void set(String dataName, Object variable) {
        set(new Key(dataName), variable);
    }
}
