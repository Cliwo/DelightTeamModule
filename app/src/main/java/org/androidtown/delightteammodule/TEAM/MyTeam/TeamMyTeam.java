package org.androidtown.delightteammodule.TEAM.MyTeam;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import org.androidtown.delightteammodule.ChanCustomViews.ViewPagerAdapter;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.CreateTeam.FragTeamIntroduce;

public class TeamMyTeam extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter pagerAdapter;
    TabLayout tabLayout;

    CollapsingToolbarLayout collapsingToolbarLayout;

    int teamUniqueID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_my_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        teamUniqueID = getIntent().getIntExtra("teamUniqueID", Integer.MIN_VALUE);
        Log.d("TeamMyTeam", "ID :"+teamUniqueID);
        if(teamUniqueID == Integer.MIN_VALUE);
        {
            //초기화 실패
        }
        /*
                                    title이 필요하면 여기 부분 살릴것
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.CTLTeamMyTeam);
        collapsingToolbarLayout.setTitle("딜라잇웍스FC"); //팀의 이름으로 설정해야함
        //collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
        //collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorBlur));
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        */

        viewPager = (ViewPager)findViewById(R.id.VPTeamMyTeam);
        tabLayout = (TabLayout)findViewById(R.id.TLTeamMyTeam);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(new FragTeamInformation(), "팀 정보");
        pagerAdapter.addFragment(new FragTeamNews(), "팀 소식");
        pagerAdapter.addFragment(new FragTeamSchedule(), "팀 일정");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

}
