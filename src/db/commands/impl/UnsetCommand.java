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

        //decrement old value count
        String oldValue = databaseContainer.getValueForKeyFromAllTransaction(name);
        databaseContainer.decrementValueCount(oldValue);
//        if (keyValue == null) {
//            //get value count from previous transactions
//            String previousValue =  transactionManager.getMostRecentValueForKey(name);
//            if (previousValue != null) {
//                //decrement count
//                Integer valueCount = currentData.getValueCount(previousValue);
//                if (valueCount == 0) {
//                    valueCount = transactionManager.getOccurrencesForValue(previousValue);
//                }
//                if (valueCount - 1 == 0) {
//                    //remove from count list
//                }
//                currentData.setValueCount(previousValue, valueCount - 1);
//            }
//        }

        //delete and mark key as deleted
        currentData.unsetKey(name);
    }
}
