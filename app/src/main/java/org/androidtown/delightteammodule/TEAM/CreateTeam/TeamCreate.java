package org.androidtown.delightteammodule.TEAM.CreateTeam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanCustomViews.ViewPagerAdapter;
import org.androidtown.delightteammodule.Connection.ConnectionManager;
import org.androidtown.delightteammodule.MYINFORMATION.MyInformationData;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.GeneralData.TeamDataBase;

public class TeamCreate extends AppCompatActivity {

    UnScrolledViewPager teamMakeViewPager;
    ViewPagerAdapter pagerAdapter;

    TextView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_create);
        getSupportActionBar().hide();
        //ActionBar 숨기기

        teamMakeViewPager = (UnScrolledViewPager)findViewById(R.id.VPTeamMake);
        teamMakeViewPager.setPagingEnabled(false);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new FragMakeTeam(), "Make");
        pagerAdapter.addFragment(new FragInviteTeam(), "Invite");


        teamMakeViewPager.setAdapter(pagerAdapter);

        setViews();

    }

    public void setViews()
    {
        cancel = (TextView)findViewById(R.id.TVCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void completeMakingTeam(TeamDataBase data)
    {
        /* 여기서 이름 중복처리 등을 하면 될 듯 */

        /*Team에 내 data를 넣어서, 나를 팀 개설자로 설정하기*/
        data.teamMaker = MyInformationData.getInstance().pageCompositionData;
        /*Data 를 서버에 넘기기 */
        ConnectionManager.sendJsonToServer(this, ConnectionManager.DataToJson(data));
        /*완료 시 넘어가기*/
        teamMakeViewPager.setCurrentItem(1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
