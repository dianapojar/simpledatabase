package db.data;

public class DatabaseContainer {
    private TransactionManager transactionManager = new TransactionManager();
    private TransactionData data = new TransactionData();

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public TransactionData getData() {
        return data;
    }

    public void setData(TransactionData data) {
        this.data = data;
    }

    public void updateDataToNewTransaction() {
        data = transactionManager.begin(data);
    }
}
