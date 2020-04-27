package controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dto.Repository;
import dto.Results;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlagiarismChecker extends Thread {

    private static List<Repository> repositories;
    private static String CHECK_NAME = "plagiarism";
    private static String localPath = "/GitHubPlagiarismDetector/src/main/resources/Repositories/team";
    private static String takeZIP = "/archive/master.zip";
    private static String changeDirectory = "cd ";
    private static String command = "ls";
    private static LocalTime startTime = null;
    private static LocalTime currentTime = null;
    private static CSVReader teams = null;
    private static boolean reload = false;
    private static boolean isCancelled = false;
    private static Scanner scanner = new Scanner(System.in);
    private static String language;
    private static Language languageObject;
    private static int refreshTime = 2;
    private String csvFile;

    /**
     *
     * @param csvFile CSV file of individuals/teams
     * @param language Programming language of the sourcecode
     * @param pathToRepo Name of the check to be created in CodeQuiry
     * @param time duration to take latest pull again
     */

    public PlagiarismChecker(String csvFile, String language, String pathToRepo, int time) {
        languageObject = new Language(language);
        this.language = language;
        localPath = pathToRepo + localPath;
        changeDirectory += localPath;

        refreshTime = time;
        this.csvFile = csvFile;

        try {
            read(csvFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PlagiarismChecker() {
    }

    /**
     * Clones the Github repositories, calls CodeQuiry API for plagiarism check, and displays results
     * @throws Exception
     */
    public static void runChecker() throws Exception {
        Repository.setCheckID(CHECK_NAME, languageObject.getLanguageCode(language));
        if (reload || repositories == null) {
            startTime = LocalTime.now();
            initialize(teams);
        }

        takePull(repositories);

        Results results = new Results();
        results.compareRepos(repositories);
        results.displayResults();
        System.out.println("Next Pull will be taken after ");
    }

    @Override
    public void run() {
        while (!isCancelled) {
            if (this.getName().equals("controller")) {
                controlOptions();
            } else {
                if (startTime == null) {
                    startTime = LocalTime.now();
                    reload = true;
                }

                currentTime = LocalTime.now();
                if (reload || shouldTakePull()) {
                    try {
                        reload = false;
                        runChecker();
                        startTime = currentTime;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private void controlOptions() {
        String command;
        try {
            System.out.println("type: \"reload\" or \"stop\" or \"restart\" ");
            Thread.sleep(1000);
            command = scanner.nextLine();

            if (command.equals("stop")) isCancelled = true;
            if (command.equals("reload")) reload = true;
            if (command.equals("restart")) {
                reload = true;
                repositories = null;
                read(csvFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean shouldTakePull() {
        return currentTime.getHour() - startTime.getHour() >= refreshTime ||
                currentTime.getMinute() - startTime.getMinute() >= refreshTime * 60;
    }

    /**
     *
     * @param repositories List of Github repositories to clone
     * @throws Exception
     */
    private static void takePull(List<Repository> repositories) throws Exception {
        FileUtility fileUtility = new FileUtility();
        fileUtility.makeDirectories(repositories.size());

        for (int index = 1; index <= repositories.size(); index++) {
            Repository repository = repositories.get(index - 1);
            String gitHubLink = repository.getRepoLink().replace(".git", "");
            command += " && " + changeDirectory + index + " && " +
                    "curl -LOk " + gitHubLink +
                    takeZIP;
        }

        fileUtility.runShell(command);
    }

    /**
     *
     *  This is the main function. Takes csv file, name of the check, and type of programming language as input
     */

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

    private void read(String file) throws Exception {
        ClassLoader classLoader = new PlagiarismChecker().getClass().getClassLoader();
        FileReader resource = new FileReader(classLoader.getResource(file).getFile());
        teams = new CSVReader(resource);
        reload = true;
    }

    public static String getLocalPath() {
        return localPath;
    }


    public static void main(String a[]) {
        try {
            PlagiarismChecker checkProcess = new
                    PlagiarismChecker("Teams.csv", "python",
                    "/Users/prakshat/Documents/git", 2);
            PlagiarismChecker controller = new PlagiarismChecker();
            controller.setName("controller");

            checkProcess.start();
            controller.start();
        } catch (Exception e) {
            System.out.println("Had a trouble in : " + e);
        }
    }
}
