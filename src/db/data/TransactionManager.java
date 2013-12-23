package db.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a list of parent transactions and implements a set of actions over the past opened transactions
 *
 */
public class TransactionManager {
    private List<Data> previousTransactions = new ArrayList<Data>();

    public TransactionManager() {
    }

    public Data begin(Data oldTransaction) {
        previousTransactions.add(oldTransaction);
        return new Data();
    }

    public Data rollback() {
        if (previousTransactions.size() == 0) {
            return null;
        }
        return previousTransactions.remove(previousTransactions.size() - 1);
    }

    public Data commit(Data lastTransaction) {
        if (previousTransactions.size() == 0) {
            return null;
        }
        Data oldestTransaction = previousTransactions.get(0);
        for (int i = 1; i < previousTransactions.size(); i++) {
            Data transactionToBeMerged = previousTransactions.get(i);
            oldestTransaction.mergeTransaction(transactionToBeMerged);
        }
        oldestTransaction.mergeTransaction(lastTransaction);
        return oldestTransaction;
    }

    public String getMostRecentValueForKey(String key) {
        for (int i = previousTransactions.size() - 1; i >= 0 ; i--) {
            Data transaction = previousTransactions.get(i);
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
            Data transaction = previousTransactions.get(i);
            Integer valueCount = transaction.getValueCount(value);
            if (valueCount != null) {
                return valueCount;
            }
        }
        return 0;
    }

    public void cleanOldTransactions() {
        previousTransactions = new ArrayList<Data>();
    }
}
