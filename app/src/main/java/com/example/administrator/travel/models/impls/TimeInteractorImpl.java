package com.example.administrator.travel.models.impls;

import com.example.administrator.travel.models.bases.TimeInteractor;

/**
 * Created by Admin on 4/3/2019.
 */

public class TimeInteractorImpl implements TimeInteractor {
    @Override
    public long getInternetTime() {

       // return ServerValue.TIMESTAMP;
//
//        String TIME_SERVER = "time-a.nist.gov";
//        NTPUDPClient timeClient = new NTPUDPClient();
//        InetAddress inetAddress = null;
//        try {
//            inetAddress = InetAddress.getByName(TIME_SERVER);
//            TimeInfo timeInfo = timeClient.getTime(inetAddress);
//            long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
//            return returnTime;
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
       return -1;
    }
}
