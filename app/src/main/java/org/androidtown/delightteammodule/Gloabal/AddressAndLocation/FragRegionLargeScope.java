package org.androidtown.delightteammodule.Gloabal.AddressAndLocation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.Gloabal.Emblem.GlobalEmblemView;
import org.androidtown.delightteammodule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragRegionLargeScope extends Fragment {

    ChanRegionSelectDialog dialog;

    RecyclerView RVLargeScope;
    RecyclerAdapter<GlobalRegionView,GlobalRegionData> adapter;

    public FragRegionLargeScope()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_region_large_scope, container, false);

        RVLargeScope = (RecyclerView)view.findViewById(R.id.RVLargeScope);
        adapter = new RecyclerAdapter<>(R.layout.item_global_region, new CustomViewFactory<GlobalRegionView>() {
            @Override
            public GlobalRegionView createViewHolder(View layout, ViewGroup parent) {
                final GlobalRegionView view = new GlobalRegionView(layout);
                view.RLRegion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GlobalRegionData data = (GlobalRegionData)(view.getData());
                        dialog.onLargeScopeItemSelected(data);
                    }
                });
                //이제 data 를 기반으로 FragRegionSmallScope 를 호출
                return view;
            }
        });
        setRecyclerViewWithData();

        RVLargeScope.setItemAnimator(new DefaultItemAnimator());
        RVLargeScope.setLayoutManager(new GridLayoutManager(getContext(), 3));
        RVLargeScope.setAdapter(adapter);
        return view;
    }

    public void setRecyclerViewWithData()
    {
        for(int i = 0 ; i <11; i++)
        {
            adapter.addItem(new GlobalRegionData());
        }
    }

    public void setMotherDialog(ChanRegionSelectDialog dialog)
    {
        this.dialog=dialog;
    }
}
