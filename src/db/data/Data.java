package db.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Contains the current data that is being queried and modified by the user.
 * If there are transactions open, this will be the latest opened transaction
 *
 * Choose to use maps for storing the data and valueCountMap, because we have O(1) runtime for get, set, unset, numEqualTo.
 *
 */
public class Data {
    private Map<String, String> data = new HashMap<String, String>();
    private Map<String, Integer> valueCountMap = new HashMap<String, Integer>();

    private List<String> keysToBeDeleted = new ArrayList<String>();

    public void setData(String key, String value) {
        if (keysToBeDeleted.contains(key)) {
            keysToBeDeleted.remove(key);
        }
        data.put(key, value);
    }

    public Map<String, String> getData() {
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

    public void mergeTransaction(Data transaction) {
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

    public Map<String, Integer> getValueCountMap() {
        return valueCountMap;
    }
}
