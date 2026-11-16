package SplitWiseSystem;

import java.util.List;
import java.util.Map;

public class Group {
    private String groupId;
    private String groupName;
    private List<User> members;
    private List<Expense> expenses;
    private BalanceSheet balanceSheet;
    private SplitStrategy splitStrategy = new EqualSplitExpense();

    public String getGroupId() {
        return groupId;
    }

    public Map<User, Map<User, Integer>> getBalanceSheet() {
        return balanceSheet.getBalanceSheet();
    }

    public synchronized void addMember(User user) {
        members.add(user);
    }

    public boolean addExpense(Expense expense) {
        expenses.add(expense);
        Map<User, Integer> debts = splitStrategy.equalSplit(expense, balanceSheet);
        balanceSheet.updateBalanceSheet(debts, expense.getPaidBy());
        return true;
    }

}
