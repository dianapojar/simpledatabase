package db.data;

/**
 * Container that stores the past transaction (TransactionManager) and the current used data (Data)
 *
 * It contains some methods used for getting values from the past transaction if they are not present in the current data.
 * This is done by iterating back through the past transaction (from newest to oldest) and the first value that is found is returned.
 *          After the value is retrieved is saved in the current used data (as cache - the next queries will not need to interate back though all the
 *          old transaction)
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
        data = transactionManager.begin(data);
    }
}
