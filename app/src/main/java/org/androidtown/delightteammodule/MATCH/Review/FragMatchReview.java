package org.androidtown.delightteammodule.MATCH.Review;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.Connection.ConnectionManager;
import org.androidtown.delightteammodule.Connection.HttpUtilReceive;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchCardData;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchDataBase;
import org.androidtown.delightteammodule.MATCH.Items.View.MatchCardView;
import org.androidtown.delightteammodule.MATCH.Main.MatchMain;
import org.androidtown.delightteammodule.R;


public class FragMatchReview extends Fragment {



    RecyclerView recyclerView;
    RecyclerAdapter<MatchCardView, MatchCardData> recyclerAdapter;

    private LinearLayoutManager mLayoutManager;
    private boolean recyclerViewLoading = true;

    public FragMatchReview() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_match_review, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.RVMatchList);
        recyclerAdapter = new RecyclerAdapter<MatchCardView, MatchCardData>(R.layout.item_match_card, new CustomViewFactory<MatchCardView>() {
            @Override
            public MatchCardView createViewHolder(View layout, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_card, parent, false);
                //위의 코드는 RecyclerView내에서 CardView의 width 설정에 match_parent가
                //정상적으로 작동하지 않기 때문에 그걸 바꾸기 위해서 쓰는 형식임
                //원래는 view = 생성자(lauout); 임
                final MatchCardView matchCardView = new MatchCardView(view,MatchDataBase.MATCH_REVIEW);
                matchCardView.CVMatchCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ReviewMatch.class);
                        intent.putExtra("matchUniqueID", ((MatchCardData)matchCardView.getData()).baseData.matchUniqueID);
                        startActivityForResult(intent, 1234); //결과를 받을 일 없으면 forResult지워버리고, 있으면 Activity code 제대로 추가하기
                    }
                });
                return matchCardView;
            }
        });

        setRecyclerViewWithData();

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (recyclerViewLoading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            recyclerViewLoading = false;
                            addAdapterData();
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return view;
    }

    public void setRecyclerViewWithData()
    {
        recyclerAdapter.ClearAllData();
        for(MatchCardData data : MatchMain.cardData)
        {
            if(data.baseData.matchState== MatchDataBase.MATCH_REVIEW)
                recyclerAdapter.addItem(data);
        }
    }


    public void addAdapterData()
    {
        //addAdapterData 의 모든 add 가 끝난 후 1초 후에 recyclerViewLoading = true; 를 해주면됨.
        Log.d("Review", "add");
    }
}
