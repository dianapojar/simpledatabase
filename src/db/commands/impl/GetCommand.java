package db.commands.impl;

import db.data.Data;
import db.data.DataContainer;
import db.data.TransactionManager;

public class GetCommand implements Command {
    private String name;

    private static final String VALUE_NOT_FOUND = "NULL";

    public GetCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(DataContainer container) {
        Data data = container.getData();
        TransactionManager transactionManager = container.getTransactionManager();

        if (data.isKeyDeleted(name)) {
            //key is marked as deleted in a transaction
            System.out.println(VALUE_NOT_FOUND);
        } else {
            //find key in most recent transaction
            String oldValue = data.getKeyValue(name);
            if (oldValue == null) {
                oldValue = transactionManager.getMostRecentValueForKey(name);
            }

            if (oldValue == null) {
                System.out.println(VALUE_NOT_FOUND);
            } else {
                System.out.println(oldValue);
            }
        }
    }

}
