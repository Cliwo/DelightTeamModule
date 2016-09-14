package org.androidtown.delightteammodule.MYINFORMATION;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.facebook.Profile;
import com.google.gson.Gson;
import com.kakao.usermgmt.response.model.UserProfile;

import org.androidtown.delightteammodule.SIGNUP.SignUpData;
import org.androidtown.delightteammodule.SQLite.DBHelper;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamMemberSimpleData;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Chan on 2016-07-24.
 */
public class MyInformationData {

    private static MyInformationData inst;

    public SignUpData myData;
    public UserProfile kakaoProfile;
    public Profile facebookProfile;
    //위의 것은 사실 myData안에 들어있다, 근데 내부디비 컬럼을 정리하지 않아서 임시로 이렇게 해놓은것.
    //내부디비 설계가 끝나면 위를 변경하고, myData를 로드해서 데이터를 만들기로 하자.

    public TeamMemberSimpleData pageCompositionData;

    private MyInformationData()
    {

    }
    public static MyInformationData getInstance()
    {
        if(inst == null)
        {
            inst = new MyInformationData();
            inst.pageCompositionData=new TeamMemberSimpleData();
            DBHelper dbHelper = DBHelper.getInstance();
            String str = dbHelper.getSignUpData(); //왼쪽 메소드 변경되야함 //getSignUpData() -> getMyInformationData();;
            Gson gson = new Gson();
            SignUpData data = gson.fromJson(str, SignUpData.class);
            inst.myData = data;
            inst.pageCompositionData.nickName = data.nickName;
            Log.d("MyInformation", str);
        }
        return inst;
    }

    /*public static TeamMemberSimpleData getInstance()
    {
        if(myInfo==null)
        {
            myInfo = new TeamMemberSimpleData();
            //setMyInfo();
        }
        return myInfo;
    }*/

    public boolean isTemporaryAccount()
    {
        if(inst.myData.imageURL.equals(SignUpData.imageURLDefault))
        {
            Log.d("MyInformationData", "임시계정입니다.");
            return true;
        }
        return false;
    }

    public static void createDrawable(final Context context, final MyInformation callbackClass)
    {
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                try {
                    getInstance().pageCompositionData.profileImage = drawableFromUrl(context,getInstance().myData.imageURL);
                    while(getInstance().pageCompositionData.profileImage==null);
                    callbackClass.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callbackClass.setDrawableInProfile(getInstance().pageCompositionData.profileImage);
                        }
                    });
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public static Drawable drawableFromUrl(Context context, String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(context.getResources(),x);
    }
}
