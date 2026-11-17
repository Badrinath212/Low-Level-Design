package SplitWiseSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import HotelBookingSystem.User;

public class SplitWiseSystem {
    private List<User> users;
    private List<Group> groups;

    public SplitWiseSystem(List<User> users, List<Group> groups) {
        this.users = Objects.requireNonNull(users, "Users list cannot be null");
        this.groups = Objects.requireNonNull(groups, "Groups list cannot be null");
    }

    public static void main(String[] args) {

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

    public void addUser(User user) {
        users.add(user);
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public synchronized void addMemberToGroup(String groupId, User user) {
        if (groupId.isBlank()) {
            throw new IllegalArgumentException("GroupId must contain atleast one non-empty character")
        }
        Group group = getGroupById(groupId);
        if (group == null) {
            throw new RuntimeException("Invalid group");
        }
        group.addMember(user);
    }
}

