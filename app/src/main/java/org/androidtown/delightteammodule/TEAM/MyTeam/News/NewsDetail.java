package org.androidtown.delightteammodule.TEAM.MyTeam.News;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.MYINFORMATION.MyInformationData;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamNewsReplyData;
import org.androidtown.delightteammodule.TEAM.Items.View.TeamNewsReplyView;

public class NewsDetail extends AppCompatActivity {

    RecyclerView RVReply;
    RecyclerAdapter<TeamNewsReplyView, TeamNewsReplyData> adapter;

    EditText ETReply;
    ImageView IVReply;
    TeamNewsReplyData data;

    Intent intent;
    int teamNewsDataUniqueID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();
        Bundle bundle = intent.getExtras();
        teamNewsDataUniqueID = bundle.getInt("teamNewsDataUniqueID");
        Log.d("NewsDetail", "teamNewsDataUniqueID : " + teamNewsDataUniqueID);

        setViews();
        setRecyclerViews();
    }

    public void setRecyclerViews()
    {
        RVReply = (RecyclerView)findViewById(R.id.RVReply);
        adapter = new RecyclerAdapter<>(R.layout.item_team_news_reply, new CustomViewFactory<TeamNewsReplyView>() {
            @Override
            public TeamNewsReplyView createViewHolder(View layout, ViewGroup parent) {
                TeamNewsReplyView view = new TeamNewsReplyView(layout);
                return view;
            }
        });

        //
        adapter.addItem(new TeamNewsReplyData());
        adapter.addItem(new TeamNewsReplyData());
        //
        RVReply.setAdapter(adapter);
        RVReply.setLayoutManager(new LinearLayoutManager(this));
        RVReply.setItemAnimator(new DefaultItemAnimator());

    }
    public void setViews()
    {
        ETReply = (EditText)findViewById(R.id.ETReply);
        IVReply = (ImageView)findViewById(R.id.IVReply);
        IVReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = new TeamNewsReplyData();
                data.content=ETReply.getText().toString();
                data.replyMaker= MyInformationData.getInstance().pageCompositionData;

                /*

                Gson gson = new Gson();
                gson.toJson(data);

                HttpUtilSend util = new HttpUtilSend(new DataSetUpCallback() {
                    @Override
                    public void onDataReceived(String result) {

                    }
                });

                */
                ETReply.setText("");
            }
        });
    }
}
