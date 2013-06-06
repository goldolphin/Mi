package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-06-06 22:35
 */
public class Bitmap {
    private byte[] bytes;

    private static final int M = 8;

    /**
     *
     * @param size must be divisible by 8
     */
    public Bitmap(int size) {
        bytes = new byte[size/M];
    }

    public boolean get(int n) {
        return (bytes[n/M] & (1 << (n%M))) != 0;
    }

    public void set(int n, boolean value) {
        if (value) {
            bytes[n/M] |= 1 << (n%M);
        } else {
            bytes[n/M] &= ~(1 << (n%M));
        }
    }

    public static void main(String[] args) {
        Bitmap map = new Bitmap(256*256);
        char c = '我';
        System.out.println((int)'们');
        System.out.println(map.get(c));
        map.set(c, true);
        System.out.println(map.get(c));
        map.set(c, false);
        System.out.println(map.get(c));
    }
}
