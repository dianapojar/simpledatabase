package db.commands.impl;

import db.commands.CommandType;
import db.data.SimpleDBData;

public abstract class Command {
    CommandType type;

    public abstract void execute(SimpleDBData data);
}
