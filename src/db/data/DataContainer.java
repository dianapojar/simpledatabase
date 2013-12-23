package db.data;

/**
 * Container that stores the past transaction (TransactionManager) and the current used data (Data)
 */
public class DataContainer {
    private TransactionManager transactionManager = new TransactionManager();
    private Data data = new Data();

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void updateDataToNewTransaction() {
        // the old data is added to the list of transaction in transaction manager and a new data is set
        data = transactionManager.begin(data);
    }
}
