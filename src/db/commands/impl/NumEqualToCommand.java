package db.commands.impl;

import db.data.DatabaseContainer;
import db.data.TransactionData;
import db.data.TransactionManager;

public class NumEqualToCommand extends Command {
    private String value;

    public NumEqualToCommand(String value) {
        this.value = value;
    }

//    @Override
//    public void execute(SimpleDBData data) {
//        System.out.println(data.getValueCountData(value));
//    }


    @Override
    public void execute(DatabaseContainer databaseContainer) {
        TransactionData currentData = databaseContainer.getData();
        TransactionManager transactionManager = databaseContainer.getTransactionManager();

        Integer currentCount = currentData.getValueCount(value);
        if (currentCount == null) {
            currentCount = transactionManager.getOccurrencesForValue(value);
        }

        System.out.println(currentCount);
    }
}
