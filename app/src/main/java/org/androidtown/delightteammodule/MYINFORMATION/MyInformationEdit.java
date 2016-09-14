package org.androidtown.delightteammodule.MYINFORMATION;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;

import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.ChanRegionSelectDialog;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.GlobalRegionData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.R;

import java.io.IOException;

public class MyInformationEdit extends AppCompatActivity implements View.OnClickListener{

    ImageView IVProfileImage;

    TextView TVComplete;
    TextView TVBirth;
    TextView TVPosition;
    EditText ETIntroduce;
    ChanDate cDate; //생년월일

    ImageView IVRegionAdd;
    TextView TVRegionAdd;
    ImageView[] IVRegionDelete = new ImageView[3];
    TextView [] TVSelectedRegions = new TextView[3];
    GlobalRegionData[] regionData = new GlobalRegionData[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadData();
        setViews();
    }
    private void loadData()
    {
        //아래는 더미
        cDate= new ChanDate();
    }
    private void onComplete()
    {
        //서버에 올리고
        //성공하면 디비에 넣는다.
        //종료
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("취소").setMessage("수정을 취소하시겠습니까?").setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).create();
        dialog.show();
    }

    public void setViews()
    {
        setRegionSelectViews();
        TVComplete = (TextView)findViewById(R.id.TVComplete);
        TVComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onComplete();
            }
        });
        TVBirth = (TextView)findViewById(R.id.TVBirth);
        TVBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChanDate date = new ChanDate(ChanDate.getCurrentDateByFormat(ChanDate.DEFAULT));
                DatePickerDialog dialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        cDate.year = year;
                        cDate.month = monthOfYear + 1;
                        cDate.day = dayOfMonth;
                        TVBirth.setText(cDate.getDateByFormat(ChanDate.WITH_KOREAN));
                    }
                }, date.year, date.month, date.day);
                dialog.show();
            }
        });
        ETIntroduce = (EditText)findViewById(R.id.ETIntroduce);
        IVProfileImage = (ImageView) findViewById(R.id.IVProfileImage);
        Thread networkThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                try {
                    final Drawable drawable =
                            MyInformationData.drawableFromUrl(MyInformationEdit.this, MyInformationData.getInstance().myData.imageURL);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            IVProfileImage.setBackground(drawable);
                        }
                    });
                }catch (IOException e)
                {
                    Log.d("MyInformationEdit", "이미지 로드 실패");
                }
            }
        };
        networkThread.start();
        TVPosition = (TextView)findViewById(R.id.TVPosition);
    }

    public void sortRegionTextViews()
    {
        for(int i=0;i<2;i++)
        {
            if(regionData[i]==null)
            {
                for(int j=i+1; j<3; j++)
                {
                    if(regionData[j]!=null)
                    {
                        regionData[i]=regionData[j];
                        regionData[j] = null;
                        //TVSelectedRegions[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorAccent, null));
                        TVSelectedRegions[i].setText(TVSelectedRegions[j].getText().toString());
                        IVRegionDelete[i].setVisibility(View.VISIBLE);
                        //TVSelectedRegions[j].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorBright, null));
                        TVSelectedRegions[j].setText("");
                        IVRegionDelete[j].setVisibility(View.INVISIBLE);
                        break;
                    }
                }
            }
        }
    }

    public void setRegionSelectViews()
    {
        TVRegionAdd = (TextView)findViewById(R.id.TVRegionAdd);
        TVRegionAdd.setOnClickListener(this);
        IVRegionAdd = (ImageView)findViewById(R.id.IVRegionAdd);
        IVRegionAdd.setOnClickListener(this);

        TVSelectedRegions[0] = (TextView)findViewById(R.id.TVSelectedRegions0);
        TVSelectedRegions[0].setOnClickListener(this);
        TVSelectedRegions[1] = (TextView)findViewById(R.id.TVSelectedRegions1);
        TVSelectedRegions[1].setOnClickListener(this);
        TVSelectedRegions[2] = (TextView)findViewById(R.id.TVSelectedRegions2);
        TVSelectedRegions[2].setOnClickListener(this);

        IVRegionDelete[0]=(ImageView)findViewById(R.id.IVRegionDelete0);
        IVRegionDelete[0].setOnClickListener(this);
        IVRegionDelete[1]=(ImageView)findViewById(R.id.IVRegionDelete1);
        IVRegionDelete[1].setOnClickListener(this);
        IVRegionDelete[2]=(ImageView)findViewById(R.id.IVRegionDelete2);
        IVRegionDelete[2].setOnClickListener(this);

        for(int i=0;i<3;i++)
            IVRegionDelete[i].setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        { case R.id.TVRegionAdd:case R.id.TVSelectedRegions0:case R.id.TVSelectedRegions1:case R.id.TVSelectedRegions2:case R.id.IVRegionAdd:
            //지역 선택
            ChanRegionSelectDialog dialog = new ChanRegionSelectDialog();
            dialog.setCallBackViewAndData(TVSelectedRegions, IVRegionDelete, regionData);
            dialog.show(getSupportFragmentManager(), "");
            break;

            case R.id.IVRegionDelete0:
                regionData[0]=null;
                //TVSelectedRegions[0].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorBright, null));
                TVSelectedRegions[0].setText("");
                IVRegionDelete[0].setVisibility(View.INVISIBLE);
                sortRegionTextViews();
                break;
            case R.id.IVRegionDelete1:
                regionData[1]=null;
                //TVSelectedRegions[1].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorBright, null));
                TVSelectedRegions[1].setText("");
                IVRegionDelete[1].setVisibility(View.INVISIBLE);
                sortRegionTextViews();
            case R.id.IVRegionDelete2:
                regionData[2]=null;
                //TVSelectedRegions[2].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorBright, null));
                TVSelectedRegions[2].setText("");
                IVRegionDelete[2].setVisibility(View.INVISIBLE);
                sortRegionTextViews();

                break;
        }
    }

}
