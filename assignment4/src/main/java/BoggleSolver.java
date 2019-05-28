import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.SET;

public class BoggleSolver {
    private final BoggleDict dict;

    public BoggleSolver(String[] dictionary) {
        // assume each word in dictionary contains only uppercase letters A through Z
        this.dict = new BoggleDict(dictionary);
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int rows = board.rows();
        int cols = board.cols();
        // Create tiles
        Tile[] tiles = new Tile[rows * cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                tiles[y * cols + x] = new Tile(x, y, board.getLetter(y, x));
            }
        }
        // Precompute neighbor graph
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Tile tile = tiles[y * cols + x];
                tile.neighbors = neighbors(x, y, tiles, rows, cols);
            }
        }
        return dfsRecursive(tiles, rows, cols);
    }

    private Iterable<String> dfsRecursive(Tile[] tiles, int rows, int cols) {
        SET<String> result = new SET<>();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                // Run DFS for each tile on board
                Tile tile = tiles[y * cols + x];
                dfsHelper(tile, result, new StringBuilder());
                // Reset marking for start tile
                tile.marked = false;
            }
        }
        return result;
    }

    private void dfsHelper(Tile tile, SET<String> collector, StringBuilder word) {
        word.append(tile.letter == 'Q' ? "QU" : tile.letter);
        tile.marked = true;
        if (word.length() > 2 && this.dict.contains(word)) {
            // Found a match
            collector.add(word.toString());
        }
        // Only continue exploring if there are words in the dict starting with the current prefix
        if (this.dict.hasKeyWithPrefix(word)) {
            // Continue search
            for (Tile neighbor : tile.neighbors) {
                if (!neighbor.marked) {
                    dfsHelper(neighbor, collector, word);
                    // Backtrack
                    neighbor.marked = false;
                    int start = neighbor.letter == 'Q' ? word.length() - 2 : word.length() - 1;
                    word.delete(start, word.length());
                }
            }
        }
    }

    private Iterable<Tile> neighbors(int x, int y, Tile[] tiles, int rows, int cols) {
        Bag<Tile> neighbors = new Bag<>();
        for (int row = Math.max(0, y - 1); row <= Math.min(rows - 1, y + 1); row++) {
            for (int col = Math.max(0, x - 1); col <= Math.min(cols - 1, x + 1); col++) {
                if (row == y && col == x) {
                    continue;
                }
                neighbors.add(tiles[row * cols + col]);
            }
        }
        return neighbors;
    }

    public int scoreOf(String word) {
        if (!this.dict.contains(word)) {
            // Word not in dictionary
            return 0;
        }
        int wordLength = word.length();
        if (wordLength <= 2) return 0;
        if (wordLength <= 4) return 1;
        if (wordLength <= 5) return 2;
        if (wordLength <= 6) return 3;
        if (wordLength <= 7) return 5;
        return 11;
    }

    private class Tile {
        private final int x;
        private final int y;
        private final char letter;
        private boolean marked = false;
        private Iterable<Tile> neighbors;

        Tile(int x, int y, char letter) {
            this.x = x;
            this.y = y;
            this.letter = letter;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) return true;
            if (other == null) return false;
            if (other.getClass() != this.getClass()) return false;
            Tile that = (Tile) other;
            return this.x == that.x && this.y == that.y && letter == that.letter;
        }

        @Override
        public int hashCode() {
            int hashX = ((Integer) x).hashCode();
            int hashY = ((Integer) y).hashCode();
            int hashZ = ((Character) letter).hashCode();
            return 31 * hashX + hashY + hashZ;
        }
    }
}
