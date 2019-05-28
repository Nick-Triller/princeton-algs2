public class BoggleDict {
    private static final int R = 26;        // A-Z

    private Node root;      // root of trie
    private int n;          // number of keys in trie

    // R-way trie node
    private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }

    public BoggleDict(String[] strings) {
        for (String s : strings) {
            add(s);
        }
    }

    /**
     * Does the set contain the given key?
     * @param key the key
     * @return {@code true} if the set contains {@code key} and
     *     {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(StringBuilder key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString;
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString;
    }

    private Node get(Node x, StringBuilder key, int d) {
        while (x != null) {
            if (d == key.length()) return x;
            int c = key.charAt(d) - 65;
            x = x.next[c];
            d++;
        }
        return null;
    }

    private Node get(Node x, String key, int d) {
        while (x != null) {
            if (d == key.length()) return x;
            int c = key.charAt(d) - 65;
            x = x.next[c];
            d++;
        }
        return null;
    }

    /**
     * Adds the key to the set if it is not already present.
     * @param key the key to add
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    private void add(String key) {
        if (key == null) throw new IllegalArgumentException("argument to add() is null");
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (!x.isString) n++;
            x.isString = true;
        }
        else {
            int c = key.charAt(d) - 65;
            x.next[c] = add(x.next[c], key, d+1);
        }
        return x;
    }

    /**
     * Returns the number of strings in the set.
     * @return the number of strings in the set
     */
    public int size() {
        return n;
    }

    /**
     * Is the set empty?
     * @return {@code true} if the set is empty, and {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }


    private Node prev = null;
    private int prevLength;

    public boolean hasKeyWithPrefix(StringBuilder prefix) {
        // WARNING: Assumes the prefix is the same as in the last call if the
        // prefix has the same or a larger length
        int d = 0;
        Node n = this.root;
        if (prev != null && prefix.length() >= prevLength) {
            n = prev;
            d = prevLength;
        }
        prevLength = prefix.length();
        n = get(n, prefix, d);
        return n != null;
    }
}
