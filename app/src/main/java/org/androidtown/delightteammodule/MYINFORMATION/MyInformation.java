package org.androidtown.delightteammodule.MYINFORMATION;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.ChanRegionSelectDialog;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.GlobalRegionData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.MENU.MenuActivity;
import org.androidtown.delightteammodule.NOTIFICATION.Notification;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.SQLite.DBHelper;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamMemberSimpleData;

public class MyInformation extends AppCompatActivity{

    RelativeLayout RLAccountLinkState;

    ImageView IVAccountLinkState;
    ImageView IVProfileImage;
    ImageView IVCancel;
    ImageView IVAccountLinkCancel;
    TextView TVEdit;
    Button registerPhone;
    TextView[] TVSelectedRegions = new TextView[3];

    TextView TVNickName;
    TextView TVBirth;
    TextView TVIntroduce;
    TextView TVPosition;
    TextView TVManner;

    public static int APP_REQUEST_CODE = 99;
    static final int MY_PERMISSIONS_REQUEST_FOR_SMS= 101;
    static final int MY_PERMISSIONS_REQUEST_FOR_PHONE = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setViews();
        setRegionSelectViews();
        loadMyInformation();
    }
    public void setViews()
    {
        TVEdit = (TextView)findViewById(R.id.TVEdit);
        TVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MyInformationEdit.class);
                startActivity(intent);
            }
        });

        IVCancel = (ImageView)findViewById(R.id.IVCancel);
        IVCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        IVProfileImage = (ImageView)findViewById(R.id.IVProfileImage);

        registerPhone = (Button)findViewById(R.id.btnRegisterPhoneNumber);
        registerPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionForReceiveSMS();
                //차례대로 SMS-권한요청  - Phone State 권한요청 - 전화번호 인증이 들어감
                //지금에서는 권한 허용, 거부 아무 상관없이 전화번호 인증 가능함.
            }
        });
        RLAccountLinkState = (RelativeLayout)findViewById(R.id.RLAccountLinkState);
        IVAccountLinkCancel = (ImageView)findViewById(R.id.IVAccountLinkCancel);
        IVAccountLinkCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RLAccountLinkState.setVisibility(View.GONE);
            }
        });
        IVAccountLinkState = (ImageView)findViewById(R.id.IVAccountLinkState);

        TVNickName = (TextView)findViewById(R.id.TVNickName);;
        TVBirth = (TextView)findViewById(R.id.TVBirth);;
        TVIntroduce = (TextView)findViewById(R.id.TVIntroduce);;
        TVPosition = (TextView)findViewById(R.id.TVPosition);;
        TVManner = (TextView)findViewById(R.id.TVManner);;
    }

    public void loadMyInformation()
    {
        MyInformationData data = MyInformationData.getInstance(); //getInstance 내부에서, data를 디비에서 불러온다.
        setViewsWithData(data);
    }
    public void setViewsWithData(MyInformationData data)
    {
        MyInformationData.createDrawable(this, this);
        TVNickName.setText(data.myData.nickName);
        TVBirth.setText(data.myData.birth.getDateByFormat(ChanDate.WITH_KOREAN));
        for(int i =0 ; i<3; i++)
            if(TVSelectedRegions[i]!=null && data.myData.regions[i]!=null && (!data.myData.regions[i].regionName.equals("NULL")))
                TVSelectedRegions[i].setText(data.myData.regions[i].regionName);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case MY_PERMISSIONS_REQUEST_FOR_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //성공!
                    checkPermissionForReadPhoneState();
                } else {
                    //유저가 거부함!
                    checkPermissionForReadPhoneState();
                }
                return;
            case MY_PERMISSIONS_REQUEST_FOR_PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //성공!
                    onLoginPhone(registerPhone);
                } else {
                    //유저가 거부함!
                    onLoginPhone(registerPhone);
                }
                return ;
            default:


        }
    }
    public void checkPermissionForReceiveSMS()
    {
        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},MY_PERMISSIONS_REQUEST_FOR_SMS);
            }
        }
        else
        {
            checkPermissionForReadPhoneState();
        }
    }
    public void checkPermissionForReadPhoneState()
    {
        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},MY_PERMISSIONS_REQUEST_FOR_PHONE);
            }
        }
        else
        {
            onLoginPhone(registerPhone);
        }
    }

    public void onLoginPhone(final View view) {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN //액세스 토큰 플로 활성화시 TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                //showErrorActivity(loginResult.getError());
                Log.d("Account Kit : ", toastMessage);
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
                Log.d("Account Kit : ", toastMessage);
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                    Log.d("Account Kit not null", toastMessage);
                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0,10));
                    Log.d("Account Kit null: ", toastMessage);
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
                onSuccessRegisterPhoneNumber();
            }

            // Surface the result to your user in an appropriate way.
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
        }
    }

    public void onSuccessRegisterPhoneNumber()
    {
        Log.d("MyInfoActivity" , "성공적으로 전화번호 인증 완료");
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                String accountKitId = account.getId();

                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                String phoneNumberString = phoneNumber.toString();
                Log.d("MyInfoActivity", phoneNumberString);
            }

            @Override
            public void onError(AccountKitError accountKitError) {

            }
        });
    }

    public void setDrawableInProfile(Drawable image)
    {
        IVProfileImage.setBackground(image);
    }

    public void setRegionSelectViews() {
        TVSelectedRegions[0] = (TextView) findViewById(R.id.TVSelectedRegions0);
        TVSelectedRegions[1] = (TextView) findViewById(R.id.TVSelectedRegions1);
        TVSelectedRegions[2] = (TextView) findViewById(R.id.TVSelectedRegions2);
    }


}
