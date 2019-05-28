import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture picture) {
        if (picture == null) { throw new IllegalArgumentException(); }
        this.picture = new Picture(picture);
    }

    /**
     * Returns a copy of the picture
     * @return Copy of picture
     */
    public Picture picture() {
        return new Picture(this.picture);
    }

    /**
     * Returns width of the picture
     * @return Width of picture
     */
    public int width() {
        return this.picture.width();
    }

    /**
     * Returns height of picture
     * @return Height of picture
     */
    public int height() {
        return this.picture.height();
    }

    /**
     * Calculates the energy of a pixel in the picture.
     * Border pixels always have energy 1000.
     * @param x x-coordinate (0 to width - 1)
     * @param y y-coordinate (0 to height - 1)
     * @return Energy of pixel
     */
    public double energy(int x, int y) {
        validateY(y);
        validateX(x);

        // Border pixels have energy 1000
        if (y == 0 || x == 0 || y == height() - 1 || x == width() - 1) {
            return 1000;
        }

        Color left = picture.get(x - 1, y);
        Color right = picture.get(x + 1, y);

        Color above = picture.get(x, y - 1);
        Color below = picture.get(x, y + 1);

        double centralDifferenceXRed = right.getRed() - left.getRed();
        double centralDifferenceXGreen = right.getGreen() - left.getGreen();
        double centralDifferenceXBlue = right.getBlue() - left.getBlue();

        double centralDifferenceYRed = above.getRed() - below.getRed();
        double centralDifferenceYGreen = above.getGreen() - below.getGreen();
        double centralDifferenceYBlue = above.getBlue() - below.getBlue();

        double xGradient = Math.pow(centralDifferenceXRed, 2) +
                Math.pow(centralDifferenceXGreen, 2) +
                Math.pow(centralDifferenceXBlue, 2);
        double yGradient = Math.pow(centralDifferenceYRed, 2) +
                Math.pow(centralDifferenceYGreen, 2) +
                Math.pow(centralDifferenceYBlue, 2);

        return Math.sqrt(xGradient + yGradient);
    }

    /**
     * Finds a horizontal seam with the lowest total energy.
     * @return Seam as array of y-coordinates (left to right)
     */
    public int[] findHorizontalSeam() {
        this.picture = transpose(picture);
        int[] seam = findVerticalSeam();
        this.picture = transpose(picture);
        return seam;
    }

    /**
     * Finds a vertical seam with the lowest total energy.
     * @return Seam as array of x-coordinates (top to bottom)
     */
    public int[] findVerticalSeam() {
        double[][] energy = new double[height()][width()];

        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                energy[y][x] = energy(x, y);
            }
        }

        double[][] distTo = new double[height()][width()];
        // edgeTo only contains the x coordinate. The y coordinate is implicit
        int[][] edgeTo = new int[height()][width()];

        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                distTo[y][x] = y == 0 ? 0 : Double.POSITIVE_INFINITY;
            }
        }

        // Topological order is implicit.
        // Relax row by row from top to bottom.
        for (int y = 0; y < height() - 1; y++) {
            for (int x = 0; x < width(); x++) {
                relax(distTo, edgeTo, x, y, energy);
            }
        }

        // Check bottom row for shortest distance
        int minX = -1;
        double minDist = Double.POSITIVE_INFINITY;
        for (int x = 0; x < width(); x++) {
            double dist = distTo[height() - 1][x];
            if (dist < minDist) {
                minX = x;
                minDist = dist;
            }
        }

        // Backtrack edgeTo to get seam
        int[] seam = new int[height()];
        seam[height() - 1] = minX;
        for (int y = height() - 2; y >= 0; y--) {
            minX = edgeTo[y+1][minX];
            seam[y] = minX;
        }
        return seam;
    }

    /**
     * Updates the shortest distance and shortest edge for vertices
     * pointed to by the given vertex
     * @param distTo Tracks the minimum distance from any source vertex to any point
     * @param edgeTo Tracks the shortest path from any source vertex to any point
     * @param x x-coordinate of point to relax
     * @param y y-coordinate of point to relax
     * @param energy Energy of each point (distance metric)
     */
    private void relax(double[][] distTo, int[][] edgeTo, int x, int y, double[][] energy) {
        // Relax below
        if (y < height() - 1) {
            double dist = energy[y+1][x] + distTo[y][x];
            if (dist < distTo[y+1][x]) {
                distTo[y+1][x] = dist;
                edgeTo[y+1][x] = x;
            }
        }
        // Relax below left
        if (x > 0) {
            double dist = energy[y+1][x-1] + distTo[y][x];
            if (dist < distTo[y+1][x-1]) {
                distTo[y+1][x-1] = dist;
                edgeTo[y+1][x-1] = x;
            }
        }
        // Relax below right
        if (x < width() - 1) {
            double dist = energy[y+1][x+1] + distTo[y][x];
            if (dist < distTo[y+1][x+1]) {
                distTo[y+1][x+1] = dist;
                edgeTo[y+1][x+1] = x;
            }
        }
    }

    /**
     * Removes a given horizontal seam from picture
     * @param seam Seam to remove (array of y coordinates from left to right)
     */
    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam, false);

        Picture newPicture = new Picture(width(), height() - 1);
        for (int x = 0; x < width(); x++) {
            int currentY = 0;
            for (int y = 0; y < height(); y++) {
                if (seam[x] != y) {
                    newPicture.setRGB(x, currentY, picture.getRGB(x, y));
                    currentY++;
                }
            }
        }
        this.picture = newPicture;
    }

    /**
     * Removes a given vertical seam from picture
     * @param seam Seam to remove (array of x coordinates from top to bottom)
     */
    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam, true);

        Picture newPicture = new Picture(width()-1, height());
        for (int y = 0; y < height(); y++) {
            int currentX = 0;
            for (int x = 0; x < width(); x++) {
                // Skip seam pixel
                if (seam[y] != x) {
                    newPicture.setRGB(currentX, y, picture.getRGB(x, y));
                    currentX++;
                }
            }
        }
        this.picture = newPicture;

    }

    /**
     * Pure method transposing a picture
     * @param picture Picture to transpose
     * @return Transposed picture
     */
    private Picture transpose(Picture picture) {
        Picture transposed = new Picture(picture.height(), picture.width());
        for (int y = 0; y < picture.height(); y++) {
            for (int x = 0; x < picture.width(); x++) {
                int c = picture.getRGB(x, y);
                transposed.setRGB(y, x, c);
            }
        }
        return transposed;
    }

    /**
     * Checks if the given y coordinate is in valid range
     * @param y y-coordinate to check
     * @return True if valid
     */
    private void validateY(int y) {
        if (y < 0 || y >= height()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks if the given x coordinate is in valid range
     * @param x y-coordinate to check
     * @return True if valid
     */
    private void validateX(int x) {
        if (x < 0 || x >= width()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Validates a given seam. The following things are checked:
     * - Seam array not null?
     * - Picture size big enough to remove seam?
     * - Has the seam array the correct length?
     * - Is every entry in the seam array in valid range?
     * - No two adjacent entries in seam array differ by more than 1?
     * @param seam
     * @param vertical
     */
    private void validateSeam(int[] seam, boolean vertical) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        // Check minimum picture size
        if (!vertical && height() <= 1 || vertical && width() <= 1) {
            throw new IllegalArgumentException();
        }

        // Check seam length
        int expectedLength = vertical ? height() : width();
        if (seam.length != expectedLength) {
            throw new IllegalArgumentException();
        }
        int prev = -1;
        for (int position : seam) {
            // Check range
            if (vertical) { validateX(position); }
            else { validateY(position); }
            // Check distance between any two points in seam is less than 2
            if (prev != -1 && Math.abs(prev - position) > 1) {
                throw new IllegalArgumentException();
            }
            prev = position;
        }
    }
}