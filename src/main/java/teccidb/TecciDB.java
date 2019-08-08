package teccidb;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * TecciDB Main class. Instantiate a TecciDB class if you have a project that
 * needs a database.
 */
public class TecciDB {
	public JSONObject db;
	public String projectID;
	public Map<String, Database> databases = new HashMap<String, Database>();

	/**
	 * Instantiate a new main database.
	 * 
	 * @param projectName Name of your project. This will also be your main database
	 *                    name.
	 */
	public TecciDB(String projectName) {
		this.db = new JSONObject();
		this.projectID = UUID.randomUUID().toString();
		db.put("projectName", projectName);
		db.put("projectId", projectID);
		saveToFile();
	}
	public TecciDB(File file) {
		try (FileReader reader = new FileReader(file)) {
			JSONParser parser = new JSONParser();
			this.db = (JSONObject) parser.parse(reader);
			this.projectID = (String) db.get("projectId");
			JSONArray dbNames = (JSONArray) db.get("databases");
			for (Object str : dbNames) {
				databases.put((String) str, loadDatabase(str + "_" + projectID + ".json"));
			}
			reader.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Database createDatabase(String name) {
		Database db = new Database(name);
		db.saveToFile(projectID);
		addDatabase(name, db);
		return db;
	}
	
	public Database getDatabase(String name) {
		if (databases.containsKey(name)) {
			return databases.get(name);
		}
		return null; // Should exception be thrown instead?
	}
	
	public Database loadDatabase(String fileName) {
		return new Database(new File(fileName));
	}

	public void addDatabase(String name, Database database) {
		databases.put(name, database);
		db.put("databases", new JSONArray(databases.entrySet()));
	}
	
	public String saveToFile() {
		try (FileWriter file = new FileWriter(db.get("projectName") + "_" + projectID + ".json")) {
			file.write(db.toJSONString());
			file.flush();
			return db.get("projectName") + "_" + projectID + ".json";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}