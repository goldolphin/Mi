package mi.lexer;

import java.io.IOException;
import java.io.Reader;

/**
 * User: goldolphin
 * Time: 2013-05-18 10:44
 */
public class Lexer {
    private Reader reader;
    private int lineNum;
    private int colNum;

    private static final char EOF = '\0';
    private char[] buffer = new char[1];

    private TokenInfo token;

    public Lexer(Reader reader) {
        this.reader = reader;
        this.lineNum = 0;
        this.colNum = 0;
        forwardChar();
        forwardInternal();
    }

    char peekChar() {
        return buffer[0];
    }

    void forwardChar() {
        try {
            if(reader.read(buffer, 0, 1) < 0) {
                buffer[0] = EOF;
                return;
            }
            char c = peekChar();
            if (c == '\r') {
                forwardChar();
            }
            else if (c == '\n') {
                lineNum ++;
                colNum = 0;
            } else {
                colNum ++;
            }
        } catch (IOException e) {
            throw new LexException("IO error occurs.", e);
        }
    }

    char pollChar() {
        char c = peekChar();
        forwardChar();
        return c;
    }

    public TokenInfo peek() {
        return token;
    }

    public void forward() {
        if (token == null) {
            throw new LexException("End of file.");
        }
        forwardInternal();
    }

    public TokenInfo poll() {
        TokenInfo t = peek();
        forward();
        return t;
    }

    void forwardInternal() {
        char c = peekChar();
        if (c == EOF) {
            token = null;
            return;
        }
        forwardChar();
        if (c == '/' && peekChar() == '/') {
            forwardChar();
            consumeComment();
            forwardInternal();
        } else if (Symbols.isIdHead(c)) {
            //parseId();
        } else if (Symbols.isOperatorPart(c)) {
            //parseOperator();
        } else if (Symbols.isSpaceChar(c)) {
            forwardChar();
            forwardInternal();
        } else {
            //
        }

    }

    void consumeComment() {
        char c = peekChar();
        if (c == EOF) {
            return;
        }
        if (Symbols.isNewLineChar(c)) {
            forwardChar();
            return;
        }
        consumeComment();
    }
}
