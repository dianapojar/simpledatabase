package db.commands.impl;

import db.data.DatabaseContainer;
import db.data.TransactionData;
import db.data.TransactionManager;

public class GetCommand extends Command {
    private String name;

    public GetCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(DatabaseContainer container) {
        TransactionData currentData = container.getData();
        TransactionManager transactionManager = container.getTransactionManager();

        if (currentData.isKeyDeleted(name)) {
            System.out.println("NULL");
        } else {
            String value = currentData.getKeyValue(name);
            if (value == null) {
                //we must search if the value exist in previous transactions
                value = transactionManager.getMostRecentValueForKey(name);
            }

            if (value == null) {
                System.out.println("NULL");
            } else {
                System.out.println(value.toUpperCase());
            }
        }
    }
}
