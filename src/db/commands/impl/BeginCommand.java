package db.commands.impl;

import db.data.SimpleDBData;
import db.data.TransactionData;

public class BeginCommand extends Command {
    @Override
    public void execute(SimpleDBData data) {
        data.beginTransaction();
    }
}
