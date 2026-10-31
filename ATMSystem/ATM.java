package ATMSystem;

public class ATM {
    public ATMState atmState = ATMState.IDLE;
    public BankCard bankCard;
    public BankService bankService;

    public ATM(BankCard bankCard) {
        if (bankCard == null) {
            throw new IllegalArgumentException("Invalid Card");
        }
        this.bankCard = bankCard;
        if (bankCard.getBankCode().equals("HDFC")) {
            this.bankService = new HDFCBankService();
        }
        insertCard(bankCard);
    }

    public static void main(String[] args) {

        BankCard bankCard = new BankCard("1234567890123456", "Badri", "09/28", 123, "HDFC");
        ATM atm  = new ATM(bankCard);
        double balance = atm.checkBalance(bankCard, 1234);

        System.out.println(balance);

        WithDrawStatus withdrawReq = atm.withdrawAmount(bankCard, 2000, 1234);

        System.out.println("Withdraw status: " + withdrawReq);

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
        if (atmState != ATMState.AUTHENTICATED)  {
            throw new RuntimeException("Invalid card!");
        }
        double balance = bankService.checkBalance(bankCard, pin);
        return balance;
    }

    public WithDrawStatus withdrawAmount(BankCard bankCard, int requestedMoney, int pin) {
        if (atmState != ATMState.AUTHENTICATED)  {
            throw new RuntimeException("Invalid card!");
        }
        return bankService.withdrawAmount(bankCard, pin, requestedMoney);
    }
}
