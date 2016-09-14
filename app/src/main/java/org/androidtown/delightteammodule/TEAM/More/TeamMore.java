package org.androidtown.delightteammodule.TEAM.More;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.Connection.ConnectionManager;
import org.androidtown.delightteammodule.Connection.HttpUtilReceive;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamDetailData;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamSimpleData;
import org.androidtown.delightteammodule.TEAM.Items.View.TeamDetailView;
import org.androidtown.delightteammodule.TEAM.Items.View.TeamSimpleView;
import org.androidtown.delightteammodule.TEAM.MyTeam.TeamMyTeam;

public class TeamMore extends AppCompatActivity {


    RecyclerView teamWaitingView;
    RecyclerAdapter<TeamDetailView, TeamDetailData> waitingViewAdapter;
    ImageView IVBackSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_more);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setViews();
        setRecyclerViews();
    }

    public void onCancelJoinRequest(String teamName)
    {
        //승인취소한 teamName을 가져옴, 승인 취소시 일어날 상황을 여기서 처리할 것. 2016-07-19
        //1. 이미 신청이 허가 되었는지 확인하기
        //1-1. 신청 허가되었다면 이미 팀에 가입되셨습니다. 메시지 띄우기
        //2. 신청 허가되지 않은 상태라면 team을 개설한 사람에게 간 Noti 지우기 (DB수정하기)
        //3. 승인취소한 사람의 승인 대기팀 리스트에서 그 팀 지우기 (DB 수정후, Client에 반영하기)
        //4.
    }
    public void setViews()
    {
        IVBackSpace = (ImageView)findViewById(R.id.IVBackSpace);
        IVBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void setRecyclerViews()
    {
        teamWaitingView = (RecyclerView)findViewById(R.id.RVTeamWaitingView);
        waitingViewAdapter = new RecyclerAdapter<>(R.layout.item_team_detail, new CustomViewFactory<TeamDetailView>() {
            @Override
            public TeamDetailView createViewHolder(View layout, ViewGroup parent) {
                TeamDetailView view = new TeamDetailView(layout);
                view.IVDelete.setVisibility(View.VISIBLE);
                final TextView teamTitle = view.TVTeamName;
                view.IVDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //다이얼로그 생성
                        final String teamName = teamTitle.getText().toString();
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setMessage(teamName + "팀 가입 신청을 취소하시겠습니까?");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onCancelJoinRequest(teamName);
                            }
                        });
                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create().show();
                    }
                });
                return view;
            }
        });
        waitingViewAdapter.addItem(new TeamDetailData(R.drawable.logo1, true,"2016/04/20", 34 , 11));
        waitingViewAdapter.addItem(new TeamDetailData(R.drawable.logo1, true,"2016/04/20", 34 , 11));
        waitingViewAdapter.addItem(new TeamDetailData(R.drawable.logo1, false,"2016/04/20", 34 , 11));

        //HttpUtilReceive util = new HttpUtilReceive(ConnectionManager.ConnectionManagerBuilder(TeamDetailData.class, waitingViewAdapter));
        //util.execute(url);

        teamWaitingView.setItemAnimator(new DefaultItemAnimator());
        teamWaitingView.setLayoutManager(new LinearLayoutManager(this));
        teamWaitingView.setAdapter(waitingViewAdapter);
    }
}
