package Controller;

import com.mashape.unirest.http.exceptions.UnirestException;
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


    public CodeQuiryProxy() {
        client = new OkHttpClient();
        BUILDER = new Request.Builder()
                .addHeader("Accept", "*/*")
                .addHeader("apikey", "e72b308caa6d25681e133175682cbd55ceb27eb0bae13d509407e3a7f51d38c2");
        parser = new Parser();
    }

    public void getAccount() throws IOException, ParseException {

        Request request = BUILDER
                .url(BASE_URL + "account")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        parser.showAccountDetails(response.body().string());
    }

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
        return parser.getID(response.body().string());
    }

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

    public String startCheck(String checkID) throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("check_id", checkID)
                .add("dbcheck", "1")
                .build();

        Request request = BUILDER
                .url(BASE_URL + "check/start")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return parser.getSubmissionID(response.body().string());
    }

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

    public double results(String referenceID, String compareID) throws Exception {
        return parser.getSimilarityScore(overview,  Long.parseLong(referenceID),  Long.parseLong(compareID));
    }
}
