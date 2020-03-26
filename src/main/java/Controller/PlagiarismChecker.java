package Controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dto.Repository;
import dto.Results;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlagiarismChecker {

    private static List<Repository> repositories;

    public static void run(CSVReader teams, boolean reload) throws IOException, CsvValidationException {

        if (reload || repositories == null) {
            initialize(teams);
        }
        takePull(repositories);
        Results results = Compare.compare(repositories);
        results.printResults();
        results.displayResults();
    }

    private static void takePull(List<Repository> repositories) {

    }

    private static void initialize(CSVReader teams) throws IOException, CsvValidationException {
        String[] teamData;
        repositories = new ArrayList<>();
        int rows = 0;

        while ((teamData = teams.readNext()) != null) {
            if (rows == 0) {
                rows++;
                continue;
            }
            Repository repository = new Repository(teamData);
            repositories.add(repository);
        }
    }

    /* temporary implementation through main method will be replaced by API calls */
    public static void main(String a[]) {
        try {
            CSVReader teams = read("Teams.csv");
            run(teams, true);
        } catch (Exception e) {
            System.out.println("Had a trouble in : " + e);
        }
    }

    private static CSVReader read(String file) throws FileNotFoundException {
        ClassLoader classLoader = new PlagiarismChecker().getClass().getClassLoader();
        FileReader resource = new FileReader(classLoader.getResource(file).getFile());
        CSVReader teams = new CSVReader(resource);

        return teams;
    }
}
