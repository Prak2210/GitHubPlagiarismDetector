package Controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dto.Repository;
import dto.Results;
import okhttp3.OkHttpClient;

import java.io.*;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlagiarismChecker extends Thread {

    private static List<Repository> repositories;
    private static OkHttpClient httpClient;
    private static final String LOCALPATH = "/Users/prakshat/Documents/git/GitHubPlagiarismDetector/src/main/resources/Repositories/team";
    private static String changeDirectory = "cd " + LOCALPATH;
    private static String takePull = "git pull -r";
    private static String cloneRepository = "git clone ";
    private static String command = "ls";
    private static LocalTime startTime = null;
    private static LocalTime currentTime = null;
    private static CSVReader teams = null;
    private static boolean reload = false;
    private static int pullCounts = 0;
    private static boolean isCancelled = false;
    private static Scanner scanner = new Scanner(System.in);
    private static Compare compare;
    private static Language languageObject;

    public PlagiarismChecker(String file, String checkName, String language) {
        languageObject = new Language(language);
        compare = new Compare(checkName, languageObject.getLanguageCode(language));

        try {
            read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PlagiarismChecker() throws Exception {
        if(compare == null) {
            System.out.println("Use the other constructor initially for parameters");
            throw new Exception();
        }
    }


    public static void doCompare() throws Exception {

        if (reload || repositories == null) {
            startTime = LocalTime.now();
            initialize(teams);
        }
        takePull(repositories);
//        reposToZIP(repositories);
        Results results = compare.compare(repositories);
        results.displayResults();
    }

    private static void reposToZIP(List<Repository> repositories) {
        FileUtility fileUtility = new FileUtility();
        List<File> directories = new ArrayList<>();
        for(Repository repository: repositories) {
            directories.add(new File(repository.getLocalLink()+repository.getRepoIndex()+".zip"));
        }
    }

    @Override
    public void run() {
        while (!isCancelled) {
            String command = "";
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
                        pullCounts++;
                        doCompare();
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
            Thread.sleep(10000);
            command = scanner.nextLine();

            if (command.equals("stop")) isCancelled = true;
            if (command.equals("reload")) reload = true;
            if (command.equals("restart")) {
                reload = true;
                repositories = null;
                read("Teams.csv");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean shouldTakePull() {
        return currentTime.getHour() - startTime.getHour() > 0 ||
                currentTime.getMinute() - startTime.getMinute() > 0 ||
                currentTime.getSecond() - startTime.getSecond() == 90000;
    }

    private static void takePull(List<Repository> repositories) throws Exception {
        FileUtility fileUtility = new FileUtility();
        fileUtility.makeTeamRepositories(repositories.size());

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

    public static void main(String a[]) {
        try {
            PlagiarismChecker checker = new PlagiarismChecker("Teams.csv", "plgiarism", "python");
            PlagiarismChecker stopper = new PlagiarismChecker();
            stopper.setName("controller");

            checker.start();
            stopper.start();
        } catch (Exception e) {
            System.out.println("Had a trouble in : " + e);
        }
    }

    private PlagiarismChecker read(String file) throws Exception {
        ClassLoader classLoader = new PlagiarismChecker().getClass().getClassLoader();
        FileReader resource = new FileReader(classLoader.getResource(file).getFile());
        teams = new CSVReader(resource);
        reload = true;
        return this;
    }
}
