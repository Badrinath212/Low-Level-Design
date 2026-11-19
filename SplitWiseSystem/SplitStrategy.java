package SplitWiseSystem;

import java.util.Map;

public interface SplitStrategy {
    Map<User, Integer> split(Expense expense);
}
