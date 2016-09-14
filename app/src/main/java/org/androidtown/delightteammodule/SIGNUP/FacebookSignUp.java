package org.androidtown.delightteammodule.SIGNUP;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.params.Face;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import org.androidtown.delightteammodule.Connection.ConnectionManager;
import org.androidtown.delightteammodule.Connection.DataSetUpCallback;
import org.androidtown.delightteammodule.Connection.HttpUtilSend;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.ChanRegionSelectDialog;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.GlobalRegionData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.MENU.MenuActivity;
import org.androidtown.delightteammodule.MYINFORMATION.MyInformationData;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.SQLite.DBHelper;

import java.io.IOException;
import java.util.Calendar;

public class FacebookSignUp extends AppCompatActivity implements View.OnClickListener{

    static LoginResult loginResult;
    ProfileTracker profileTracker;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;

    ImageView IVProfileImage;
    TextView TVSignUp;
    TextView TVBirth;
    EditText ETNickName;
    ChanDate cDate; //생년월일
    EditText ETIntroduce; //자기소개

    ImageView IVRegionAdd;
    TextView TVRegionAdd;
    ImageView[] IVRegionDelete = new ImageView[3];
    TextView [] TVSelectedRegions = new TextView[3];
    GlobalRegionData[] regionData = new GlobalRegionData[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab=getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        setViews();
        checkInvalidResult();
        makeTrackers();
        setRegionSelectViews();
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

    public boolean checkRegionDataValidity()
    {
        boolean isEmpty=true;
        for(int i=0;i<3; i++)
            if(regionData[i]!=null)
                isEmpty=false;
        if(isEmpty)
            Toast.makeText(this, "최소 하나의 지역을 선택해야합니다.", Toast.LENGTH_SHORT).show();
        return !isEmpty;
    }
    public boolean checkNickNameValidity()
    {
        String str = ETNickName.getText().toString();
        if(str==null || str.equals("") || str.length()<2) {
            Toast.makeText(this, "16글자, 2글자 이상으로 닉네임을 입력해 주세요.", Toast.LENGTH_LONG).show();
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
    public boolean checkBirthValidity()
    {
        if(cDate == null)
            return false;
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
                    Toast.makeText(FacebookSignUp.this, "닉네임 중복 입니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(result.equals("false"))
                {
                    //2.데이터 인스턴스에 값 저장
                    final String nickName = ETNickName.getText().toString();
                    SignUpData data = new SignUpData(Profile.getCurrentProfile().getId()+"" ,cDate,
                            Profile.getCurrentProfile().getProfilePictureUri(200,200).toString() ,
                            nickName,LinkAssorter.FACEBOOK, regionData);
                    //3.서버로 전송
                    sendSignUpDataToServer(data);
                    //Server 에 전송하고 이후는 위에서 처리.
                }
            }
        });
        send.execute(HttpUtilSend.URLBase + "/user/nicknameDuplCheck",
                "nickname", nickName);
    }


    public void completeSignUpSession()
    {
        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);
        finish();
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
                    Toast.makeText(FacebookSignUp.this, "오류가 발생했습니다. 인터넷 연결을 확인하고, 잠시 후 다시 시도해 주세요.",Toast.LENGTH_LONG).show();
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
        if(ETNickName.getText()==null || cDate == null) {
            TVSignUp.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            return;
        }
        if(!ETNickName.getText().toString().equals("") &&  regionData[0] != null && checkBirthValidity())
            TVSignUp.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        else
            TVSignUp.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    public void setViews() {
        ETNickName = (EditText) findViewById(R.id.ETNickName);
        ETNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkValidity();
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                    }
                }, date.year, date.month, date.day);
                checkValidity();
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
                checkValidity();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        TVSignUp = (TextView) findViewById(R.id.TVSignUp);
        TVSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUp();
            }
        });
        IVProfileImage = (ImageView) findViewById(R.id.IVProfileImage);
        Thread networkThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                try {
                    final Drawable drawable =
                            MyInformationData.drawableFromUrl(FacebookSignUp.this, Profile.getCurrentProfile().getProfilePictureUri(200, 200).toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                             IVProfileImage.setBackground(drawable);
                        }
                    });
                }catch (IOException e)
                {
                    Log.d("FacebookSignUp", "이미지 로드 실패");
                }
            }
        };
        networkThread.start();
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
    public static void setLoginResultFromLink(LoginResult result)
    {
        loginResult=result;
    }

    public void checkInvalidResult()
    {
        //기초정보 가입 안하고 앱 강제종료한 사람들만 뜨는 코드임... ㅜㅠㅠ
        if(loginResult==null) {
            //finish();
            Log.d("Facebook", "loginResult is null.");
            Log.d("Facebook","로그인에 성공한 후, 기초정보를 입력하지 않고 앱이 종료되었습니다. 페이스북 로그인은 되어있는 상태입니다.");
        }
    }
    public void makeTrackers()
    {
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
            }
        };
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        accessToken = AccessToken.getCurrentAccessToken();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
        accessTokenTracker.stopTracking();
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
