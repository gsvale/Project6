package com.example.project6.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.project6.objects.New;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // Tags for Json values obtained
    private static final String RESPONSE_TAG = "response";
    private static final String RESULTS_TAG = "results";
    private static final String WEB_TITLE_TAG = "webTitle";
    private static final String SECTION_NAME_TAG = "sectionName";
    private static final String TAGS_TAG = "tags";
    private static final String WEB_PUBLICATION_DATE_TAG = "webPublicationDate";
    private static final String WEB_URL_TAG = "webUrl";


    // Parse Json data and create New item objects from that information
    public static List<New> extractNewsFeedDataFromJson(String jsonString) {

        // If Json string is empty or null, return null
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        List<New> news = new ArrayList<>();

        try{

            // Create Root Json Object
            JSONObject rootJsonResponse = new JSONObject(jsonString);

            // Create Response Json Object
            JSONObject responseJsonResponse = rootJsonResponse.getJSONObject(RESPONSE_TAG);

            // Create News json Array associated with key "results"
            JSONArray newsJsonArray = responseJsonResponse.getJSONArray(RESULTS_TAG);

            int newsJsonArraySize = newsJsonArray.length();

            // Parse all items of array results to create New items
            for(int i = 0 ; i < newsJsonArraySize; i++){

                JSONObject newJsonObject = newsJsonArray.getJSONObject(i);

                String title = "";
                String section = "";
                String author = "";
                String date = "";
                String url = "";

                if(!TextUtils.isEmpty(newJsonObject.getString(WEB_TITLE_TAG))) {
                    title = newJsonObject.getString(WEB_TITLE_TAG);
                }

                if(!TextUtils.isEmpty(newJsonObject.getString(SECTION_NAME_TAG))) {
                    section = newJsonObject.getString(SECTION_NAME_TAG);
                }

                JSONArray tagsJsonArray = newJsonObject.getJSONArray(TAGS_TAG);
                int tagsJsonArraySize = tagsJsonArray.length();

                for(int j = 0 ; j < tagsJsonArraySize; j++){
                    JSONObject tagJsonObject = tagsJsonArray.getJSONObject(j);
                    if(!TextUtils.isEmpty(tagJsonObject.getString(WEB_TITLE_TAG))) {
                        author = tagJsonObject.getString(WEB_TITLE_TAG);
                        break;
                    }
                }


                if(!TextUtils.isEmpty(newJsonObject.getString(WEB_PUBLICATION_DATE_TAG))) {
                    date = newJsonObject.getString(WEB_PUBLICATION_DATE_TAG);
                }

                if(!TextUtils.isEmpty(newJsonObject.getString(WEB_URL_TAG))) {
                    url = newJsonObject.getString(WEB_URL_TAG);
                }

                // Create New item with values parsed and extracted from Json data
                New newItem = new New(title, section, author, date, url);
                news.add(newItem);
            }

        }
        catch(JSONException e){
            Log.e(HttpUtils.LOG_TAG, "Problem parsing the news JSON results", e);
        }

        return news;
    }





}
