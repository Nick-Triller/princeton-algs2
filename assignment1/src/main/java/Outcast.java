public class Outcast {
    private final WordNet wordNet;

    public Outcast(WordNet wordNet) {
        if (wordNet == null) { throw new IllegalArgumentException(); }
        this.wordNet = wordNet;
    }

    /**
     * Returns the noun that is least related the the others
     * @param nouns Array of WordNet nouns
     * @return The outcast noun
     */
    public String outcast(String[] nouns) {
        // assume "nouns" contains only valid WordNet nouns (at least 2)
        String outcast = null;
        int outcastDist = 0;
        for (String noun : nouns) {
            int dist = 0;
            for (String other : nouns) {
                // Distance from two equal nouns is 0 and can be ignored
                dist += this.wordNet.distance(noun, other);
            }
            if (outcast == null || dist > outcastDist) {
                outcast = noun;
                outcastDist = dist;
            }
        }
        return outcast;
    }
}
