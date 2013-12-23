package db.commands.impl;

import db.data.DatabaseContainer;
import db.data.TransactionData;

public class CommitCommand extends Command {
    @Override
    public void execute(DatabaseContainer databaseContainer) {
        Boolean isCommitOk =   databaseContainer.getTransactionManager().commit();
        if (!isCommitOk) {
            System.out.println("NO TRANSACTION");
        }
    }
}
