package ATMSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ATMTest {
    private ATM atm;

    @BeforeEach
    public void setUp() {
        CashDispenser cashDispenser = new CashDispenser();
        int[] arr = new int[]{10, 10, 10};
        cashDispenser.updateInventory(arr);

        BankCard bankCard = new BankCard("1234567890123456", "Badri", "09/28", "HDFC");
        BankService bankService = new HDFCBankService();
        atm = new ATM(cashDispenser, bankService);
        atm.insertCard(bankCard);
    }

    @Test
    public void testWithdrawAmount() {
        WithDrawStatus withdrawReq = atm.withdrawAmount(-1, 1234);
        System.out.println("Withdraw status: " + withdrawReq);
        assertEquals(WithDrawStatus.SUCCESS, withdrawReq);
    }
}
