import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private final static int R = 256;

    public static void transform() {
        String in = BinaryStdIn.readString();
        CircularSuffixArray suffixArray = new CircularSuffixArray(in);
        // Find first
        int first = 0;
        for (int i = 0; i < in.length(); i++) {
            int index = suffixArray.index(i);
            if (index == 0) {
                // Found first (new location of original string)
                first = i;
                break;
            }
        }
        BinaryStdOut.write(first);
        // Get t
        for (int i = 0; i < in.length(); i++) {
            // index is letter in input string
            int index = suffixArray.index(i);
            char c = in.charAt((index + in.length() - 1) % in.length());
            BinaryStdOut.write(c, 8);
        }
        BinaryStdOut.close();
    }

    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String t = BinaryStdIn.readString();
        int length = t.length();

        char[] firstColumn = new char[length];

        // Build next array via key-indexed counting
        int[] next = new int[length];
        int[] count = new int[R + 1];
        // Count frequencies of letters
        for (int i = 0; i < length; i++) {
            count[t.charAt(i) + 1]++;
        }
        // Calculate cumulates
        for (int r = 0; r < R; r++) {
            count[r + 1] += count[r];
        }
        for (int i = 0; i < length; i++) {
            next[count[t.charAt(i)]] = i;
            firstColumn[count[t.charAt(i)]++] = t.charAt(i);
        }

        // Restore text
        int current = first;
        for (int i = 0; i < length; i++) {
            BinaryStdOut.write(firstColumn[current], 8);
            current = next[current];
        }

        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) { transform(); }
        if (args[0].equals("+")) { inverseTransform(); }
    }
}
