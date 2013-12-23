package db.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SimpleDBData {
    protected ConcurrentMap<String, String> data = new ConcurrentHashMap<String, String>();
    protected ConcurrentMap<String, Integer> valueCountMap = new ConcurrentHashMap<String, Integer>();

    private List<TransactionData> currentTransactions = new ArrayList<TransactionData>();
    private boolean isCommitOn = false;

    private static final String NO_ELEMENT_VALUE = "NULL";

    public SimpleDBData() {
    }

    /* get/set la valorile din data => set("A","30");
     *
     */

    public void setData(String key, String value) {
        if (isInTransaction() && !isCommitOn) {
            //TODO: check if old value was deleted
            getLatestTransaction().setData(key, value);
        } else {
            set(key, value);
        }
    }

    protected void set(String key, String value) {
        updateOldValueCount(key);

        Integer valueCount = 1;
        if (valueCountMap.containsKey(value)) {
            valueCount = valueCount + valueCountMap.get(value);
        }

        data.put(key, value);
        valueCountMap.put(value, valueCount);
    }

    public void unSetData(String key) {
        if (isInTransaction() && !isCommitOn) {
            getLatestTransaction().unSetData(key);
        } else {
            updateOldValueCount(key);
            data.remove(key);
        }
    }

    protected void updateOldValueCount(String key) {
        if (isInTransaction() && !isCommitOn) {
            //daca cheia exista in ierarhia de tranzactii copii nr de aparitii asociate in latet transaction
            for (int i = currentTransactions.size() - 1; i >= 0; i--) {
                TransactionData transaction = currentTransactions.get(i);
                if (!transaction.getData(key).equals(NO_ELEMENT_VALUE)) {
                    String value = transaction.getData(key);
                    getLatestTransaction().addValueCount(value, -1);
                    break;
                }
            }
        } else {
            String oldValue = data.get(key);
            if (oldValue != null) {
                addValueCount(oldValue, -1);
            }
        }
    }

    public String getData(String key) {
        if (isInTransaction() && !isCommitOn) {
            for (int i = currentTransactions.size() - 1; i >= 0; i--) {
                if (currentTransactions.get(i).isDeleted(key)) {
                    return NO_ELEMENT_VALUE;
                }
                String value = currentTransactions.get(i).getData(key);
                if (!value.equals(NO_ELEMENT_VALUE)) {
                    return value;
                }
            }
        }
        if (data.containsKey(key)) {
            return data.get(key);
        }
        return "NULL";
    }

    public Integer getValueCountData(String value) {
        Integer valueCount = getValCount(value);
        if (isInTransaction()) {
            for (TransactionData transaction : currentTransactions) {
                    valueCount = valueCount + transaction.getValCount(value);
            }
        }
        return valueCount;
    }

    protected Integer getValCount(String value) {
        if (valueCountMap.containsKey(value)) {
            return valueCountMap.get(value);
        }
        return 0;
    }

    protected void addValueCount(String value, Integer count) {
        if (valueCountMap.containsKey(value)) {
            count = count + valueCountMap.get(value);
        }
        valueCountMap.put(value, count);
    }

    //TRANSACTION IMPLEMENTATION
    public void beginTransaction() {
        TransactionData newTransaction = new TransactionData();
        currentTransactions.add(newTransaction);
    }

    public boolean commitTransaction() {
        int numberOfTransactions = currentTransactions.size();
        if (numberOfTransactions == 0) {
            return false;
        }
        isCommitOn = true;
        //merging all transactions from older to newer
        for (int i = 0; i < numberOfTransactions; i++) {
            TransactionData transaction = currentTransactions.get(i);

            //TODO: maybe create method for merging with dependency injection in transaction data
            //merged new added keys
            for (String key : transaction.data.keySet()) {
                setData(key, transaction.getData(key));
            }
            //merging deleted keys
            for (int j = 0; j < transaction.getKeysToBeDeleted().size(); j++) {
                unSetData(transaction.getKeysToBeDeleted().get(j));
            }
        }
        //cleanup
        currentTransactions = new ArrayList<TransactionData>();
        isCommitOn = false;
        return true;
    }

    public boolean rollbackTransaction() {
        Integer numberOfActiveTransactions = currentTransactions.size();
        if (numberOfActiveTransactions == 0) {
            return false;
        }
        currentTransactions.remove(currentTransactions.size() - 1);
        return true;
    }

    private TransactionData getLatestTransaction() {
        return currentTransactions.get(currentTransactions.size() - 1);
    }

    private boolean isInTransaction() {
        return currentTransactions.size() > 0;
    }
}
