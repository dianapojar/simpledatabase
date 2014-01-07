package db.commands.impl;

import db.data.Data;
import db.data.DataContainer;
import db.data.TransactionManager;

public class UnsetCommand implements Command {
    private String name;

    public UnsetCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(DataContainer dataContainer) {
        Data currentData = dataContainer.getData();
        TransactionManager transactionManager = dataContainer.getTransactionManager();

        //get old value and decrement it's count
        String oldValue = currentData.getKeyValue(name);
        if (oldValue == null) {
            oldValue = transactionManager.getMostRecentValueForKey(name);
        }
        if (oldValue != null) {
            Integer decrementedOccurrenceCount = getOccurrenceCountFromAllTransaction(oldValue, dataContainer) - 1;
            currentData.setValueCount(oldValue, decrementedOccurrenceCount);
        }

        //delete and mark key as deleted
        currentData.unsetKey(name);
        System.out.println();
    }

    private Integer getOccurrenceCountFromAllTransaction(String value, DataContainer container) {
        Integer occurrenceCount = container.getData().getValueCount(value);
        if (occurrenceCount == null) {
            occurrenceCount = container.getTransactionManager().getOccurrencesForValue(value);
        }
        return occurrenceCount;
    }
}
