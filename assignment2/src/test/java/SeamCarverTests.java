import edu.princeton.cs.algs4.Picture;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SeamCarverTests {
    @Test
    public void constructor_Throws_WhenArgNull() {
        // Arrange
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new SeamCarver(null));
    }

    @Test
    public void picture_ReturnsNewCopy_WhenCalled() {
        // Arrange
        Picture picture = new Picture(10, 10);
        SeamCarver seamCarver = new SeamCarver(picture);
        // Act
        Picture actual = seamCarver.picture();
        // Assert
        assertNotSame(picture, actual);
    }

    @Test
    public void width_ReturnsWidth_WhenCalled() {
        // Arrange
        int expected = 10;
        Picture picture = new Picture(expected, 10);
        SeamCarver seamCarver = new SeamCarver(picture);
        // Act
        int width = seamCarver.width();
        // Assert
        assertEquals(expected, width);
    }

    @Test
    public void height_ReturnsHeight_WhenCalled() {
        // Arrange
        int expected = 10;
        Picture picture = new Picture(10, expected);
        SeamCarver seamCarver = new SeamCarver(picture);
        // Act
        int height = seamCarver.height();
        // Assert
        assertEquals(expected, height);
    }

    @Test
    public void energy_ReturnsEnergy_WhenInput1() {
        // Arrange
        Picture picture = new Picture(3, 4);
        picture.set(0, 0, new Color(255, 101, 151));
        picture.set(1, 0, new Color(255, 101, 153));
        picture.set(2, 0, new Color(255, 101, 255));
        picture.set(0, 1, new Color(255, 153, 51));
        picture.set(1, 1, new Color(255, 153, 153));
        picture.set(2, 1, new Color(255, 153, 255));
        picture.set(0, 2, new Color(255, 203, 51));
        picture.set(1, 2, new Color(255, 204, 153));
        picture.set(2, 2, new Color(255, 205, 255));
        picture.set(0, 3, new Color(255, 255, 51));
        picture.set(1, 3, new Color(255, 255, 153));
        picture.set(2, 3, new Color(255, 255, 255));
        List<Double> expected = Arrays.asList(
                Math.sqrt(52225),
                Math.sqrt(52024)
        );
        List<Double> results = new ArrayList<>();
        SeamCarver seamCarver = new SeamCarver(picture);
        // Act
        results.add(seamCarver.energy(1, 1));
        results.add(seamCarver.energy(1, 2));
        // Assert
        assertEquals(expected, results);
    }

    @Test
    public void findVerticalSeam_ReturnsSeam_WhenInput1() {
        // Arrange
        Picture picture = new Picture(3, 4);
        picture.set(0, 0, new Color(255, 101, 151));
        picture.set(1, 0, new Color(255, 101, 153));
        picture.set(2, 0, new Color(255, 101, 255));
        picture.set(0, 1, new Color(255, 153, 51));
        picture.set(1, 1, new Color(255, 153, 153));
        picture.set(2, 1, new Color(255, 153, 255));
        picture.set(0, 2, new Color(255, 203, 51));
        picture.set(1, 2, new Color(255, 204, 153));
        picture.set(2, 2, new Color(255, 205, 255));
        picture.set(0, 3, new Color(255, 255, 51));
        picture.set(1, 3, new Color(255, 255, 153));
        picture.set(2, 3, new Color(255, 255, 255));
        SeamCarver seamCarver = new SeamCarver(picture);
        // Act
        int[] result = seamCarver.findVerticalSeam();
        // Assert
        assertTrue(result[0] == 0 || result[0] == 1 || result[0] == 2);
        assertEquals(1, result[1]);
        assertEquals(1, result[2]);
        assertTrue(result[3] == 0 || result[3] == 1 || result[3] == 2);
    }
}
