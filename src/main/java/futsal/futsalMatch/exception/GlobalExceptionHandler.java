package futsal.futsalMatch.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRegionException.class)
    public ResponseEntity<ErrorMessage> handleInvalidRegionException(InvalidRegionException e) {
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }
}
