import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    // Must be n*R or better in the worst case
    // and proportional to n + R or better in practice on inputs that
    // arise when compressing typical English text, where n is the number
    // of characters in the input and R is the alphabet size.
    // Memory used must be proportional to n + R (or better) in the worst case

    public static void encode() {
        char[] seq = new char[256]; // 256 extended ASCII characters
        for (int i = 0; i < 256; i++) {
            seq[i] = (char) i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int index = find(seq, c);
            // System.out.println(index);
            BinaryStdOut.write(index, 8);
            moveToFront(seq, index);
        }
        BinaryStdOut.close();
    }

    public static void decode() {
        char[] seq = new char[256]; // 256 extended ASCII characters
        for (int i = 0; i < 256; i++) {
            seq[i] = (char) i;
        }
        while (!BinaryStdIn.isEmpty()) {
            int i = (int) BinaryStdIn.readChar();
            // Output ith character in seq
            BinaryStdOut.write(seq[i], 8);
            moveToFront(seq, i);
        }
        BinaryStdOut.close();
    }

    private static void moveToFront(char[] arr, int index) {
        char tmp = arr[index];
        System.arraycopy(arr, 0, arr, 1, index);
        arr[0] = tmp;
    }

    private static int find(char[] arr, char c) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == c) {
                return i;
            }
        }
        throw new RuntimeException("Character not in alphabet");
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) { encode(); }
        if (args[0].equals("+")) { decode(); }
    }
}
