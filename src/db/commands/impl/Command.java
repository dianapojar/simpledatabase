package db.commands.impl;

import db.commands.CommandType;
import db.data.DatabaseContainer;

public interface Command {
    public abstract void execute(DatabaseContainer databaseContainer);
}
