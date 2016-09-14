package org.androidtown.delightteammodule.Gloabal.AddressAndLocation;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanCustomViews.ViewPagerAdapter;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.PickCallBack;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.CreateTeam.UnScrolledViewPager;
import org.w3c.dom.Text;

/**
 * Created by Chan on 2016-07-20.
 */
public class ChanRegionSelectDialog extends DialogFragment {

    FragRegionLargeScope largeScope;
    FragRegionSmallScope smallScope;;

    TextView TVLarge;
    TextView TVSmall;
    UnScrolledViewPager VPRegionSelect;
    ViewPagerAdapter adapter;

    TextView TVCancel;
    TextView TVAccept;

    GlobalRegionData[] data = new GlobalRegionData[3];
    GlobalRegionData[] result;
    TextView[] views;
    ImageView[] IVviews;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_global_region_select,container);

        VPRegionSelect = (UnScrolledViewPager)view.findViewById(R.id.VPRegionSelect);
        TVLarge = (TextView)view.findViewById(R.id.TVLarge);
        TVSmall = (TextView)view.findViewById(R.id.TVSmall);
        largeScope = new FragRegionLargeScope();
        largeScope.setMotherDialog(this);
        smallScope = new FragRegionSmallScope();
        smallScope.setMotherDialog(this);

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        //new ViewPagerAdapter(mFragmentManager); 로 해버리면 안됨, 다시 말해서 getSupportFragmentManager() 이거 안됨.
        adapter.addFragment(largeScope, "시/도");
        adapter.addFragment(smallScope, "시/군/구");
        GlobalRegionData.context=getContext(); //View 에서 Image 를 사용하므로.

        VPRegionSelect.setPagingEnabled(false);
        VPRegionSelect.setAdapter(adapter);

        TVLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentPage(0);
            }
        });
        TVAccept = (TextView)view.findViewById(R.id.TVAccept);
        TVAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAcceptPressed();
            }
        });
        TVCancel = (TextView)view.findViewById(R.id.TVCancel);
        TVCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    public void setWidthAndHeight()
    {
        WindowManager.LayoutParams layoutParams =getDialog().getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) layoutParams);
    }

    @Override
    public void onResume() {
        super.onResume();
        setWidthAndHeight();
    }

    public void onAcceptPressed()
    {
        if(result!=null) {
            for (int i = 0; i < 3; i++) {
                //1. data를 반영한다.
                result[i] = data[i];
                //2. data를 기준으로 textView 를 업데이트한다.
                if(data[i]!=null)
                {
                    //views[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorAccent,null));
                    views[i].setText(data[i].regionName);
                    //views[i].setTextColor(ResourcesCompat.getColor(getResources(), R.color.WHITE, null));
                    IVviews[i].setVisibility(View.VISIBLE);
                }
                else
                {
                    //views[i].setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorBright,null));
                    views[i].setText("");
                    IVviews[i].setVisibility(View.INVISIBLE);
                }
            }
        }
        dismiss();
    }

    public void setCurrentPage(int page)
    {
        VPRegionSelect.setCurrentItem(page);
        if(page==0)
        {
            TVLarge.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorPrimary, null));
            TVSmall.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.WHITE, null));
        }
        else if(page==1)
        {
            TVSmall.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorPrimary, null));
            TVLarge.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.WHITE, null));
        }
    }


    public void setCallBackViewAndData(TextView[] views,ImageView[] ivViews,GlobalRegionData[] data)
    {
        this.result=data;
        this.views=views;
        this.IVviews=ivViews;
        loadSelectedData();
    }

    public void loadSelectedData()
    {
        if(result!=null) {
            for (int i = 0; i < 3; i++) {
                if (result[i] != null)
                    data[i] = result[i];
            }
        }
    }

    public void onLargeScopeItemSelected(GlobalRegionData data)
    {
        // 1. data 를 기반으로 FragRegionSmallScope data 설정하기.
        //예를 들어 smallScope.setRecyclerViewWithData();

        //마지막 : 페이지 넘기기
        setCurrentPage(1);
    }
}
