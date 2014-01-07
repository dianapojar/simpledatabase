package db;

import db.commands.parser.ICommandParser;
import db.commands.parser.SimpleCommandParser;
import db.reader.FileReader;
import db.reader.IReader;
import org.junit.*;

public class SimpleDatabaseTest {
    Database database;

    @Before
    public void setUp() throws Exception {
        ICommandParser parser = new SimpleCommandParser();
        database = new Database(parser);
    }

}
