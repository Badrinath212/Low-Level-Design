package SplitWiseSystem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BalanceSheet {
    private String balanceSheetId;
    private Group group;
    private Map<User, Map<User, Integer>> balances;


    public Map<User, Map<User, Integer>> getBalanceSheet() {
        if (balances == null) return null;
        Map<User, Map<User, Integer>> copy = new HashMap<>();
        for (Map.Entry<User, Map<User, Integer>> entry : balances.entrySet()) {
            copy.put(entry.getKey(), Collections.unmodifiableMap(new HashMap<>(entry.getValue())));
        }
        return Collections.unmodifiableMap(copy);
    }

    public synchronized boolean updateBalanceSheet(Map<User, Integer> debts, User paidBy) {
        for (User user : debts.keySet()) {
            if (!balances.containsKey(user)) return false;
            Map<User, Integer> crediters = balances.get(user);
            if (!crediters.containsKey(paidBy)) {
                crediters.put(paidBy, debts.get(user));
            } else {
                crediters.put(paidBy, debts.get(user) + crediters.get(paidBy));
            }
        }

        // one more thing need to check if user A have debt and User B have credit both cancel each other before updating the map
        return true;

    }

}
