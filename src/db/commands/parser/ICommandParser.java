package db.commands.parser;

import db.commands.CommandType;
import db.commands.impl.Command;

public interface ICommandParser {
    public Command getCommand(String rawCommand);
}
