package dto;

import java.util.List;
import java.util.Map;

public class Results {
    Map<Pair, List<Integer>> rows;

    public Results(Map<Pair, List<Integer>> rows) {
        this.rows = rows;
    }

    public void displayResults() {
        // we make a table from rows: pair(r1, r2) and value of rows hashmap is % plagiarism

        // System.out.----> table
        // try to figure out some interactive charts
    }
}
