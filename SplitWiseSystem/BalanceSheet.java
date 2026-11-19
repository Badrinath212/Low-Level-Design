package SplitWiseSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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

    /**
     * Positive values mean this user owes the key; negative values mean the key owes this user.
     */
    public synchronized Map<User, Integer> getBalancesForUser(User user) {
        Map<User, Integer> userBalances = new HashMap<>();
        Map<User, Integer> debts = balances.get(user);
        if (debts != null) {
            userBalances.putAll(debts);
        }

        for (Map.Entry<User, Map<User, Integer>> entry : balances.entrySet()) {
            Integer amount = entry.getValue().get(user);
            if (amount != null) {
                userBalances.merge(entry.getKey(), -amount, Integer::sum);
            }
        }

        userBalances.values().removeIf(amount -> amount == 0);
        return Collections.unmodifiableMap(userBalances);
    }

    public synchronized boolean updateBalanceSheet(Map<User, Integer> debts, User paidBy) {
        for (Map.Entry<User, Integer> entry : debts.entrySet()) {
            User debtor = entry.getKey();
            int amount = entry.getValue();
            if (debtor.equals(paidBy) || amount == 0) {
                continue;
            }

            int reverseDebt = getDebt(paidBy, debtor);
            removeDebt(paidBy, debtor);
            if (amount > reverseDebt) {
                addDebt(debtor, paidBy, amount - reverseDebt);
            } else if (reverseDebt > amount) {
                addDebt(paidBy, debtor, reverseDebt - amount);
            }
        }
        return true;
    }

    public synchronized void simplifyDebts() {
        Map<User, Integer> netBalances = new LinkedHashMap<>();
        for (Map.Entry<User, Map<User, Integer>> entry : balances.entrySet()) {
            User debtor = entry.getKey();
            for (Map.Entry<User, Integer> debt : entry.getValue().entrySet()) {
                netBalances.merge(debtor, -debt.getValue(), Integer::sum);
                netBalances.merge(debt.getKey(), debt.getValue(), Integer::sum);
            }
        }

        balances.clear();
        List<User> debtors = new ArrayList<>();
        List<User> creditors = new ArrayList<>();
        for (Map.Entry<User, Integer> entry : netBalances.entrySet()) {
            if (entry.getValue() < 0) {
                debtors.add(entry.getKey());
            } else if (entry.getValue() > 0) {
                creditors.add(entry.getKey());
            }
        }

        int debtorIndex = 0;
        int creditorIndex = 0;
        while (debtorIndex < debtors.size() && creditorIndex < creditors.size()) {
            User debtor = debtors.get(debtorIndex);
            User creditor = creditors.get(creditorIndex);
            int debt = -netBalances.get(debtor);
            int credit = netBalances.get(creditor);
            int amount = Math.min(debt, credit);
            addDebt(debtor, creditor, amount);
            netBalances.put(debtor, -debt + amount);
            netBalances.put(creditor, credit - amount);

            if (netBalances.get(debtor) == 0) {
                debtorIndex++;
            }
            if (netBalances.get(creditor) == 0) {
                creditorIndex++;
            }
        }
    }

    public synchronized void settleSplit(User debtUser, User creditUser) {
        Map<User, Integer> debts = balances.get(debtUser);
        if (debts == null || !debts.containsKey(creditUser)) {
            throw new IllegalArgumentException("No debt exists between these users");
        }
        removeDebt(debtUser, creditUser);
    }

    private int getDebt(User debtor, User creditor) {
        Map<User, Integer> debts = balances.get(debtor);
        return debts == null ? 0 : debts.getOrDefault(creditor, 0);
    }

    private void addDebt(User debtor, User creditor, int amount) {
        balances.computeIfAbsent(debtor, key -> new LinkedHashMap<>())
            .merge(creditor, amount, Integer::sum);
    }

    private void removeDebt(User debtor, User creditor) {
        Map<User, Integer> debts = balances.get(debtor);
        if (debts == null) {
            return;
        }
        debts.remove(creditor);
        if (debts.isEmpty()) {
            balances.remove(debtor);
        }
    }

}
