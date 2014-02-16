package mi.parser;

/**
 * @author goldolphin
 *         2014-02-13 16:12
 */
public class ParseException extends RuntimeException {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
