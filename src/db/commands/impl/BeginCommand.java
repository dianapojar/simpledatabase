package db.commands.impl;

import db.data.DataContainer;

public class BeginCommand implements Command {
    @Override
    public void execute(DataContainer dataContainer) {
        dataContainer.updateDataToNewTransaction();
    }
}
