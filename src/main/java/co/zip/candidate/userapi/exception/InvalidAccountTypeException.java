package co.zip.candidate.userapi.exception;
@SuppressWarnings("serial")
public class InvalidAccountTypeException extends RuntimeException {
    public InvalidAccountTypeException(String message) {
        super(message);
    }
}
