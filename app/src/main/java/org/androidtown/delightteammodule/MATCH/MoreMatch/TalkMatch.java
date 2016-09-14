package org.androidtown.delightteammodule.MATCH.MoreMatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidtown.delightteammodule.Connection.DataSetUpCallback;
import org.androidtown.delightteammodule.Connection.HttpUtilSend;
import org.androidtown.delightteammodule.R;

public class TalkMatch extends AppCompatActivity {

    int talkID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getIDFromIntent();
        loadReplyData();
    }

    public void getIDFromIntent()
    {
        Intent intent = getIntent();
        talkID = intent.getExtras().getInt("talkID");
    }
    public void loadReplyData()
    {
        /*
        HttpUtilSend send = new HttpUtilSend(new DataSetUpCallback() {
            @Override
            public void onDataReceived(String result) {
                //여기서 이 액티비티 내부 뷰들을 초기화
            }
        });
        send.execute("URL"
        , "talkID", ""+talkID);
        */
    }


}
