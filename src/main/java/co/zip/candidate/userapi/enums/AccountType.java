package co.zip.candidate.userapi.enums;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum AccountType {

    ZIP_PAY("ZIP PAY", 0, BigDecimal.valueOf(7.95D), BigDecimal.valueOf(10D)),

    ZIP_MONEY("ZIP MONEY", 0.03, BigDecimal.valueOf(7.95D), BigDecimal.valueOf(20D));

    public final String description;

    public final double interest;

    public final BigDecimal monthlyAccountFee;

    public final BigDecimal minimalWeeklyRepayment;


    AccountType(String description, double interest, BigDecimal monthlyAccountFee, BigDecimal minimalWeeklyRepayment) {
        this.description = description;
        this.interest = interest;
        this.monthlyAccountFee = monthlyAccountFee;
        this.minimalWeeklyRepayment = minimalWeeklyRepayment;
    }

    public static final Map<String, AccountType> lookup = new HashMap<>();

    static {
        for (AccountType d : AccountType.values()) {
            lookup.put(d.description, d);
        }
    }
    public static Optional<AccountType> get(String description) {
        return Optional.ofNullable(lookup.get(description));
    }
}
