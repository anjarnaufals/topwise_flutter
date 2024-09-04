package com.example.topwise;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.topwise.cloudpos.aidl.magcard.AidlMagCard;
import com.topwise.cloudpos.aidl.magcard.EncryptMagCardListener;
import com.topwise.cloudpos.aidl.magcard.MagCardListener;
import com.topwise.cloudpos.aidl.magcard.TrackData;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;


public class SwipeCardActivity {

    private AidlMagCard magCardDev = null; // 磁条卡设备
    private boolean isSwipeCard = false;
    private final int timeOut =6 * 1000;
    private final byte keyIndex = 0x00;
    String data = "";

    public SwipeCardActivity(AidlMagCard magCardDev) {
        this.magCardDev = magCardDev;
    }

    public interface SwipeCardCallback {
        void onCardSwiped(String cardData);
    }

    /**
     * get track data
     *
     * @createtor：Administrator
     * @date:2015-8-4 上午7:29:30
     */
    public void getTrackData(SwipeCardCallback callback) {

        data = magCardDev != null ? "AidlMagCard not null" : "AidlMagCard is Null";

        if (magCardDev != null) {
            try {
                isSwipeCard = true;

                magCardDev.searchCard(timeOut, new MagCardListener.Stub() {
                    @Override
                    public void onTimeout() throws RemoteException {
                        data = "Swipe Timeout";
                        Log.d("Swipe Timeout", "onTimeout: ");
                        isSwipeCard = false;
                    }
                    @Override
                    public void onSuccess(TrackData trackData)
                            throws RemoteException {

                        Map<String, String> dataMap = new HashMap<>();
                        dataMap.put("firstTrackData", trackData.getFirstTrackData());
                        dataMap.put("secondTrackData", trackData.getSecondTrackData());
                        dataMap.put("thirdTrackData", trackData.getThirdTrackData());
                        dataMap.put("cardNo", trackData.getCardno());
                        dataMap.put("expiryDate", trackData.getExpiryDate());
                        dataMap.put("formattedTrackData", trackData.getFormatTrackData());
                        dataMap.put("serviceCode", trackData.getServiceCode());

                        ObjectMapper objectMapper = new ObjectMapper();

                        try {
                            data = objectMapper.writeValueAsString(dataMap);
                            Log.d("onSuccess", "onSuccess: "+objectMapper.writeValueAsString(dataMap));

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (callback != null) {
                                        callback.onCardSwiped(data);
                                    }
                                }
                            });

//                            if (callback != null) {
//                                callback.onCardSwiped(data);
//                            }
                        } catch (JsonProcessingException e) {
                            data = e.toString();
                        }

                        isSwipeCard = false;

                    }

                    @Override
                    public void onGetTrackFail() throws RemoteException {
                        Log.d("onGetTrackFail", "onGetTrackFail: ");

                        isSwipeCard = false;
                        data = "track failed";

                    }

                    @Override
                    public void onError(int arg0) throws RemoteException {
                        Log.d("onError", "onError: ");

                        isSwipeCard = false;
                        data = "error";
                    }

                    @Override
                    public void onCanceled() throws RemoteException {
                        Log.d("onCanceled", "onCanceled: ");

                        isSwipeCard = false;
                        data = "cancelled";

                    }
                });
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                data = e.toString();
            }
        }

    }

    /**
     * get encrypt Track data
     *
     * @createtor：Administrator
     * @date:2015-8-4 上午7:29:40
     */
    public void getEncryptTrackData() {
        try {
            if (null != magCardDev) {
                isSwipeCard = true;
                magCardDev.searchEncryptCard(timeOut, keyIndex, (byte) 0x00, null, (byte) 0x00, new EncryptMagCardListener.Stub() {

                    @Override
                    public void onTimeout() throws RemoteException {
                        isSwipeCard = false;
                        data = "Swipe Timeout";

                    }

                    @Override
                    public void onSuccess(String[] trackData) throws RemoteException {
                        isSwipeCard = false;

                    }

                    @Override
                    public void onGetTrackFail() throws RemoteException {
                        isSwipeCard = false;

                    }

                    @Override
                    public void onError(int arg0) throws RemoteException {
                        isSwipeCard = false;
                    }

                    @Override
                    public void onCanceled() throws RemoteException {
                        isSwipeCard = false;
                    }
                });
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //get encrypt format track data
    public void getEncryptFormatTrackData() {
        try {
            if (null != magCardDev) {
                isSwipeCard = true;
                magCardDev.searchEncryptCard(timeOut, keyIndex, (byte) 0x01,
                        HexUtil.hexStringToByte("1122334455667788"), (byte) 0x00,
                        new EncryptMagCardListener.Stub() {

                            @Override
                            public void onTimeout() throws RemoteException {
                                isSwipeCard = false;
                            }

                            @Override
                            public void onSuccess(String[] trackData) throws RemoteException {
                                isSwipeCard = false;

                            }

                            @Override
                            public void onGetTrackFail() throws RemoteException {
                                isSwipeCard = false;
                            }

                            @Override
                            public void onError(int arg0) throws RemoteException {
                                isSwipeCard = false;
                            }

                            @Override
                            public void onCanceled() throws RemoteException {
                                isSwipeCard = false;
                            }
                        });
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * cancel swipe
     *
     * @createtor：Administrator
     * @date:2015-8-4 上午7:29:49
     */
    public void cancelSwipe() {
        if (isSwipeCard == false) {

            return;
        }
        isSwipeCard = false;
        if (null != magCardDev) {
            try {
                magCardDev.stopSearch();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


}
