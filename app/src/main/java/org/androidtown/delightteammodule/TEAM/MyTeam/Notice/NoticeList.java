package org.androidtown.delightteammodule.TEAM.MyTeam.Notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.androidtown.delightteammodule.ChanDirector.ActivityCode;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.Connection.ConnectionManager;
import org.androidtown.delightteammodule.Connection.DataSetUpCallback;
import org.androidtown.delightteammodule.Connection.HttpUtilReceive;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamNewsData;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamNoticeData;
import org.androidtown.delightteammodule.TEAM.Items.View.TeamNoticeView;

public class NoticeList extends AppCompatActivity {

    RecyclerAdapter<TeamNoticeView, TeamNoticeData> noticeViewAdapter;
    RecyclerView noticeRecyclerView;

    ImageView IVEdit;
    ImageView IVCancel;

    SwipyRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setRecyclerViews();
        setViews();
    }

    public void setRecyclerViews()
    {
        noticeRecyclerView = (RecyclerView)findViewById(R.id.RVNoticeView);
        noticeViewAdapter = new RecyclerAdapter<>(R.layout.item_team_notice_title, new CustomViewFactory<TeamNoticeView>() {
            @Override
            public TeamNoticeView createViewHolder(View layout, ViewGroup parent) {
                TeamNoticeView view= new TeamNoticeView(layout);
                /*view.LLNoticeItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), NoticeDetail.class);
                        startActivityForResult(intent, ActivityCode.NoticeDetail_CODE);
                    }
                });*/
                return view;
            }
        });
        for(int i=0;i<10;i++)
            noticeViewAdapter.addItem(new TeamNoticeData()).addItem(new TeamNoticeData());

        noticeRecyclerView.setAdapter(noticeViewAdapter);
        noticeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noticeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setViews()
    {
        refreshLayout=(SwipyRefreshLayout)findViewById(R.id.SwipeRefreshLayout);
        refreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Log.d("NoticeList", "Refreshing");
                updateRecyclerViewAdapter();
            }
        });
        refreshLayout.setRefreshing(false);
        IVEdit = (ImageView)findViewById(R.id.IVEdit);
        IVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoticeEdit.class);
                startActivityForResult(intent, ActivityCode.NoticeEdit_CODE);
            }
        });

        IVCancel = (ImageView)findViewById(R.id.IVCancel);
        IVCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void updateRecyclerViewAdapter()
    {
        /*
        HttpUtilReceive util = new HttpUtilReceive(ConnectionManager.ConnectionManagerBuilder(TeamNoticeData.class, noticeViewAdapter), new DataSetUpCallback() {
            @Override
            public void onDataReceived(String result) {
                refreshLayout.setRefreshing(false);
            }
        });
        util.execute(URL);
        */
    }
}
