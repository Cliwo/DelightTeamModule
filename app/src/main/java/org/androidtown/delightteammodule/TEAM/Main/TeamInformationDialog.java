package org.androidtown.delightteammodule.TEAM.Main;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.delightteammodule.Connection.ConnectionManager;
import org.androidtown.delightteammodule.Connection.DataSetUpCallback;
import org.androidtown.delightteammodule.Connection.HttpUtilReceive;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.GeneralData.TeamDataWithMembers;

/**
 * Created by Chan on 2016-03-29.
 */
public class TeamInformationDialog extends Dialog {

    ImageView cancel;
    TextView TVTeamJoin;

    TeamDataWithMembers data;
    int teamUniqueID;

    public TeamInformationDialog(Context context, int teamUniqueID) {
        super(context);
        this.teamUniqueID=teamUniqueID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_team_information);
        cancel = (ImageView)findViewById(R.id.IVCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TVTeamJoin = (TextView)findViewById(R.id.TVTeamJoin);
        TVTeamJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinTeam();
            }
        });
        setWidthAndHeight();

        setData();
    }

    public void setData()
    {
        /*
        teamUniqueID
        HttpUtilReceive util = new HttpUtilReceive(new DataSetUpCallback() {
            @Override
            public void onDataReceived(String result) {
                여기서 Data 세팅
            }
        });
        util.execute(URL)
        */
    }

    public void joinTeam()
    {
        /* 여기서 팀 가입 신청 */
        //data.dataBase.teamMaker 에게 노티발싸
        //AlertDialog 사용하기
    }


    public void setWidthAndHeight()
    {
        WindowManager.LayoutParams layoutParams =getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) layoutParams);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
