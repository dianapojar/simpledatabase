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

    public boolean commit() {
        TransactionData mergedTransaction = transactionManager.commit(data);
        if (mergedTransaction == null) {
            return false;
        } else {
            data = mergedTransaction;
            transactionManager.cleanOldTransactions();
            return true;
        }
    }

    public String getValueForKeyFromAllTransaction(String key) {
        String oldValue = data.getKeyValue(key);
        if (oldValue == null) {
            oldValue = transactionManager.getMostRecentValueForKey(key);
        }
        return oldValue;
    }

    public void decrementValueCount(String value){
        if (value != null) {
            Integer decrementedOccurrenceCount = getOccurrenceCountFromAllTransaction(value) - 1;
            data.setValueCount(value, decrementedOccurrenceCount);
        }
    }

    public Integer getOccurrenceCountFromAllTransaction(String value) {
        Integer occurrenceCount = data.getValueCount(value);
        if (occurrenceCount == null) {
            occurrenceCount = transactionManager.getOccurrencesForValue(value);
        }
        return occurrenceCount;
    }
}
