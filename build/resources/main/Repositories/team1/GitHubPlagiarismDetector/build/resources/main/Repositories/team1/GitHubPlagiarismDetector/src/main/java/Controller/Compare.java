package Controller;

import dto.Pair;
import dto.Repository;
import dto.Results;

import java.util.*;

public class Compare {

    public static Results compare(List<Repository> repositories) {

        Map<Pair, List<Double>> rows = new HashMap<>();

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

    private static List<Double> CodeQuiry(String referenceURL, String referredURL) {
        return Arrays.asList(new Double[]{99.9, 23.0});
    }

}
