package ATMSystem;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class BankCard {
    private String cardNumber;
    private String holderName;
    private String expiry;
    private int cvv;

    public BankCard(String cardNumber, String holderName, String expiry, int cvv) {
        if (cardNumber == null || cardNumber.isBlank() || cardNumber.length() != 16) {
            throw new IllegalArgumentException("Invalid card");
        }
        this.cardNumber = cardNumber;
        if (holderName == null || holderName.isBlank() || expiry == null || expiry.isBlank()) {
            throw new IllegalArgumentException("Invalid card");
        }
        this. holderName = holderName;
        this.expiry = expiry;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getHolderName() {
        return holderName;
    }

     public boolean isCardExpired(String expiry) {
        // 1. Build a formatter specifically for MM/YY (2-digit year)
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("MM/")
                .appendValue(ChronoField.YEAR_OF_ERA, 2)
                .toFormatter();

        // 2. Parse the input string into a YearMonth object
        YearMonth expiryDate = YearMonth.parse(expiry, formatter);

        // 3. Compare it directly to the current year and month
        // Returns true if the expiry month is strictly before the current month
        return expiryDate.isBefore(YearMonth.now());
    }

}
