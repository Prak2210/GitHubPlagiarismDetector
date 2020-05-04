package controller;

import okhttp3.*;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class CodeQuiryProxy {
    private static OkHttpClient client;
    private static final String BASE_URL = "https://codequiry.com/api/v1/";
    private static Request.Builder BUILDER;
    private static Parser parser;
    static String overview = "";

    /**
    * initialize client and Codequiry account information with API Key
    **/
    public CodeQuiryProxy() {
        client = new OkHttpClient();
        BUILDER = new Request.Builder()
                .addHeader("Accept", "*/*")
                .addHeader("apikey", "ea0fc2a276089c875492ade3a2080af7115a8da5954d2d6fe21826da308590f6");
        parser = new Parser();
    }

    /**
     * get Account details printed to check whether you have access
     **/
    public void getAccount() throws IOException, ParseException {

        Request request = BUILDER
                .url(BASE_URL + "account")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        parser.showAccountDetails(response.body().string());
    }

    /**
     * generates a check for a project with a given language parser
     * java -> 13, python -> 14 ruby -> 33
     **/
    public String createCheck(String checkName, String languageCode) throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("name", checkName)
                .add("language", languageCode)
                .build();

        Request request = BUILDER
                .url(BASE_URL + "check/create")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response);
        return parser.getCheckID(response.body().string());
    }

    /**
     * uploads required files to code-quiry for further checks and return ID of the file on code-quiry
     **/
    public String uploadFile(String fileURL, String checkID) throws Exception {
        MediaType mediaType = MediaType.parse("multipart/form-data");
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("check_id", checkID)
                .addFormDataPart("file", fileURL,
                        RequestBody.create(
                                mediaType, new File(fileURL)))
                .build();

        Request request = BUILDER
                .url(BASE_URL + "check/upload")
                .post(body)
                .addHeader("Content-Type", "multipart/form-data")
                .build();

        Response response = client.newCall(request).execute();
        return parser.getFileID(response.body().string());
    }

    /**
     * after start check is done and upload is done, this will start comparisons between all the repositories there
     **/

    public String startCheck(String checkID) throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("check_id", checkID)
                .build();

        Request request = BUILDER
                .url(BASE_URL + "check/start")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return parser.getSubmissionID(response.body().string());
    }

    /**
     *  gives the update of comparison status: it can be "halted", "completed" or "processing"
     **/

    public String checkStatus(String checkID) throws Exception {

        RequestBody body = new FormBody.Builder()
                .add("check_id", checkID)
                .build();

        Request request = BUILDER
                .url(BASE_URL + "check/get")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return parser.getStatus(response.body().string());
    }

    /**
     * gives the results of all the files compared
     **/
    public void overview(String checkID) throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("check_id", checkID)
                .build();

        Request request = BUILDER
                .url(BASE_URL + "check/overview")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        overview = response.body().string();
    }

    /**
     * processes results by the specific files on CodeQuiry to get their similarity
     **/
    public double results(String referenceID, String compareID) throws Exception {
        return parser.getSimilarityScore(overview,  Long.parseLong(referenceID),  Long.parseLong(compareID));
    }
}
