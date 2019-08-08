package teccidb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

public class TestTecciDB {
	
	@Test
	public void testTecciDB() {
		TecciDB db = new TecciDB("test");
		String projectId = db.projectID;
		Database database = db.createDatabase("test");
		database.set("test", "Hello_World");
		String fname = db.saveToFile();
		
		TecciDB db2 = new TecciDB(new File(fname));
		assertEquals(projectId, db2.projectID);
		Database database2 = db.getDatabase("test");
		assertEquals(database2.get("test"), "Hello_World");
	}
}
