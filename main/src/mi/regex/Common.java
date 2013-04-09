package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:58
 */
public class Common {

//    static IRegex EndRegex = new IRegex() {
//        @Override
//        public int match(String text, int start, int end, IRegex next) {
//            return 1;
//        }
//    };

//    static boolean match(IRegex regex, IRegex next, String text, int start, int end, int lower, int upper) {
//        if (upper <= 0) return next.match(text, start, end, EndRegex);
//        if (!regex.match(text, start, end, next)) {
//            return lower <= 0;
//        }
//        return match(text, newStart, end, lower-1, upper-1);
//    }
//
//    static int match(IRegex regex, String text, int start, int end, int lower) {
//        int newStart = regex.match(text, start, end);
//        if (newStart < 0) {
//            return lower > 0 ? -1 : start;
//        }
//        return match(regex, text, newStart, end, lower-1);
//    }
//
    public static void main(String[] args) throws InterruptedException {
        Regex regex = new Regex("(ab|a)*c$");
        regex.dump();
        Match match = regex.match("abc");
        match.dump();

        match = regex.match("abcd");
        match.dump();

        System.out.println();
        Thread.sleep(100);
        regex = new Regex("ab*\\0");
        regex.dump();
    }
}
