import dto.Pair;
import dto.Repository;
import dto.Results;

import javax.xml.transform.Result;
import java.util.*;

public class Compare {

    public static Results compare(Repository repository) {
        List<Repository> repositories = repository.repositories;

        List<Pair> pairs = getPairs(repositories); //can be eliminated
        Map<Pair, List<Integer>> rows = new HashMap<>();

        for (Pair pair : pairs) {
            rows.put(pair, checkSimilarity(pair));
        }

        return new Results(rows);
    }

    private static List<Integer> checkSimilarity(Pair pair) {
        // get plagiarsim from API
        return new ArrayList<>();
    }

    private static List<Pair> getPairs(List<Repository> repositories) {

        List<Pair> pairs = new ArrayList<>();

        for (int i = 0; i < repositories.size(); i++) {
            for (int j = i + 1; j < repositories.size(); j++) {
                Pair pair = new Pair(repositories.get(i), repositories.get(j));
                pairs.add(pair);
            }
        }

        return pairs;
    }

}
