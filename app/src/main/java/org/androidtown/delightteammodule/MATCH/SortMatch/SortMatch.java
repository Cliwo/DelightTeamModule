package org.androidtown.delightteammodule.MATCH.SortMatch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.ChanRegionSelectDialog;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.GlobalRegionData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.DatePicker;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.MomentData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.PickCallBack;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchFilter;
import org.androidtown.delightteammodule.MATCH.MakeMatch.MakeMatch;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.CreateTeam.TeamCreate;

public class SortMatch extends AppCompatActivity implements View.OnClickListener{

    NestedScrollView NSVMatchSort;

    ImageView IVCancel;

    Spinner SPMatchFormationText;
    Spinner SPMatchFormation;
    SeekBar SBManner;
    TextView TVProgress;
    TextView TVTimeFrom;
    TextView TVTimeTo;
    TextView TVSet;

    //아래에 데이터가 모인다.
    MatchFilter mData;
    Intent intent;

    ImageView [] IVRegionDelete = new ImageView[3];
    TextView [] TVSelectedRegions = new TextView[3];
    GlobalRegionData[] regionData = new GlobalRegionData[3];
    TextView TVRegionAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mData = new MatchFilter();
        setViews();
        setRegionSelectViews();
    }

    public void setViews()
    {
        NSVMatchSort = (NestedScrollView)findViewById(R.id.NSVMatchSort);

        IVCancel = (ImageView)findViewById(R.id.IVCancel);
        IVCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SPMatchFormation=(Spinner)findViewById(R.id.SPMatchFormation);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_settings, new String[]{"3 vs 3", "6 vs 6","11 vs 11" }) {

            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                return v;
            }
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;
            }
        };
        SPMatchFormation.setAdapter(adapter);
        SPMatchFormation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mData.formation = parent.getItemAtPosition(position).toString();
                Log.d("SP", mData.formation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SPMatchFormationText=(Spinner)findViewById(R.id.SPMatchFormationText);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_settings, new String[]{"풋살", "축구" }) {

            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                return v;
            }
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;
            }
        };
        SPMatchFormationText.setAdapter(adapter2);
        SPMatchFormationText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mData.formationText = parent.getItemAtPosition(position).toString();
                Log.d("SpinnerSelected", "스피너(Text)에서 값이 선택됨 : "+mData.formationText );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SBManner = (SeekBar)findViewById(R.id.SBManner);
        SBManner.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TVProgress.setText("" + progress);
                mData.manner = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        TVProgress = (TextView)findViewById(R.id.TVProgress);
        TVTimeFrom = (TextView)findViewById(R.id.TVTimeFrom);
        mData.dateFrom = new ChanDate(ChanDate.getCurrentDateByFormat(ChanDate.SLASH_SLASH_SLASH_WITHOUT_ZERO));
        mData.dateFrom.rewindTheDay(7);
        TVTimeFrom.setText(mData.dateFrom.getDateByFormat(ChanDate.SLASH_SLASH_SLASH_WITHOUT_ZERO));
        TVTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context ct= v.getContext();
                DatePicker.getInstance().showDialog(v.getContext(), new PickCallBack() {
                    @Override
                    public void onPicked(MomentData data) {
                        ChanDate temp =(ChanDate) data;
                        if(Integer.parseInt(mData.dateTo.getDateByFormat(ChanDate.PURE_NUMBER)) - Integer.parseInt(temp.getDateByFormat(ChanDate.PURE_NUMBER)) < 0)
                            Toast.makeText(ct, "올바르지 않은 선택 입니다.",Toast.LENGTH_LONG).show();
                        else {
                            mData.dateFrom = temp;
                            DatePicker.getInstance().setDateToTextView(TVTimeFrom, mData.dateFrom, ChanDate.SLASH_SLASH_SLASH_WITHOUT_ZERO);
                        }
                    }
                });
            }
        });
        TVTimeTo = (TextView)findViewById(R.id.TVTimeTo);
        mData.dateTo = new ChanDate(ChanDate.getCurrentDateByFormat(ChanDate.SLASH_SLASH_SLASH_WITHOUT_ZERO));
        TVTimeTo.setText(mData.dateTo.getDateByFormat(ChanDate.SLASH_SLASH_SLASH_WITHOUT_ZERO));
        TVTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context ct= v.getContext();
                DatePicker.getInstance().showDialog(v.getContext(), new PickCallBack() {
                    @Override
                    public void onPicked(MomentData data) {
                        ChanDate temp = (ChanDate) data;
                        if(Integer.parseInt(temp.getDateByFormat(ChanDate.PURE_NUMBER)) - Integer.parseInt(mData.dateFrom.getDateByFormat(ChanDate.PURE_NUMBER)) < 0)
                            Toast.makeText(ct, "올바르지 않은 선택 입니다.",Toast.LENGTH_LONG).show();
                        else {
                            mData.dateTo = temp;
                            DatePicker.getInstance().setDateToTextView(TVTimeTo, mData.dateTo, ChanDate.SLASH_SLASH_SLASH_WITHOUT_ZERO);
                        }
                    }
                });

            }
        });
        TVSet = (TextView)findViewById(R.id.TVSortSet);
        TVSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TVSet", "ONClick");
                intent= new Intent();
                mData.region1 = regionData[0];
                mData.region2= regionData[1];
                mData.region3= regionData[2];
                intent.putExtra("Filter", mData);
                setResult(1234, intent); //1234 라고 막 넣은 이유는, Result 코드와 Request 코드가 다른데, 어짜피 Result 는 한종류밖에 없어서, 코드로 분기할 필요 X
                finish();
            }
        });

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
