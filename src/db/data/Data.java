package db.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains the current data that is being queried and modified by the user.
 * If there are transactions open, this will contain the data of the latest opened transaction
 *
 * Choose to use maps for storing the data and valueCountMap, because we have O(1) runtime for get, set, unset, numEqualTo.
 * The list keysToBeDeleted is used to mark the keys that need to be deleted when committing the transactions
 *
 * In case of a concurrent situation, then the Map should be ConcurrentMaps and the operations should acquire locks on the
 * keys it modifies, before taking any action.
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

    public boolean isKeyDeleted(String key) {
        return keysToBeDeleted.contains(key);
    }

    public String getKeyValue(String key) {
        if (keysToBeDeleted.contains(key)) {
            return null;
        }
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
