package org.androidtown.delightteammodule.MATCH.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import org.androidtown.delightteammodule.ChanCustomViews.ViewPagerAdapter;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchCardData;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchDataBase;
import org.androidtown.delightteammodule.MATCH.MakeMatch.MakeMatch;
import org.androidtown.delightteammodule.MATCH.Preview.FragMatchPreview;
import org.androidtown.delightteammodule.MATCH.Review.FragMatchReview;
import org.androidtown.delightteammodule.MATCH.Search.FragMatchSearch;
import org.androidtown.delightteammodule.MATCH.Waiting.FragMatchWaiting;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamSimpleData;

import java.util.ArrayList;

public class MatchMain extends AppCompatActivity {

    public static final int FRAG_SEARCH = 0;
    public static final int FRAG_WAITING = 1;
    public static final int FRAG_PREVIEW = 2;
    public static final int FRAG_REVIEW = 3;

    ViewPager viewPager;
    ViewPagerAdapter pagerAdapter;
    TabLayout tabLayout;

    public static ArrayList<MatchCardData> cardData;

    public void receiveDataFromServer()
    {
        cardData = new ArrayList<>();
        MatchCardData.context=this;
        //서버에서 받아오기.

        //아래는 그냥더미..
        for(int i=0;i<5;i++)
        {
            MatchCardData da= new MatchCardData();
            da.baseData.matchState=0;
            cardData.add(da);
        }
        for(int i=0;i<3;i++)
        {
            MatchCardData da= new MatchCardData();
            da.baseData.matchState=1;
            cardData.add(da);
        }
        for(int i=0;i<2;i++)
        {
            MatchCardData da= new MatchCardData();
            da.baseData.matchState=2;
            cardData.add(da);
        }
        for(int i=0;i<4;i++)
        {
            MatchCardData da= new MatchCardData();
            da.baseData.matchState=3;
            cardData.add(da);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        receiveDataFromServer(); //무조건 가장 먼저 호출 되야함.

        viewPager = (ViewPager)findViewById(R.id.VPMatchMain);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout)findViewById(R.id.TLMatchMain);

        pagerAdapter.addFragment(new FragMatchSearch(), "매치 검색");
        pagerAdapter.addFragment(new FragMatchWaiting(), "대기중 매치");
        pagerAdapter.addFragment(new FragMatchPreview(), "매치 프리뷰");
        pagerAdapter.addFragment(new FragMatchReview(), "매치 리뷰");
        /*
        * Fragment 생성시 모든 데이터를 그냥 받아와버릴거임 (2016-07-31) 받고나서, 표기만 다르게 하도록
        * 매치 검색 부분 / 매치 리뷰 부분에서는 RecyclerView의 스크롤이 맨아래에 닿았을경우 3초의 쿨타임을 갖는 스레드를 한번 돌리는데
        * 이 스레드에서 새로운 데이터를 더 추가하도록 해야함
        * 대기중과 매치 프리뷰는 모든 데이터를 다 받아오도록 하기.
        *
        * 현재 매치 fragment 들은 setRecyclerViews 메소드로 정리하지 않을 거임
        * 왜냐면 대부분의 frament에서 추가적인 view 없이 카드들만 보여주기 때문.
        * 대신 통신관련한 메소드들을 추가할것.
        * setAdapterData(); 와 loadMoreData(); 를 이용.
        * */

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setTabListener();

        MatchCardData.context=this; //MatchCardData에 쓰일 context를 이 Activity 로 만듬.
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_search_black_24dp));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_ready_gray));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_preview_gray));
        tabLayout.getTabAt(3).setIcon(getResources().getDrawable(R.drawable.ic_review_gray));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabMakeMatch);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MakeMatch.class);
                startActivity(intent);
            }
        });
    }

    public void setTabListener()
    {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    tab.setIcon(getResources().getDrawable(R.drawable.ic_search_black_24dp));
                    break;
                case 1:
                    tab.setIcon(getResources().getDrawable(R.drawable.ic_ready_gold));
                    break;
                case 2:
                    tab.setIcon(getResources().getDrawable(R.drawable.ic_preview_gold));
                    break;
                case 3:
                    tab.setIcon(getResources().getDrawable(R.drawable.ic_review_gold));
                    break;
            }
            viewPager.setCurrentItem(tab.getPosition());
            //지금 setTint가 api require가 높으니까
            //황금색 icon을 받아서 체크하는식으로 만들면 될 듯.
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    tab.setIcon(getResources().getDrawable(R.drawable.ic_search_black_24dp));
                    break;
                case 1:
                    tab.setIcon(getResources().getDrawable(R.drawable.ic_ready_gray));
                    break;
                case 2:
                    tab.setIcon(getResources().getDrawable(R.drawable.ic_preview_gray));
                    break;
                case 3:
                    tab.setIcon(getResources().getDrawable(R.drawable.ic_review_gray));
                    break;
            }
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    tab.setIcon(getResources().getDrawable(R.drawable.ic_search_black_24dp));
                    break;
                case 1:
                    tab.setIcon(getResources().getDrawable(R.drawable.ic_ready_gold));
                    break;
                case 2:
                    tab.setIcon(getResources().getDrawable(R.drawable.ic_preview_gold));
                    break;
                case 3:
                    tab.setIcon(getResources().getDrawable(R.drawable.ic_review_gold));
                    break;
            }
            viewPager.setCurrentItem(tab.getPosition());
        }
    });


    }
}
