package mi.lexer;

import mi.stream.ICharStream;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * User: goldolphin
 * Time: 2013-05-22 22:59
 */
public class LexStream implements ICharStream {

    private ICharStream source;
    private int charNum;
    private int lineNum;
    private int colNum;
    private ArrayList<Integer> lineStarts;

    public LexStream(ICharStream source) {
        this.source = source;
        charNum = 0;
        lineNum = 0;
        colNum = 0;
        lineStarts = new ArrayList<>(1024);
        lineStarts.add(0);
    }

    public char peek() {
        char c = source.peek();
        if (c == '\r') {
            source.poll();
            return peek();
        }
        return c;
    }

    public char poll() {
        char c = source.poll();
        if (c == '\r') {
            return poll();
        }
        charNum ++;
        if (c == '\n') {
            lineNum ++;
            colNum = 0;
        } else {

        }

        source.poll();
        return c;
    }

    @Override
    public void retract() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getLineNum() {
        return lineNum;
    }

    public int getColNum() {
        return colNum;
    }
}
