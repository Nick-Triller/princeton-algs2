import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordNetTests {
    @Test
    public void constructor_Throws_WhenArgNull() {
        // Arrange
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new WordNet(null, null));
        assertThrows(IllegalArgumentException.class, () -> new WordNet(null, ""));
        assertThrows(IllegalArgumentException.class, () -> new WordNet("", null));
    }

    @Test
    public void isNoun_Throws_WhenArgNull() {
        // Arrange
        WordNet wordNet = WordNetFactory.getWordNet();
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> wordNet.isNoun(null));
    }

    @Test
    public void isNoun_ReturnsTrue_IfWordInAnySynset() {
        // Arrange
        WordNet wordNet = WordNetFactory.getWordNet();
        List<String> nouns = Arrays.asList("'hood", "1780s", "genus_Acheta", "zymolysis", "zymosis");
        List<Boolean> result = new ArrayList<>();
        // Act
        for (String noun : nouns) {
            result.add(wordNet.isNoun(noun));
        }
        // Assert
        for (boolean b : result) {
            assertTrue(b);
        }
    }

    @Test
    public void isNoun_ReturnsFalse_IfWordNotInAnySynset() {
        // Arrange
        WordNet wordNet = WordNetFactory.getWordNet();
        List<String> nouns = Arrays.asList("asd54dddd", "54dsfs46df", "78sd98f723s", "1sdf3s1d2f3", "a7a77asdd");
        List<Boolean> result = new ArrayList<>();
        // Act
        for (String noun : nouns) {
            result.add(wordNet.isNoun(noun));
        }
        // Assert
        for (boolean b : result) {
            assertFalse(b);
        }
    }

    @Test
    public void distance_Throws_WhenArgNull() {
        // Arrange
        WordNet wordNet = WordNetFactory.getWordNet();
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> wordNet.distance(null, null));
        assertThrows(IllegalArgumentException.class, () -> wordNet.distance(null, ""));
        assertThrows(IllegalArgumentException.class, () -> wordNet.distance("", null));
    }

    @Test
    public void distance_Throws_WhenArgNotWordNetNoun() {
        // Arrange
        WordNet wordNet = WordNetFactory.getWordNet();
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> wordNet.distance("zymosis", "1a5d45ff"));
        assertThrows(IllegalArgumentException.class, () -> wordNet.distance("1a5d45ff", "1a5d45ff"));
        assertThrows(IllegalArgumentException.class, () -> wordNet.distance("1a5d45ff", "zymosis"));
    }

    @Test
    public void distance_ReturnsDistance_WhenInput1() {
        // Arrange
        WordNet wordNet = WordNetFactory.getWordNet();
        // Act
        int distance = wordNet.distance("zymosis", "'hood");
        // Assert
        assertEquals(11, distance);
    }

    @Test
    public void sap_Throws_WhenArgNull() {
        // Arrange
        WordNet wordNet = WordNetFactory.getWordNet();
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> wordNet.sap(null, null));
        assertThrows(IllegalArgumentException.class, () -> wordNet.sap(null, ""));
        assertThrows(IllegalArgumentException.class, () -> wordNet.sap("", null));
    }

    @Test
    public void sap_Throws_WhenArgNotWordNetNoun() {
        // Arrange
        WordNet wordNet = WordNetFactory.getWordNet();
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> wordNet.sap("zymosis", "1a5d45ff"));
        assertThrows(IllegalArgumentException.class, () -> wordNet.sap("1a5d45ff", "1a5d45ff"));
        assertThrows(IllegalArgumentException.class, () -> wordNet.sap("1a5d45ff", "zymosis"));
    }

    @Test
    public void nouns_ReturnsNouns_WhenCalled() {
        // Arrange
        WordNet wordNet = WordNetFactory.getWordNet();
        // Act
        Iterable<String> nouns = wordNet.nouns();
        int count = 0;
        for (String noun : nouns) {
            count++;
        }
        // Assert
        assertEquals(119188, count);
    }
}
