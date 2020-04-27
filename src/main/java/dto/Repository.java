package dto;

import controller.CodeQuiryProxy;
import controller.PlagiarismChecker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {
    private static CodeQuiryProxy codeQuiryProxy = new CodeQuiryProxy();
    private static String checkID;
    private static Map<String, String> reposToID = new HashMap<>();
    private int repoIndex;
    private String repoLink; //link to their github repository
    private String localLink; // local url to access their repository on our machine
    private List<String> team; //unity-ids
    private static int index = 1;

    public Repository(String[] teamData) {
        this.repoIndex = index++;
        this.team = Arrays.asList(teamData[1].split(","));
        this.repoLink = teamData[2];
        this.localLink = PlagiarismChecker.getLocalPath() + this.repoIndex + "/codequiry.py.zip";
    }

    /**
     *
     * @param repositories List of all the repositories to perform plagiarism
     * @return Class Result
     */
    public double compare(Repository repository) throws Exception {
        String referenceURL = this.getLocalLink();
        String referredURL = repository.getLocalLink();

        /*
         * uncomment below to upload files when premium account is activated
         */
//        upload(referenceURL, referredURL);
        while (codeQuiryProxy.checkStatus(checkID).toLowerCase().equals("processing")) {
            System.out.println("Check is still under process. Please wait....");
        }

        String status = codeQuiryProxy.checkStatus(checkID);
        if ("completed".equals(status.toLowerCase())) {
            codeQuiryProxy.overview(checkID);
        } else {
            System.out.println("comparison failed because your check " + status + " . check your account details below:");
            codeQuiryProxy.getAccount();
            throw new Exception();
        }

        // TODO: replace by reposToID.get(referenceURL(line51)/referredURL(line 52)) when premium account is purchased
        String referenceID = "46479";
        String compareID = "46480";

        return codeQuiryProxy.results(referenceID, compareID);
    }

    /**
     *
     * @param referenceURL
     * @param referredURL
     * uploads files and starts checking
     * @throws Exception
     */
    private void upload(String referenceURL, String referredURL) throws Exception {

        String value = "";
        if (!reposToID.containsKey(referenceURL)) {
            value = codeQuiryProxy.uploadFile(referenceURL, checkID);
            // delay to have some time to clear the upload queue
            Thread.sleep(5000);
            reposToID.put(referenceURL, value);
        }

        if (!reposToID.containsKey(referredURL)) {
            reposToID.put(referredURL, codeQuiryProxy.uploadFile(referredURL, checkID));
        }
        Thread.sleep(5000);

        codeQuiryProxy.startCheck(checkID);
        Thread.sleep(10000);
    }


    public String show() {
        return "ID: " + repoIndex + " Team: " + team.toString() + " URL: " + repoLink + " Local-Path: " + localLink;
    }

    public String getTeam() {
        return team.toString();
    }

    public int getRepoIndex() {
        return this.repoIndex;
    }

    public String getLocalLink() {
        return this.localLink;
    }

    public String getRepoLink() {
        return this.repoLink;
    }

    public static void setCheckID(String checkName, String languageCode) throws Exception {
        // TODO: Uncomment this part when premium account is purchased and remove the hardcoded part
//        checkID = codeQuiryProxy.createCheck(checkName, languageCode);
        checkID = "13117";
    }
}
