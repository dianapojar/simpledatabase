package db.commands.impl;

import db.data.SimpleDBData;

public class UnsetCommand extends Command {
    private String name;

    public UnsetCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(SimpleDBData data) {
        data.unSetData(name);
    }
}
