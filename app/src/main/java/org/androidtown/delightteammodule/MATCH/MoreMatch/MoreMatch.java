package org.androidtown.delightteammodule.MATCH.MoreMatch;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.androidtown.delightteammodule.ChanCustomViews.ViewPagerAdapter;
import org.androidtown.delightteammodule.Connection.HttpUtilReceive;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchMemberData;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchTalkData;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchTalkReplyData;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchDataBase;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchDataDetail;
import org.androidtown.delightteammodule.MATCH.Preview.FragMatchPreview;
import org.androidtown.delightteammodule.MATCH.Review.FragMatchReview;
import org.androidtown.delightteammodule.MATCH.Search.FragMatchSearch;
import org.androidtown.delightteammodule.MATCH.Waiting.FragMatchWaiting;
import org.androidtown.delightteammodule.R;

import java.util.ArrayList;

public class MoreMatch extends AppCompatActivity {

    static MatchDataDetail detailData;

    ViewPager viewPager;
    ViewPagerAdapter pagerAdapter;
    TabLayout tabLayout;

    int matchUniqueID;
    public void setMatchData()
    {
        matchUniqueID = getIntent().getIntExtra("matchUniqueID", Integer.MIN_VALUE);
        Log.d("TeamMyTeam", "ID :" + matchUniqueID);
        if(matchUniqueID == Integer.MIN_VALUE)
        {
            //초기화 실패
        }
        else
        {
            //여기서 통신처리해서 받아오기.
            //아래는 dummy;
            detailData = new MatchDataDetail();
            detailData.baseData = new MatchDataBase();
            detailData.baseData.matchState=1;
            detailData.matchMemberData = new ArrayList<MatchMemberData>();
            for(int i = 0; i<8; i++)
                detailData.matchMemberData.add(new MatchMemberData(101));
            for(int i = 0; i<3; i++)
                detailData.matchMemberData.add(new MatchMemberData(102));
            for(int i = 0; i<3; i++)
                detailData.matchMemberData.add(new MatchMemberData(201));
            for(int i = 0; i<1; i++)
                detailData.matchMemberData.add(new MatchMemberData(202));
            for(int i = 0; i<2; i++)
                detailData.matchMemberData.add(new MatchMemberData(301));
            ArrayList<MatchTalkReplyData> childDatas = new ArrayList<>();
            childDatas.add(new MatchTalkReplyData("안녕하세요"));
            childDatas.add(new MatchTalkReplyData("테스트"));
            childDatas.add(new MatchTalkReplyData("텍스트입니다."));

            ArrayList<MatchTalkReplyData> childDatas2 = new ArrayList<>();
            childDatas2.add(new MatchTalkReplyData("방가방가"));
            childDatas2.add(new MatchTalkReplyData("hihi"));

            detailData.matchTalkData= new ArrayList<>();
            detailData.matchTalkData.add(new MatchTalkData((childDatas)));
            detailData.matchTalkData.add(new MatchTalkData(childDatas2));
            detailData.matchTalkData.add(new MatchTalkData());
            //

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setMatchData();

        viewPager = (ViewPager)findViewById(R.id.VPMatchMore);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout)findViewById(R.id.TLMatchMore);

        pagerAdapter.addFragment(new FragInformationMatch(), "매치정보");
        pagerAdapter.addFragment(new FragLineUpMatch(), "라인업");
        pagerAdapter.addFragment(new FragTalkMatch(), "대화");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
