package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.dto.AccountDetails;
import co.zip.candidate.userapi.dto.UserDetails;
import co.zip.candidate.userapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDetails> getUserById(@PathVariable(value = "userId") String userId) {
        log.debug("Fetching user details with id : {} ", userId);
        UserDetails user = userService.getUser(Long.parseLong(userId));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDetails>> fetchAllUsers() {
        log.debug("Fetching all user details");
        List<UserDetails> users = userService.fetchAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/user")
    public ResponseEntity<UserDetails> createUser(@Valid @RequestBody UserDetails userDetails) {
        userService.validateIfEmailExists(userDetails.getEmail());
        log.debug("Create user with email : {}", userDetails.getEmail());
        UserDetails user = userService.createNewUser(userDetails);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/user/{userId}/account")
    public ResponseEntity<Void> createUserAccount(@PathVariable(value = "userId") String id,
                                                  @Valid @RequestBody AccountDetails accountDetails) {
        Long userId = Long.parseLong(id);
        userService.validateIfUserCanCreateNewAccount(userId, accountDetails);
        log.debug("Create user {} account with account type : {}", userId, accountDetails.getAccountType());
        userService.createNewUserAccount(userId, accountDetails.getAccountType());
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
