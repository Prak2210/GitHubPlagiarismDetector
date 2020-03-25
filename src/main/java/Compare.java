import dto.Pair;
import dto.Repository;
import dto.Results;

import javax.xml.transform.Result;
import java.util.*;

public class Compare {

    public static Results compare(List<Repository> repositories) {

        // repo1, repo2, repo3 = 3
        List<Pair> pairs = getPairs(repositories); //can be eliminated  //3 pairs
        Map<Pair, List<Integer>> rows = new HashMap<>();

        // r1,r2 ------ Ha
        // f2, r3
        // r1, r3

        //hashmap will contain pair as a key and percentage of plagiarism detected as value
        for (Pair pair : pairs) {
            rows.put(pair, checkSimilarity(pair));
        }

        return new Results(rows);
    }

    private static List<Double> checkSimilarity(Pair pair) {
        // get plagiarsim from codequiry API
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

        // Pair(repo1, repo2), Pair(repo2, repo3),.....

        return pairs;
    }

}
