package ATMSystem;

public class CashDispenser {
    private static final int[] DENOMINATIONS = {100, 200, 500};
    private final int[] inventoryArr = new int[DENOMINATIONS.length];
    private CashCollectorState cashCollectorState = CashCollectorState.CLOSED;

    public synchronized void updateInventory(int[] arr) {
        if (arr == null || arr.length != inventoryArr.length) {
            throw new IllegalArgumentException("Inventory must contain counts for all denominations");
        }
        for (int i = 0; i < inventoryArr.length; i++) {
            if (arr[i] < 0 || arr[i] > Integer.MAX_VALUE - inventoryArr[i]) {
                throw new IllegalArgumentException("Inventory counts must be nonnegative and within range");
            }
        }
        for (int i = 0; i < inventoryArr.length; i++) {
            inventoryArr[i] += arr[i];
        }
    }

    public synchronized int[] prepareWithDraw(int requestedAmount) {
        if (requestedAmount <= 0 || requestedAmount % DENOMINATIONS[0] != 0) {
            return new int[0];
        }

        int[] notes = new int[DENOMINATIONS.length];
        int remaining = requestedAmount;
        for (int i = DENOMINATIONS.length - 1; i >= 0; i--) {
            notes[i] = Math.min(remaining / DENOMINATIONS[i], inventoryArr[i]);
            remaining -= notes[i] * DENOMINATIONS[i];
        }
        return remaining == 0 ? notes : new int[0];
    }

    public synchronized WithDrawStatus dispatchAmount(int[] requestArr, int requestedAmount) {
        if (requestArr == null || requestArr.length != inventoryArr.length || requestedAmount <= 0) {
            return WithDrawStatus.INSUFFICIENT_ATM_NOTES;
        }

        long total = 0;
        for (int i = 0; i < requestArr.length; i++) {
            if (requestArr[i] < 0 || requestArr[i] > inventoryArr[i]) {
                return WithDrawStatus.INSUFFICIENT_ATM_NOTES;
            }
            total += (long) requestArr[i] * DENOMINATIONS[i];
        }
        if (total != requestedAmount) {
            return WithDrawStatus.INSUFFICIENT_ATM_NOTES;
        }

        for (int i = 0; i < inventoryArr.length; i++) {
            inventoryArr[i] -= requestArr[i];
        }
        return WithDrawStatus.SUCCESS;
    }

    public synchronized double getTotalATMMoney() {
        long totalAmount = 0;
        for (int i = 0; i < inventoryArr.length; i++) {
            totalAmount += (long) inventoryArr[i] * DENOMINATIONS[i];
        }
        return totalAmount;
    }

    public synchronized void openCashCollector() {
        cashCollectorState = CashCollectorState.OPENED;
    }

    public synchronized void closeCashCollector() {
        cashCollectorState = CashCollectorState.CLOSED;
    }

    public synchronized CashCollectorState getCashCollectorState() {
        return cashCollectorState;
    }
}
