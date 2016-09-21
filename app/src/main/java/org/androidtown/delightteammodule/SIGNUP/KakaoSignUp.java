package org.androidtown.delightteammodule.SIGNUP;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.gson.Gson;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import org.androidtown.delightteammodule.Connection.DataSetUpCallback;
import org.androidtown.delightteammodule.Connection.HttpUtilSend;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.ChanRegionSelectDialog;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.GlobalRegionData;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.RegionSelectCallback;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.MENU.MenuActivity;
import org.androidtown.delightteammodule.MYINFORMATION.MyInformationData;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.SQLite.DBHelper;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

//만약에 디바이스를 바꿔서 회원가입한 정보가 서버에는 있고, 기기에는 없는 경우를 체크해야함
//현재는 그런 기능이 없음 2016-08-14

public class KakaoSignUp extends AppCompatActivity implements View.OnClickListener{

    public static final int KAKAO_SUCCESS = 9999;
    Context context;

    TextView TVBirth;
    TextView TVSignUp;
    EditText ETNickName;
    EditText ETIntroduce; //자기소개

    ImageView IVProfileImage;
    ChanDate cDate; //생년월일

    ImageView IVRegionAdd;
    TextView TVRegionAdd;
    ImageView[] IVRegionDelete = new ImageView[3];
    TextView [] TVSelectedRegions = new TextView[3];
    GlobalRegionData[] regionData = new GlobalRegionData[3];

    private UserProfile profile;
    private long userId; //accessToken 에서 가져오는 값.
    private String profileImageURL;
    public void setUserId(long val)
    {
        userId=val;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab=getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        context=this;
        setViews();

        requestAccessTokenInfo();
        requestMe();
        setRegionSelectViews();
        TVSignUp = (TextView)findViewById(R.id.TVSignUp);
        TVSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUp();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Log.d("FacebookSignUp", "백키를 막습니다.");
        Toast.makeText(this,"기초 정보를 입력해주세요.",Toast.LENGTH_LONG).show();
        //super.onBackPressed();
    }
    public void addMyInformationInSQLite(SignUpData data)
    {
        DBHelper dbHelper = DBHelper.getInstance();
        dbHelper.addSignUpData(data);
    }
    public void onSignUp()
    {
        //1.닉네임이 올바른지 체크
        if(!checkNickNameValidity())
            return;
        //1-1.지역선택 올바른지  체크
        if(!checkRegionDataValidity())
            return;
        //1-2. 생년월일 체크
        if(!checkBirthValidity())
            return;
        //1-3. 닉네임 중복체크
        checkNickNameDuplicate(ETNickName.getText().toString());
        //이후의 작업들은, 중복체크에서 부릅니다. 왜냐하면
        //중복체크가 실패할경우 값 저장을 하지 않고, 다시 돌아와야하기 때문.

        /*
            대략 이런 루트로 메소드가 진행됩니다.
            1. checkNickNameValidity -> 닉네임이 올바른지 체크
            1-1. checkRegionDataValidity -> 지역을 최소 하나 이상 선택했는지 체크
            1-2. checkNickNameDuplicate -> 닉네임 중복체크 (서버)
                성공 : sendSignUpDataToServer -> 2로 갑니다.
                실패 : onSignUp이 끝나버리고, 다시 버튼을 눌러서 진행해야 합니다.

            2. sendSignUpDataToServer ->  다시 서버로, 데이터를 보냅니다.
                성공 : addMyInformationInSQLite -> 3으로 갑니다.
                실패 : onSignUp이 끝나버리고, 다시 버튼을 눌러서 진행해야 합니다.
            3. addMyInformationInSQLite ->
         */
    }
    public void sendSignUpDataToServer(final SignUpData data)
    {
        Log.d("ServerSend", data.socialID);
        HttpUtilSend send = new HttpUtilSend(new DataSetUpCallback() {
            @Override
            public void onDataReceived(String result) {
                //3-1 서버에 저장을 성공했다면, 가입이 완료된것이므로, 내부디비에도 저장
                if(result == null || result.equals(""))
                {
                    Log.d("SignUp Fail", "회원가입 실패, 서버로 Data 가 안 감");
                    Toast.makeText(KakaoSignUp.this, "오류가 발생했습니다. 인터넷 연결을 확인하고, 잠시 후 다시 시도해 주세요.",Toast.LENGTH_LONG).show();
                    return;
                }
                addMyInformationInSQLite(data);
                //4 회원가입 완료.
                completeSignUpSession();
            }
        });
        send.execute("http://footballnow.ap-northeast-2.elasticbeanstalk.com/user/join"
                , "social_id", data.socialID + ""
                , "social_type", data.linkAssort + ""
                , "nickname", data.nickName
                , "birth_date", data.birth.getDateByFormat(ChanDate.SLASH_SLASH_SLASH_WITHOUT_ZERO)
                , "profile_img", data.imageURL);
    }
    public void checkValidity()
    {
        Log.d("Validity", "Check");
        if(ETNickName.getText()==null || cDate == null) {
            TVSignUp.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            return;
        }
        if(regionData[0] != null && checkBirthValidity() && ETNickName.getText().length()>1)
            TVSignUp.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        else {
            Log.d("Validity", ETNickName.getText().toString());
            TVSignUp.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    public void setViews()
    {
        ETNickName = (EditText) findViewById(R.id.ETNickName);
        ETNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkValidity();
            }
        });
        TVBirth = (TextView)findViewById(R.id.TVBirth);
        TVBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChanDate date = new ChanDate(ChanDate.getCurrentDateByFormat(ChanDate.DEFAULT));
                DatePickerDialog dialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (cDate == null)
                            cDate = new ChanDate();
                        cDate.year = year;
                        cDate.month = monthOfYear + 1;
                        cDate.day = dayOfMonth;
                        TVBirth.setText(cDate.getDateByFormat(ChanDate.WITH_KOREAN));
                        checkValidity();
                    }
                }, date.year, date.month, date.day);
                dialog.show();
            }
        });
        ETIntroduce = (EditText)findViewById(R.id.ETIntroduce);
        ETIntroduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkValidity();
            }
        });
        TVSignUp = (TextView)findViewById(R.id.TVSignUp);
        TVSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUp();
            }
        });
        IVProfileImage = (ImageView) findViewById(R.id.IVProfileImage);
    }

    public boolean checkBirthValidity()
    {
        if(cDate == null)
            return false;
        return true;
    }

    public boolean checkRegionDataValidity()
    {
        boolean isEmpty=true;
        for(int i=0;i<3; i++)
            if(regionData[i]!=null)
                isEmpty=false;
        if(isEmpty)
            Toast.makeText(KakaoSignUp.this, "최소 하나의 지역을 선택해야합니다.", Toast.LENGTH_SHORT).show();
        return !isEmpty;
    }

    public boolean checkNickNameValidity()
    {
        String str = ETNickName.getText().toString();
        if(str==null || str.equals("") || str.length()<2) {
            Toast.makeText(this, "16글자 미만, 2글자 이상으로 닉네임을 입력해 주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        //http://okky.kr/article/33317
        for(int i=0;i<str.length();i++)
        {
            if(!isHangul(str.charAt(i)) && !Character.isLetterOrDigit(str.charAt(i)))
            {
                Log.d("SignUP Facebook", "한글도, 영어도, 숫자도 아닙니다.");
                Toast.makeText(this, "특수문자는 입력할 수 없습니다.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }
    public void checkNickNameDuplicate(String nickName)
    {
        //여기서 중복검사 만들기.
        //서버에서 true를 반환하면 중복된 것이다.
        Log.d("duplicateCheck", nickName);
        HttpUtilSend send = new HttpUtilSend(new DataSetUpCallback() {
            @Override
            public void onDataReceived(String result) {
                Log.d("duplicateCheckResult", result);
                if(result.equals("true"))
                {
                    Toast.makeText(KakaoSignUp.this, "닉네임 중복 입니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(result.equals("false"))
                {
                    //2.데이터 인스턴스에 값 저장
                    final String nickName = ETNickName.getText().toString();
                    SignUpData data = new SignUpData(profile.getId()+"" ,cDate,
                            profile.getProfileImagePath().toString() ,
                            nickName,LinkAssorter.FACEBOOK, regionData);
                    //3.서버로 전송
                    sendSignUpDataToServer(data);
                    //Server 에 전송하고 이후는 위에서 처리.
                }
            }
        });
        send.execute(HttpUtilSend.URLBase + "/user/nicknameDuplCheck",
                "nickname",nickName); //Json 으로 변환을, 내가 하는 부분
    }

    public void completeSignUpSession()
    {
        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);
        finish();
    }

    public static boolean isHangul(char ch) {
        //http://outliers.tistory.com/entry/Java-%EC%97%90%EC%84%9C-%ED%95%9C%EA%B8%80-%EC%B2%B4%ED%81%AC
        Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);
        if (Character.UnicodeBlock.HANGUL_SYLLABLES == block
                || Character.UnicodeBlock.HANGUL_JAMO == block
                || Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO == block) {
            return true;
        }
        return false;
    }


    protected void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);
                Log.d("Kakao", "failed");
                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    Toast.makeText(getApplicationContext(), "에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                //redirectLoginActivity();
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                profile = userProfile;
                profileImageURL = userProfile.getProfileImagePath();
                Log.d("Kakao", "UserProfile : " + userProfile);
//                MyInformationData.setLinkKakaoAccountData(userProfile);
                //redirectMainActivity();
                Thread networkThread = new Thread()
                {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            final Drawable drawable =
                                    MyInformationData.drawableFromUrl(KakaoSignUp.this, profileImageURL);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    IVProfileImage.setBackground(drawable);
                                }
                            });
                        }catch (IOException e)
                        {
                            Log.d("KakaoSignUp", "이미지 로드 실패");
                        }
                    }
                };
                networkThread.start();
            }

            @Override
            public void onNotSignedUp() {
                Log.d("Kakao", "onNotSignedUp");
            }
        });
    }


    private void requestAccessTokenInfo() {

        AuthService.requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                //redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
                // not happened
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e("failed to get access token info. msg=" + errorResult);
            }

            @Override
            public void onSuccess(AccessTokenInfoResponse accessTokenInfoResponse) {
                long userId = accessTokenInfoResponse.getUserId();
                Log.d("Kakao", "this access token is for userId=" + userId);
                setUserId(userId);
                long expiresInMilis = accessTokenInfoResponse.getExpiresInMillis();
                Log.d("Kakao", "this access token expires after " + expiresInMilis + " milliseconds.");

            }
        });
    }

    private void redirectLoginActivity()
    {
        setResult(KAKAO_SUCCESS);
        finish();
    }

    private void redirectMainActivity() {
        //startActivity(new Intent(this, KakaoServiceListActivity.class));
        finish();
    }

    public void sortRegionTextViews()
    {
        for(int i=0;i<2;i++)
        {
            if(regionData[i]==null)
            {
                for(int j=i+1; j<3; j++)
                {
                    if(regionData[j]!=null)
                    {
                        regionData[i]=regionData[j];
                        regionData[j] = null;
                        //TVSelectedRegions[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorAccent, null));
                        TVSelectedRegions[i].setText(TVSelectedRegions[j].getText().toString());
                        IVRegionDelete[i].setVisibility(View.VISIBLE);
                        //TVSelectedRegions[j].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorBright, null));
                        TVSelectedRegions[j].setText("");
                        IVRegionDelete[j].setVisibility(View.INVISIBLE);
                        break;
                    }
                }
            }
        }
    }

    public void setRegionSelectViews()
    {
        TVRegionAdd = (TextView)findViewById(R.id.TVRegionAdd);
        TVRegionAdd.setOnClickListener(this);
        IVRegionAdd = (ImageView)findViewById(R.id.IVRegionAdd);
        IVRegionAdd.setOnClickListener(this);

        TVSelectedRegions[0] = (TextView)findViewById(R.id.TVSelectedRegions0);
        TVSelectedRegions[0].setOnClickListener(this);
        TVSelectedRegions[1] = (TextView)findViewById(R.id.TVSelectedRegions1);
        TVSelectedRegions[1].setOnClickListener(this);
        TVSelectedRegions[2] = (TextView)findViewById(R.id.TVSelectedRegions2);
        TVSelectedRegions[2].setOnClickListener(this);

        IVRegionDelete[0]=(ImageView)findViewById(R.id.IVRegionDelete0);
        IVRegionDelete[0].setOnClickListener(this);
        IVRegionDelete[1]=(ImageView)findViewById(R.id.IVRegionDelete1);
        IVRegionDelete[1].setOnClickListener(this);
        IVRegionDelete[2]=(ImageView)findViewById(R.id.IVRegionDelete2);
        IVRegionDelete[2].setOnClickListener(this);

        for(int i=0;i<3;i++)
            IVRegionDelete[i].setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        { case R.id.TVRegionAdd:case R.id.TVSelectedRegions0:case R.id.TVSelectedRegions1:case R.id.TVSelectedRegions2:case R.id.IVRegionAdd:
            //지역 선택
            ChanRegionSelectDialog dialog = new ChanRegionSelectDialog();
            dialog.setCallBackViewAndData(TVSelectedRegions, IVRegionDelete, regionData);
            dialog.setCallback(new RegionSelectCallback() {
                @Override
                public void onAcceptPressed() {
                    checkValidity();
                }
            });
            dialog.show(getSupportFragmentManager(), "");
            break;

            case R.id.IVRegionDelete0:
                regionData[0]=null;
                //TVSelectedRegions[0].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorBright, null));
                TVSelectedRegions[0].setText("");
                IVRegionDelete[0].setVisibility(View.INVISIBLE);
                sortRegionTextViews();
                break;
            case R.id.IVRegionDelete1:
                regionData[1]=null;
                //TVSelectedRegions[1].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorBright, null));
                TVSelectedRegions[1].setText("");
                IVRegionDelete[1].setVisibility(View.INVISIBLE);
                sortRegionTextViews();
            case R.id.IVRegionDelete2:
                regionData[2]=null;
                //TVSelectedRegions[2].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorBright, null));
                TVSelectedRegions[2].setText("");
                IVRegionDelete[2].setVisibility(View.INVISIBLE);
                sortRegionTextViews();

                break;
        }
    }
}


