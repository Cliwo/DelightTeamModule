package org.androidtown.delightteammodule.Gloabal.AddressAndLocation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragRegionSmallScope extends Fragment {

    ChanRegionSelectDialog dialog;

    RecyclerView RVSmallScope;
    RecyclerAdapter<GlobalRegionView, GlobalRegionData> adapter;

    public FragRegionSmallScope()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_region_small_scope, container, false);
        RVSmallScope = (RecyclerView)view.findViewById(R.id.RVSmallScope);
        adapter = new RecyclerAdapter<>(R.layout.item_global_region, new CustomViewFactory<GlobalRegionView>() {
            @Override
            public GlobalRegionView createViewHolder(View layout, ViewGroup parent) {
                final GlobalRegionView view = new GlobalRegionView(layout);
                view.RLRegion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectItem(view,(GlobalRegionData)view.getData());
                    }
                });
                return view;
            }
        });
        setRecyclerViewWithData();

        RVSmallScope.setItemAnimator(new DefaultItemAnimator());
        RVSmallScope.setLayoutManager(new GridLayoutManager(getContext(), 3));
        RVSmallScope.setAdapter(adapter);
        return view;
    }

    public void setRecyclerViewWithData()
    {
        GlobalRegionData[] all = new GlobalRegionData[5];
        all[0]=new GlobalRegionData("백석동");
        all[1]=new GlobalRegionData("마두동");
        all[2]=new GlobalRegionData("장항동");
        all[3]=new GlobalRegionData("마두2동");
        all[4]=new GlobalRegionData("덕이동");
        synchronizeData(all); //꼭 Synchronize 해야함.
        for(GlobalRegionData item :all)
            adapter.addItem(item);

    }
    public void synchronizeData(GlobalRegionData[] allData)
    {
        for(GlobalRegionData item :allData)
            for(int i =0;i<3;i++)
                if(dialog.data[i]!=null && dialog.data[i].equals(item))
                    item.isSelected=true;
    }
    public void synchronizeData(GlobalRegionData data)
    {
        for(int i =0;i<3;i++)
            if(dialog.data[i].equals(data))
                data.isSelected=true;
    }

    public void setMotherDialog(ChanRegionSelectDialog dialog)
    {
        this.dialog=dialog;
    }

    public void selectItem(GlobalRegionView view, GlobalRegionData data)
    {
        //누른걸 다시 눌렀는지 확인
        for(int i =0 ; i<3; i++)
        {
            if(dialog.data[i]!=null) {
                if (dialog.data[i].equals(data)) {
                    Log.d("SmallScope", "equal");
                    dialog.data[i].isSelected=false;
                    dialog.data[i] = null;
                    view.RLRegion.setBackgroundColor(getResources().getColor(R.color.WHITE));
                    return;
                }
            }
        }
        //누른걸 저장함. 단, 3개가 이미 선택된 상황엔 선택할 수 없음.
        for(int i =0 ; i<3; i++)
        {
            if(dialog.data[i] == null )
            {
                Log.d("SmallScope", "save");
                dialog.data[i]=data;
                dialog.data[i].isSelected=true;
                view.RLRegion.setBackgroundColor(getResources().getColor(R.color.colorBright));
                return;
            }
        }
    }
}
