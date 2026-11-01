package ATMSystem;

public class ATM {
    private ATMState atmState = ATMState.IDLE;
    private BankCard bankCard;
    private final BankService bankService;
    private final CashDispenser cashDispenser;

    public ATM(CashDispenser cashDispenser, BankService bankService) {
        if (cashDispenser == null || bankService == null) {
            throw new IllegalArgumentException("Cash dispenser and bank service are required");
        }
        this.bankService = bankService;
        this.cashDispenser = cashDispenser;
    }

    public static void main(String[] args) {
        CashDispenser cashDispenser = new CashDispenser();
        int[] arr = new int[]{10, 10, 10};
        cashDispenser.updateInventory(arr);

        BankCard bankCard = new BankCard("1234567890123456", "Badri", "09/28", "HDFC");
        BankService bankService = new HDFCBankService();
        ATM atm = new ATM(cashDispenser, bankService);
        atm.insertCard(bankCard);

        double balance = atm.checkBalance(1234);
        System.out.println("Balance: " + balance);

        WithDrawStatus withdrawReq = atm.withdrawAmount(2000, 1234);
        System.out.println("Withdraw status: " + withdrawReq);
    }

    private void changeATMState(ATMState atmState) {
        this.atmState = atmState;
    }

    public synchronized ATMState getState() {
        return atmState;
    }

    public synchronized void insertCard(BankCard bankCard) {
        if (atmState != ATMState.IDLE) {
            throw new IllegalStateException("Eject the current card before inserting another card");
        }
        if (bankCard == null) {
            throw new IllegalArgumentException("Card is required");
        }

        changeATMState(ATMState.CARD_INSERTED);
        try {
            if (bankCard.isCardExpired()) {
                throw new IllegalArgumentException("Card is expired");
            }
            if (!bankService.validateCard(bankCard)) {
                throw new IllegalArgumentException("Card is invalid");
            }
            this.bankCard = bankCard;
            changeATMState(ATMState.CARD_VALIDATED);
        } finally {
            if (atmState == ATMState.CARD_INSERTED) {
                changeATMState(ATMState.IDLE);
            }
        }
    }

    public synchronized void ejectCard() {
        if (atmState == ATMState.PROCESSING) {
            throw new IllegalStateException("Cannot eject card while a transaction is processing");
        }
        bankCard = null;
        changeATMState(ATMState.IDLE);
    }

    public synchronized double checkBalance(int pin) {
        ensureCardValidated();
        changeATMState(ATMState.PROCESSING);
        try {
            return bankService.checkBalance(bankCard, pin);
        } finally {
            changeATMState(ATMState.CARD_VALIDATED);
        }
    }

    public synchronized WithDrawStatus withdrawAmount(int requestedMoney, int pin) {
        ensureCardValidated();
        if (requestedMoney <= 0 || requestedMoney % 100 != 0) {
            throw new IllegalArgumentException("Withdrawal amount must be a positive multiple of 100");
        }

        changeATMState(ATMState.PROCESSING);
        try {
            synchronized (cashDispenser) {
                int[] notes = cashDispenser.prepareWithDraw(requestedMoney);
                if (notes.length == 0) {
                    return WithDrawStatus.INSUFFICIENT_ATM_NOTES;
                }

                WithDrawStatus bankStatus = bankService.withdrawAmount(bankCard, pin, requestedMoney);
                if (bankStatus != WithDrawStatus.SUCCESS) {
                    return bankStatus;
                }

                return cashDispenser.dispatchAmount(notes, requestedMoney);
            }
        } finally {
            changeATMState(ATMState.CARD_VALIDATED);
        }
    }

    private void ensureCardValidated() {
        if (atmState != ATMState.CARD_VALIDATED || bankCard == null) {
            throw new IllegalStateException("Insert and validate a card first");
        }
    }
}
