package mi.lexer;

import java.io.IOException;
import java.io.Reader;

/**
 * User: goldolphin
 * Time: 2013-05-22 22:59
 */
public class CharReader {
    public static final char EOF = '\0';

    private Reader reader;
    private int lineNum;
    private int colNum;

    private char[] buffer = new char[1];

    public CharReader(Reader reader) {
        this.reader = reader;
        this.lineNum = 0;
        this.colNum = 0;
        forward();
    }

    public char peek() {
        return buffer[0];
    }

    public void forward() {
        try {
            if(reader.read(buffer, 0, 1) < 0) {
                buffer[0] = EOF;
                return;
            }
            char c = peek();
            if (c == '\r') {
                forward();
            } else if (c == '\n') {
                lineNum ++;
                colNum = 0;
            } else {
                colNum ++;
            }
        } catch (IOException e) {
            throw new LexException("IO error occurs.", e);
        }
    }

    public char poll() {
        char c = peek();
        forward();
        return c;
    }

    public int getLineNum() {
        return lineNum;
    }

    public int getColNum() {
        return colNum;
    }
}
