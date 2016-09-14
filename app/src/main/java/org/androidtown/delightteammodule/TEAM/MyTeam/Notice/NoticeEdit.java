package org.androidtown.delightteammodule.TEAM.MyTeam.Notice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import org.androidtown.delightteammodule.MYINFORMATION.MyInformationData;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamNoticeData;

public class NoticeEdit extends AppCompatActivity {

    TeamNoticeData noticeData;

    ImageView IVBackSpace;
    EditText ETTitle;
    EditText ETContent;
    CheckBox CBisMain;

    Button btnAddNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_edit);

        IVBackSpace = (ImageView)findViewById(R.id.IVBackSpace);
        IVBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ETTitle = (EditText)findViewById(R.id.ETTitle);
        ETContent = (EditText)findViewById(R.id.ETContent);
        CBisMain = (CheckBox)findViewById(R.id.CBRegisterMain);
        btnAddNotice = (Button)findViewById(R.id.btnAddNotice);

        btnAddNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeData=new TeamNoticeData();
                noticeData.noticeTitle = ETTitle.getText().toString();
                noticeData.noticeContent = ETContent.getText().toString();
                noticeData.isOnMain = CBisMain.isChecked();
                noticeData.noticeMaker = MyInformationData.getInstance().pageCompositionData;
                noticeData.watcherNumber=0;

                //Gson gson = new Gson();
                //ConnectionManager.sendJsonToServer(v.getContext(), gson.toJson(noticeData));
                finish(); //완료
            }
        });
    }
}
