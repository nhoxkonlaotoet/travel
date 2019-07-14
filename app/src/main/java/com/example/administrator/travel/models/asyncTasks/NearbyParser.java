package com.example.administrator.travel.models.asyncTasks;

import java.util.HashMap;

import android.util.Log;

import com.example.administrator.travel.models.entities.Nearby;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Administrator on 23/12/2018.
 */

public class NearbyParser {
    public String nextPageToken = "";

    private Nearby getPlace(JSONObject googlePlaceJson) {
        Nearby nearBy = new Nearby();
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String id = "";
        String placeName = "--NA--";
        String vicinity = "--NA--";
        RealmList<String> types;
        Double latitude;
        Double longitude;
        String photo_reference=null;
        Boolean openNow = null;
        Integer priceLevel = -1;
        Double rating = 0D;
        String iconURL = "";
        Log.d("NearbyParser", "jsonobject =" + googlePlaceJson.toString());


        try {

            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            if (!googlePlaceJson.isNull("opening_hours")
                    && !googlePlaceJson.getJSONObject("opening_hours").isNull("open_now")) {
                openNow = googlePlaceJson.getJSONObject("opening_hours").getBoolean("open_now");
            }
            if (!googlePlaceJson.isNull("photos")) {
                JSONArray photoArr = googlePlaceJson.getJSONArray("photos");
                JSONObject jsonObject = photoArr.getJSONObject(0);
                if (!jsonObject.isNull("photo_reference"))
                    photo_reference = jsonObject.getString("photo_reference");
            }
            if (!googlePlaceJson.isNull("price_level"))
                priceLevel = googlePlaceJson.getInt("price_level");
            if (!googlePlaceJson.isNull("rating"))
                rating = googlePlaceJson.getDouble("rating");
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
            JSONArray arr = googlePlaceJson.getJSONArray("types");
            id = googlePlaceJson.getString("id");
            iconURL = googlePlaceJson.getString("icon");
            types = new RealmList<>();
            for (int i = 0; i < arr.length(); i++) {
                types.add(arr.get(i).toString());
            }


            nearBy.id = id;
            nearBy.name = placeName;
            nearBy.vicinity = vicinity;
            nearBy.openNow = openNow;
            nearBy.priceLevel = priceLevel;
            nearBy.rating = rating;
            nearBy.types = types;
            nearBy.latitude = latitude;
            nearBy.longitude = longitude;
            if (photo_reference != null)
                nearBy.photos.add(photo_reference);
            nearBy.icon = iconURL;
            //  Log.e("nearby",nearBy.toString() );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nearBy;

    }

    private List<Nearby> getPlaces(JSONArray jsonArray) {
        int count = jsonArray.length();
        List<Nearby> placelist = new ArrayList<>();
        Nearby placeMap = new Nearby();
        for (int i = 0; i < count; i++) {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placelist.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Log.e( "getPlaces: ","Count="+placelist.size() );
        return placelist;
    }

    public List<Nearby> parse(String jsonData) {
        // Log.e( "json data: ", jsonData);
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        //   Log.d("json data", jsonData);

        try {
            jsonObject = new JSONObject(jsonData);
            if (!jsonObject.isNull("next_page_token"))
                nextPageToken = jsonObject.getString("next_page_token");
            jsonArray = jsonObject.getJSONArray("results");
            //   for(int i=0;i<jsonArray.length();i++)
            //       Log.e( "parse: ", jsonArray.getJSONObject(i).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }
}
