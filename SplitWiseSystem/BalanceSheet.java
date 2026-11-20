package SplitWiseSystem;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class BalanceSheet {
    private String balanceSheetId;
    private Map<User, Map<User, Integer>> balances;

    public BalanceSheet(String balanceSheetId) {
        this.balanceSheetId = Objects.requireNonNull(balanceSheetId, "BalanceSheetId cannot be null");
        this.balances = new LinkedHashMap<>();
    }


    public synchronized Map<User, Map<User, Integer>> getBalanceSheet() {
        Map<User, Map<User, Integer>> copy = new HashMap<>();
        for (Map.Entry<User, Map<User, Integer>> entry : balances.entrySet()) {
            copy.put(entry.getKey(), Collections.unmodifiableMap(new HashMap<>(entry.getValue())));
        }
        return Collections.unmodifiableMap(copy);
    }

    public synchronized boolean updateBalanceSheet(Map<User, Integer> debts, User paidBy) {
        for (Map.Entry<User, Integer> entry : debts.entrySet()) {
            User debtKey = entry.getKey();
            int amount = entry.getValue();
            if (debtKey.equals(paidBy) || amount == 0) {
                continue;
            }

            Map<User, Integer> creditorMap =
                balances.computeIfAbsent(paidBy, key -> new LinkedHashMap<>());
            Map<User, Integer> debtMap =
                balances.computeIfAbsent(debtKey, key -> new LinkedHashMap<>());

            int reverseDebt = creditorMap.getOrDefault(debtKey, 0);
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

    public synchronized void settleSplit(User debtUser, User creditUser) {
        Map<User, Integer> debts = balances.get(debtUser);
        if (debts == null || !debts.containsKey(creditUser)) {
            throw new IllegalArgumentException("No debt exists between these users");
        }
        debts.remove(creditUser);
    }

}
