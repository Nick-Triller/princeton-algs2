import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordNet {
    private final Digraph graph;
    private final HashMap<Integer, String[]> idToSynset = new HashMap<>();
    private final HashMap<String, List<Integer>> wordToSynsetId = new HashMap<>();
    private final SAP sap;

    /**
     * Constructs a WordNet object
     * @param synsetsPath Name of synsets file
     * @param hypernymsPath Name of hypernyms file
     */
    public WordNet(String synsetsPath, String hypernymsPath) {
        if (synsetsPath == null || hypernymsPath == null) { throw new IllegalArgumentException(); }

        // Read synsets and hypernyms
        In synsetsIn = new In(synsetsPath);
        In hypernymsIn = new In(hypernymsPath);
        List<String> synsetLines = new ArrayList<>();
        while (synsetsIn.hasNextLine()) {
            synsetLines.add(synsetsIn.readLine());
        }
        List<String> hypernymLines = new ArrayList<>();
        while (hypernymsIn.hasNextLine()) {
            hypernymLines.add(hypernymsIn.readLine());
        }

        // Construct id to synset map
        for (String line : synsetLines) {
            String[] lineParts = line.split(",");
            int synsetId = Integer.parseInt(lineParts[0]);
            String[] synset = lineParts[1].split(" ");
            for (String word : synset) {
                List<Integer> synsetIds = wordToSynsetId.computeIfAbsent(word, k -> new ArrayList<>());
                synsetIds.add(synsetId);
            }
            idToSynset.put(synsetId, synset);
        }

        // Instantiate graph
        this.graph = new Digraph(synsetLines.size());

        // Add edges representing hypernym relationships to graph
        for (String line : hypernymLines) {
            String[] lineParts = line.split(",");
            int synsetId = Integer.parseInt(lineParts[0]);
            // Ignore synsetId in line, get all hypernym ids
            for (int i = 1; i < lineParts.length; i++) {
                int hypernymId = Integer.parseInt(lineParts[i]);
                graph.addEdge(synsetId, hypernymId);
            }
        }

        if (!isRootedDAG(this.graph)) {
            throw new IllegalArgumentException();
        }

        this.sap = new SAP(this.graph);
    }

    /**
     * Returns all nouns contained in the WordNet without duplicates.
     * @return All nouns contained in the WordNet without duplicates.
     */
    public Iterable<String> nouns() {
        return wordToSynsetId.keySet();
    }

    /**
     * Determines if a word is a WordNet noun
     * @param word The word
     * @return True if the word is a WordNet noun
     */
    public boolean isNoun(String word) {
        if (word == null) { throw new IllegalArgumentException(); }
        return this.wordToSynsetId.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        List<Integer> synsetsA = this.wordToSynsetId.getOrDefault(nounA, new ArrayList<>());
        List<Integer> synsetsB = this.wordToSynsetId.getOrDefault(nounB, new ArrayList<>());

        return this.sap.length(synsetsA, synsetsB);
    }

    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        List<Integer> synsetsA = this.wordToSynsetId.getOrDefault(nounA, new ArrayList<>());
        List<Integer> synsetsB = this.wordToSynsetId.getOrDefault(nounB, new ArrayList<>());

        int sapSynsetId = this.sap.ancestor(synsetsA, synsetsB);
        String[] sapSynset = idToSynset.get(sapSynsetId);
        return String.join(" ", sapSynset);
    }

    /**
     * Determines if a digraph is a rooted directed acyclic graph
     * @param g Digraph to check
     * @return True if rooted DAG
     */
    private boolean isRootedDAG(Digraph g) {
        // Check only one vertex has out-degree 0
        int outdegreeZeroCount = 0;
        for (int v = 0; v < g.V(); v++) {
            if (this.graph.outdegree(v) == 0) {
                outdegreeZeroCount++;
            }
        }
        if (outdegreeZeroCount != 1) {
            return false;
        }
        // Check if cycles exist
        return new edu.princeton.cs.algs4.Topological(this.graph).hasOrder();
    }
}
