package samdasu.recipt.api.gpt.exception;

public class MaxTokenException extends RuntimeException {

    public MaxTokenException() {
        super();
    }

    public MaxTokenException(String message) {
        super(message);
    }

}
