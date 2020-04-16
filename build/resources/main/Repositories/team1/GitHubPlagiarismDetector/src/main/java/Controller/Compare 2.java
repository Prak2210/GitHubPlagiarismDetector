package Controller;

import dto.Pair;
import dto.Repository;
import dto.Results;

import java.util.*;

public class Compare {

    public static Results compare(List<Repository> repositories) {

        Map<Pair, List<Double>> rows = new HashMap<>();

        // r1, r2, r3 ---> [r1,r2], [r1,r3], [r2, r3]


        // rows will contains this:
        // [r1, r2] -> 99.8%
        // [r1, r3] -> 85%
        for (int i = 0; i < repositories.size(); i++) {
            for (int j = i + 1; j < repositories.size(); j++) {
                Pair pair = new Pair(repositories.get(i), repositories.get(j));
                rows.put(pair, checkSimilarity(pair));
            }
        }
        return new Results(rows);
    }

    private static List<Double> checkSimilarity(Pair pair) {
        String referenceURL = pair.getReference().getLocalLink();
        String referredURL = pair.getReferred().getLocalLink();

        //TODO: call CodeQuiry
        List<Double> ret = CodeQuiry(referenceURL, referredURL);

        return ret;
    }

    //TODO
    private static List<Double> CodeQuiry(String referenceURL, String referredURL) {
        return Arrays.asList(new Double[]{99.9, 23.0});
    }

}


// Connecting our code with Codequiry API
// Modify results to suit our requirements
// Making this entire application live as an API