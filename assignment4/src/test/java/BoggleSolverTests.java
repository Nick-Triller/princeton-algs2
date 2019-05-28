import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Picture;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoggleSolverTests {
    @Test
    public void scoreOf_Returns0_WhenWordNotInDict() {
        // Arrange
        BoggleSolver boggleSolver = new BoggleSolver(getDict("dictionary-common.txt"));
        // Act
        int score = boggleSolver.scoreOf("ASAJDPQWJDKAMCLKASDFIOASJD");
        // Assert
        assertEquals(0, score);
    }

    @Test
    public void scoreOf_ReturnsScore_WhenInput() {
        // Arrange
        BoggleSolver boggleSolver = new BoggleSolver(getDict("dictionary-common.txt"));
        List<String> input = Arrays.asList(
                "X",
                "ADJSFHLAKCNKASJ", // Not in dict
                "ZIRCONIUM",
                "YAP",
                "YEARN",
                "YEASTY",
                "YESHIVA",
                "XEROGRAPHY",
                "WRATHFUL"
        );
        List<Integer> expected = Arrays.asList(
                0,
                0,
                11,
                1,
                2,
                3,
                5,
                11,
                11
        );
        List<Integer> result = new ArrayList<>();
        // Act
        for (String word : input) {
            result.add(boggleSolver.scoreOf(word));
        }
        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void getAllValidWords_ReturnsAllValidWords_WhenInput() {
        // Arrange
        List<BoggleBoard> boards = Arrays.asList(
                new BoggleBoard("board4x4.txt")
        );
        BoggleSolver boggleSolver = new BoggleSolver(getDict("dictionary-algs4.txt"));
        List<List<String>> result = new ArrayList<>();
        // Act
        for (BoggleBoard board : boards) {
            List<String> words = new ArrayList<>();
            for (String word : boggleSolver.getAllValidWords(board)) {
                words.add(word);
            }
            result.add(words);
        }
        // Assert
        assertEquals(29, result.get(0).size());
    }

    private Map<String, String[]> dictCache = new HashMap<>();

    private String[] getDict(String path) {
        if (!dictCache.containsKey(path)) {
            In in = new In(path);
            String[] dict = in.readAllLines();
            dictCache.put(path, dict);
        }
        return dictCache.get(path);
    }
}
