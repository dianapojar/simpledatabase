package db.commands.impl;

import db.data.DatabaseContainer;
import db.data.TransactionData;

public class RollbackCommand implements Command {

    @Override
    public void execute(DatabaseContainer databaseContainer) {
        TransactionData data =  databaseContainer.getTransactionManager().rollback();
        if (data == null) {
            System.out.println("NO TRANSACTION");
        } else {
            databaseContainer.setData(data);
        }
    }
}
