package mi.parser.character;

import mi.common.IntStack;
import mi.parser.ParseException;

import java.util.ArrayList;

/**
 * User: goldolphin
 * Time: 2013-07-05 16:29
 */
class LeftRec<T> implements IPattern<T> {
    private final IPattern<T> raw;
    private final ArrayList<PosEntry<T>> posStack;

    public LeftRec(IPattern<T> raw) {
        this.raw = raw;
        posStack = new ArrayList<>();
        posStack.add(new PosEntry(-1));
    }

    @Override
    public T match(IParseStream stream) {
        int pos = stream.tell();
        PosEntry<T> top = posStack.get(posStack.size()-1);
        if (top.pos > pos) {
            throw new ParseException(String.format("PosStackError: %d > %d", top, pos));
        }
        if (top.pos == pos) {
            if (top.value == null) {
                top.isLeftRec = true;
            } else {
                ((LRParseStream)stream).yield();
            }
            return top.value;
        }
        PosEntry<T> newTop = new PosEntry(pos);
        posStack.add(newTop);
        T t = raw.match(stream);
        if (newTop.isLeftRec && t != null) {
            LRParseStream lrStream = new LRParseStream(stream);
            while (true) {
                newTop.value = t;
                lrStream.lock();
                t = raw.match(lrStream);
                if (t == null) {
                    t = newTop.value;
                    break;
                }
            }
        }
        posStack.remove(posStack.size()-1);
        return t;
    }

    private static class PosEntry<T> {
        public final int pos;
        public boolean isLeftRec;
        public T value;

        private PosEntry(int pos) {
            this.pos = pos;
            isLeftRec = false;
            value = null;
        }
    }
}