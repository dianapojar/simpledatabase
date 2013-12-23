package db.commands.impl;

import db.data.Data;
import db.data.DataContainer;
import db.data.TransactionManager;

public class NumEqualToCommand implements Command {
    private String value;

    public NumEqualToCommand(String value) {
        this.value = value;
    }

    @Override
    public void execute(DataContainer dataContainer) {
        Data currentData = dataContainer.getData();
        TransactionManager transactionManager = dataContainer.getTransactionManager();

        //get value count
        Integer currentCount = currentData.getValueCount(value);
        if (currentCount == null) {
            currentCount = transactionManager.getOccurrencesForValue(value);
            //cache count value in current transaction
            currentData.setValueCount(value, currentCount);
        }
        System.out.println(currentCount);
    }
}
