package db.commands.impl;

import db.data.DatabaseContainer;

public class CommitCommand implements Command {
    @Override
    public void execute(DatabaseContainer databaseContainer) {
        Boolean isCommitOk = databaseContainer.commit();
        if (!isCommitOk) {
            System.out.println("NO TRANSACTION");
        }
    }
}
