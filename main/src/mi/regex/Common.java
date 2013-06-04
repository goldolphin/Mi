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

        verify(testMatch(regex, "abc"));

        verify(!testMatch(regex, "aabc"));

        verify(!testMatch(regex, "abcd"));

        verify(testStartWith(regex, "abcd"));

        verify(testStartWith(new Regex("(a|b)+$"), "aaa"));

        verify(!testStartWith(new Regex("(a|b)+$"), "abb"));

        verify(!testStartWith(new Regex("(a|b)\1*$"), "abb"));

        System.out.println();
        Thread.sleep(100);
        try {
            regex = new Regex("ab*\\0");
            regex.dump();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        verify(false);
    }

    static boolean testMatch(Regex regex, String text) {
        System.out.println(text);
        Match match = regex.match(text);
        match.dump();
        System.out.println();
        return match.succeed();
    }

    static boolean testStartWith(Regex regex, String text) {
        System.out.println(text);
        Match match = regex.startWith(text);
        match.dump();
        System.out.println();
        return match.succeed();
    }

    static void verify(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Verification fails.");
        }
    }
}
