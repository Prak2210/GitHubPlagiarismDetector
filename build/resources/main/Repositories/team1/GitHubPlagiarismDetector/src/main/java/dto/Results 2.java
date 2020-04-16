package dto;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Results {
    Map<Pair, List<Double>> rows;

    public Results(Map<Pair, List<Double>> rows) {
        this.rows = rows;
    }

    public void displayResults() {
        for (Map.Entry<Pair, List<Double>> row : rows.entrySet()) {
            Pair pair = row.getKey();
            int referenceID = pair.getReference().getRepoIndex();
            int referredID = pair.getReferred().getRepoIndex();
            String referenceTeam = pair.getReference().getTeam();
            String referredTeam = pair.getReferred().getTeam();
            double plagiarism = row.getValue().get(0);

            System.out.println("Source Team ID:" + referenceID + " Authors: " + referenceTeam +
                    " Compared with Team ID: " + referredID + " and Authors: " + referredTeam + " " + plagiarism);
        }
    }
}
