package org.androidtown.delightteammodule.MATCH.MakeMatch;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.ChanAddress;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.MapSearchActivity;
import org.androidtown.delightteammodule.Gloabal.ChanTextInputDialog;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanTime;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.MomentData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.PickCallBack;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.CreateTeam.FragMakeTeam;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragMatchTitleAndLocation extends Fragment {

    EditText ETTitle;
    TextView TVNickName;
    TextView TVAddress;
    TextView tvDatePicker;
    TextView tvTimePicker;

    ViewGroup mapViewContainer;
    MapView mapView;
    //날짜 정보가 담길 object
    ChanDate dateData;
    ChanTime timeData;

    public FragMatchTitleAndLocation() {
        // Required empty public constructor
        dateData=new ChanDate(ChanDate.getCurrentDateByFormat(ChanDate.SLASH_SLASH_SLASH_WITHOUT_ZERO));
        timeData=new ChanTime(ChanTime.getCurrentTimeByFormat(ChanTime.SEPARATED_WITH_COLON_WITH_ZERO));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_match_title_and_location, container, false);
        setViews(view);
        linkPickers();
        return view;
    }

    public void setViews(View view)
    {
        ETTitle = (EditText)view.findViewById(R.id.ETTitle);
        ETTitle.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //해야되는 것, 모든 변환이 끝난 후 최종적으로 text가 "" 가 아니면 completed 조건 true임
                //모든 변환이 끝난 후 data 반영하기
                MakeMatch.data.matchTitle = s.toString();
            }
        });
        TVNickName = (TextView)view.findViewById(R.id.TVNickName);
        TVNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChanTextInputDialog dialog = new ChanTextInputDialog(v.getContext(), TVNickName);
                dialog.setTitle("장소 추가설명 입력");
                dialog.show();
            }
        });
        TVAddress = (TextView)view.findViewById(R.id.TVAddress);
        TVAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //위치 설정 activity
                Intent intent = new Intent(getContext(), MapSearchActivity.class);
                mapViewContainer.removeAllViews();
                startActivityForResult(intent, MapSearchActivity.MapSearchActivityCode);
            }
        });
        tvDatePicker= (TextView)view.findViewById(R.id.TVDatePicker);
        //tvDatePicker.setText(ChanDate.getCurrentDateByFormat(ChanDate.SLASH_SLASH_SLASH_WITH_ZERO));
        tvTimePicker= (TextView)view.findViewById(R.id.TVTimePicker);
        //에러 발생가능 (처음 테스트하는 디바이스에서)
        mapView = new MapView(getActivity());
        mapView.setDaumMapApiKey(MapSearchActivity.DAUM_MAP_VIEW_API_KEY);
        mapViewContainer = (ViewGroup)view.findViewById(R.id.RLMapView);
        mapViewContainer.addView(mapView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //맵뷰 검색이 끝났을 때.
        if(requestCode == MapSearchActivity.MapSearchActivityCode) {
            mapView = new MapView(getActivity());
            mapView.setDaumMapApiKey(MapSearchActivity.DAUM_MAP_VIEW_API_KEY);
            mapViewContainer.addView(mapView);
            if(data == null)
                return;
            Bundle bundle  = data.getExtras();
            MakeMatch.data.matchAddress = (ChanAddress)bundle.getSerializable("Address");
            TVNickName.setText(MakeMatch.data.matchAddress.nickName);
            TVAddress.setText(MakeMatch.data.matchAddress.addressInformation);
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(MakeMatch.data.matchAddress.latitude,
                    MakeMatch.data.matchAddress.longitude), true);

            MapPOIItem poiItem = new MapPOIItem();
            poiItem.setItemName(MakeMatch.data.matchAddress.nickName);
            poiItem.setTag(0);
            poiItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            poiItem.setCustomImageResourceId(R.drawable.ic_waiting);
            poiItem.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
            poiItem.setCustomSelectedImageResourceId(R.drawable.ic_teammaking);
            poiItem.setCustomImageAutoscale(false);
            poiItem.setCustomImageAnchor(0.5f, 1.0f);
            poiItem.setMapPoint(MapPoint.mapPointWithGeoCoord(MakeMatch.data.matchAddress.latitude,
                    MakeMatch.data.matchAddress.longitude));
            mapView.addPOIItem(poiItem);
            mapView.selectPOIItem(poiItem, true);
        }

    }

    public void linkPickers()
    {
        tvDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                org.androidtown.delightteammodule.Gloabal.DateAndTime.DatePicker.getInstance().showDialog(v.getContext(), new PickCallBack() {
                    @Override
                    public void onPicked(MomentData data) {
                        dateData.year = ((ChanDate) data).year;
                        dateData.month = ((ChanDate) data).month;
                        dateData.day = ((ChanDate) data).day;
                        org.androidtown.delightteammodule.Gloabal.DateAndTime.DatePicker.getInstance().setDateToTextView(tvDatePicker, dateData, ChanDate.SLASH_SLASH_SLASH_WITH_ZERO);
                        MakeMatch.data.cDate=dateData;
                    }
                });
            }
        });
        tvTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                org.androidtown.delightteammodule.Gloabal.DateAndTime.TimePicker.getInstance().showDialog(v.getContext(), new PickCallBack() {
                    @Override
                    public void onPicked(MomentData data) {
                        timeData.hour = ((ChanTime) data).hour;
                        timeData.minute = ((ChanTime) data).minute;
                        org.androidtown.delightteammodule.Gloabal.DateAndTime.TimePicker.getInstance().setTImeToTextView(tvTimePicker, timeData,ChanTime.SEPARATED_WITH_COLON_WITH_ZERO_WITH_AM_PM);
                        MakeMatch.data.cTime=timeData;
                    }

                });
            }
        });
    }

    public boolean checkCompleted()
    {
        if(ETTitle.getText().toString().length() < 2 )
        {
            Toast.makeText(getContext(),"제목이 올바르지 않습니다.",Toast.LENGTH_LONG).show();
            return false;
        }
        if(MakeMatch.data.matchAddress == null )
        {
            Toast.makeText(getContext(),"위치를 선택해주세요",Toast.LENGTH_LONG).show();
            return false;}
        if(MakeMatch.data.cDate == null)
        {
            Toast.makeText(getContext(),"경기 날짜가 올바르지 않습니다.",Toast.LENGTH_LONG).show();
            return false;
        }
        if(MakeMatch.data.cTime == null )
        {
            Toast.makeText(getContext(),"경기 시간이 올바르지 않습니다.",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
