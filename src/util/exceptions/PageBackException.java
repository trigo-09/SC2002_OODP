package util.exceptions;

/**
 * Represents an exception that is thrown to simulate toggling to the previous page.
 */
public class PageBackException extends RuntimeException {

    /**
     * Creates a new instance of the {@link PageBackException} class with error message = "Page back".
     *
     */
    public PageBackException() {
        super("Page Back");
    }
}
