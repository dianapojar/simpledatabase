package db.commands.impl;

import db.data.SimpleDBData;

public class InvalidCommand extends Command {
    private String errorMessage;

    public InvalidCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void execute(SimpleDBData data) {
        System.out.println(errorMessage);
    }
}
