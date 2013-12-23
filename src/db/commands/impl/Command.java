package db.commands.impl;

import db.commands.CommandType;
import db.data.DatabaseContainer;

public abstract class Command {
    CommandType type;

    public abstract void execute(DatabaseContainer databaseContainer);
}
