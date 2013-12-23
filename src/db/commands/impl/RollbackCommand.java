package db.commands.impl;

import db.data.SimpleDBData;

public class RollbackCommand extends Command {
    @Override
    public void execute(SimpleDBData data) {
        if (!data.rollbackTransaction()) {
            System.out.println("NO TRANSACTION");
        }
    }
}
