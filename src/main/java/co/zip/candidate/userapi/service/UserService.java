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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    @Transactional
    public UserDetails getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Sorry, this user is not found"));
        log.debug("User is found with user name: {} and email: {}.", user.getName(), user.getEmail());
        return UserAdaptor.adaptToUserDetails(user);
    }

    @Transactional
    public List<UserDetails> fetchAllUsers() {
        List<User> users = userRepository.findAll();
        log.debug("There are {} users being fetched.", users.size());
        return users.stream().map(UserAdaptor::adaptToUserDetails).collect(Collectors.toList());
    }

    public void validateIfEmailExists(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new UserEmailExistingException(String.format("Sorry, this email has been associated with user: %s", user.getName()));
        });
    }

    @Transactional(rollbackOn = Exception.class)
    public UserDetails createNewUser(UserDetails userDetails) {
        User newUser = userRepository.saveAndFlush(UserAdaptor.adaptToUserEntity(userDetails));
        log.debug(String.format("New user(id: %s) has been created with email %s", newUser.getId(), newUser.getEmail()));
        return UserAdaptor.adaptToUserDetails(newUser);
    }

    @Transactional(rollbackOn = Exception.class)
    public void createNewUserAccount(Long userId, String accountType) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Sorry, this user is not found"));
        Account account = AccountAdaptor.adaptToAccount(accountType);
        account.setUser(user);
        accountRepository.saveAndFlush(account);
        log.debug(String.format("New account of type %s has been created for user %s", accountType, userId));
    }

    @Transactional
    public void validateIfUserCanCreateNewAccount(Long userId, AccountDetails accountDetails) {
        AccountType.get(accountDetails.getAccountType()).orElseThrow(() -> new InvalidAccountTypeException(
                String.format("Sorry, this account type is invalid, please try using any of these: '%s', '%s'", "ZIP MONEY", "ZIP PAY")));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Sorry, this user is not found"));
        Optional.ofNullable(user.getAccount()).ifPresent(i -> {
            throw new UserAccountExistingException("Sorry, this user has one account already");
        });
        if (user.getMonthlySalary().compareTo(user.getMonthlyExpense().add(BigDecimal.valueOf(1000L))) < 0) {
            throw new InsufficientCreditException("Sorry, this user does not have the sufficient credit of $1000");
        }
    }
}
