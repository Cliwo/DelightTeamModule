package org.androidtown.delightteammodule.TEAM.MyTeam.News;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.androidtown.delightteammodule.MYINFORMATION.MyInformationData;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamNewsData;

public class CreateNews extends AppCompatActivity {

    TextView TVPost;
    EditText ETText;

    TeamNewsData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TVPost = (TextView)findViewById(R.id.TVPost);
        ETText = (EditText)findViewById(R.id.ETText);
        TVPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data= new TeamNewsData();
                data.newsContent = ETText.getText().toString();
                data.newsMaker = MyInformationData.getInstance().pageCompositionData;
                data.replyNumber=0;

                //Gson gson = new Gson();
                //ConnectionManager.sendJsonToServer(v.getContext(), gson.toJson(data));
                finish();
            }
        });
    }


}
