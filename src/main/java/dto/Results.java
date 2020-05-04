package dto;

import java.util.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Results {
    private Map<Pair, Double> rows;

    public Results() {
        rows = new HashMap<>();
    }

    /**
     * @param repositories list of all repositories
     *                     compare them one by one and get similarity
     */
    public void compareRepos(List<Repository> repositories) {

        try {
            /* Todo: uncomment the line "uploadAndCheck" if you want to start your own checks
                I have commented this out because, we don't want to waste available
                checks if not necessary or only for trial. Also, delete the call to
                "removeThisMethodInFuture" as it is not useful when you wannt to
                run on your actual data.
            */

//            Repository.uploadAndCheck(repositories);
            Repository.removeThisMethodInFuture();
            for (int i = 0; i < repositories.size(); i++) {
                for (int j = i + 1; j < repositories.size(); j++) {
                    Pair pair = new Pair(repositories.get(i), repositories.get(j));
                    Repository referencedRepo = repositories.get(i);
                    Repository referredRepo = repositories.get(j);
                    double plagiarismDetected = referencedRepo.compare(referredRepo);
                    this.rows.put(pair, plagiarismDetected);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in comparing files");
        }
    }

    /**
     * Display results in a table format
     */
    public void displayResults() {

        for (Map.Entry<Pair, Double> row : rows.entrySet()) {
            Pair pair = row.getKey();
            int referenceID = pair.getReference().getRepoIndex();
            int referredID = pair.getReferred().getRepoIndex();
            String referenceTeam = pair.getReference().getTeam();
            String referredTeam = pair.getReferred().getTeam();
            double plagiarism = row.getValue();

            System.out.println("Source Team ID:" + referenceID + " Authors: " + referenceTeam +
                    " Compared with Team ID: " + referredID + " and Authors: " + referredTeam + " " + plagiarism);
        }

        int ind = 0, size = 0;
        String[][] data = new String[rows.size()][5];
        for (Map.Entry<Pair, Double> row : rows.entrySet()) {

            Pair pair = row.getKey();
            int referenceID = pair.getReference().getRepoIndex();
            int referredID = pair.getReferred().getRepoIndex();
            String referenceTeam = pair.getReference().getTeam();
            String referredTeam = pair.getReferred().getTeam();
            double plagiarism = row.getValue();
            data[ind][0] = Integer.toString(referenceID);
            data[ind][1] = referenceTeam;
            data[ind][2] = Integer.toString(referredID);
            data[ind][3] = referredTeam;
            data[ind][4] = Double.toString(plagiarism);
            ind++;

        }
        JFrame frame;
        JTable table;
        frame = new JFrame();

        frame.setTitle("Plagiarism Results");

        String[] columnNames = {"Source Team ID", "Authors", "Comapred with Team ID", "Authors", "Plagiarism"};
        table = new JTable(data, columnNames);
        table.setBounds(30, 40, 1000, 1000);

        JScrollPane sp = new JScrollPane(table);
        frame.add(sp);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }
}

