package db;

import db.commands.impl.Command;
import db.commands.parser.ICommandParser;
import db.data.DataContainer;

/**
 * The database wrapper that receives a command and executes it
 */
public class Database {
    private DataContainer dataContainer = new DataContainer();
    private final ICommandParser parser;

    public Database(ICommandParser parser) {
        this.parser = parser;
    }

    public void executeCommand(String rawCommand) {
        Command command = parser.getCommand(rawCommand);
        command.execute(dataContainer);
    }
}
