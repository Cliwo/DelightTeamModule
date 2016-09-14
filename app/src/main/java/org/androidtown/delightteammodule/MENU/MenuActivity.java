package org.androidtown.delightteammodule.MENU;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.appevents.AppEventsLogger;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchCardData;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchDataBase;
import org.androidtown.delightteammodule.MATCH.Items.View.MatchCardView;
import org.androidtown.delightteammodule.MATCH.Main.MatchMain;
import org.androidtown.delightteammodule.MYINFORMATION.MyInformation;
import org.androidtown.delightteammodule.NOTIFICATION.Notification;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.SIGNUP.Welcome;
import org.androidtown.delightteammodule.SQLite.DBHelper;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamSimpleData;
import org.androidtown.delightteammodule.TEAM.Items.View.TeamSimpleView;
import org.androidtown.delightteammodule.TEAM.Main.TeamMain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MenuActivity extends AppCompatActivity {

    public static final String DB_NAME = "TEST";
    static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION= 101;

    static DBHelper dbHelper;

    private static final int TEAM = 1000;
    private static final int MATCH = 2000;

    TextView btnMatch;
    TextView btnTeam;
    TextView btnSignUp;
    TextView btnMyInfo;
    TextView btnNotification;

    RecyclerView RVTeamNews;
    RecyclerView RVPreview;
    RecyclerView RVRecommendMatch;

    RecyclerAdapter<MatchCardView, MatchCardData> recommendAdapter;
    RecyclerAdapter<MatchCardView, MatchCardData> previewAdapter;
    RecyclerAdapter<TeamSimpleView, TeamSimpleData> teamNewsAdapter;


    int redirect;

    static Activity currentActivityForKakao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("MenuActivity", "onCreate");
        setSDKs();
        setButtons();
        setRecyclerViews();
        loadMyInformation();
        getKeyHash();
        requestUnLinkOfKakao();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MenuActivity", "onResume");
        loadMyInformation();
    }

    public void setRecyclerViews()
    {
        TeamSimpleData.context=this;
        RVRecommendMatch= (RecyclerView)findViewById(R.id.RVRecommendMatch);
        RVPreview = (RecyclerView)findViewById(R.id.RVPreview);
        RVTeamNews = (RecyclerView)findViewById(R.id.RVTeamNews);
        recommendAdapter=new RecyclerAdapter<>(R.layout.item_match_card_in_menu, new CustomViewFactory<MatchCardView>() {
            @Override
            public MatchCardView createViewHolder(View layout, ViewGroup parent) {
                MatchCardView view = new MatchCardView(layout, MatchDataBase.MATCH_WAITING);
                return view;
            }
        });
        previewAdapter=new RecyclerAdapter<>(R.layout.item_match_card_in_menu, new CustomViewFactory<MatchCardView>() {
            @Override
            public MatchCardView createViewHolder(View layout, ViewGroup parent) {
                MatchCardView view = new MatchCardView(layout, MatchDataBase.MATCH_PREVIEW);
                return view;
            }
        });
        teamNewsAdapter = new RecyclerAdapter<>(R.layout.item_team_simple, new CustomViewFactory<TeamSimpleView>() {
            @Override
            public TeamSimpleView createViewHolder(View layout, ViewGroup parent) {
                TeamSimpleView view = new TeamSimpleView(layout);
                view.setNewsCountVisible();
                return view;
            }
        });

        //loadDataFromServer();
        //아래코드 더미
        for(int i=0;i<3;i++)
        {
            recommendAdapter.addItem(new MatchCardData());
            previewAdapter.addItem(new MatchCardData());
            teamNewsAdapter.addItem(new TeamSimpleData(R.drawable.logo1, "안녕FC"));
        }

        RVRecommendMatch.setAdapter(recommendAdapter);
        LinearLayoutManager temp = new LinearLayoutManager(this);
        temp.setOrientation(LinearLayoutManager.HORIZONTAL);
        RVRecommendMatch.setLayoutManager(temp);
        RVRecommendMatch.setItemAnimator(new DefaultItemAnimator());

        RVPreview.setAdapter(previewAdapter);
        LinearLayoutManager temp2 = new LinearLayoutManager(this);
        temp2.setOrientation(LinearLayoutManager.HORIZONTAL);
        RVPreview.setLayoutManager(temp2);
        RVPreview.setItemAnimator(new DefaultItemAnimator());

        RVTeamNews.setAdapter(teamNewsAdapter);
        LinearLayoutManager temp3 = new LinearLayoutManager(this);
        temp3.setOrientation(LinearLayoutManager.HORIZONTAL);
        RVTeamNews.setLayoutManager(temp3);
        RVTeamNews.setItemAnimator(new DefaultItemAnimator());

    }


    public void getKeyHash()
    {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "org.androidtown.delightteammodule",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("KeyHash:", "error, name not found");
        } catch (NoSuchAlgorithmException e) {
            Log.d("KeyHash:", "error, no such algorithem");
        }

    }

    public void loadDataFromServer()
    {

    }

    public void loadMyInformation()
    {
        //1. dbHelper 만들기
        dbHelper = DBHelper.getInstance(this, DB_NAME, null, 1); //항상 여기서 이형태의 getInstance가 불려야한다.

        //dbHelper에서 onCreate가 호출되면, 앱이 처음 깔리는 순간이라는 것, 즉 SignUp으로 넘어가게 만든다.
        //현재 dbHelper의 onCreate에서 welcome으로 넘어가면 MenuActivity가 사라지지 않게됨.
        //그래서 백키를 눌러도 무한하게 Welcome으로 다시 돌아갈거임.

        //2. db에서 내정보 가져오기
        String test = dbHelper.getSignUpData();
        //3. db에 내정보가 없다면 바로 SignUp으로 넘어가기
        if(test == null || test.equals("")) //사실 test가 null일 경우는 존재하지 않을것임, table이 db생성과 동시에 만들어지고
                                            //table에 아무것도 없으면 null이 아니라 ""이 반환됨.
        {
            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Log.d("MenuActivity", "startActivity Welcome.activity");
            finish();
        }
        else
        {
            //4. 있다면 정보 가져오기
            Log.d("MenuActivity", test);
        }
    }
    public void setSDKs()
    {
        MenuActivity.setCurrentActivity(this);
        if (KakaoSDK.getAdapter() == null) {//KaKao sdk 초기화
            KakaoSDK.init(new KakaoSDKAdapter());
        }
        FacebookSdk.sdkInitialize(getApplicationContext()); //Facebook sdk 초기화
        AppEventsLogger.activateApp(this);
        // KakaoSDK.init(new KakaoSDKAdapter());
        AccountKit.initialize(getApplicationContext());
        //checkAccessTokenOfAccountKit();
    }
    public void checkAccessTokenOfAccountKit()
    {
        //https://developers.facebook.com/docs/accountkit/android
        //Facebook API 적용하기 (2016-08-16) AccountKit 확인하기 (안드로이드 - Facebook API 폴더내에)
        //엑세스 플로 활성화를 꺼야할 것임. (예상)
        AccessToken accessToken = AccountKit.getCurrentAccessToken();

        if (accessToken != null) {
            //Handle Returning User
            //이미 가입한 유저
        } else {
            //Handle new or logged out user
            //로그아웃 했었거나, 처음 온 유저
        }
    }
    public void requestUnLinkOfKakao()
    {
        UserManagement.requestUnlink(new UnLinkResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onNotSignedUp() {

            }

            @Override
            public void onSuccess(Long result) {
                Log.d("kakao", "앱 연결 해제 성공");
            }
        });
    }

    public void setButtons()
    {
        btnMatch = (TextView)findViewById(R.id.btnMatch);
        btnTeam = (TextView)findViewById(R.id.btnTeam);
        btnSignUp=(TextView)findViewById(R.id.btnSignUp);
        btnMyInfo = (TextView)findViewById(R.id.btnMyInfo);
        btnNotification = (TextView)findViewById(R.id.btnNotification);

        btnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect = MATCH;
                checkPermissionForMapView();
            }
        });
        btnTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect = TEAM;
                checkPermissionForMapView();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), Welcome.class);
                startActivity(intent);
            }
        });
        btnMyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyInformation.class);
                startActivity(intent);
            }
        });
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Notification.class);
                startActivity(intent);
            }
        });
    };

    public static Activity getCurrentActivity() {
        return currentActivityForKakao;
    } //kakao

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        MenuActivity.currentActivityForKakao = currentActivity;
    }

    private static class KakaoSDKAdapter extends KakaoAdapter {
        /**
         * Session Config에 대해서는 default값들이 존재한다.
         * 필요한 상황에서만 override해서 사용하면 됨.
         * @return Session의 설정값.
         */
        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Activity getTopActivity() {
                    return MenuActivity.getCurrentActivity();
                }

                @Override
                public Context getApplicationContext() {
                    return MenuActivity.getCurrentActivity().getApplicationContext();
                }
            };
        }
    }

    public void checkPermissionForMapView()
    {
        int permissionCheck= ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            Log.d("Permission", "dialog");
        }
        else if(permissionCheck == PackageManager.PERMISSION_GRANTED)
        {
            redirectActivity();
            Log.d("Permission", "redirect");
        }
    }

    public void redirectActivity()
    {
        if(redirect == TEAM)
        {
            Intent intent = new Intent(this, TeamMain.class);
            startActivity(intent);
        }
        else if (redirect == MATCH)
        {
            Intent intent = new Intent(this, MatchMain.class);
            startActivity(intent);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //성공!
                    redirectActivity();
                } else {
                    //유저가 거부함!
                    Toast.makeText(this, "위치정보를 받아올 수 없으므로, 앱이 간혈적으로 강제종료 될 수 있습니다.",Toast.LENGTH_LONG).show();
                }
                return;
            default:
        }
    }
}
