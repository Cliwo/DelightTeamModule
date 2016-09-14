package org.androidtown.delightteammodule.MATCH.Preview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchCardData;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchDataBase;
import org.androidtown.delightteammodule.MATCH.Items.View.MatchCardView;
import org.androidtown.delightteammodule.MATCH.Main.MatchMain;
import org.androidtown.delightteammodule.MATCH.MoreMatch.MoreMatch;
import org.androidtown.delightteammodule.R;

public class FragMatchPreview extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter<MatchCardView, MatchCardData> recyclerAdapter;


    public FragMatchPreview() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_frag_match_preview, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.RVMatchList);
        recyclerAdapter = new RecyclerAdapter<MatchCardView, MatchCardData>(R.layout.item_match_card, new CustomViewFactory<MatchCardView>() {
            @Override
            public MatchCardView createViewHolder(View layout, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_card, parent, false);
                //위의 코드는 RecyclerView내에서 CardView의 width 설정에 match_parent가
                //정상적으로 작동하지 않기 때문에 그걸 바꾸기 위해서 쓰는 형식임
                //원래는 view = 생성자(lauout); 임
                final MatchCardView matchCardView = new MatchCardView(view, MatchDataBase.MATCH_PREVIEW);
                matchCardView.CVMatchCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), MoreMatch.class);
                        intent.putExtra("matchUniqueID", ((MatchCardData)matchCardView.getData()).baseData.matchUniqueID);
                        startActivity(intent);
                    }
                });
                return matchCardView;
            }
        });

        setRecyclerViewWithData();


        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public void setRecyclerViewWithData()
    {
        recyclerAdapter.ClearAllData();
        for(MatchCardData data : MatchMain.cardData)
        {
            if(data.baseData.matchState== MatchDataBase.MATCH_PREVIEW)
                recyclerAdapter.addItem(data);
        }

    }



}
