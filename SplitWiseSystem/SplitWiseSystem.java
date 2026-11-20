package SplitWiseSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SplitWiseSystem {
    private List<User> users;
    private List<Group> groups;

    public SplitWiseSystem(List<User> users, List<Group> groups) {
        this.users = new ArrayList<>(Objects.requireNonNull(users, "Users list cannot be null"));
        this.groups = new ArrayList<>(Objects.requireNonNull(groups, "Groups list cannot be null"));
    }

    public static void main(String[] args) {
        List<User> users = new ArrayList<>(List.of(
            new User(UUID.randomUUID().toString(), "Alice", "alice@gmail.com", "123456789", new ArrayList<>()),
            new User(UUID.randomUUID().toString(), "Bob", "bob@gmail.com", "123456789", new ArrayList<>()),
            new User(UUID.randomUUID().toString(), "Charlie", "charlie@gmail.com", "123456789", new ArrayList<>())
        ));
        SplitWiseSystem system = new SplitWiseSystem(users, new ArrayList<>());
        Group group = system.createGroup("Boys", users);

        User alice = users.get(0);
        User bob = users.get(1);

        system.splitExpense(group.getGroupId(), alice.getUserId(),
            new Expense(UUID.randomUUID().toString(), "Groceries", 12000, alice, users));
        system.splitExpense(group.getGroupId(), bob.getUserId(),
            new Expense(UUID.randomUUID().toString(), "Dinner", 6000, bob, users));

        printBalances("Balances after expenses", system, group.getGroupId(), users);
    }

    private static void printBalances(String title, SplitWiseSystem system, String groupId, List<User> users) {
        System.out.println("\n" + title + ":");
        Map<User, Map<User, Integer>> balances =
            system.getBlanceSheetForGroup(groupId, users.get(0).getUserId());
        if (balances.isEmpty()) {
            System.out.println("No outstanding balances");
            return;
        }
        for (Map.Entry<User, Map<User, Integer>> debtor : balances.entrySet()) {
            for (Map.Entry<User, Integer> debt : debtor.getValue().entrySet()) {
                System.out.printf("%s owes %s $%.2f%n",
                    debtor.getKey().getUserName(), debt.getKey().getUserName(), debt.getValue());
            }
        }
    }

    public synchronized Group createGroup(String groupName, List<User> members) {
        Group group = new Group(UUID.randomUUID().toString(), groupName, members);
        groups.add(group);
        return group;
    }

    public synchronized void settleSplit(String groupId, String userId, String creditorUserId) {
        if (userId.isBlank()) {
            throw new IllegalArgumentException("UserId must contains at least one non-white space character");
        }

        if (groupId.isBlank()) {
            throw new IllegalArgumentException("GroupId must contains at least one non-white space character");
        }

        Group group = getGroupById(groupId);
        if (group == null) {
            throw new RuntimeException("Group is not found");
        }
        User debtor = getUserById(userId);
        User creditor = getUserById(creditorUserId);
        group.settleSplit(debtor, creditor);
    }

    public synchronized void splitExpense(String groupId, String userId, Expense expense) {
        if (userId.isBlank()) {
            throw new IllegalArgumentException("UserId must contains at least one non-white space character");
        }

        if (groupId.isBlank()) {
            throw new IllegalArgumentException("GroupId must contains at least one non-white space character");
        }

        Group group = getGroupById(groupId);
        if (group == null) {
            throw new RuntimeException("Group is not found");
        }

        group.addExpense(expense);
    }

    public synchronized Map<User, Map<User, Integer>> getBlanceSheetForGroup(String groupId, String userId) {
        if (userId.isBlank()) {
            throw new IllegalArgumentException("UserId must contains at least one non-white space character");
        }

        if (groupId.isBlank()) {
            throw new IllegalArgumentException("GroupId must contains at least one non-white space character");
        }

        if (!isValidUser(userId)) {
            throw new RuntimeException("Invalid user");
        }

        Group group = getGroupById(groupId);
        if (group == null) {
            throw new RuntimeException("Invalid group");
        }

        return group.getBalanceSheet();
        
    }

    public Group getGroupById(String groupId) {
        for (Group group : groups) {
            if (group.getGroupId().equals(groupId)) return group;
        }
        return null;
    }

    public boolean isValidGroup(String groupId) {
        for (Group group : groups) {
            if (group.getGroupId().equals(groupId)) return true;
        }
        return false;
    }

    public boolean isValidUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    private User getUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        throw new RuntimeException("Invalid user");
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public synchronized void addMemberToGroup(String groupId, User user) {
        if (groupId.isBlank()) {
            throw new IllegalArgumentException("GroupId must contain atleast one non-empty character");
        }
        Group group = getGroupById(groupId);
        if (group == null) {
            throw new RuntimeException("Invalid group");
        }
        group.addMember(user);
    }
}

