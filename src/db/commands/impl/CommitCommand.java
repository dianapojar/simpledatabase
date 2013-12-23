package db.commands.impl;

import db.data.DataContainer;

public class CommitCommand implements Command {
    @Override
    public void execute(DataContainer dataContainer) {
        Boolean isCommitOk = dataContainer.commit();
        if (!isCommitOk) {
            System.out.println("NO TRANSACTION");
        }
    }
}
