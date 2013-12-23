package db.commands.impl;

import db.data.SimpleDBData;

public class CommitCommand extends Command {
    @Override
    public void execute(SimpleDBData data) {
        if (!data.commitTransaction()) {
            System.out.println("NO TRANSACTION!");
        }
    }
}
