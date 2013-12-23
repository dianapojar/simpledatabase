package db.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a list of parent transactions
 */
public class TransactionManager {
    private List<TransactionData> previousTransactions = new ArrayList<TransactionData>();

    public TransactionManager() {
    }

    public TransactionData begin(TransactionData oldTransaction) {
        previousTransactions.add(oldTransaction);
        return new TransactionData();
    }

    public TransactionData rollback() {
        if (previousTransactions.size() == 0) {
            return null;
        }
        return previousTransactions.remove(previousTransactions.size() - 1);
    }

    public boolean commit() {
        if (previousTransactions.size() == 0) {
            return false;
        }
        return true;
    }

    public String getMostRecentValueForKey(String key) {
        for (int i = previousTransactions.size() - 1; i >= 0 ; i--) {
            TransactionData transaction = previousTransactions.get(i);
            if (transaction.isKeyDeleted(key)) {
                return null;
            } else {
                String value = transaction.getKeyValue(key);
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }

    public Integer getOccurrencesForValue(String value) {
        for (int i = previousTransactions.size() - 1; i >= 0 ; i--) {
            TransactionData transaction = previousTransactions.get(i);
            Integer valueCount = transaction.getValueCount(value);
            if (valueCount > 0) {
                return valueCount;
            }
        }
        return 0;
    }
}
