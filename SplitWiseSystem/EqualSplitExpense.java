package SplitWiseSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqualSplitExpense implements SplitStrategy {
    @Override
    public Map<User, Integer> split(Expense expense) {
        User paidBy = expense.getPaidBy();
        int amount = expense.getAmount();
        List<User> participents = expense.getParticipents();
        int n = participents.size();

        int quotient = amount / n;
        int remainder = amount % n;
        Map<User, Integer> debts = new HashMap<>();
        
        for (User user : participents) {
            int am = quotient;
            if (remainder > 0) {
                am++;
                remainder--;
            }
            if (user.getUserId().equals(paidBy.getUserId())) continue;
            debts.put(user, am);
        }
        return debts;
    }
}
