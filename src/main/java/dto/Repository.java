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
        this.localLink = PlagiarismChecker.getLocalPath() + this.repoIndex + "/master.zip";
    }

    /**
     * @param repository compare this object with parsed repository object
     * @return Class Result
     */
    public double compare(Repository repository) throws Exception {
        String referenceURL = this.getLocalLink();
        String referredURL = repository.getLocalLink();

        codeQuiryProxy.getAccount();
        while (codeQuiryProxy.checkStatus(checkID).toLowerCase().equals("processing")) {
            System.out.println("Check is still under process. Please wait....");
        }

        String status = codeQuiryProxy.checkStatus(checkID);
        System.out.println("your checking has been " + status);
        if ("completed".equals(status.toLowerCase())) {
            System.out.println("wait...retrieving results");
            codeQuiryProxy.overview(checkID);
        } else {
            System.out.println("comparison failed because your check " + status + ". check your account details below:");
            codeQuiryProxy.getAccount();
            throw new Exception();
        }
        String referenceID = reposToID.get(referenceURL);
        String compareID = reposToID.get(referredURL);

        return codeQuiryProxy.results(referenceID, compareID);
    }

    /**
     * @param repositories list of repos get uploaded to API
     * @throws Exception
     */
    static void uploadAndCheck(List<Repository> repositories) throws Exception {
        for (Repository repository : repositories) {
            System.out.println("Uploading repository: Team" + repository.repoIndex + "by authors: " + repository.getTeam());
            if (!reposToID.containsKey(repository.getLocalLink())) {
                reposToID.put(repository.getLocalLink(), codeQuiryProxy.uploadFile(repository.getLocalLink(), checkID));
            }
        }
        System.out.println("uploading done and now, starting the check...");
        Thread.sleep(5000);
        codeQuiryProxy.startCheck(checkID);
    }

    public static void removeThisMethodInFuture() {
        reposToID.clear();
        reposToID.put("/Users/prakshat/Documents/git/GitHubPlagiarismDetector/src/main/resources/Repositories/team1/master.zip", "48735");
        reposToID.put("/Users/prakshat/Documents/git/GitHubPlagiarismDetector/src/main/resources/Repositories/team2/master.zip", "48739");
        reposToID.put("/Users/prakshat/Documents/git/GitHubPlagiarismDetector/src/main/resources/Repositories/team3/master.zip", "48744");
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
        /*
         Todo: Uncomment line "createCheck" and delete the line below it. Reason to comment out this
          line for now is we do not want to waste our checks just for sake of trying the application.
         */
//        checkID = codeQuiryProxy.createCheck(checkName, languageCode);
        checkID = "13783";
        System.out.println("space for the checking is created with ID: " + checkID);
    }

}
