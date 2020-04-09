package Controller;

import dto.Repository;
import okhttp3.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class OkHttpExample {
    private final String localPath = "/Users/prakshat/Documents/git/GitHubPlagiarismDetector/src/main/resources/Repositories/team";
    private String changeDirectory = "cd " + localPath;
    private String takePull = "git pull -r";
    private String cloneRepository = "git clone ";
    public void makeTeamRepositories(int size) throws InterruptedException, IOException {

        for(int index=1; index<= size; index++) {
            File f = new File((localPath+index));
            f.mkdir();

        }

    }


    public void sendPost(OkHttpClient okHttpClient, List<Repository> repositories) throws Exception {
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/prak2210/WolfHospital")
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (Exception e) {
            System.out.println(e);
        }



    }

}