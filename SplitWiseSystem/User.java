package SplitWiseSystem;

import java.util.List;

public class User {
    private String userId;
    private String userName;
    private String email;
    private String phoneNumber;
    private List<Group> groups;
    private String status;

    public User(String userId, String userName, String email, String phoneNumber, List<Group> groups, String status) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.groups = groups;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public synchronized void addGroup(Group group) {
        groups.add(group);
    }
}
