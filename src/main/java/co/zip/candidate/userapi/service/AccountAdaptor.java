package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.AccountDetails;
import co.zip.candidate.userapi.entity.Account;
import co.zip.candidate.userapi.enums.AccountType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Random;

public class AccountAdaptor {

    public static AccountDetails adaptToAccountDetails(Account account) {
        return Optional.ofNullable(account)
                .map(i -> AccountDetails.builder().balance(account.getBalance())
                        .accountType(account.getAccountType().description)
                        .accountNumber(account.getAccountNumber())
                        .interest(account.getInterest())
                        .minimalWeeklyRepayment(account.getMinimalWeeklyRepayment())
                        .monthlyAccountFee(account.getMonthlyAccountFee())
                        .dateCreated(account.getDateCreated())
                        .build())
                .orElse(null);
    }

    public static Account adaptToAccount(String accountTypeInput) {
        AccountType accountType = AccountType.get(accountTypeInput).get();
        return Account.builder().balance(BigDecimal.valueOf(0L))
                .accountType(accountType)
                .dateCreated(OffsetDateTime.now())
                .accountNumber(generateAccountNumber())
                .interest(BigDecimal.valueOf(accountType.interest))
                .minimalWeeklyRepayment(accountType.minimalWeeklyRepayment)
                .monthlyAccountFee(accountType.monthlyAccountFee)
                .build();
    }

    public static String generateAccountNumber() {
        Random rnd = new Random();
        int firstPart = 10000 + rnd.nextInt(9000);
        int secondPart = 100000 + rnd.nextInt(900000);
        return String.valueOf(firstPart) + secondPart;
    }
}
