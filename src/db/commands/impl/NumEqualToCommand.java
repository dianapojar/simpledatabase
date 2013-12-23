package db.commands.impl;

import db.data.SimpleDBData;

public class NumEqualToCommand extends Command {
    private String value;

    public NumEqualToCommand(String value) {
        this.value = value;
    }

    @Override
    public void execute(SimpleDBData data) {
        System.out.println(data.getValueCountData(value));
    }
}
