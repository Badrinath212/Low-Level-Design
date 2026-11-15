package SplitWiseSystem;

import java.util.Map;

public interface SplitStrategy {
    public Map<User, Integer> equalSplit(Expense expense, BalanceSheet blanceSheet);
}
