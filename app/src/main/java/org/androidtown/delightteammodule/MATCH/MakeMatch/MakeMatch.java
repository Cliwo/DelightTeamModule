package org.androidtown.delightteammodule.MATCH.MakeMatch;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.androidtown.delightteammodule.ChanCustomViews.VerticalViewPager;
import org.androidtown.delightteammodule.ChanCustomViews.ViewPagerAdapter;
import org.androidtown.delightteammodule.Connection.DataSetUpCallback;
import org.androidtown.delightteammodule.Connection.HttpUtilSend;
import org.androidtown.delightteammodule.MYINFORMATION.MyInformationData;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchDataBase;
import org.androidtown.delightteammodule.R;

public class MakeMatch extends AppCompatActivity {

    ImageView ivCancel;
    Button btnMakeMatch;

    FragMatchTitleAndLocation matchTitleAndLocation;
    FragMatchFormationAndManner matchFormationAndManner;
    FragMatchSelectRepresentative matchSelectRepresentative;

    VerticalViewPager verticalViewPager;
    ViewPagerAdapter viewPagerAdapter;

    static MatchDataBase data;

    public void setNewData()
    {
        if(data==null)
            data = new MatchDataBase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setNewData();

        btnMakeMatch = (Button)findViewById(R.id.btnMakeMatch);
        verticalViewPager = (VerticalViewPager)findViewById(R.id.VVVerticalViewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        matchTitleAndLocation=new FragMatchTitleAndLocation();
        matchFormationAndManner=new FragMatchFormationAndManner();
        matchSelectRepresentative = new FragMatchSelectRepresentative();

        viewPagerAdapter.addFragment(matchTitleAndLocation, "TitleAndLocation");
        viewPagerAdapter.addFragment(matchFormationAndManner, "FormationAndManner");
        viewPagerAdapter.addFragment(matchSelectRepresentative, "SelectRepresentative");

        verticalViewPager.setAdapter(viewPagerAdapter);
        verticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 2 && matchFormationAndManner.checkCompleted() && matchTitleAndLocation.checkCompleted())
                {
                    btnMakeMatch.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
                    btnMakeMatch.setTextColor(ResourcesCompat.getColor(getResources(), R.color.WHITE, null));
                }
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 2 && matchFormationAndManner.checkCompleted() && matchTitleAndLocation.checkCompleted()) {
                    //마지막 page이며, 모든 page가 정상적으로 채워졌을때.
                    btnMakeMatch.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
                    btnMakeMatch.setTextColor(ResourcesCompat.getColor(getResources(), R.color.WHITE, null));
                    btnMakeMatch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //한 번 complete 된 상황으로 만들고, 지운다음에 다음으로 넘어가버리는걸 방지, 항상 Complete 되어있어야 넘어갈수 있다.
                            if (FragMatchTitleAndLocation.isCompleted && FragMatchFormationAndManner.isCompleted) {
                                completeMakingMatch();
                            } else {
                                ((TextView) v).setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorBright, null));
                                ((TextView) v).setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorDark, null));
                            }
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ivCancel = (ImageView)findViewById(R.id.IVCancel);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void completeMakingMatch()
    {
        data.matchMakerNickName= MyInformationData.getInstance().pageCompositionData.nickName;
        String play_type = parseMatchFormationText(data.matchFormationText);
        Log.d("MatchMake", data.matchMakerNickName);
        HttpUtilSend util = new HttpUtilSend(new DataSetUpCallback() {
            @Override
            public void onDataReceived(String result) {
                data = null;
                //현재 완성한 data 는 보내버림
                Log.d("MakeMatch", "매치 생성 성공");
            }
        });
        util.execute(HttpUtilSend.URLBase+"/match/add",
                "user_id", MyInformationData.getInstance().myData.socialID,
                "match_day", data.cDate.day+"",
                "match_month",  data.cDate.month+"",
                "match_year",  data.cDate.year+"",
                "match_hour",  data.cTime.hour+"",
                "match_minute", data.cTime.minute+"",
                "main_team_id",  1234+"",
                "main_team_name", "Dummy",
                "main_team_logo", 1234+"",
                "title", data.matchTitle,
                "min_manner_point", data.matchManner,
                "game_type", data.matchFormationIntValue+"", //3 vs 3 , 6 vs 6 , 11 vs 11
                "play_type", play_type, //축구 or 풋살 , data.matchFormationText
                "meet_addr", data.matchAddress.addressInformation,
                "meet_addr_name", data.matchAddress.nickName,
                "area_cd", "Dummy",
                "geo_x", data.matchAddress.longitude+"",
                "geo_y", data.matchAddress.latitude+"",
                "level", data.matchLevelIntValue+""); //data.matchLevel
        //level 이랑 game_type integer로 바꿔야함.
        //play_type 이것도 integer나 영어로 바꾸기
        //09-05
        finish();
    }

    public String parseMatchFormationText(String str)
    {
        switch (str)
        {
            case "축구":
                return "soccer";
            case "풋살":
                return "mini_soccer";
        }
        return null;
    }
}
