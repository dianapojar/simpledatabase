package db.commands.impl;

import db.data.DatabaseContainer;

public class InvalidCommand implements Command {
    private String errorMessage;

    public InvalidCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void execute(DatabaseContainer databaseContainer) {
        System.out.println(errorMessage);
    }
}
