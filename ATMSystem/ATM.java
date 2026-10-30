package ATMSystem;

public class ATM {
    public ATMState atmState;
    public BankCard bankCard;
    public BankService bankService;

    public ATM(ATMState atmState, BankCard bankCard) {
        this.atmState = atmState;
        this(bankCard);
    } 

    public ATM(BankCard bankCard) {
        if (bankCard == null) {
            throw new IllegalArgumentException("Invalid Card");
        }
        this.bankCard = bankCard;
        if (bankCard.getBankCode().equals("HDFC")) {
            this.bankService = new HDFCBankService();
        }
    }

    public static void main(String[] args) {
        ATM atm  = new ATM(ATMState.IDLE, null);

    }

    public void changeATMState(ATMState atmState) {
        this.atmState = atmState;
    }

    public void insertCard(BankCard bankCard) {
        changeATMState(ATMState.CARD_INSERTED);
        if (bankCard.isCardExpired()) {
            throw new RuntimeException("Invalid Card");
        }
        changeATMState(ATMState.AUTHENTICATED);
    }

    public double checkBalance(BankCard bankCard, int pin) {
        double balance = bankService.checkBalance(bankCard, pin);
        return balance;
    }
}
