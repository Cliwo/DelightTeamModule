package org.androidtown.delightteammodule.Gloabal.AddressAndLocation;

import java.io.Serializable;

/**
 * Created by Chan on 2016-07-20.
 */
public class ChanAddress implements Serializable {
    //gps 정보 넣어야함
    public String nickName; //주소 간단 이름 (마두역)
    public String addressInformation; //실제 주소 (경기도 고양시 일산동구 마두역 이런식)
    public double longitude;
    public double latitude;

    public ChanAddress(String nickName, String addressInformation, double longitude, double latitude)
    {
        this.nickName=nickName;
        this.addressInformation=addressInformation;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public ChanAddress(String nickName)
    {
        this.nickName=nickName;
    }
    public ChanAddress()
    {

    }
}
