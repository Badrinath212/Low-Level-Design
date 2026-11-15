package SplitWiseSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqualSplitExpense implements SplitStrategy {
    public Map<User, Integer> equalSplit(Expense expense, BalanceSheet balanceSheet) {
        User paidBy = expense.getPaidBy();
        int amount = expense.getAmount();
        List<User> participents = expense.getParticipents();
        int n = participents.size();

        int quotient = amount / n;
        int reminder = amount % n;
        Map<User, Integer> debts = new HashMap<>();
        
        for (User user : participents) {
            int am = quotient;
            if (reminder > 0) {
                am++;
                reminder--;
            }
            if (user.getUserId().equals(paidBy.getUserId())) continue;
            debts.put(user, am);
        }
        return debts;
    }
}
