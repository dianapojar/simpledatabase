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

    public boolean commit() {
        Data mergedTransaction = transactionManager.commit(data);
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
