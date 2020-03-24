package dto;

import java.util.List;
import java.util.Map;

public class Results {
    Map<Pair, List<Integer>> rows;

    public Results(Map<Pair, List<Integer>> rows) {
        this.rows = rows;
    }

    public void displayResults() {
        showTable(rows);
    }
}
