package org.androidtown.delightteammodule.TEAM.MyTeam.Schedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.ChanAddress;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanTime;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.MomentData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.PickCallBack;
import org.androidtown.delightteammodule.MYINFORMATION.MyInformationData;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamScheduleData;

public class CreateSchedule extends AppCompatActivity {

    TeamScheduleData data;

    ChanDate dateData;
    ChanTime timeData;

    EditText ETTitle;
    EditText ETLocation;
    TextView cancel;
    TextView TVtime;
    TextView TVdate;

    Button btnRegisterSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);

        setViews();

    }
    public void setViews()
    {
        ETTitle = (EditText)findViewById(R.id.ETTitle);
        ETLocation = (EditText)findViewById(R.id.ETLocation);

        cancel = (TextView)findViewById(R.id.TVCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TVtime = (TextView)findViewById(R.id.TVTime);
        TVdate =(TextView)findViewById(R.id.TVDate);

        TVtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog(v);
            }
        });
        TVdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog(v);
            }
        });

        btnRegisterSchedule = (Button)findViewById(R.id.btnRegisterSchedule);
        btnRegisterSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateData == null || timeData == null || ETTitle.getText().toString() == null || ETTitle.getText().toString() == ""
                        || ETLocation.getText().toString() == "" || ETLocation.getText().toString() == null)
                {
                    //일정 등록이 안되는 상황
                    Toast.makeText(v.getContext(), "일정 등록을 위해서 올바른 데이터를 입력 해야 합니다.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    data = new TeamScheduleData();
                    data.cDate=dateData;
                    data.cTime=timeData;
                    data.scheduleMaker = MyInformationData.getInstance().pageCompositionData;
                    data.viewKind = TeamScheduleData.SCHEDULE;
                    data.location = new ChanAddress(ETLocation.getText().toString());
                    data.scheduleTitle = ETTitle.getText().toString();

                    /*
                    Gson gson = new Gson();
                    ConnectionManager.sendJsonToServer(v.getContext(), gson.toJson(data));
                    */
                    finish();
                }
            }
        });
    }

    public void DateDialog(View v)
    {
        dateData = new ChanDate(ChanDate.getCurrentDateByFormat(ChanDate.SLASH_SLASH_SLASH_WITH_ZERO));
        org.androidtown.delightteammodule.Gloabal.DateAndTime.DatePicker.getInstance().showDialog(v.getContext(), new PickCallBack() {
            @Override
            public void onPicked(MomentData data) {
                dateData.year = ((ChanDate) data).year;
                dateData.month = ((ChanDate) data).month;
                dateData.day = ((ChanDate) data).day;
                org.androidtown.delightteammodule.Gloabal.DateAndTime.DatePicker.getInstance().setDateToTextView(TVdate, dateData, ChanDate.WITH_KOREAN);
            }

        });
    }


    public void TimeDialog(View v)
    {
        timeData = new ChanTime(ChanTime.getCurrentTimeByFormat(ChanTime.SEPARATED_WITH_COLON_WITH_ZERO));
        org.androidtown.delightteammodule.Gloabal.DateAndTime.TimePicker.getInstance().showDialog(v.getContext(), new PickCallBack() {
            @Override
            public void onPicked(MomentData data) {
                timeData.hour = ((ChanTime) data).hour;
                timeData.minute = ((ChanTime) data).minute;
                org.androidtown.delightteammodule.Gloabal.DateAndTime.TimePicker.getInstance().setTImeToTextView(TVtime, timeData, ChanTime.SEPARATED_WITH_COLON_WITH_ZERO_WITH_AM_PM);
            }
        });
    }

}
