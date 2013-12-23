package db.commands.impl;

import db.data.Data;
import db.data.DataContainer;
import db.data.TransactionManager;

public class SetCommand implements Command {
    private String name;
    private String value;

    public SetCommand(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void execute(DataContainer container) {
        Data currentData = container.getData();
        TransactionManager transactionManager = container.getTransactionManager();

        if (!currentData.isKeyDeleted(name)) {
            //get oldValue
            String oldValue = currentData.getKeyValue(name);
            if (oldValue == null) {
                oldValue = transactionManager.getMostRecentValueForKey(name);
            }
            //decrement oldValue count
            if (oldValue != null) {
                Integer decrementedOccurrenceCount = getOccurrenceCountFromAllTransaction(oldValue, container) - 1;
                currentData.setValueCount(oldValue, decrementedOccurrenceCount);
            }
        }

        //set new value and update value count
        Integer occurrences = getOccurrenceCountFromAllTransaction(value, container);
        currentData.setValueCount(value, occurrences + 1);

        currentData.setData(name, value);
    }

    private Integer getOccurrenceCountFromAllTransaction(String value, DataContainer container) {
        Integer occurrenceCount = container.getData().getValueCount(value);
        if (occurrenceCount == null) {
            occurrenceCount = container.getTransactionManager().getOccurrencesForValue(value);
        }
        return occurrenceCount;
    }
}
