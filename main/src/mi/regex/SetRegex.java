package mi.regex;

import mi.stream.ICharStream;

import java.util.Collection;

/**
 * User: goldolphin
 * Time: 2013-06-06 23:22
 */
public class SetRegex extends AtomRegex {
    //private final boolean inverse;

    private final Bitmap map;
    private final int min;
    private final int max;


    public SetRegex(char[] array) {
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (char c: array) {
            if (min > c) {
                min = c;
            }
            if (max < c) {
                max = c;
            }
        }
        this.min = min;
        this.max = max;
        map = new Bitmap(max-min+1);
        for (char c: array) {

        }
    }

    void set(char c) {
        map.set(c-min, true);
    }



    @Override
    void print(int indent) {
        describe(indent, String.valueOf(max-min+1));
        next.print(indent);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        char c = stream.peek();
        if (map.get(c-min)) {
            match.append(stream.poll());
            return next.match(stream, match);
        }
        return false;
    }
}
