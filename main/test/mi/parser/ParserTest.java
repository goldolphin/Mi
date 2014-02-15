package mi.parser;

import mi.stream.StringStream;
import org.junit.Assert;

/**
 * User: goldolphin
 * Time: 2014-02-15 17:40
 */
public class ParserTest {

    @org.junit.Test
    public void testParse1() throws Exception {
        ParserBuilder parserBuilder = new ParserBuilder();
        Parser parser = parserBuilder.build(new Grammar() {
            @Override
            protected void define() {
                addProduction(N("A"), T("abcde"));
            }
        }, false);
        Assert.assertTrue(parser.parse(new StringStream("abcde")));
        Assert.assertFalse(parser.parse(new StringStream("abcdef")));
        Assert.assertFalse(parser.parse(new StringStream("abcd")));
        Assert.assertFalse(parser.parse(new StringStream("bcd")));
        Assert.assertFalse(parser.parse(new StringStream("")));
    }

    @org.junit.Test
    public void testParse2() throws Exception {
        ParserBuilder parserBuilder = new ParserBuilder();
        Parser parser = parserBuilder.build(new Grammar() {
            @Override
            protected void define() {
                addProduction(N("A"), N("B"), T("b"));
                addProduction(N("B"), N("A"), T("a"));
                addProduction(N("A"), T("z"));
                addProduction(N("A"), T("cd"), N("A"), T("ef"));
            }
        }, true);
        Assert.assertTrue(parser.parse(new StringStream("z"))); // A
        Assert.assertTrue(parser.parse(new StringStream("zaba"))); // B
        Assert.assertTrue(parser.parse(new StringStream("zabab"))); // A
        Assert.assertTrue(parser.parse(new StringStream("cdcdzefef"))); // A
        Assert.assertTrue(parser.parse(new StringStream("cdzabef"))); // A
        Assert.assertTrue(parser.parse(new StringStream("cdcdzababefef"))); // A
        Assert.assertFalse(parser.parse(new StringStream("cdcdzababefefe")));
    }

}
