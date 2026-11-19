package SplitWiseSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Group {
    private String groupId;
    private String groupName;
    private List<User> members;
    private List<Expense> expenses;
    private BalanceSheet balanceSheet;
    private SplitStrategy splitStrategy = new EqualSplitExpense();

    public Group(String groupId, String groupName, List<User> members) {
        this.groupId = Objects.requireNonNull(groupId, "GroupId cannot be null");
        this.groupName = Objects.requireNonNull(groupName, "GroupName cannot be null");
        this.members = new ArrayList<>(Objects.requireNonNull(members, "Members cannot be null"));
        this.expenses = new ArrayList<>();
        this.balanceSheet = new BalanceSheet(UUID.randomUUID().toString());
        for (User member : this.members) {
            member.addGroup(this);
        }
    }

    public String getGroupId() {
        return groupId;
    }

    public synchronized Map<User, Map<User, Integer>> getBalanceSheet() {
        return balanceSheet.getBalanceSheet();
    }

    public synchronized Map<User, Integer> getBalancesForUser(User user) {
        return balanceSheet.getBalancesForUser(user);
    }

    public synchronized void addMember(User user) {
        if (!members.contains(user)) {
            members.add(user);
            user.addGroup(this);
        }
    }

    public synchronized void addExpense(Expense expense) {
        Map<User, Integer> debts = splitStrategy.split(expense);
        balanceSheet.updateBalanceSheet(debts, expense.getPaidBy());
        expenses.add(expense);
    }

    public synchronized void settleSplit(User debtUser, User creditUser) {
        balanceSheet.settleSplit(debtUser, creditUser);
    }

    public synchronized void simplifyDebts() {
        balanceSheet.simplifyDebts();
    }

}
