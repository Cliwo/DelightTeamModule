package org.androidtown.delightteammodule.MATCH.MoreMatch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.MYINFORMATION.MyInformation;
import org.androidtown.delightteammodule.MYINFORMATION.MyInformationData;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchMemberData;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchDataBase;
import org.androidtown.delightteammodule.MATCH.Items.View.MatchMemberView;
import org.androidtown.delightteammodule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragLineUpMatch extends Fragment implements View.OnClickListener {

    RecyclerView RVMatchLineUpHome;
    RecyclerView RVMatchLineUpAway;
    RecyclerView RVMatchCandidateHome;
    RecyclerView RVMatchCandidateAway;
    RecyclerView RVMatchWaiting;

    RecyclerAdapter<MatchMemberView, MatchMemberData> lineUpAdapterHome;
    RecyclerAdapter<MatchMemberView, MatchMemberData> candidateAdapterHome;
    RecyclerAdapter<MatchMemberView, MatchMemberData> lineUpAdapterAway;
    RecyclerAdapter<MatchMemberView, MatchMemberData> candidateAdapterAway;
    RecyclerAdapter<MatchMemberView, MatchMemberData> waitingAdapter;

    TextView TVJoinHomePlayer;
    TextView TVJoinAwayPlayer;
    TextView TVMatchState;
    public FragLineUpMatch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_frag_line_up_match, container, false);
        setViews(view);
        setRecyclerViews(view);
        return view;
    }
    public void checkMatchMaker()
    {
        if(MoreMatch.detailData.baseData.matchMakerNickName.equals(MyInformationData.getInstance().pageCompositionData.nickName))
        {
            //만약 매치 개설자의 nickname과 나의 nickname이 동일하다면
            //매치 확정 버튼을 액티베이트한다.
        }
    }
    public void onMatchReady()
    {
        //1. 서버에서 이 매치에 대한 정보를 가져와서 서버는 이 메소드가 호출된 직 후, 매치 정보가 바뀌지 않게 해야함
        //2. 클라이언트 뷰에 적용하고
        //3. 매치 준비가 다 됬는지 확인하고
        //4-1. 다 됬으면 프리뷰로 넘어가게 하고 다시 서버에 쏜다.
        //4-2 아니면 이 함수 return 해 버린다.
    }

    public void setViews(View view)
    {
        TVJoinHomePlayer = (TextView)view.findViewById(R.id.TVJoinHomePlayer);
        TVJoinHomePlayer.setOnClickListener(this);
        TVJoinAwayPlayer = (TextView)view.findViewById(R.id.TVJoinAwayPlayer);
        TVJoinAwayPlayer.setOnClickListener(this);
        TVMatchState = (TextView)view.findViewById(R.id.TVMatchState);
        switch (MoreMatch.detailData.baseData.matchState)
        {
            case MatchDataBase.MATCH_SEARCH:
                TVMatchState.setText("매치 준비중");
                break;
            case MatchDataBase.MATCH_WAITING:
                TVMatchState.setText("매치 대기중");
                break;
            case MatchDataBase.MATCH_PREVIEW:
                TVMatchState.setText("매치 진행중 ?? 이거맞나?");
                break;
            case MatchDataBase.MATCH_REVIEW:
                TVMatchState.setText("매치 종료");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.TVJoinHomePlayer:
                changeMyState(MatchMemberData.AS_A_PLAYER_OF_HOME);
                break;

            case R.id.TVJoinAwayPlayer:
                changeMyState(MatchMemberData.AS_A_PLAYER_OF_AWAY);
                break;
        }
        ;
    }

    public void setRecyclerViews(View view)
    {
        RVMatchLineUpHome = (RecyclerView)view.findViewById(R.id.RVMatchLineUpHome);
        RVMatchLineUpAway = (RecyclerView)view.findViewById(R.id.RVMatchLineUpAway);
        RVMatchCandidateHome = (RecyclerView)view.findViewById(R.id.RVMatchCandidateHome);
        RVMatchCandidateAway = (RecyclerView)view.findViewById(R.id.RRVMatchCandidateAway);
        RVMatchWaiting = (RecyclerView)view.findViewById(R.id.RVMatchWaiting);

        lineUpAdapterHome = new RecyclerAdapter<>(R.layout.item_match_member, new CustomViewFactory<MatchMemberView>() {
            @Override
            public MatchMemberView createViewHolder(View layout, ViewGroup parent) {
                MatchMemberView memberView = new MatchMemberView(layout);
                return memberView;
            }
        });
        lineUpAdapterAway = new RecyclerAdapter<>(R.layout.item_match_member, new CustomViewFactory<MatchMemberView>() {
            @Override
            public MatchMemberView createViewHolder(View layout, ViewGroup parent) {
                MatchMemberView memberView = new MatchMemberView(layout);
                return memberView;
            }
        });
        candidateAdapterHome = new RecyclerAdapter<>(R.layout.item_match_member, new CustomViewFactory<MatchMemberView>() {
            @Override
            public MatchMemberView createViewHolder(View layout, ViewGroup parent) {
                MatchMemberView memberView = new MatchMemberView(layout);
                return memberView;
            }
        });
        candidateAdapterAway = new RecyclerAdapter<>(R.layout.item_match_member, new CustomViewFactory<MatchMemberView>() {
            @Override
            public MatchMemberView createViewHolder(View layout, ViewGroup parent) {
                MatchMemberView memberView = new MatchMemberView(layout);
                return memberView;
            }
        });
        waitingAdapter = new RecyclerAdapter<>(R.layout.item_match_member, new CustomViewFactory<MatchMemberView>() {
            @Override
            public MatchMemberView createViewHolder(View layout, ViewGroup parent) {
                MatchMemberView memberView = new MatchMemberView(layout);
                return memberView;
            }
        });

        setRecyclerViewWithData(); //통신으로 받은 data 를 적절히 분기해서 보여줌.

        RVMatchLineUpHome.setAdapter(lineUpAdapterHome);
        RVMatchLineUpHome.setLayoutManager(new LinearLayoutManager(getContext()));
        RVMatchLineUpHome.setItemAnimator(new DefaultItemAnimator());
        RVMatchLineUpAway.setAdapter(lineUpAdapterAway);
        RVMatchLineUpAway.setLayoutManager(new LinearLayoutManager(getContext()));
        RVMatchLineUpAway.setItemAnimator(new DefaultItemAnimator());

        RVMatchCandidateHome.setAdapter(candidateAdapterHome);
        RVMatchCandidateHome.setLayoutManager(new LinearLayoutManager(getContext()));
        RVMatchCandidateHome.setItemAnimator(new DefaultItemAnimator());
        RVMatchCandidateAway.setAdapter(candidateAdapterAway);
        RVMatchCandidateAway.setLayoutManager(new LinearLayoutManager(getContext()));
        RVMatchCandidateAway.setItemAnimator(new DefaultItemAnimator());

        RVMatchWaiting.setAdapter(waitingAdapter);
        RVMatchWaiting.setLayoutManager(new LinearLayoutManager(getContext()));
        RVMatchWaiting.setItemAnimator(new DefaultItemAnimator());
    }

    public void changeMyState(int state)
    {
        MatchMemberData myData=null;
        //1.받아온 데이터 중에 내 데이터를 찾고
        for(MatchMemberData member : MoreMatch.detailData.matchMemberData)
            if(member.teamMemberSimpleData.nickName == MyInformationData.getInstance().pageCompositionData.nickName)
                myData=member;
        //2. 내 데이터의 state를 변경한다.
        if(myData != null)
            myData.state=state;
        else {
            Log.d("FragLineUpMatch", "매치 내에서 내 데이터를 찾을 수 없습니다");
            return;
        }
        //3. 내 데이터의 state를 변경한 것을 server에 알린다.
        sendChangeToServer();
        //4. server 에서 새로 데이터를 받아온다. (send에서 콜백으로 부름)
        //5. 받아온 data 를 다시 정렬한다. (receive에서 콜백으로 부름)
    }
    public void sendChangeToServer()
    {
        //4. server 에서 새로 데이터를 받아온다. (send에서 콜백으로 부름)
        receiveChangeFromServer();
    }
    public void receiveChangeFromServer()
    {
        //MoreMatch.detailData.matchMemberData

        //5. 받아온 data 를 다시 정렬한다. (receive에서 콜백으로 해결)
        setRecyclerViewWithData();
    }

    public void setRecyclerViewWithData()
    {
        //data를 모두 날려버리고 처음부터 적용
        lineUpAdapterHome.ClearAllData(); lineUpAdapterAway.ClearAllData(); candidateAdapterHome.ClearAllData(); candidateAdapterAway.ClearAllData(); waitingAdapter.ClearAllData();
        for(MatchMemberData member : MoreMatch.detailData.matchMemberData)
        {
            switch (member.state)
            {
                case MatchMemberData.AS_A_PLAYER_OF_HOME:
                    lineUpAdapterHome.addItem(member);
                    break;
                case MatchMemberData.AS_A_PLAYER_OF_AWAY:
                    lineUpAdapterAway.addItem( member);
                    break;
                case MatchMemberData.AS_A_CANDIDATE_OF_HOME:
                    candidateAdapterHome.addItem( member);
                    break;
                case MatchMemberData.AS_A_CANDIDATE_OF_AWAY:
                    candidateAdapterAway.addItem(member);
                    break;
                case MatchMemberData.AS_A_WAITING:
                    waitingAdapter.addItem(member);
                    break;
                default:
                    Log.d("FragLineUpMatch" , "RecyclerView data 세팅시 에러발생 _ 알 수 없는 state 를 가진 data 가 존재");
            }
        }
    }
}
