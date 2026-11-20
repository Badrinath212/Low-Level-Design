package SplitWiseSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private String userId;
    private String userName;
    private String email;
    private String phoneNumber;
    private List<Group> groups;

    public User(String userId, String userName, String email, String phoneNumber, List<Group> groups) {
        this.userId = Objects.requireNonNull(userId, "UserId cannot be null");
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.groups = new ArrayList<>(Objects.requireNonNull(groups, "Groups cannot be null"));
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public synchronized void addGroup(Group group) {
        groups.add(group);
    }
}
