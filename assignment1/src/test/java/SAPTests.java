import edu.princeton.cs.algs4.Digraph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SAPTests {

    @Test
    public void constructor_Throws_WhenArgsNull() {
        // Arrange
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new SAP(null));
    }

    @Test
    public void length_Throws_WhenArgsNull() {
        // Arrange
        Digraph g = new Digraph(0);
        SAP sap = new SAP(g);
        List<Integer> vertices =  new ArrayList<>();
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> sap.length(null, null));
        assertThrows(IllegalArgumentException.class, () -> sap.length(null, vertices));
        assertThrows(IllegalArgumentException.class, () -> sap.length(vertices, null));
    }

    @Test
    public void length_Throws_WhenArgsOutOfRange() {
        // Arrange
        Digraph g = new Digraph(10);
        SAP sap = new SAP(g);
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> sap.length(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> sap.length(0, -1));
        assertThrows(IllegalArgumentException.class, () -> sap.length(0, 100));
        assertThrows(IllegalArgumentException.class, () -> sap.length(100, 0));
    }

    @Test
    public void length_ReturnsLength_WhenInput1() {
        // Arrange
        Digraph g = new Digraph(6);
        g.addEdge(0, 2);
        g.addEdge(2, 3);
        g.addEdge(2, 1);
        g.addEdge(1, 4);
        g.addEdge(5, 1);
        g.addEdge(4, 3);
        SAP sap = new SAP(g);
        // Act
        int length = sap.length(0, 5);
        // Assert
        assertEquals(3, length);
    }

    @Test
    public void length_ReturnsLength_WhenInput2() {
        // Arrange
        Digraph g = new Digraph(6);
        g.addEdge(0, 2);
        g.addEdge(2, 3);
        g.addEdge(2, 1);
        g.addEdge(1, 4);
        g.addEdge(5, 1);
        g.addEdge(4, 3);
        SAP sap = new SAP(g);
        // Act
        int length = sap.length(Arrays.asList(0), Arrays.asList(5));
        // Assert
        assertEquals(3, length);
    }

    @Test
    public void ancestor_Throws_WhenArgsNull() {
        // Arrange
        Digraph g = new Digraph(0);
        SAP sap = new SAP(g);
        List<Integer> vertices =  new ArrayList<>();
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> sap.ancestor(null, null));
        assertThrows(IllegalArgumentException.class, () -> sap.ancestor(null, vertices));
        assertThrows(IllegalArgumentException.class, () -> sap.ancestor(vertices, null));
    }

    @Test
    public void ancestor_Throws_WhenArgsOutOfRange() {
        // Arrange
        Digraph g = new Digraph(10);
        SAP sap = new SAP(g);
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> sap.ancestor(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> sap.ancestor(0, -1));
        assertThrows(IllegalArgumentException.class, () -> sap.ancestor(0, 100));
        assertThrows(IllegalArgumentException.class, () -> sap.ancestor(100, 0));
    }

    @Test
    public void ancestor_ReturnsMinusOne_WhenNoSCA() {
        // Arrange
        Digraph g = new Digraph(10);
        SAP sap = new SAP(g);
        // Act
        int result = sap.ancestor(0, 5);
        // Assert
        assertEquals(-1, result);
    }

    @Test
    public void ancestor_ReturnsSCA_WhenInput1() {
        // Arrange
        Digraph g = new Digraph(6);
        g.addEdge(0, 2);
        g.addEdge(2, 3);
        g.addEdge(2, 1);
        g.addEdge(1, 4);
        g.addEdge(5, 1);
        g.addEdge(4, 3);
        SAP sap = new SAP(g);
        // Act
        int result = sap.ancestor(0, 5);
        // Assert
        assertEquals(1, result);
    }

    @Test
    public void ancestor_ReturnsSCA_WhenInput2() {
        // Arrange
        Digraph g = new Digraph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        SAP sap = new SAP(g);
        // Act
        int result = sap.ancestor(0, 3);
        // Assert
        assertEquals(3, result);
    }

    @Test
    public void ancestor_ReturnsSCA_WhenInput3() {
        // Arrange
        Digraph g = new Digraph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        SAP sap = new SAP(g);
        // Act
        int result = sap.ancestor(Arrays.asList(0), Arrays.asList(3));
        // Assert
        assertEquals(3, result);
    }
}
