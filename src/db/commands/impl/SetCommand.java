package db.commands.impl;

import db.data.SimpleDBData;

public class SetCommand extends Command {
    private String name;
    private String value;

    public SetCommand(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void execute(SimpleDBData data) {
        data.setData(name, value);
    }
}
