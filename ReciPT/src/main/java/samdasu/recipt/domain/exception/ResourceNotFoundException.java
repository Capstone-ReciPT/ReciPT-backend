package samdasu.recipt.domain.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("No Such Resource");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
