package Controller;

import dto.Pair;
import dto.Repository;
import dto.Results;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;

public class Compare {
    private CodeQuiryProxy codeQuiryProxy;
    private String checkID;
    private Map<String, String> mapFileToIDs;
    private final String ZIP = "zip";

    public Compare(String checkName, String languageCode) {
        codeQuiryProxy = new CodeQuiryProxy();
        this.mapFileToIDs = new HashMap<>();

        try {
            codeQuiryProxy.getAccount();
//            TODO: Uncomment this part
//            checkID = codeQuiryProxy.createCheck(checkName, languageCode);
            checkID = "12288";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Results compare(List<Repository> repositories) {

        Map<Pair, Double> rows = new HashMap<>();

        // r1, r2, r3 ---> [r1,r2], [r1,r3], [r2, r3]


        // rows will contains this:
        // [r1, r2] -> 99.8%
        // [r1, r3] -> 85%
        for (int i = 0; i < repositories.size(); i++) {
            for (int j = i + 1; j < repositories.size(); j++) {
                Pair pair = new Pair(repositories.get(i), repositories.get(j));
                rows.put(pair, checkSimilarity(pair));
            }
        }
        return new Results(rows);
    }

    private Double checkSimilarity(Pair pair) {
        String referenceURL = pair.getReference().getLocalLink();
        String referredURL = pair.getReferred().getLocalLink();
        List<Double> comparisonResult = null;
        double totalPlagiarism = 0;
        try {
            comparisonResult = CodeQuiry(referenceURL, referredURL);

            for (double score : comparisonResult) {
                totalPlagiarism += score;
            }
        } catch (Exception e) {
            System.out.println("Error from comparing files");
        }

        return totalPlagiarism;
    }

    //TODO
    private List<Double> CodeQuiry(String referenceURL, String referredURL) throws Exception {
        List<Double> results = new ArrayList<>();

        List<String> referencedFiles = new ArrayList<>();
        List<String> referredFiles = new ArrayList<>();

        listAllFileURLs(referenceURL, referencedFiles, ZIP);
        listAllFileURLs(referredURL, referredFiles, ZIP);


        /**
         *
         *  below part will work when the premium account is enabled
         * **/

        /*
        uploadAndCheck(referencedFiles, referredFiles);
         */

        String status = codeQuiryProxy.checkStatus(checkID);

        System.out.println("your check is: " + status +" for files: "+ referenceURL+" and " +referredURL);

        if ("completed".equals(status.toLowerCase())) {
            codeQuiryProxy.overview(checkID);
        } else {
            System.out.println("comparison failed because of " + status);
            throw new Exception();
        }

        for (String reference : referencedFiles) {
            for (String compared : referredFiles) {
                double results1 = codeQuiryProxy.results("44798", "44799");
                results.add(results1);
            }
        }

        return results;
    }

    private void uploadAndCheck(List<String> referencedFiles, List<String> referredFiles) throws Exception {
        for (String reference : referencedFiles) {
            String value = "";
            if (!mapFileToIDs.containsKey(reference)) {
                value = codeQuiryProxy.uploadFile(reference, checkID);
                Thread.sleep(5000);
                mapFileToIDs.put(reference, value);
            }
        }
        for (String compared : referredFiles) {
            if (!mapFileToIDs.containsKey(compared)) {
                mapFileToIDs.put(compared, codeQuiryProxy.uploadFile(compared, checkID));
            }
            Thread.sleep(5000);
        }

        codeQuiryProxy.startCheck(checkID);
        Thread.sleep(10000);
    }

    private void listAllFileURLs(String directory, List<String> results, String extension) {

        File d = new File(directory);
        List<File> files = (List<File>) FileUtils.listFiles(new File(directory), new String[]{extension}, true);
        if (files != null)
            for (File file : files) {
                if (file.isFile()) {
                    results.add(file.getPath());
                } else if (file.isDirectory()) {
                    listAllFileURLs(file.getAbsolutePath(), results, extension);
                }
            }
    }

}


// Connecting our code with Codequiry API
// Modify results to suit our requirements
// Making this entire application live as an API