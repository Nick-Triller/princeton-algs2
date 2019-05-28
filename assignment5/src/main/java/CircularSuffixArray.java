import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private final int length;
    private final Integer[] indices;

    public CircularSuffixArray(String s) {
        if (s == null) { throw new IllegalArgumentException(); }
        this.length = s.length();
        indices = new Integer[s.length()];
        for (int i = 0; i < indices.length; i++) { indices[i] = i; }

        Comparator<Integer> comparator = (i1, i2) -> {
            // i2 and i2 are start indices of circular suffix in s
            if (i1 < 0 || i1 >= length() || i2 < 0 || i2 >= length()) {
                validateIndex(i1, i2);
            }
            for (int i = 0; i < length(); i++) {
                char c1 = s.charAt((i + i1) % length());
                char c2 = s.charAt((i + i2) % length());
                if (c1 > c2) { return 1; }
                if (c2 > c1) { return -1; }
            }
            return 0;
        };

        Arrays.sort(indices, comparator);
    }

    public int length() {
        return this.length;
    }

    public int index(int i) {
        validateIndex(i);
        return indices[i];
    }

    private void validateIndex(int... x) {
        for (int i : x) {
            if (i < 0 || i >= length()) { throw new IllegalArgumentException(); }
        }
    }

    public static void main(String[] args) {
        String string = "ABRACADABRA!";
        CircularSuffixArray suffixArray = new CircularSuffixArray(string);
        System.out.println(suffixArray.length);
        for (int i = 0; i < string.length(); i++) {
            System.out.println(suffixArray.index(i));
        }
    }
}
