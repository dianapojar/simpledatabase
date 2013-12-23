package db.commands.impl;

import db.data.DatabaseContainer;
import db.data.TransactionData;
import db.data.TransactionManager;

public class UnsetCommand extends Command {
    private String name;

    public UnsetCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(DatabaseContainer databaseContainer) {
        TransactionData currentData = databaseContainer.getData();
        TransactionManager transactionManager = databaseContainer.getTransactionManager();

        String keyValue = currentData.getKeyValue(name);
        if (keyValue == null) {
            //get value count from previous transactions
            String previousValue =  transactionManager.getMostRecentValueForKey(name);
            if (previousValue != null) {
                //decrement count
                Integer valueCount = currentData.getValueCount(previousValue);
                currentData.setValueCount(previousValue, valueCount - 1);
            }
        }

        //delete and mark key as deleted
        //TODO: update get to check if key is deleted
        currentData.unsetKey(name);
    }
}
