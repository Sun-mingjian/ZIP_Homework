package co.zip.candidate.userapi.exception;

import co.zip.candidate.userapi.dto.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> UserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder().description(ex.getMessage()).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserEmailExistingException.class)
    public ResponseEntity<?> UserEmailExistingExceptionHandler(UserEmailExistingException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder().description(ex.getMessage()).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAccountTypeException.class)
    public ResponseEntity<?> InvalidAccountTypeExceptionHandler(InvalidAccountTypeException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder().description(ex.getMessage()).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAccountExistingException.class)
    public ResponseEntity<?> UserAccountExistingExceptionHandler(UserAccountExistingException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder().description(ex.getMessage()).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientCreditException.class)
    public ResponseEntity<?> InsufficientCreditExceptionHandler(InsufficientCreditException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder().description(ex.getMessage()).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, List<String>> body = new HashMap<>();
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
