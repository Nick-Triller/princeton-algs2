public class WordNetFactory {
    private static WordNet wordNetSingleton;
    static WordNet getWordNet() {
        if (wordNetSingleton == null) {
            wordNetSingleton = new WordNet("./src/main/resources/synsets.txt",
                    "./src/main/resources/hypernyms.txt");
        }
        return wordNetSingleton;
    }
}
