package org.androidtown.delightteammodule.TEAM.CreateTeam;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.daum.mf.map.api.MapView;

import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.ChanRegionSelectDialog;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.GlobalRegionData;
import org.androidtown.delightteammodule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragTeamConfigure extends Fragment implements View.OnClickListener {

    static boolean isCompleted=true;

    ImageView IVToggleAccessConfigure;
    TextView TVToggleText;
    EditText ETNickName;
    Switch SWOwnGround;

    TextView TVRegionAdd;
    ImageView [] IVRegionDelete = new ImageView[3];
    TextView [] TVSelectedRegions = new TextView[3];
    GlobalRegionData[] regionData = new GlobalRegionData[3];
    LinearLayout LLHomeGroundView;

    MapView mapView;

    boolean isPublic=true;
    boolean regionCheck= true;
    boolean addressCheck=false;
    boolean nickNameCheck=false;

    final static String accessUnable = "팀 가입을 위해 팀장(개설자)의 승인이 필요합니다.";
    final static String accessAble = "누구나 이 팀에\n 가입할 수 있습니다.";

    public void isNickNameValid(String nickName)
    {
        if(nickName.length() > 2)
            nickNameCheck=true;
        else
            nickNameCheck=false;
    }

    public FragTeamConfigure() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_frag_team_configure, container, false);
        setViews(view);
        return view;
    }

    public void setViews(View view)
    {
        //에러 발생가능 (처음 테스트하는 디바이스에서)
        mapView = new MapView(getActivity());
        mapView.setDaumMapApiKey("6c41ef44bd1084dd89295a62d0dd7a6a");
        ViewGroup mapViewContainer = (ViewGroup)view.findViewById(R.id.RLMapView);
        mapViewContainer.addView(mapView);
        LLHomeGroundView = (LinearLayout)view.findViewById(R.id.LLHomeGroundView);

        IVToggleAccessConfigure= (ImageView)view.findViewById(R.id.IVToggleAccessConfigure);
        IVToggleAccessConfigure.setOnClickListener(this);

        TVToggleText = (TextView)view.findViewById(R.id.TVAccessExplanation);
        TVRegionAdd = (TextView)view.findViewById(R.id.TVRegionAdd);
        TVRegionAdd.setOnClickListener(this);

        TVSelectedRegions[0] = (TextView)view.findViewById(R.id.TVSelectedRegions0);
        TVSelectedRegions[0].setOnClickListener(this);
        TVSelectedRegions[1] = (TextView)view.findViewById(R.id.TVSelectedRegions1);
        TVSelectedRegions[1].setOnClickListener(this);
        TVSelectedRegions[2] = (TextView)view.findViewById(R.id.TVSelectedRegions2);
        TVSelectedRegions[2].setOnClickListener(this);

        IVRegionDelete[0]=(ImageView)view.findViewById(R.id.IVRegionDelete0);
        IVRegionDelete[0].setOnClickListener(this);
        IVRegionDelete[1]=(ImageView)view.findViewById(R.id.IVRegionDelete1);
        IVRegionDelete[1].setOnClickListener(this);
        IVRegionDelete[2]=(ImageView)view.findViewById(R.id.IVRegionDelete2);
        IVRegionDelete[2].setOnClickListener(this);

        for(int i=0;i<3;i++)
            IVRegionDelete[i].setVisibility(View.INVISIBLE);

        SWOwnGround = (Switch)view.findViewById(R.id.SWOwnGround);
        SWOwnGround.setChecked(true); //기본 체크된 상태로 표현
        FragMakeTeam.data.hasOwnGround=true;
        SWOwnGround.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    LLHomeGroundView.setVisibility(View.VISIBLE);
                else
                    LLHomeGroundView.setVisibility(View.INVISIBLE);
                FragMakeTeam.data.hasOwnGround=isChecked;
            }
        });

        ETNickName=(EditText)view.findViewById(R.id.ETNickname);
        ETNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //여기 s에 뭐가들어가는거지
            }

            @Override
            public void afterTextChanged(Editable s) {
                //해야되는 것, 모든 변환이 끝난 후 최종적으로 text가 "" 가 아니면 completed조건 true임
                //모든 변환이 끝난 후 data 반영하기
                FragMakeTeam.data.groundAddress.nickName = s.toString();
                isNickNameValid(s.toString());
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
                        TVSelectedRegions[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorAccent, null));
                        TVSelectedRegions[i].setText(TVSelectedRegions[j].getText().toString());
                        IVRegionDelete[i].setVisibility(View.VISIBLE);
                        TVSelectedRegions[j].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorBright, null));
                        TVSelectedRegions[j].setText("");
                        IVRegionDelete[j].setVisibility(View.INVISIBLE);
                        break;
                    }
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.IVToggleAccessConfigure:
                if(isPublic)
                {
                    IVToggleAccessConfigure.setBackground(getResources().getDrawable(R.drawable.ic_locking));
                    TVToggleText.setText(accessUnable);
                    isPublic=false;
                    FragMakeTeam.data.isPublic=isPublic;
                }
                else {
                    IVToggleAccessConfigure.setBackground(getResources().getDrawable(R.drawable.ic_opening));
                    TVToggleText.setText(accessAble);
                    isPublic=true;
                    FragMakeTeam.data.isPublic=isPublic;
                }
                break;

            case R.id.TVRegionAdd:case R.id.TVSelectedRegions0:case R.id.TVSelectedRegions1:case R.id.TVSelectedRegions2:
                //지역 선택
                ChanRegionSelectDialog dialog = new ChanRegionSelectDialog();
                dialog.setCallBackViewAndData(TVSelectedRegions, IVRegionDelete, regionData);
                dialog.show(((TeamCreate) getActivity()).getSupportFragmentManager(), "");
                break;

            case R.id.IVRegionDelete0:
                regionData[0]=null;
                TVSelectedRegions[0].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorBright, null));
                TVSelectedRegions[0].setText("");
                IVRegionDelete[0].setVisibility(View.INVISIBLE);
                sortRegionTextViews();
                break;
            case R.id.IVRegionDelete1:
                regionData[1]=null;
                TVSelectedRegions[1].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorBright, null));
                TVSelectedRegions[1].setText("");
                IVRegionDelete[1].setVisibility(View.INVISIBLE);
                sortRegionTextViews();
            case R.id.IVRegionDelete2:
                regionData[2]=null;
                TVSelectedRegions[2].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorBright, null));
                TVSelectedRegions[2].setText("");
                IVRegionDelete[2].setVisibility(View.INVISIBLE);
                sortRegionTextViews();

                break;

        }
        checkCompleted(); //나중에 수정하기 2016-07-20
    }
    public boolean checkCompleted()
    {
        if(regionCheck)
        {
            if(FragMakeTeam.data.hasOwnGround)
            {
                if(addressCheck && nickNameCheck)
                    isCompleted=true;
                else
                    isCompleted=false;
            }
            else
                isCompleted=true;
        }
        else
        {
            isCompleted=false;
        }
        return isCompleted;
    }

}
