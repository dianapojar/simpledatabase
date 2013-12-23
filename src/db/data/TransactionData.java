package db.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * represent the current transaction that is queried
 */
public class TransactionData {
    private ConcurrentMap<String, String> data = new ConcurrentHashMap<String, String>();
    private ConcurrentMap<String, Integer> valueCountMap = new ConcurrentHashMap<String, Integer>();

    private List<String> keysToBeDeleted = new ArrayList<String>();

    public void setData(String key, String value) {
        if (keysToBeDeleted.contains(key)) {
            keysToBeDeleted.remove(key);
        }
        data.put(key, value);
    }

    public ConcurrentMap<String, String> getData() {
        return data;
    }

    public boolean isExistingKey(String key) {
        return data.containsKey(key);
    }

    public boolean isExistingValueCountKey(String value) {
        return valueCountMap.containsKey(value);
    }

    public boolean isKeyDeleted(String key) {
        return keysToBeDeleted.contains(key);
    }

    public String getKeyValue(String key) {
        return data.get(key);
    }

    public Integer getValueCount(String value) {
        if (!valueCountMap.containsKey(value)) {
            return null;
        }
        return valueCountMap.get(value);
    }

    public void setValueCount(String value, Integer valueCount) {
        valueCountMap.put(value, valueCount);
    }

    public void unsetKey(String key) {
        keysToBeDeleted.add(key);
        data.remove(key);
    }

    public void removeValueCount(String value) {
        valueCountMap.remove(value);
    }

    public void mergeTransaction(TransactionData transaction) {
        //merge keys
        for (String key : transaction.getData().keySet()) {
            this.data.put(key, transaction.getKeyValue(key));
        }

        //delete deleted keys
        for (String deletedKey : transaction.getKeysToBeDeleted()) {
            this.data.remove(deletedKey);
        }

        //update valueCounts
        for (String value : transaction.getValueCountMap().keySet()) {
            this.valueCountMap.put(value, transaction.getValueCount(value));
        }
    }

    public List<String> getKeysToBeDeleted() {
        return keysToBeDeleted;
    }

    public ConcurrentMap<String, Integer> getValueCountMap() {
        return valueCountMap;
    }

    //    public void incrementValueCount(String value) {
//        Integer count = 0;
//        if (valueCountMap.containsKey(value)) {
//            count = valueCountMap.get(value) + 1;
//        }
//        valueCountMap.put(value, count);
//    }
//
//    public void decrementValueCount(String value) {
//        Integer count = 0;
//        if (valueCountMap.containsKey(value)) {
//            count = valueCountMap.get(value) - 1;
//        }
//        valueCountMap.put(value, count);
//    }
}
