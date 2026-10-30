package ATMSystem;

public class HDFCBankService implements BankService{

    public boolean validateCard(BankCard bankCard) {
        // calls the bank server with the card number and check's the status of the card
        // if status is inactive returns false else true
        return true;
    }

    public double checkBalance(BankCard bankCard, int pin) {
        // calls the bank server with card number and pin 
        // if valid returns the users balance
        return 123.0;
    }

    public WithDrawStatus withdrawAmount(BankCard bankCard, int pin, long requestedAmount) {
        // calls the bank server with the card number, expiry, pin and requestedAmount
        // if card is valid and user have enough money 
        // request is succeed 
        return WithDrawStatus.SUCCESS;
    }
}
