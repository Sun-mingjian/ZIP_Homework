package co.zip.candidate.userapi.exception;
@SuppressWarnings("serial")
public class InsufficientCreditException extends RuntimeException {
    public InsufficientCreditException(String message) {
        super(message);
    }
}
