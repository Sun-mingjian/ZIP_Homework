package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.AccountDetails;
import co.zip.candidate.userapi.dto.UserDetails;
import co.zip.candidate.userapi.entity.Account;
import co.zip.candidate.userapi.entity.User;
import co.zip.candidate.userapi.enums.AccountType;
import co.zip.candidate.userapi.exception.InsufficientCreditException;
import co.zip.candidate.userapi.exception.InvalidAccountTypeException;
import co.zip.candidate.userapi.exception.UserAccountExistingException;
import co.zip.candidate.userapi.exception.UserEmailExistingException;
import co.zip.candidate.userapi.exception.UserNotFoundException;
import co.zip.candidate.userapi.repository.AccountRepository;
import co.zip.candidate.userapi.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("Get user by Id would work as expected")
    public void testGetUser() {
        String testName = "Michael";
        String testEmail = "Michael@gmail.com";
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);

        User testUser = User.builder().name(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        long testUserId = 2l;
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        UserDetails userDetails = userService.getUser(testUserId);
        assertEquals(userDetails.getUserName(), testName);
        assertEquals(userDetails.getEmail(), testEmail);
        assertEquals(userDetails.getMonthlySalary(), monthlySalary);
        assertEquals(userDetails.getMonthlyExpense(), monthlyExpense);
    }

    @Test
    @DisplayName("Get not found user by Id would raise an exception")
    public void testGetUserRaiseException() {
        String testName = "Michael";
        String testEmail = "Michael@gmail.com";
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        long testUserId = 2l;
        when(userRepository.findById(testUserId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUser(testUserId));
    }

    @Test
    @DisplayName("Get all users would work as expected")
    public void testFetchAllUsers() {
        String testName = "Michael";
        String testEmail = "Michael@gmail.com";
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        User testUser = User.builder().name(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();

        String testNameTwo = "Jame";
        String testEmailTwo = "Jame@gmail.com";
        BigDecimal monthlySalaryTwo = BigDecimal.valueOf(3000);
        BigDecimal monthlyExpenseTwo = BigDecimal.valueOf(2000);
        User testUserTwo = User.builder().name(testNameTwo).email(testEmailTwo).monthlySalary(monthlySalaryTwo).monthlyExpense(monthlyExpenseTwo).build();

        when(userRepository.findAll()).thenReturn(asList(testUser, testUserTwo));
        List<UserDetails> users = userService.fetchAllUsers();
        assertEquals(users.size(), 2);

        assertTrue(users.stream().map(UserDetails::getUserName).collect(Collectors.toList()).contains(testNameTwo));
        assertTrue(users.stream().map(UserDetails::getUserName).collect(Collectors.toList()).contains(testName));
        assertTrue(users.stream().map(UserDetails::getEmail).collect(Collectors.toList()).contains(testEmail));
        assertTrue(users.stream().map(UserDetails::getEmail).collect(Collectors.toList()).contains(testEmailTwo));
    }

    @Test
    @DisplayName("Get all users would return empty list")
    public void testFetchAllUsersWhenThereAreNone() {
        when(userRepository.findAll()).thenReturn(EMPTY_LIST);
        List<UserDetails> users = userService.fetchAllUsers();
        assertEquals(users.size(), 0);
    }

    @Test
    @DisplayName("Validate Email would raise Exception when it exists")
    public void testValidateUserEmailToRaiseException() {
        String testEmail = "Michael@gmail.com";
        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(Mockito.mock(User.class)));
        assertThrows(UserEmailExistingException.class, () -> userService.validateIfEmailExists(testEmail));
    }

    @Test
    @DisplayName("Validate Email would do nothing when user email does not exist")
    public void testValidateUserEmail() {
        String testEmail = "Michael@gmail.com";
        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> userService.validateIfEmailExists(testEmail));
    }

    @Test
    @DisplayName("Create new user works as expected")
    public void testCreateNewUser() {
        String testName = "Michael";
        String testEmail = "Michael@gmail.com";
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        User testUser = User.builder().name(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        UserDetails testUserDetails = UserDetails.builder().userName(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        when(userRepository.saveAndFlush(testUser)).thenReturn(testUser);
        UserDetails result = userService.createNewUser(testUserDetails);
        assertEquals(result.getUserName(), testName);
        assertEquals(result.getEmail(), testEmail);
        assertEquals(result.getMonthlySalary(), monthlySalary);
        assertEquals(result.getMonthlyExpense(), monthlyExpense);
    }

    @Test
    @DisplayName("Create new user account works as expected")
    public void testCreateNewUserAccount() {
        String testName = "Michael";
        String testEmail = "Michael@gmail.com";
        Long testUserId = 3L;
        String accountType = "ZIP PAY";
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        User testUser = User.builder().name(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        UserDetails testUserDetails = UserDetails.builder().userName(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        userService.createNewUserAccount(testUserId, accountType);
        verify(accountRepository).saveAndFlush(any());
    }

    @Test
    @DisplayName("Create new user account will raise exception when user is not existing")
    public void testCreateNewUserAccountWithNoneExistingUser() {
        String testName = "Michael";
        String testEmail = "Michael@gmail.com";
        Long testUserId = 3L;
        String accountType = "ZIP PAY";
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        when(userRepository.findById(testUserId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.createNewUserAccount(testUserId, accountType));
    }

    @Test
    @DisplayName("Validate if user can create new account works as expected")
    public void testValidateIfUserCanCreateNewAccount() {
        String testName = "Michael";
        String testEmail = "Michael@gmail.com";
        Long testUserId = 3L;
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        User testUser = User.builder().name(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        AccountDetails accountDetails = AccountDetails.builder().accountType(AccountType.ZIP_MONEY.description).build();
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        assertDoesNotThrow(() -> userService.validateIfUserCanCreateNewAccount(testUserId, accountDetails));
    }

    @Test
    @DisplayName("Validate if user can create new account works will raise exception when user is not exiting")
    public void testValidateIfUserCanCreateNewAccountWithNoneExistingUser() {
        String testName = "Michael";
        String testEmail = "Michael@gmail.com";
        Long testUserId = 3L;
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        User testUser = User.builder().name(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        AccountDetails accountDetails = AccountDetails.builder().accountType(AccountType.ZIP_MONEY.description).build();
        when(userRepository.findById(testUserId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.validateIfUserCanCreateNewAccount(testUserId, accountDetails));
    }

    @Test
    @DisplayName("Validate if user can create new account works will raise exception when account type is invalid")
    public void testValidateIfUserCanCreateNewAccountWithInvalidAccountType() {
        String testName = "Michael";
        String testEmail = "Michael@gmail.com";
        Long testUserId = 3L;
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        User testUser = User.builder().name(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        AccountDetails accountDetails = AccountDetails.builder().accountType("invalidAccountType").build();
        assertThrows(InvalidAccountTypeException.class, () -> userService.validateIfUserCanCreateNewAccount(testUserId, accountDetails));
    }

    @Test
    @DisplayName("Validate if user can create new account works will raise exception when an existing account linked")
    public void testValidateIfUserCanCreateNewAccountWithExistingAccount() {
        String testName = "Michael";
        String testEmail = "Michael@gmail.com";
        String accountType = "ZIP PAY";
        Long testUserId = 3L;
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        User testUser = User.builder().name(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense)
                .account(Account.builder().build()).build();
        AccountDetails accountDetails = AccountDetails.builder().accountType(accountType).build();
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        assertThrows(UserAccountExistingException.class, () -> userService.validateIfUserCanCreateNewAccount(testUserId, accountDetails));
    }

    @Test
    @DisplayName("Validate if user can create new account works will raise exception when there is no sufficient credit")
    public void testValidateIfUserCanCreateNewAccountWithoutSufficientCredit() {
        String testName = "Michael";
        String testEmail = "Michael@gmail.com";
        String accountType = "ZIP PAY";
        Long testUserId = 3L;
        BigDecimal monthlySalary = BigDecimal.valueOf(1000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(10);
        User testUser = User.builder().name(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        AccountDetails accountDetails = AccountDetails.builder().accountType(accountType).build();
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        assertThrows(InsufficientCreditException.class, () -> userService.validateIfUserCanCreateNewAccount(testUserId, accountDetails));
    }

}


