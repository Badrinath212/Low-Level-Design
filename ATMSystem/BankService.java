package ATMSystem;

public interface BankService {
    public double checkBalance(BankCard bankCard, int pin);
    public boolean validateCard(BankCard bankCard);
    public WithDrawStatus withdrawAmount(BankCard bankCard, int pin, long requestedAmount);
}
