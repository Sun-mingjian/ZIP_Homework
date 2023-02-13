package co.zip.candidate.userapi.exception;
@SuppressWarnings("serial")
public class UserEmailExistingException extends RuntimeException {
    public UserEmailExistingException(String message) {
        super(message);
    }
}
