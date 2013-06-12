package mi.common;

import mi.stream.ICharStream;

import java.util.Arrays;

/**
 * User: goldolphin
 * Time: 2013-06-12 11:30
 */
public class KMPSearcher {
    private final char[] pattern;
    private final int[] fail;

    public KMPSearcher(String pattern) {
        this.pattern = pattern.toCharArray();
        fail = new int[this.pattern.length-1];
        for (int i = 0; i < fail.length; i ++) {
            if (i == 0) {
                fail[i] = 0;
                continue;
            }
            char c = this.pattern[i];
            for (int f = fail[i-1]; ; ) {
                if (c == this.pattern[f]) {
                    fail[i] = f+1;
                    break;
                } else if (f == 0) {
                    fail[i] = 0;
                    break;
                } else {
                    f = fail[f];
                }
            }
        }
        System.out.println(Arrays.toString(fail));
    }

    public boolean search(ICharStream stream) {
        int i = 0;
        while (true) {
            if (i >= pattern.length) {
                return true;
            }
            char c = stream.peek();
            if (c == ICharStream.EOF) {
                return false;
            }
            if (c == pattern[i]) {
                i ++;
                stream.poll();
            } else if (i == 0) {
                stream.poll();
            } else {
                i = fail[i-1];
            }
        }
    }

    public boolean search(String source) {
        int i = 0;
        for (int s = 0;;) {
            if (i >= pattern.length) {
                return true;
            }
            if (s >= source.length()) {
                return false;
            }
            char c = source.charAt(s);
            if (c == pattern[i]) {
                i ++;
                s ++;
            } else if (i == 0) {
                s ++;
            } else {
                i = fail[i-1];
            }
        }
    }

    public static void main(String[] args) {
        String p = "abeabc";
        String s = "abeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabeabc";
        KMPSearcher kmp = new KMPSearcher(p);
        System.out.println(kmp.search(s));
        System.out.println(kmp.search("sadfklhabeabeabsdfdsaf"));

        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10000; i ++) {
            kmp.search(s);
        }
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        for (int i = 0; i < 10000; i ++) {
            s.indexOf(p);
        }
        System.out.println(System.currentTimeMillis() - begin);
    }
}
