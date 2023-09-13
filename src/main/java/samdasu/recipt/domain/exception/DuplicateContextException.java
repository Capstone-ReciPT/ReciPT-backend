package samdasu.recipt.domain.exception;

public class DuplicateContextException extends RuntimeException {
    public DuplicateContextException(String message) {
        super(message);
    }

    public DuplicateContextException(String message, Throwable cause) {
        super(message, cause);
    }
}
