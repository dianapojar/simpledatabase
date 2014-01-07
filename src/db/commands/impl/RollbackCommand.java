package db.commands.impl;

import db.data.Data;
import db.data.DataContainer;

public class RollbackCommand implements Command {

    @Override
    public void execute(DataContainer dataContainer) {
        //the last transaction is discarded
        Data data =  dataContainer.getTransactionManager().rollback();
        if (data == null) {
            System.out.println("NO TRANSACTION");
        } else {
            dataContainer.setData(data);
            System.out.println();
        }
    }
}
