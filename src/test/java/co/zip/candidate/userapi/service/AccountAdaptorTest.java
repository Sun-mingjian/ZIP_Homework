package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.AccountDetails;
import co.zip.candidate.userapi.dto.UserDetails;
import co.zip.candidate.userapi.entity.Account;
import co.zip.candidate.userapi.entity.User;
import co.zip.candidate.userapi.enums.AccountType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountAdaptorTest {

    @Test
    @DisplayName("Adapt to account details would work as expected")
    public void adaptToAccountDetails() {
        long accountId = 1;
        String accountNumber = "124343434";
        BigDecimal interest = BigDecimal.valueOf(0.03);
        BigDecimal monthlyAccountFee = BigDecimal.valueOf(10);
        BigDecimal minimalRepayment = BigDecimal.valueOf(20.11);
        Account testAccount = Account.builder().id(accountId).accountNumber(accountNumber).accountType(AccountType.ZIP_MONEY)
                .interest(interest).monthlyAccountFee(monthlyAccountFee).minimalWeeklyRepayment(minimalRepayment).build();
        AccountDetails accountDetails = AccountAdaptor.adaptToAccountDetails(testAccount);
        assertEquals(accountDetails.getAccountType(), AccountType.ZIP_MONEY.description);
        assertEquals(accountDetails.getInterest(), interest);
        assertEquals(accountDetails.getMonthlyAccountFee(), monthlyAccountFee);
        assertEquals(accountDetails.getMinimalWeeklyRepayment(), minimalRepayment);
    }

    @Test
    @DisplayName("Adapt to account would work as expected")
    public void testAdaptToAccount() {
        Account account = AccountAdaptor.adaptToAccount(AccountType.ZIP_PAY.description);
        assertEquals(account.getAccountType(), AccountType.ZIP_PAY);
        assertEquals(account.getInterest(), BigDecimal.valueOf(AccountType.ZIP_PAY.interest));
        assertEquals(account.getMonthlyAccountFee(), AccountType.ZIP_PAY.monthlyAccountFee);
        assertEquals(account.getMinimalWeeklyRepayment(), AccountType.ZIP_PAY.minimalWeeklyRepayment);
    }
}
