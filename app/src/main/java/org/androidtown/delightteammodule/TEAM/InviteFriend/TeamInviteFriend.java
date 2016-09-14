package org.androidtown.delightteammodule.TEAM.InviteFriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.androidtown.delightteammodule.R;

public class TeamInviteFriend extends AppCompatActivity {

    TextView cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_invite_friend);

        cancel = (TextView)findViewById(R.id.TVCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
