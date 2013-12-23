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

        //decrement oldValue count
        String oldValue = container.getValueForKeyFromAllTransaction(name);
        if (!value.equals(oldValue)) {
            container.decrementValueCount(oldValue);

            //set new value and update value count
            Integer occurrences = container.getOccurrenceCountFromAllTransaction(value);
            currentData.setValueCount(value, occurrences + 1);
        }
        currentData.setData(name, value);
    }
}
