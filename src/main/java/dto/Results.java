package dto;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Results {
    Map<Pair, List<Double>> rows;

    public Results(Map<Pair, List<Double>> rows) {
        this.rows = rows;
    }

    public void printResults() {

        Iterator it = rows.entrySet().iterator();

        while (it.hasNext())
        {
            Map.Entry mapElement = (Map.Entry)it.next();
            System.out.println(mapElement.getKey() + " : " + mapElement.getValue());
        }

        // we make a table from rows: pair(r1, r2) and value of rows hashmap is % plagiarism

        // System.out.----> table
        // try to figure out some interactive charts
    }
    public void displayResults()
    {
        
    }
}
