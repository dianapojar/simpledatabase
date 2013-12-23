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

    public TransactionData commit(TransactionData lastTransaction) {
        if (previousTransactions.size() == 0) {
            return null;
        }

        TransactionData oldestTransaction = previousTransactions.get(0);
        for (int i = 1; i < previousTransactions.size(); i++) {
            TransactionData transactionToBeMerged = previousTransactions.get(i);
            oldestTransaction.mergeTransaction(transactionToBeMerged);
        }
        oldestTransaction.mergeTransaction(lastTransaction);
        return oldestTransaction;
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
            if (valueCount != null) {
                return valueCount;
            }
        }
        return 0;
    }

    public void cleanOldTransactions() {
        previousTransactions = new ArrayList<TransactionData>();
    }
}
