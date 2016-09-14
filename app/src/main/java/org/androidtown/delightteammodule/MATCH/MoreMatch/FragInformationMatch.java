package org.androidtown.delightteammodule.MATCH.MoreMatch;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import net.daum.mf.map.api.MapCurrentLocationMarker;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.androidtown.delightteammodule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragInformationMatch extends Fragment {

    MapView mapView;
    MapPOIItem marker = new MapPOIItem();

    public FragInformationMatch() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_information_match, container, false);
        setMapView(view);

        return view;
    }

    public void setMapView(View view)
    {
        mapView = new MapView(getActivity());
        mapView.setDaumMapApiKey("6c41ef44bd1084dd89295a62d0dd7a6a");
        ViewGroup mapViewContainer = (ViewGroup)view.findViewById(R.id.RLMapView);
        mapViewContainer.addView(mapView);

        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양
        mapView.addPOIItem(marker);

        mapView.setFocusable(false); // scroll view 가 맨 위부터 시작하지 않는 걸 방지
                                    // 이걸 안하면 scroll view 시작후, mapView가 마지막 focus를 먹으면서 스크롤이 자동으로 아래로 내려감
    }



}
