package com.example.administrator.travel.models;
import android.os.AsyncTask;
import android.util.Log;

import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.NearbyType;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 23/12/2018.
 */
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NearbyInteractor extends AsyncTask<Object, String, String> {
    private String googlePlacesData;
    String url;
    OnGetNearByFinishedListener listener;
    public NearbyInteractor(OnGetNearByFinishedListener listener){
        this.listener=listener;
    }

    @Override
    protected String doInBackground(Object... objects){

        url = (String)objects[0];

        try {
            googlePlacesData = readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }
    public void getPlaceType(final OnGetNearByFinishedListener listener)
    {
        final List<NearbyType> lstPlacetype = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference placetypeRef = database.getReference("placetypes");
        placetypeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dsPlacetype : dataSnapshot.getChildren())
                {
                    NearbyType placetype = dsPlacetype.getValue(NearbyType.class);
                    placetype.id = dsPlacetype.getKey();
                    lstPlacetype.add(placetype);
                    listener.onGetPlaceTypeSuccess(lstPlacetype);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetPlaceTypeFailure(databaseError.toException());
            }
        });
    }
    public void getNearby(LatLng location, String type, String pagetoken, String apiKey)
    {

        Log.e("getNearby: ", location + type+ pagetoken+ (this.listener==null));
        String url=getUrl(location,type,pagetoken,apiKey);
        this.execute(url);
    }
    @Override
    protected void onPostExecute(String s){

        List<Nearby> lstNearby = new ArrayList<>();
        NearbyDataParser parser = new NearbyDataParser();
        lstNearby = parser.parse(s);
        for(int i=0;i<lstNearby.size();i++)
        {
            //Log.e("onPostExecute: ", nearbyPlaceList.get(i).toString());
        }
        listener.onGetNearbySuccess(lstNearby,parser.nextPageToken );
      //  showNearbyPlaces(nearbyPlaceList);
    }

//    private void showNearbyPlaces(List<Nearby> lstNearBy)    {
//        Double lat=0D,lng=0D;
//        for(int i = 0; i < lstNearBy.size(); i++)
//        {
//
//            MarkerOptions markerOptions = new MarkerOptions();
//            Nearby nearBy = lstNearBy.get(i);
//
//
//            lat+=nearBy.location.latitude;
//            lng+=nearBy.location.longitude;
//
//            markerOptions.position(nearBy.location);
//            markerOptions.title(nearBy.name + " : "+ nearBy.vicinity);
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//
//            mMap.addMarker(markerOptions);
//
//        }
//        lat/=lstNearBy.size();
//        lng/=lstNearBy.size();
//        LatLng latLng= new LatLng(lat,lng);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
//    }
    public String readUrl(String myUrl) throws IOException
    {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(myUrl);
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while((line = br.readLine()) != null)
            {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        Log.d("DownloadURL","Returning data= "+data);

        return data;
    }
    private String getUrl(LatLng location , String type, String pagetoken, String api_key)
    {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+ location.latitude+","+location.longitude);
       // googlePlaceUrl.append("&radius="+radius);
        googlePlaceUrl.append("&rankby=distance");
        googlePlaceUrl.append("&type="+type);
        //     googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+ api_key);
        if(!pagetoken.equals(""))
        {
            googlePlaceUrl.append("&hasNextPage=true");
            googlePlaceUrl.append("&nextPage()=true");
            googlePlaceUrl.append("&pagetoken="+pagetoken);
        }
        Log.e("MapsActivity", "url = "+googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
}
