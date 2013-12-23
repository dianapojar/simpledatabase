package db.data;

import java.util.ArrayList;
import java.util.List;

public class TransactionData extends SimpleDBData {
    private List<String> keysToBeDeleted = new ArrayList<String>();

    public void unSetData(String key) {
        //TODO: trebuie actualizata valoarea de enum in current transaction ( " -1") pentru cheia asociata
        super.unSetData(key);
        keysToBeDeleted.add(key);
    }

    @Override
    public void setData(String key, String value) {
        //TODO: old key value count must be changed
        //TODO: old value was deleted
        super.setData(key, value);
        keysToBeDeleted.remove(key);
    }

    public boolean isDeleted(String key) {
        return keysToBeDeleted.contains(key);
    }

    public List<String> getKeysToBeDeleted() {
        return keysToBeDeleted;
    }


}
