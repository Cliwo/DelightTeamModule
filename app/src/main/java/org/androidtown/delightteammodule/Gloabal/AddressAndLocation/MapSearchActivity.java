package org.androidtown.delightteammodule.Gloabal.AddressAndLocation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import org.androidtown.delightteammodule.DaumAPI.Searcher.Item;
import org.androidtown.delightteammodule.DaumAPI.Searcher.OnFinishSearchListener;
import org.androidtown.delightteammodule.DaumAPI.Searcher.Searcher;
import org.androidtown.delightteammodule.R;

import java.util.HashMap;
import java.util.List;

public class MapSearchActivity extends AppCompatActivity implements MapView.POIItemEventListener{

    private MapPOIItem selectedItem;
    private EditText ETSearch;
    private TextView TVSearch;

    private HashMap<Integer, Item> mTagItemMap = new HashMap<Integer, Item>();
    private MapView mMapView;
    private  ViewGroup mapViewContainer;

    public static final String DAUM_MAP_VIEW_API_KEY = "6c41ef44bd1084dd89295a62d0dd7a6a";
    public static final int MapSearchActivityCode = 9012;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab=getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        setViews();
    }

    public void setViews()
    {
        ETSearch = (EditText)findViewById(R.id.ETSearch);

        TVSearch = (TextView)findViewById(R.id.TVSearch);
        TVSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = null;
                onSearch(ETSearch.getText().toString());
            }
        });
        //http://comostudio.tistory.com/73
        //http://stackoverflow.com/questions/1489852/android-handle-enter-in-an-edittext
        ETSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                TVSearch.callOnClick();
                return true;
            }
        });
        mMapView = new MapView(this);
        mMapView.setDaumMapApiKey(DAUM_MAP_VIEW_API_KEY);
        mapViewContainer = (ViewGroup)findViewById(R.id.RLMapView);
        mapViewContainer.addView(mMapView);
        mMapView.setPOIItemEventListener(this);

    }

    public void onSearch(String queryText)
    {
        if(queryText == null || queryText.length() == 0)
            return;

        hideSoftKeyboard();
        MapPoint.GeoCoordinate geoCoordinate = mMapView.getMapCenterPoint().getMapPointGeoCoord();
        double latitude = geoCoordinate.latitude; // 위도
        double longitude = geoCoordinate.longitude; // 경도
        int radius = 10000; // 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 사용. meter 단위 (0 ~ 10000)
        int page = 1; // 페이지 번호 (1 ~ 3). 한페이지에 15개

        Searcher searcher = new Searcher(); // net.daum.android.map.openapi.search.Searcher
                                            // 지금은 아님
        searcher.searchKeywordWithoutPointAndRadius(getApplicationContext(), queryText, page, DAUM_MAP_VIEW_API_KEY, new OnFinishSearchListener() {
            @Override
            public void onSuccess(List<Item> itemList) {
                mMapView.removeAllPOIItems(); // 기존 검색 결과 삭제
                showResult(itemList, mMapView, mTagItemMap); // 검색 결과 보여줌

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ETSearch.setText("");
                    }
                });
            }

            @Override
            public void onFail() {
                Log.d("MapSearchActivity", "검색 실패");
            }
        });
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ETSearch.getWindowToken(), 0);
    }

    private void showResult(List<Item> itemList, MapView mMapView ,HashMap mTagItemMap) {
        MapPointBounds mapPointBounds = new MapPointBounds();

        if(itemList.size() == 0)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MapSearchActivity.this, "검색된 결과가 없습니다.", Toast.LENGTH_LONG).show();
                }
            });

            Log.d("MapSearchActivity", "검색된 결과가 없습니다.");
            return ;
        }

        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);

            MapPOIItem poiItem = new MapPOIItem();
            poiItem.setItemName(item.title);
            poiItem.setTag(i);
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(item.latitude, item.longitude);
            poiItem.setMapPoint(mapPoint);
            mapPointBounds.add(mapPoint);
            poiItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            poiItem.setCustomImageResourceId(R.drawable.ic_waiting);
            poiItem.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
            poiItem.setCustomSelectedImageResourceId(R.drawable.ic_teammaking);
            poiItem.setCustomImageAutoscale(false);
            poiItem.setCustomImageAnchor(0.5f, 1.0f);

            mMapView.addPOIItem(poiItem);
            mTagItemMap.put(poiItem.getTag(), item);
        }

        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));

        MapPOIItem[] poiItems = mMapView.getPOIItems();
        if (poiItems.length > 0) {
            mMapView.selectPOIItem(poiItems[0], false);
        }
    }

    @Override
    public void onBackPressed() {
        onFinish();
        super.onBackPressed();
    }

    public void onFinish()
    {
        mapViewContainer.removeAllViews();
        if(selectedItem == null)
            return;


        Item item = mTagItemMap.get(selectedItem.getTag());
        Log.d("MapSearchActivity", "POItem, Title : " + selectedItem.getItemName()
                + " Address : " + item.address
                + " Location(latitude, longitude) : ("
                + selectedItem.getMapPoint().getMapPointGeoCoord().latitude + ", " +
                selectedItem.getMapPoint().getMapPointGeoCoord().longitude +")");
        ChanAddress address = new ChanAddress(selectedItem.getItemName(),
                item.address,
                item.longitude,
                item.latitude
                );

        Intent intent = new Intent();
        intent.putExtra("Address",address);
        setResult(1234, intent);
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        selectedItem = mapPOIItem;
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}
