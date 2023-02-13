package co.zip.candidate.userapi.exception;
@SuppressWarnings("serial")
public class UserAccountExistingException extends RuntimeException {
    public UserAccountExistingException(String message) {
        super(message);
    }
}
