package db.commands.impl;

import db.data.DatabaseContainer;

public class BeginCommand implements Command {
    @Override
    public void execute(DatabaseContainer databaseContainer) {
        databaseContainer.updateDataToNewTransaction();
    }
}
