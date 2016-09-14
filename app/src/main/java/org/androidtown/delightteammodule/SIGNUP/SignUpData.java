package org.androidtown.delightteammodule.SIGNUP;

import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.GlobalRegionData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;

/**
 * Created by Chan on 2016-08-14.
 */
public class SignUpData {

    public transient boolean isTemporary;

    public static final String socialIDDefault = "defaultID";
    public static final String nickNameDefault = "defaultNickName";
    public static final String imageURLDefault = "defaultURL";
    public static final String birthDefault = "1/1/1";

    public String socialID;
    public String nickName;
    public String accessToken; //카카오톡일 경우 userID , 페이스북은 엑세스토큰
    public String imageURL;
    public ChanDate birth;

    public int linkAssort; //어떻게 연동했는지 구분
    public GlobalRegionData[] regions = new GlobalRegionData[3];

    public SignUpData(String socialId, ChanDate birth, String imageURL, String nickName, int linkAssort ,GlobalRegionData[] regions) {
        this.socialID = socialId;
        this.birth = birth;
        this.imageURL = imageURL;
        this.nickName = nickName;
        this.regions = regions;
        this.linkAssort=linkAssort;
    }
    public SignUpData(String socialId, String birth, String imageURL, String nickName, String linkAssort , String[] regions) {
        this.socialID = socialId;
        this.birth = new ChanDate(birth);
        this.imageURL = imageURL;
        this.nickName = nickName;
        this.linkAssort= (linkAssort == "KAKAO"? LinkAssorter.KAKAO : LinkAssorter.FACEBOOK);
        for(int i =0 ; i<regions.length; i++)
        {
            this.regions[i] = new GlobalRegionData(regions[i]);
        }
    }
    public SignUpData(boolean isTemporary)
    {
        this.birth = new ChanDate(birthDefault);
        this.imageURL = imageURLDefault;
        this.isTemporary = isTemporary;
        this.nickName = nickNameDefault;
        this.socialID = socialIDDefault;
    }
}

