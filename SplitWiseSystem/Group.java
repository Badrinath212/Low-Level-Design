package SplitWiseSystem;

import java.util.List;
import java.util.Map;

public class Group {
    private String groupId;
    private String groupName;
    private List<User> members;
    private List<Expense> expenses;
    private BalanceSheet balanceSheet;

    public String getGroupId() {
        return groupId;
    }

    public Map<User, Map<User, Double>> getBalanceSheet() {
        return balanceSheet.getBalanceSheet();
    }

    public boolean addExpense(Expense expense) {
        expenses.add(expense);
        balanceSheet.updateBalances(expense);
    }

}
