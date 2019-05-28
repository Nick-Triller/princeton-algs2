import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BaseballElimination {
    private int[][] schedule;
    private int[] wins;
    private int[] losses;
    private int[] remaining;
    private final Map<String, Integer> nameToTeamId = new HashMap<>();

    public BaseballElimination(String filename) {
        In in = new In(filename);
        parseFile(in);
    }

    /**
     * Returns the number of teams in the division
     * @return Number of teams
     */
    public int numberOfTeams() {
        return this.wins.length;
    }

    /**
     * Names of all teams
     * @return Team names
     */
    public Iterable<String> teams() {
        return nameToTeamId.keySet();
    }

    /**
     * Get the number of wins for a team
     * @param team Team name
     * @return Wins of team
     */
    public int wins(String team) {
        validateTeamNames(team);
        int teamId = nameToTeamId.get(team);
        return wins[teamId];
    }

    /**
     * Get the number of losses for a team
     * @param team Team name
     * @return Losses of team
     */
    public int losses(String team) {
        validateTeamNames(team);
        int teamId = nameToTeamId.get(team);
        return losses[teamId];
    }

    /**
     * Get the number of remaining games for a team
     * @param team Team name
     * @return Remaining games of team
     */
    public int remaining(String team) {
        validateTeamNames(team);
        int teamId = nameToTeamId.get(team);
        return remaining[teamId];
    }

    /**
     * Get the number of remaining games between team1 and team2
     * @param team1 Team name
     * @param team2 Team name
     * @return Number of games remaining between the two given teams
     */
    public int against(String team1, String team2) {
        validateTeamNames(team1, team2);
        int teamId1 = nameToTeamId.get(team1);
        int teamId2 = nameToTeamId.get(team2);
        return schedule[teamId1][teamId2];
    }

    /**
     * Checks if a team is mathematically eliminated (team
     * can't be first or tie with first).
     * @param team Team name
     * @return True if team is mathematically eliminated
     */
    public boolean isEliminated(String team) {
        validateTeamNames(team);
        if (trivialElimination(team)) {
            return true;
        }

        FlowNetwork network = buildNetwork(team);
        int targetIndex = network.V() - 1;
        int startIndex = network.V() - 2;

        new FordFulkerson(network, startIndex, targetIndex);

        // Check flow of all edges between s and game vertices.
        for (FlowEdge edge : network.adj(startIndex)) {
            if (edge.flow() < edge.capacity()) {
                // If any edge is not full, then team is eliminated
                return true;
            }
        }
        // If all edges are full, then a scenario exists where team "team" is not eliminated
        return false;
    }

    /**
     * Calculates a set of teams of which, mathematically, at least one will have more
     * wins than the given team can have wins in the best case.
     * @param team Team name for which the certificate set should be calculated
     * @return Team names or null if not eliminated
     */
    public Iterable<String> certificateOfElimination(String team) {
        validateTeamNames(team);
        Set<String> result = new HashSet<>();
        if (trivialElimination(team)) {
            // Return top scorer
            String topScorer = null;
            int topScore = -1;
            for (String t : teams()) {
                int wins = wins(t);
                if (wins > topScore) {
                    topScore = wins;
                    topScorer = t;
                }
            }
            result.add(topScorer);
            return result;
        }
        FlowNetwork network = buildNetwork(team);
        FordFulkerson fordFulkerson = new FordFulkerson(network, network.V()-2, network.V()-1);

        // Iterate through teams
        for (String teamName : teams()) {
            int teamVertex = nameToTeamId.get(teamName);
            if (fordFulkerson.inCut(teamVertex)) {
                result.add(teamName);
            }
        }

        return result.isEmpty() ? null : result;
    }

    private FlowNetwork buildNetwork(String team) {
        int teamId = nameToTeamId.get(team);

        int gameVertexCount = (numberOfTeams() - 1) * (numberOfTeams() - 2) / 2;
        int teamVertexCount = numberOfTeams();
        int vertexCount = 2 + gameVertexCount + teamVertexCount;
        int sourceVertex = vertexCount - 2;
        int targetVertex = vertexCount - 1;
        FlowNetwork flowNetwork = new FlowNetwork(vertexCount);

        // Add edges from source to game vertices and from game vertices to team vertices
        int offset = numberOfTeams();
        int counter = 0;
        for (int v1 = 0; v1 < numberOfTeams(); v1++) {
            for (int v2 = v1; v2 < numberOfTeams(); v2++) {
                if (v2 == teamId || v1 == teamId || v1 == v2) {
                    continue;
                }
                int remaining = schedule[v1][v2];
                int gameVertex = offset + counter;
                // Edge from source vertex to game vertex
                flowNetwork.addEdge(new FlowEdge(sourceVertex, gameVertex, remaining));
                // Edges from game vertex to team vertices
                flowNetwork.addEdge(new FlowEdge(gameVertex, v1, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(gameVertex, v2, Double.POSITIVE_INFINITY));
                counter++;
            }
        }

        // Add edges from team vertices to target vertex
        int maxWins = wins(team) + remaining(team);
        for (int tid = 0; tid < numberOfTeams(); tid++) {
            if (tid != teamId) {
                int capacity = maxWins - wins[tid];
                flowNetwork.addEdge(new FlowEdge(tid, targetVertex, capacity));
            }
        }

        return flowNetwork;
    }

    private void parseFile(In in) {
        int teamsCount = in.readInt();
        // team id to games left against other teams where array index is id of other team
        this.schedule = new int[teamsCount][teamsCount];
        this.wins = new int[teamsCount];
        this.losses = new int[teamsCount];
        this.remaining = new int[teamsCount];

        for (int teamId = 0; teamId < teamsCount; teamId++) {
            String name = in.readString();
            nameToTeamId.put(name, teamId);

            wins[teamId] = in.readInt();
            losses[teamId] = in.readInt();
            remaining[teamId] = in.readInt();
            for (int other = 0; other < teamsCount; other++) {
                // Remaining games of team teamId against team other
                schedule[teamId][other] = in.readInt();
            }
        }
    }

    private void validateTeamNames(String... teamNames) {
        for (String name: teamNames) {
            if (name == null || !this.nameToTeamId.containsKey(name)) {
                throw new IllegalArgumentException();
            }
        }
    }

    private boolean trivialElimination(String team) {
        // Check trivial elimination:
        // Number of games team x can win is less than the number of wins of some
        // other team i, e. g. w[x] + r[x] < w[i]
        for (String team1: teams()) {
            if (team1.equals(team)) { continue; }
            if (wins(team) + remaining(team) < wins(team1)) {
                return true;
            }
        }
        return false;
    }
}
