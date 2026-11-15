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

    public boolean addExpense(Expense expense) {
        // need to write try catch and finally for revert
        expenses.add(expense);
        Map<User, Integer> debts = splitStrategy.equalSplit(expense, balanceSheet);
        boolean updateResponse = balanceSheet.updateBalanceSheet(debts, expense.getPaidBy());
        return true;
    }

}
