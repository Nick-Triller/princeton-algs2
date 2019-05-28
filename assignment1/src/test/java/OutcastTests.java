import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Out;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OutcastTests {
    @Test
    public void outcast_ReturnsOutcast_WhenInput1() {
        // Arrange
        WordNet wordNet = WordNetFactory.getWordNet();
        Outcast outcast = new Outcast(wordNet);
        String[] input = new String[] {
                "Turing",
                "von_Neumann"
        };
        // Act
        String result = outcast.outcast(input);
        // Assert
        // Both have the same distance, therefore only check if result is in input
        assertTrue(input[0].equals(result) || input[1].equals(result));
    }

    @Test
    public void outcast_ReturnsOutcast_WhenInput2() {
        // Arrange
        WordNet wordNet = WordNetFactory.getWordNet();
        Outcast outcast = new Outcast(wordNet);
        String[] input = new String[] {
                "Turing",
                "von_Neumann",
                "Mickey_Mouse"
        };
        // Act
        String result = outcast.outcast(input);
        // Assert
        // Both have the same distance, therefore only check if result is in input
        assertEquals("Mickey_Mouse", result);
    }
}
