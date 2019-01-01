package com.example.administrator.travel.presenters;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.DownLoadImageTask;
import com.example.administrator.travel.models.NearbyInteractor;
import com.example.administrator.travel.models.OnDownloadImageFinishedListener;
import com.example.administrator.travel.models.OnGetNearByFinishedListener;
import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.NearbyType;
import com.example.administrator.travel.views.NearbyView;
import com.example.administrator.travel.views.fragments.NearbyFragment;
import com.google.android.gms.location.places.PlaceTypes;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Administrator on 23/12/2018.
 */

public class NearbyPresenter implements OnGetNearByFinishedListener,OnDownloadImageFinishedListener {
    NearbyView view;
    NearbyInteractor nearbyInteractor;
    Integer radius;
    String type;
    Boolean showOption=false;
    Location location;
    String nextPageToken="";
    String apiKey="";
    int page=0;
    boolean loadFinished=false;
    public NearbyPresenter(NearbyView view)
    {
        this.view=view;
        nearbyInteractor=new NearbyInteractor(this);
    }
    public void onViewLoad()
    {
        nearbyInteractor.getPlaceType(this);

    }
    public void onListViewNearbyScrollBottom(){
        if(page<3 && loadFinished && !nextPageToken.equals("")) {
            loadFinished=false;
            nearbyInteractor.getNearby(new LatLng(location.getLatitude(),location.getLongitude()),type, nextPageToken,apiKey);
            page++;
        }

    }
    public void onSelectItemSpinnerPlaceType(String type, String apiKey)
    {
        this.apiKey=apiKey;
        this.type=type;
        if(location==null)
            Log.e( "location is null:  ","true" );
        else{
            loadFinished=false;
            nearbyInteractor.getNearby(new LatLng(location.getLatitude(),location.getLongitude()),type, "", apiKey);
            page=1;
        }
    }

    public void onViewReceivedLocation(Location location){
        this.location=location;
    }
    public void onItemNearbyClicked()
    {

    }


    @Override
    public void onGetNearbySuccess(List<Nearby> lstNearby, String nextPageToken) {
        loadFinished=true;
        this.nextPageToken=nextPageToken;
        if(page==1)
            view.showNearbys(lstNearby,location);
        else{
            view.appendNearbys(lstNearby);
            Log.e("append: ",lstNearby.size()+""+" page"+page );
        }
        nearbyInteractor=new NearbyInteractor(this);
        String url;
        for(int i=0;i<lstNearby.size();i++) {
            {
                if (!lstNearby.get(i).photo_reference.equals("")) {
                    url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&photoreference="
                            + lstNearby.get(i).photo_reference
                            + "&key=AIzaSyCVbtAz_cu7lQYhqXBytJQLuLB_HlclXJQ";
                    //      Log.e("photo_url_request: ", url);

                } else {
                    url = lstNearby.get(i).iconURl;
                }
                new DownLoadImageTask(this).execute(i + "", url);
            }

        }
    }

    @Override
    public void onGetNearbyFailure(Exception ex) {
        view.notifyGetNearbyFailure(ex);
    }

    @Override
    public void onGetPlaceTypeSuccess(List<NearbyType> lstPlaceType) {
        view.showPlacetypes(lstPlaceType);
    }

    @Override
    public void onGetPlaceTypeFailure(Exception ex) {

    }

    @Override
    public void onDownloadImageSuccess(int index, Bitmap bitmap) {
        view.updateListViewImages((page-1)*20+index,bitmap);
       // Log.e( "download success: ", (page-1)*10+index+"");
    }

    @Override
    public void onDownloadImageFailure(Exception ex) {

    }
}
