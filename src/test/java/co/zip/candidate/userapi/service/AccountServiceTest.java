package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.AccountDetails;
import co.zip.candidate.userapi.entity.Account;
import co.zip.candidate.userapi.enums.AccountType;
import co.zip.candidate.userapi.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Test
    @DisplayName("Get all accounts would work as expected")
    public void testFetchAllAccounts() {
        String accountNumberOne = "3242345";
        String accountNumberTwo = "3453453";
        Account accountOne = Account.builder().accountNumber(accountNumberOne).accountType(AccountType.ZIP_MONEY).build();
        Account accountTwo = Account.builder().accountNumber(accountNumberTwo).accountType(AccountType.ZIP_PAY).build();
        when(accountRepository.findAll()).thenReturn(asList(accountOne, accountTwo));
        List<AccountDetails> accounts = accountService.fetchAllAccounts();
        assertEquals(accounts.size(), 2);
        assertTrue(accounts.stream().map(AccountDetails::getAccountNumber).collect(Collectors.toList()).contains(accountNumberOne));
        assertTrue(accounts.stream().map(AccountDetails::getAccountNumber).collect(Collectors.toList()).contains(accountNumberTwo));
    }

    @Test
    @DisplayName("Get all accounts would return empty list")
    public void testFetchAllAccountsWhenThereAreNone() {
        when(accountRepository.findAll()).thenReturn(EMPTY_LIST);
        List<AccountDetails> accounts = accountService.fetchAllAccounts();
        assertEquals(accounts.size(), 0);
    }
}


