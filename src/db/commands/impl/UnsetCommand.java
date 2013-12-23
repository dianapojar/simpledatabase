package db.commands.impl;

import db.data.DatabaseContainer;
import db.data.TransactionData;
import db.data.TransactionManager;

public class UnsetCommand implements Command {
    private String name;

    public UnsetCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(DatabaseContainer databaseContainer) {
        TransactionData currentData = databaseContainer.getData();

        //decrement old value count
        String oldValue = databaseContainer.getValueForKeyFromAllTransaction(name);
        databaseContainer.decrementValueCount(oldValue);

        //delete and mark key as deleted
        currentData.unsetKey(name);
    }
}
