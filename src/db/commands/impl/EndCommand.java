package db.commands.impl;

import db.data.DatabaseContainer;

public class EndCommand extends Command {
    @Override
    public void execute(DatabaseContainer databaseContainer) {
        System.exit(0);
    }
}
