import edu.princeton.cs.algs4.Picture;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BaseballEliminationTests {
    @Test
    public void wins_ReturnsWins_WhenCalled() {
        // Arrange
        BaseballElimination baseballElimination = new BaseballElimination("teams4.txt");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Atlanta", 83);
        expected.put("Philadelphia", 80);
        expected.put("New_York", 78);
        expected.put("Montreal", 77);
        Map<String, Integer> actual = new HashMap<>();
        // Act
        for (String team: baseballElimination.teams()) {
            actual.put(team, baseballElimination.wins(team));
        }
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void losses_ReturnsLosses_WhenCalled() {
        // Arrange
        BaseballElimination baseballElimination = new BaseballElimination("teams4.txt");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Atlanta", 71);
        expected.put("Philadelphia", 79);
        expected.put("New_York", 78);
        expected.put("Montreal", 82);
        Map<String, Integer> actual = new HashMap<>();
        // Act
        for (String team: baseballElimination.teams()) {
            actual.put(team, baseballElimination.losses(team));
        }
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void remaining_ReturnsRemaining_WhenCalled() {
        // Arrange
        BaseballElimination baseballElimination = new BaseballElimination("teams4.txt");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Atlanta", 8);
        expected.put("Philadelphia", 3);
        expected.put("New_York", 6);
        expected.put("Montreal", 3);
        Map<String, Integer> actual = new HashMap<>();
        // Act
        for (String team: baseballElimination.teams()) {
            actual.put(team, baseballElimination.remaining(team));
        }
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void teams_ReturnsTeamNames_WhenCalled() {
        // Arrange
        BaseballElimination baseballElimination = new BaseballElimination("teams4.txt");
        Set<String> expected = new HashSet<>(Arrays.asList("Atlanta", "Philadelphia", "New_York", "Montreal"));
        Set<String> actual = new HashSet<>();
        // Act
        Iterable<String> teamNames = baseballElimination.teams();
        for (String team: teamNames) { actual.add(team); }
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void numberOfTeams_ReturnsNumberOfTeams_WhenCalled() {
        // Arrange
        BaseballElimination baseballElimination = new BaseballElimination("teams4.txt");
        int expected = 4;
        // Act
        int numberOfTeams = baseballElimination.numberOfTeams();
        // Assert
        assertEquals(expected, numberOfTeams);
    }

    @Test
    public void against_ReturnsAgainst_WhenCalled() {
        // Arrange
        BaseballElimination baseballElimination = new BaseballElimination("teams4.txt");
        List<Integer> expected = Arrays.asList(
                0, 1, 6, 1,
                1, 0, 0, 2,
                6, 0, 0, 0,
                1, 2, 0, 0
        );
        List<Integer> actual = new ArrayList<>();
        Iterable<String> teamNames = baseballElimination.teams();
        List<String> teamNamesSorted = new ArrayList<>();
        for (String team: teamNames) { teamNamesSorted.add(team); }
        teamNamesSorted.sort(String::compareTo);
        // Act
        for (String team1: teamNamesSorted) {
            for (String team2: teamNamesSorted) {
                actual.add(baseballElimination.against(team1, team2));
            }
        }
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void eliminated_ReturnsEliminated_WhenInput1() {
        // Arrange
        BaseballElimination baseballElimination = new BaseballElimination("teams4.txt");
        Map<String, Boolean> expected = new HashMap<>();
        expected.put("Atlanta", false);
        // Eliminated via max flow
        expected.put("Philadelphia", true);
        expected.put("New_York", false);
        // Trivially eliminated
        expected.put("Montreal", true);
        Map<String, Boolean> actual = new HashMap<>();
        // Act
        for (String team: baseballElimination.teams()) {
                actual.put(team, baseballElimination.isEliminated(team));
        }
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void eliminated_ReturnsEliminated_WhenInput2() {
        // Arrange
        BaseballElimination baseballElimination = new BaseballElimination("teams5.txt");
        Map<String, Boolean> expected = new HashMap<>();
        expected.put("Baltimore", false);
        // Eliminated via max flow
        expected.put("Detroit", true);
        expected.put("New_York", false);
        // Trivially eliminated
        expected.put("Toronto", false);
        expected.put("Boston", false);
        Map<String, Boolean> actual = new HashMap<>();
        // Act
        for (String team: baseballElimination.teams()) {
            actual.put(team, baseballElimination.isEliminated(team));
        }
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void certificateOfElimination_ReturnsCert_WhenInput1() {
        // Arrange
        BaseballElimination baseballElimination = new BaseballElimination("teams4.txt");
        Set<String> expected = new HashSet<>();
        expected.add("Atlanta");
        expected.add("New_York");
        // Act
        Iterable<String> certificate = baseballElimination.certificateOfElimination("Philadelphia");
        // Assert
        assertEquals(expected, certificate);
    }
}
