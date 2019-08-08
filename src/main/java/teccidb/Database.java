package teccidb;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Database {
	JSONObject db;
	public int size = 0;

	public Database(String databaseName) {
		this.db = new JSONObject();
		db.put("databaseName", databaseName);
	}
	public Database(File file) {
		try (FileReader reader = new FileReader(file)) {
			JSONParser parser = new JSONParser();
			this.db = (JSONObject) parser.parse(reader);
			reader.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
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

	public void saveToFile(String projectId) {
		try (FileWriter file = new FileWriter(db.get("databaseName") + "_" + projectId + ".json")) {
			file.write(db.toJSONString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}