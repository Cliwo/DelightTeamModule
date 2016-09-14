package org.androidtown.delightteammodule.TEAM.Main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.androidtown.delightteammodule.ChanDirector.ActivityCode;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.CreateTeam.TeamCreate;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamDetailData;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamMemberSimpleData;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamNewsData;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamSimpleData;
import org.androidtown.delightteammodule.TEAM.Items.View.TeamDetailView;
import org.androidtown.delightteammodule.TEAM.Items.View.TeamSimpleView;
import org.androidtown.delightteammodule.TEAM.Main.SearchAndFiilter.TeamSearch;
import org.androidtown.delightteammodule.TEAM.More.TeamMore;
import org.androidtown.delightteammodule.TEAM.MyTeam.TeamMyTeam;

import java.util.Comparator;


public class TeamMain extends AppCompatActivity {

    RecyclerView teamSimpleView;
    RecyclerView teamDetailView;

    RecyclerAdapter<TeamSimpleView, TeamSimpleData> simpleViewAdapter;
    RecyclerAdapter detailViewAdapter;

    ImageView ivMore;
    ImageView ivSort;
    ImageView ivSearch;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab=getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        setRecyclerViews();
        setViews();
    }

    public void setRecyclerViews()
    {
        /* Resource를 정상적으로 load하기 위함 */
        TeamSimpleData.context = this;
        TeamDetailData.context = this;

        teamSimpleView = (RecyclerView)findViewById(R.id.RVTeamSimpleView);
        simpleViewAdapter = new RecyclerAdapter<TeamSimpleView, TeamSimpleData>(R.layout.item_team_simple, new CustomViewFactory<TeamSimpleView>() {
            @Override
            public TeamSimpleView createViewHolder(View layout, ViewGroup parent) {
                final TeamSimpleView view = new TeamSimpleView(layout);
                view.teamEmblem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), TeamMyTeam.class);
                        intent.putExtra("teamUniqueID", ((TeamSimpleData) view.getData()).teamUniqueID);
                        startActivityForResult(intent, 1234); //나중에 원래대로 하기 ActivityCode
                        //DelightNotification.sendDelightNotification(v.getContext(),"Hi");

                        //Activity를 킬 때, 어떤 정보를 베이스로 초기화해야할지, 아래와 같은 정보를 넣어줘야할 것 같음.
                        //((TeamSimpleData)(view.getData())).teamUniqueID
                    }
                });
                return view;
            }
        });

        /* 교체될 부분 */
        // 1. Server에 데이터를 요청하고
        // 2. (1)에서 데이터를 받았다면 : addItem 실행
        // 3. (1)에서 데이터를 받지 못했다면 : internet 에러 표시
        // 4. (2)에서
        simpleViewAdapter.addItem(new TeamSimpleData(R.drawable.logo1, "딜라잇FC"));
        simpleViewAdapter.addItem(new TeamSimpleData(R.drawable.logo1, "아주FC") );
        simpleViewAdapter.addItem(new TeamSimpleData(R.drawable.logo1, "수원삼성"));
        //여기 까지

        teamSimpleView.setAdapter(simpleViewAdapter);
        teamSimpleView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager temp = new LinearLayoutManager(this);  temp.setOrientation(LinearLayoutManager.HORIZONTAL);
        teamSimpleView.setLayoutManager(temp);

        // ------------------------------------------------------------------------------

        teamDetailView = (RecyclerView)findViewById(R.id.RVTeamDetailView);
        detailViewAdapter = new RecyclerAdapter<TeamDetailView, TeamDetailData>(R.layout.item_team_detail, new CustomViewFactory<TeamDetailView>() {
            @Override
            public TeamDetailView createViewHolder(View layout, ViewGroup parent) {
                TeamDetailView view =new TeamDetailView(layout);
               return view;
            }
        });
        // 1. JSON 하나씩 자르기
        // 2. array.length 만큼 for를 돌려서 detailViewAdapter에 addItem을 호출하기
        // 3.
        detailViewAdapter.addItem(new TeamDetailData(R.drawable.logo1,true, "2016/04/20", 34 , 11));
        detailViewAdapter.addItem(new TeamDetailData(R.drawable.logo1,true,"2016/07/20", 92 , 14));
        detailViewAdapter.addItem(new TeamDetailData(R.drawable.logo1,true,"2016/04/30", 65 , 9));
        detailViewAdapter.addItem(new TeamDetailData(R.drawable.logo1,false,"2016/02/21", 78 , 6));
        detailViewAdapter.addItem(new TeamDetailData(R.drawable.logo1,false,"2016/03/1", 11 , 18));
        detailViewAdapter.addItem(new TeamDetailData(R.drawable.logo1,false,"2016/06/15", 37 , 3));


        teamDetailView.setAdapter(detailViewAdapter);
        teamDetailView.setItemAnimator(new DefaultItemAnimator());
        teamDetailView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setViews()
    {
        fab = (FloatingActionButton)findViewById(R.id.fabMakeTeam);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeamCreate.class);
                startActivityForResult(intent, ActivityCode.CreateTeam_CODE);
            }
        });
        ivMore = (ImageView)findViewById(R.id.IVMore);
        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeamMore.class);
                startActivity(intent);
            }
        });
        ivSort = (ImageView)findViewById(R.id.IVSort);
        ivSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortDialog dialog = new SortDialog(v.getContext(),detailViewAdapter);
                dialog.show();
            }
        });
        ivSearch = (ImageView)findViewById(R.id.IVSearch);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeamSearch.class);
                startActivityForResult(intent, ActivityCode.SearchAndFilter_CODE);
            }
        });

    }
}

class SortDialog extends Dialog implements View.OnClickListener
{
    RecyclerAdapter<TeamDetailView, TeamDetailData> adapter;

    TextView mannerPoint;
    TextView memberNumber;
    TextView recent;

    public SortDialog(Context context, RecyclerAdapter<TeamDetailView, TeamDetailData> detailViewAdapter) {
        super(context);
        adapter=detailViewAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_team_sort);

        mannerPoint = (TextView)findViewById(R.id.TVTeamMannerPoint);
        memberNumber = (TextView)findViewById(R.id.TVTeamMemberNumber);
        recent =(TextView)findViewById(R.id.TVTeamRecent);

        mannerPoint.setOnClickListener(this);
        memberNumber.setOnClickListener(this);
        recent.setOnClickListener(this);

        setWidthAndHeight();
    }

    public void setWidthAndHeight()
    {
        WindowManager.LayoutParams layoutParams =getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) layoutParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.TVTeamMannerPoint:
                sortBasedOnHighestManner();
                Toast.makeText(v.getContext(), "매너 지수"+" 높은 순으로 정렬",Toast.LENGTH_LONG).show();
                dismiss();
                break;

            case R.id.TVTeamMemberNumber:
                sortBasedOnBiggestMemberNumber();
                Toast.makeText(v.getContext(), "인원"+" 많은 순으로 정렬",Toast.LENGTH_LONG).show();
                dismiss();
                break;

            case R.id.TVTeamRecent:
                sortBasedOnMostRecent();
                Toast.makeText(v.getContext(), "최근 활동이"+" 가까운 순으로 정렬",Toast.LENGTH_LONG).show();
                dismiss();
                break;
        }
    }

    public void sortBasedOnHighestManner()
    {
        adapter.setSortWay(new Comparator<TeamDetailData>() {
            @Override
            public int compare(TeamDetailData lhs, TeamDetailData rhs) {
                return  rhs.avgManner - lhs.avgManner;
            }
        });
        adapter.sortItems();
    }
    public void sortBasedOnBiggestMemberNumber()
    {
        adapter.setSortWay(new Comparator<TeamDetailData>() {
            @Override
            public int compare(TeamDetailData lhs, TeamDetailData rhs) {
                return rhs.currentMemberNumber-lhs.currentMemberNumber;
            }
        });
        adapter.sortItems();
    }
    public void sortBasedOnMostRecent()
    {
        adapter.setSortWay(new Comparator<TeamDetailData>() {
            @Override
            public int compare(TeamDetailData lhs, TeamDetailData rhs) {
                return Integer.parseInt(rhs.cDate.getDateByFormat(ChanDate.PURE_NUMBER))-Integer.parseInt(lhs.cDate.getDateByFormat(ChanDate.PURE_NUMBER));
            }
        });
        adapter.sortItems();
    }
}
