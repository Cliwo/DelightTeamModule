package org.androidtown.delightteammodule.Gloabal.InviteFriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemView;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.GlobalRegionData;
import org.androidtown.delightteammodule.R;

import java.util.ArrayList;

public class InviteFriend extends AppCompatActivity {

    int num;
    ImageView IVBackSpace;
    TextView TVCurrentInviteNumber;
    TextView TVInvite;
    TextView TVClear;

    RecyclerView RVInvite;
    RecyclerAdapter<GlobalInviteView, GlobalInviteData> adapter;

    CustomViewHolder selectedView;
    ArrayList<AdapterItemData> selectedMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_invite_friend);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();
        init();
        setViews();
        setRecyclerView();
    }
    public void init()
    {
        num=0;
        selectedMember = new ArrayList<>();
    }

    public void setViews()
    {
        TVCurrentInviteNumber = (TextView)findViewById(R.id.TVCurrentInviteNumber);
        TVCurrentInviteNumber.setText(num + "명 초대");
        IVBackSpace = (ImageView)findViewById(R.id.IVBackSpace);
        IVBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TVInvite = (TextView)findViewById(R.id.TVInvite);
        TVInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInvite();
            }
        });
        TVClear = (TextView)findViewById(R.id.TVInvite);
        TVClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClear();
            }
        });
    }
    public void onInvite()
    {
        //원래액티비티로 돌아가서 일처리하지말고 여기서 서버작업 다해버리기
        finish();
    }
    public void onClear()
    {

    }
    public void setRecyclerView()
    {
        adapter= new RecyclerAdapter<>(R.layout.item_global_invite, new CustomViewFactory<GlobalInviteView>() {
            @Override
            public GlobalInviteView createViewHolder(View layout, ViewGroup parent) {
                final GlobalInviteView view = new GlobalInviteView(layout, InviteFriend.this);
                //이게 맞나? 2016-09-21
                return view;
            }
        });
        RVInvite = (RecyclerView)findViewById(R.id.RVInvite);
        RVInvite.setAdapter(adapter);
        RVInvite.setLayoutManager(new LinearLayoutManager(this));
        RVInvite.setItemAnimator(new DefaultItemAnimator());
        //dummy (아래) 2016-09-21
        for(int i =0; i<4; i++)
            adapter.addItem(new GlobalInviteData());
    }
    public void receiveData()
    {

    }

    public void onMemberSelect()
    {
        num++;
        String str = num+"명 초대";
        TVCurrentInviteNumber.setText(str);
    }
    public void onMemberUnSelect()
    {
        num--;
        String str = num+"명 초대";
        TVCurrentInviteNumber.setText(str);
    }


}
