package ATMSystem;

public class CashDispenser {
    // 0 -> 100RS, 1 -> 200RS, 2 -> 500RS
    int[] inventoryArr = new int[3];
    CashCollectorState cashCollectorState = CashCollectorState.CLOSED;

    public void updateInventory(int[] arr) {
        for (int i = 0; i < 3; i++) {
            inventoryArr[i]+= arr[i];
        }
    }

    public int[] prepareWithDraw(int requestedAmount) {
        int[] arr = new int[3];
        int curAmount = requestedAmount;
        // try with 500RS
        int requiredNotes_500 = requestedAmount / 500;
        if (inventoryArr[2] >= requiredNotes_500) {
            arr[2] = requiredNotes_500;
            curAmount -= requiredNotes_500 * 500; 
        } else {
            curAmount-= (inventoryArr[2] * 500);
            arr[2] = inventoryArr[2];
        }

        // try with 200RS
        int requiredNotes_200 = curAmount / 200;
        if (inventoryArr[1] >= requiredNotes_200) {
            arr[1] = requiredNotes_200;
            curAmount -= requiredNotes_200 * 200;
        } else {
            curAmount -= inventoryArr[1] * 200;
            arr[1] = inventoryArr[1];
        }

        // try with 100RS
        int requiredNotes_100 = curAmount / 100;
        if (inventoryArr[0] >= requiredNotes_100) {
            arr[0] = requiredNotes_100;
            curAmount -= requiredNotes_100 * 100;
        } else {
            arr[0] = inventoryArr[0];
            curAmount -= inventoryArr[0] * 100;
        }

        if ()

        return curAmount == 0 ? arr : new int[0];
    }

    public WithDrawStatus dispatchAmount(int[] requestArr, int requestedAmount) {
        if (requestArr.length == 0) return WithDrawStatus.INSUFFICIENT_ATM_NOTES;

        for (int i = 0; i < 3; i++) {
            inventoryArr[i]-= requestArr[i];
        }

        return WithDrawStatus.SUCCESS;
    }

    public double getTotalATMMoney() {
        double totalAmount = 0;
        
        totalAmount += inventoryArr[0] * 100;
        totalAmount += inventoryArr[1] * 200;
        totalAmount += inventoryArr[2] * 500;
        return totalAmount;
    }

    public void openCashCollector() {
        if (cashCollectorState.OPENED == getCashCollectorState()) return;
        cashCollectorState = cashCollectorState.CLOSED;
        return;
    }

    public void closeCashCollector() {
        if (cashCollectorState.CLOSED == getCashCollectorState()) return;
        cashCollectorState = cashCollectorState.OPENED;
        return;
    }

    public CashCollectorState getCashCollectorState() {
        return cashCollectorState;
    }
}
