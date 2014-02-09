package mi.parser;

import mi.common.Bitmap;
import mi.common.CharHashMap;

/**
 * User: goldolphin
 * Time: 2014-02-09 20:25
 */
public class State {
    private CharHashMap<State> nextMap = new CharHashMap<>();
    private FailPointer fail;


    public static class FailPointer {
        public final State in;
        public final State out;
        public final Bitmap symbols = new Bitmap(4);

        public FailPointer(State in, State out) {
            this.in = in;
            this.out = out;
        }
    }
}
