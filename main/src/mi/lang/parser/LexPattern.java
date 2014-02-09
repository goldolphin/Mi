package mi.lang.parser;

import mi.common.CharType;
import mi.legacy.parser.character.IParseStream;
import mi.legacy.parser.character.IPattern;

/**
 * User: goldolphin
 * Time: 2013-07-08 03:22
 */
public class LexPattern implements IPattern<ISymbol> {
    @Override
    public ISymbol match(IParseStream stream) {
        while (true) {
            char c = stream.peek();
            if (CharType.isBlankChar(c)) {
                continue;
            }
            if (c == '/') {
                stream.poll();
                if (stream.peek() == '/') {
                    stream.poll();
                    while (!CharType.isNewLineChar(stream.peek())) {
                        stream.poll();
                    }
                    continue;
                } else {
                    stream.retract();
                    break;
                }
            }
            break;
        }
        return null;
    }

    private static void parse
}
