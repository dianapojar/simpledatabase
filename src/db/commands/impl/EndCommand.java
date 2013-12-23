package db.commands.impl;

import db.data.SimpleDBData;

public class EndCommand extends Command {
    @Override
    public void execute(SimpleDBData data) {
        System.exit(0);
    }
}
