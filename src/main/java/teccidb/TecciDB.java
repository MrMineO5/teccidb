package teccidb;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
/**
 * TecciDB Main class.
 * Instantiate a TecciDB class if you have a project that needs a database.
 *
 *
 * */
public class TecciDB {
    public static JSONObject db;
    public static String projectID;
    public static ArrayList<Database> databases = new ArrayList<Database>();
    /**
     * Instantiate a new main database.
     * @param projectName Name of your project. This will also be your main database name.
     *
     * */
    public TecciDB(String projectName) {
        this.db = new JSONObject();
        this.projectID = UUID.randomUUID().toString();
        db.put("projectName", projectName);
        try (FileWriter file = new FileWriter(projectName + projectID + ".json")) {
            file.write(db.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void addDatabase(Database database) {
        databases.add(database);
    }
}