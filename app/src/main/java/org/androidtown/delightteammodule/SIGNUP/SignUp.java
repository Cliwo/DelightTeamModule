package org.androidtown.delightteammodule.SIGNUP;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.androidtown.delightteammodule.ChanCustomViews.ViewPagerAdapter;
import org.androidtown.delightteammodule.MENU.MenuActivity;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.SQLite.DBHelper;
import org.androidtown.delightteammodule.TEAM.CreateTeam.UnScrolledViewPager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUp extends AppCompatActivity {

    UnScrolledViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    FragContract fragContract;
    FragLinkAccount fragLinkAccount;

    ImageView IVBackSpace;
    LinkAssorter linkAssorter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getHashKey();
        getLinkAssorterFromIntent();
        MenuActivity.setCurrentActivity(this); //이 액티비티에서 로그인을 쓸거기 때문에
        //kakao 의 currentActivity 설정

        fragContract = new FragContract();
        fragLinkAccount = new FragLinkAccount();

        viewPager = (UnScrolledViewPager)findViewById(R.id.VPSignUp);
        viewPager.setPagingEnabled(false);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(fragContract , "contract");
        viewPagerAdapter.addFragment(fragLinkAccount, "linkAccount");

        viewPager.setAdapter(viewPagerAdapter);

        IVBackSpace = (ImageView)findViewById(R.id.IVBackSpace);
        IVBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        checkTemporaryAccount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkTemporaryAccount();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
        super.onBackPressed();
    }

    public void checkTemporaryAccount()
    {
        //if(임시계정이면)
        //    fragWelcome.vanishTVSightSee();
        DBHelper helper = DBHelper.getInstance();
        helper.getSignUpData();
        Log.d("SignUp", "임시계정인지 여부를 체크해서, 다시 임시계정을 발급받지 못하게 합니다.");
    }

    public void getLinkAssorterFromIntent()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        linkAssorter = (LinkAssorter)bundle.getSerializable("LinkAssorter");
    }
    public void setCurrentPage(int page)
    {
        viewPager.setCurrentItem(page);
        if(page==1)
        {
            fragLinkAccount.callOnClick(linkAssorter.linkAssorter);
        }
    }

    public void getHashKey()
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
}
