package org.androidtown.delightteammodule.SIGNUP;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.delightteammodule.MENU.MenuActivity;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.SQLite.DBHelper;

public class Welcome extends AppCompatActivity {

    public LinkAssorter linkAssorter;
    public TextView TVLinkKakao;
    public TextView TVLinkFacebook;
    public TextView TVSightSee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linkAssorter=new LinkAssorter();

        TVLinkKakao = (TextView)findViewById(R.id.TVLinkKakao);
        TVLinkKakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkAssorter.linkAssorter=LinkAssorter.KAKAO;
                Intent intent = new Intent(v.getContext(), SignUp.class);
                intent.putExtra("LinkAssorter", linkAssorter);
                startActivity(intent);
                finish();
                Log.d("Welcome", "Kakao");
            }
        });
        TVLinkFacebook = (TextView)findViewById(R.id.TVLinkFacebook);
        TVLinkFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkAssorter.linkAssorter = LinkAssorter.FACEBOOK;
                Intent intent = new Intent(v.getContext(), SignUp.class);
                intent.putExtra("LinkAssorter", linkAssorter);
                startActivity(intent);
                finish();
                Log.d("Welcome", "Facebook");
            }
        });
        TVSightSee = (TextView)findViewById(R.id.TVSightSee);
        TVSightSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSightSee();
                Log.d("Welcome", "SightSee");
            }
        });
    }

    public void onSightSee()
    {
        //1. 임시 계정 발급해주기
        SignUpData data= new SignUpData(true);
        //2. 임시 계정을 MyInformation에 넣기 (내부 디비에 넣기)
        DBHelper helper = DBHelper.getInstance();
        helper.addSignUpData(data);

        Toast.makeText(this, "임시계정을 발급받았습니다.", Toast.LENGTH_LONG).show();
        //3. SignUp 을 Finish 시킨다.
        finish();
        /*
        -> 매치 참가 / 개설
        -> 팀 가입 / 개설
        -> 알림 , 내정보
        이 세가지 상황에서 다시 SignUp 을 하겟냐고 물어봐야한다.
        수락을 했을때, 이 화면을 다시 불러오되, TVSightSee를 GONE 시킨다.
        TVSightSee를 GONE 시키는건 이 SignUp의 onResume에서 하면됨.
        BackSpace를 눌러서 뒤로 돌아가는건 문제 없을듯하다, 다시 기능을 불러와도
        onCreate나 onResume에서 다시 걸릴것이므로.
        테스트 필요
         */
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Log.d("MenuActivity", "startActivity MenuActivity.activity");
    }
    public void vanishTVSightSee()
    {
        TVSightSee.setVisibility(View.GONE);
    }

}
