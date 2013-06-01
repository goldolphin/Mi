package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:58
 */
public class Common {

    public static void main(String[] args) throws InterruptedException {
        Regex regex = new Regex("(ab|a)c");
        regex.dump();
        System.out.println();

        testMatch(regex, "abc");

        testMatch(regex, "aabc");

        testMatch(regex, "abcd");

        testStartWith(regex, "abcd");

        System.out.println();
        Thread.sleep(100);
        regex = new Regex("ab*\\0");
        regex.dump();
    }

    static void testMatch(Regex regex, String text) {
        System.out.println(text);
        Match match = regex.match(text);
        match.dump();
        System.out.println();
    }

    static void testStartWith(Regex regex, String text) {
        System.out.println(text);
        Match match = regex.startWith(text);
        match.dump();
        System.out.println();
    }
}
