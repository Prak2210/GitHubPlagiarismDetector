package Controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dto.Repository;
import dto.Results;
import okhttp3.OkHttpClient;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlagiarismChecker {

    private static List<Repository> repositories;
    private static OkHttpClient httpClient;
    private static final String LOCALPATH = "/Users/prakshat/Documents/git/GitHubPlagiarismDetector/src/main/resources/Repositories/team";
    private static String changeDirectory = "cd " + LOCALPATH;
    private static String takePull = "git pull -r";
    private static String cloneRepository = "git clone ";
    private static String command = "ls";

    public static void run(CSVReader teams, boolean reload) throws Exception {

        if (reload || repositories == null) {
            initialize(teams);
        }
        takePull(repositories);
        Results results = Compare.compare(repositories);
        results.displayResults();
    }

    private static void takePull(List<Repository> repositories) throws Exception {
        FileUtility fileUtility = new FileUtility();
        fileUtility.makeTeamRepositories(repositories.size());
        fileUtility.sendPost(httpClient, repositories);

        for (int index = 1; index <= repositories.size(); index++) {
            Repository repository = repositories.get(index - 1);
            command += " && " + changeDirectory + index + " && " + cloneRepository + repository.getRepoLink();
        }
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        Process process = processBuilder.start();

        int exitVal = process.waitFor();
        if (exitVal == 0) {
            System.out.println("Latest pull for the repositories taken!");
        }
    }


    private static void initialize(CSVReader teams) throws IOException, CsvValidationException {
        String[] teamData;
        repositories = new ArrayList<>();
        httpClient = new OkHttpClient();
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
