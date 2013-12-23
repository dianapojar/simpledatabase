package db;

import db.commands.impl.Command;
import db.commands.parser.ICommandParser;
import db.data.SimpleDBData;


public class Database {
    private SimpleDBData data = new SimpleDBData();
    private final ICommandParser parser;

    public Database(ICommandParser parser) {
        this.parser = parser;
    }

    public void executeCommand(String rawCommand) {
        Command command = parser.getCommand(rawCommand);
        command.execute(data);
    }
}
