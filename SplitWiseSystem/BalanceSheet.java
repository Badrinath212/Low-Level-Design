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
        for (Map.Entry<User, Integer> entry : debts.entrySet()) {
            User debtKey = entry.getKey();

            Map<User, Integer> creditorMap = balances.get(paidBy);
            Map<User, Integer> debtMap = balances.get(debtKey);

            int reverseDebt = creditorMap.getOrDefault(debtKey, 0);
            int amount = entry.getValue();


            if (reverseDebt >= amount) {
                int remaining = reverseDebt - amount;
                if (remaining == 0) {
                    creditorMap.remove(debtKey);
                } else {
                    creditorMap.put(debtKey, remaining);
                }
            } else {
                creditorMap.remove(debtKey);
                debtMap.put(paidBy, amount - reverseDebt);
            }
        }
        return true;
    }

}
