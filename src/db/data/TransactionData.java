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

    public boolean isExistingKey(String key) {
        return data.containsKey(key);
    }

    public boolean isExistingValueCountKey(String value) {
        return valueCountMap.containsKey(value);
    }

    public boolean isKeyDeleted(String key) {
        if (keysToBeDeleted.contains(key)) {
            return true;
        }
        return false;
    }

    public String getKeyValue(String key) {
        return data.get(key);
    }

    public Integer getValueCount(String value) {
        if (!valueCountMap.containsKey(value)) {
            return 0;
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
