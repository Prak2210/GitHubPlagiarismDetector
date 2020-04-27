package controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;

public class Parser {
    private JSONParser jsonParser;
    private final static String SUBMISSION_RESULT = "submissionresults";
    private final static String SUBMISSIONS = "submissions";
    private final static String SCORE = "score";

    public Parser() {
        jsonParser = new JSONParser();
    }


    /**
     *
     * @param json String that needs to be converted to JSONObject
     * @return JSONObject of the input string
     * @throws ParseException
     */
    private JSONObject parse(String json) throws ParseException {
        Object object = jsonParser.parse(json);
        return (JSONObject) object;
    }

    /**
     *
     * @param json
     * @return ID of the
     * @throws ParseException
     */
    public String getCheckID(String json) throws ParseException {
        return parse(json).get("id").toString();
    }

    /**
     * returns the ID of the check created on code-quiry response
     **/
    public void showAccountDetails(String json) throws ParseException {
        JSONObject response = parse(json);
        System.out.println("username: " + response.get("user") + " and email: " + response.get("email") +
                " checks-remained: " + response.get("pro_checks_remaining"));
    }

    /**
     *
     * @param json
     * @return Submission ID of a single ZIP file
     * @throws ParseException
     */

    public String getSubmissionID(String json) throws ParseException {
        return parse(json).get("submission_count").toString();
    }

    public String getStatus(String json) throws ParseException {
        return parse(json).get("status").toString();
    }


    public String getFileID(String json) throws ParseException {
        JSONObject response = parse(json);
        org.json.simple.JSONArray array = (org.json.simple.JSONArray) response.get("data");
        Iterator i = array.iterator();
        String id = "#";

        while (i.hasNext()) {
            JSONObject slide = (JSONObject) i.next();
            id = (long) slide.get("id") + "";
        }
        return id;
    }

    // code quiry refers all files with some unique ids
    public Double getSimilarityScore(String json, long referenceID, long comparedID) throws ParseException {
        JSONObject response = parse(json);
        org.json.simple.JSONArray array = (org.json.simple.JSONArray) response.get(SUBMISSIONS);
        Iterator parentIterator = array.iterator();
        String score = "#";
        l:
        while (parentIterator.hasNext()) {
            JSONObject parent = (JSONObject) parentIterator.next();
            if (parent.containsKey(SUBMISSION_RESULT)) {
                org.json.simple.JSONArray temp = (org.json.simple.JSONArray) parent.get(SUBMISSION_RESULT);
                Iterator childIterator = temp.iterator();
                while (childIterator.hasNext()) {
                    JSONObject children = (JSONObject) childIterator.next();
                    long submission_id = (long) children.get("submission_id");
                    long compare_ID = (long) children.get("submission_id_compared");
                    if (submission_id == referenceID && compare_ID == comparedID) {
                        score = (String) children.get(SCORE);
                        break l;
                    }
                }
            }
        }
        return (!(score + "").equals("#")) ? Double.parseDouble(score) : 00;
    }
}