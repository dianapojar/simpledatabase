package db.commands.impl;

import db.data.DataContainer;

public interface Command {
    public abstract void execute(DataContainer dataContainer);
}
