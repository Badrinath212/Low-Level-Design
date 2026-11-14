package SplitWiseSystem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BalanceSheet {
    private String balanceSheetId;
    private Group group;
    private Map<User, Map<User, Double>> balances;
    private SplitStrategy splitStrategy = new EqualSplitExpense();


    public Map<User, Map<User, Double>> getBalanceSheet() {
        if (balances == null) return null;
        Map<User, Map<User, Double>> copy = new HashMap<>();
        for (Map.Entry<User, Map<User, Double>> entry : balances.entrySet()) {
            copy.put(entry.getKey(), Collections.unmodifiableMap(new HashMap<>(entry.getValue())));
        }
        return Collections.unmodifiableMap(copy);
    }

    public void updateBalances(Expense expense, Map<User, Map<User, Double>> balances) {
        splitStrategy.equalSplit(expense, balances);
    }
}
