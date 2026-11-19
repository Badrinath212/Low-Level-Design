package SplitWiseSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Expense {
    private String expenseId;
    private String description;
    private int amount;
    private User paidBy;
    private List<User> participents;

    public Expense(String expenseId, String description, int amount, User paidBy, List<User> participents) {
        this.expenseId = Objects.requireNonNull(expenseId, "ExpenseId cannot be null");
        this.description = description;
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
        this.amount = amount;
        this.paidBy = Objects.requireNonNull(paidBy, "User cannot be null");
        this.participents = Objects.requireNonNull(participents, "participents cannot be null");
    }

    public User getPaidBy() {
        return paidBy;
    }

    public int getAmount() {
        return amount;
    }

    public List<User> getParticipents() {
        return Collections.unmodifiableList(new ArrayList<>(participents));
    }
}
