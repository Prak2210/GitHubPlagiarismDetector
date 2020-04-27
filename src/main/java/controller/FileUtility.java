package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class to make directories and run shell commands. Used by takePull to take latest pull from repos
 */
public class FileUtility {
    private static final int BUFFER_SIZE = 4096;
    private static String localPath;
    private List<String> results = new ArrayList<>();

    FileUtility() {
        localPath = PlagiarismChecker.getLocalPath();
    }

    public void makeDirectories(int size) {
        for (int index = 1; index <= size; index++) {
            File file = new File((localPath + index));
            file.mkdir();
        }
    }

    void runShell(String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        Process process = processBuilder.start();

        int exitVal = process.waitFor();
        if (exitVal == 0) {
            System.out.println("Latest pull for the repositories taken!");
        }
    }
}