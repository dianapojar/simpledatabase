package db.commands.impl;

import db.data.DatabaseContainer;
import db.data.TransactionData;
import db.data.TransactionManager;

public class SetCommand extends Command {
    private String name;
    private String value;

    public SetCommand(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void execute(DatabaseContainer container) {
        TransactionData currentData = container.getData();
        TransactionManager transactionManager = container.getTransactionManager();

        //verify if the key that is being added exists in the previous
        Integer occurrences = 1;

        //TODO: make method in container - getValueForKey()
        String oldValue = currentData.getKeyValue(name);
        if (oldValue == null) {
            oldValue = transactionManager.getMostRecentValueForKey(name);
        }

        //TODO: method for decrementCount and set OLD_COUNT
        if (oldValue != null) {
            Integer oldOccurrenceCount = currentData.getValueCount(oldValue);
            if (oldOccurrenceCount == 0) {
                oldOccurrenceCount = transactionManager.getOccurrencesForValue(oldValue);
            }
            //save valueCount for oldValue in current transaction as cache
            currentData.setValueCount(oldValue, oldOccurrenceCount - 1);
        }

        //TODO: increment currentCount
        occurrences = occurrences + currentData.getValueCount(value);
        if (occurrences == 0) {
            //find if any occurrences exist in the previous transactions
            occurrences = occurrences + transactionManager.getOccurrencesForValue(value);
        }

        //set the new value to the current transaction
        currentData.setValueCount(value, occurrences);
        currentData.setData(name, value);
    }
}
