package co.zip.candidate.userapi.exception;

public class UserEmailExistingException extends RuntimeException {
    public UserEmailExistingException(String message) {
        super(message);
    }
}
