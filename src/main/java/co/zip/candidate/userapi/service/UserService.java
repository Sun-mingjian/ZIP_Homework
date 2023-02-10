package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.UserDetails;
import co.zip.candidate.userapi.entity.User;
import co.zip.candidate.userapi.exception.UserEmailExistingException;
import co.zip.candidate.userapi.exception.UserNotFoundException;
import co.zip.candidate.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

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

    public void validateUserEmail(String email) {
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
}
