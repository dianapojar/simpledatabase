package db.commands.impl;

import db.data.Data;
import db.data.DataContainer;

public class GetCommand implements Command {
    private String name;

    private static final String VALUE_NOT_FOUND = "NULL";

    public GetCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(DataContainer container) {
        Data currentData = container.getData();

        if (currentData.isKeyDeleted(name)) {
            System.out.println(VALUE_NOT_FOUND);
        } else {
            String oldValue = container.getValueForKeyFromAllTransaction(name);

            if (oldValue == null) {
                System.out.println(VALUE_NOT_FOUND);
            } else {
                System.out.println(oldValue);
            }
        }
    }
}
