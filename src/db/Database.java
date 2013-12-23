package db;

import db.commands.impl.Command;
import db.commands.parser.ICommandParser;
import db.data.DatabaseContainer;


public class Database {
    private DatabaseContainer databaseContainer = new DatabaseContainer();
    private final ICommandParser parser;

    public Database(ICommandParser parser) {
        this.parser = parser;
    }

    public void executeCommand(String rawCommand) {
        Command command = parser.getCommand(rawCommand);
        command.execute(databaseContainer);
    }
}
