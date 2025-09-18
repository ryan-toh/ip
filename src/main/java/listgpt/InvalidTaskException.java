package listgpt;

/**
 * An exception thrown if task creation throws an exception due to invalid arguments.
 */
public class InvalidTaskException extends RuntimeException {
    /**
     * Constructs a new {@code InvalidTaskException} with the specified detail message.
     *
     * @param message the detail message explaining why the task is invalid
     */
    public InvalidTaskException(String message) {
        super(message);
    }
}
