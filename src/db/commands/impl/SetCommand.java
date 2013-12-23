package db.commands.impl;

import db.data.Data;
import db.data.DataContainer;

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
