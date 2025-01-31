package futsal.futsalMatch.exception;

public class UnexpectedResponseStatusException extends RuntimeException {
    public UnexpectedResponseStatusException(String message) {
        super(message);
    }
}
