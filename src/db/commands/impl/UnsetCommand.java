package db.commands.impl;

import db.data.Data;
import db.data.DataContainer;

public class UnsetCommand implements Command {
    private String name;

    public UnsetCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(DataContainer dataContainer) {
        Data currentData = dataContainer.getData();

        //decrement old value count
        String oldValue = dataContainer.getValueForKeyFromAllTransaction(name);
        dataContainer.decrementValueCount(oldValue);

        //delete and mark key as deleted
        currentData.unsetKey(name);
    }
}
