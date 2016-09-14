package org.androidtown.delightteammodule.MATCH.Search;

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
import android.widget.ImageView;

import org.androidtown.delightteammodule.ChanDirector.ActivityCode;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.Connection.ConnectionManager;
import org.androidtown.delightteammodule.Connection.HttpUtilReceive;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.MATCH.BrowseMatch.BrowseMatch;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchCardCommercial;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchCardData;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchDataBase;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchFilter;
import org.androidtown.delightteammodule.MATCH.Items.View.MatchCardView;
import org.androidtown.delightteammodule.MATCH.Main.MatchMain;
import org.androidtown.delightteammodule.MATCH.MoreMatch.MoreMatch;
import org.androidtown.delightteammodule.MATCH.SortMatch.SortMatch;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.View.TeamNewsView;

public class FragMatchSearch extends Fragment {


    RecyclerView recyclerView;
    RecyclerAdapter<MatchCardView, MatchCardData> recyclerAdapter;

    ImageView IVSort;
    ImageView IVSearch;

    private LinearLayoutManager mLayoutManager;
    private boolean recyclerViewLoading = true;
    //http://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview


    public FragMatchSearch() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_frag_match_search, container, false);

        IVSort = (ImageView)view.findViewById(R.id.IVSort);
        IVSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SortMatch.class);
                startActivityForResult(intent, ActivityCode.MatchSort);
            }
        });
        IVSearch = (ImageView)view.findViewById(R.id.IVSearch);
        IVSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BrowseMatch.class);
                startActivityForResult(intent, ActivityCode.MatchBrowse);
            }
        });

        recyclerView = (RecyclerView)view.findViewById(R.id.RVMatchList);
        recyclerAdapter = new RecyclerAdapter<MatchCardView, MatchCardData>(R.layout.item_match_card, new CustomViewFactory<MatchCardView>() {
            @Override
            public MatchCardView createViewHolder(View layout, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_card, parent, false);
                //위의 코드는 RecyclerView내에서 CardView의 width 설정에 match_parent가
                //정상적으로 작동하지 않기 때문에 그걸 바꾸기 위해서 쓰는 형식임
                //원래는 view = 생성자(lauout); 임
                final MatchCardView matchCardView = new MatchCardView(view, MatchDataBase.MATCH_SEARCH);
                matchCardView.btnMatchTransparentAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        matchCardView.LLTransparentMatchCardSpecification.startAnimation(matchCardView.animationFadeOut);
                        Intent intent = new Intent(v.getContext(), MoreMatch.class);
                        intent.putExtra("matchUniqueID", ((MatchCardData) matchCardView.getData()).baseData.matchUniqueID);
                        startActivity(intent);
                    }
                });
                return matchCardView;
            }
        });
        setRecyclerViewWithData(); //초기 설정

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);
        // 스크롤 가장 아래로 다운시 add 하는 기능 설정
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (recyclerViewLoading)
                    {
                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                        {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case ActivityCode.MatchSort:
                if(data!=null) {
                    Bundle bundle = data.getExtras();
                    MatchFilter filter = (MatchFilter) bundle.getSerializable("Filter");
                    Log.d("OnResult", filter.dateFrom.getDateByFormat(ChanDate.SLASH_SLASH_SLASH_WITHOUT_ZERO) + " ~ " + filter.dateTo.getDateByFormat(ChanDate.SLASH_SLASH_SLASH_WITHOUT_ZERO));

                    // 필터 설정 기반으로 setAdapterData 호출하기
                    // setRecyclerViewWithFilter();
                    break;
                }

        }
    }

    public void setRecyclerViewWithData()
    {
        recyclerAdapter.ClearAllData();
        for(MatchCardData data : MatchMain.cardData)
        {
            if(data.baseData.matchState== MatchDataBase.MATCH_SEARCH)
                recyclerAdapter.addItem(data);
        }
    }
    public void setRecyclerViewWithFilter()
    {

    }

    public void addAdapterData()
    {
        //addAdapterData 의 모든 add 가 끝난 후 1초 후에 recyclerViewLoading = true; 를 해주면됨.
        Log.d("Search", "add");
    }


}
