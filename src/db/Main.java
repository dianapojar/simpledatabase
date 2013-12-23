package db;

import db.commands.parser.ICommandParser;
import db.commands.parser.SimpleCommandParser;
import db.reader.IReader;
import db.reader.StdinReader;

public class Main {
    public static void main(String[] args) {
        IReader reader = new StdinReader();
        ICommandParser parser = new SimpleCommandParser();

        //injected parser into database, this way we have support for implementing a different command structure
        Database database = new Database(parser);

        while (true) {
            String rawCommand = reader.getRawCommand();
            database.executeCommand(rawCommand);
        }
    }
}
