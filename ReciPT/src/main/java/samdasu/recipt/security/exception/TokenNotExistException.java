package samdasu.recipt.security.exception;

public class TokenNotExistException extends RuntimeException {
    public TokenNotExistException(String message) {
        super(message);
    }

    public TokenNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}