package org.androidtown.delightteammodule.TEAM.MyTeam;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanDirector.ActivityCode;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.MYINFORMATION.MyInformationData;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.Gloabal.InviteFriend.InviteFriend;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamMemberSimpleData;
import org.androidtown.delightteammodule.TEAM.Items.GeneralData.TeamDataWithMembers;
import org.androidtown.delightteammodule.TEAM.Items.View.TeamMemberSimpleView;
import org.androidtown.delightteammodule.TEAM.MyTeam.Notice.NoticeList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragTeamInformation extends Fragment {

    //한 번 받은 data 를 계속 가지고 있도록 의도함. 다른 방법이 있다면 static 대신 그걸 쓰자.
    //2016 07 31 지금 FragTeamInformation 에서 정보를 받는데 이렇게 하지말고, TeamMyTeam , 즉 Activity 에서 fragment를 구성할 data를 한방에
    //다 받아버리는걸로 수정하기. 현재 match는 그런식으로 짜려고함. 참고할 것.
    static TeamDataWithMembers teamData;

    RecyclerView teamMemberRecyclerView;
    RecyclerAdapter<TeamMemberSimpleView, TeamMemberSimpleData> teamMemberViewAdapter;

    Button btnShowNoticeList;
    Button btnInviteFriend;
    TextView TVTeamOutOrDelete;

    private boolean IAMMAKER;

    /* 현재 없는 기능
    1. 팀 삭제하기
    2. 팀 수정하기
        팀 경기장 수정
        활동지역 수정
        팀 설명 수정
        팀원 추방
     */

    public void setNewData()
    {
        //받아온 data가 없다면 새로 받는다.
        //항상 setViews 이후에 호출되어야한다.
        if(teamData==null) {
            teamData = new TeamDataWithMembers();
            getDataFromServer();
        }
    }
    public void getDataFromServer()
    {

        /*
        ((TeamMyTeam)getActivity()).teamUniqueID //이걸로 불러올 팀 식별
        HttpUtilReceive util = new HttpUtilReceive(new DataSetUpCallback() {
            @Override
            public void onDataReceived(String result) {
                Gson gson = new Gson();
                teamData = (TeamDataWithMembers)gson.fromJson(result, teamData.getClass());
                setUpPageData();
            }
        });
        util.execute(URL);
        */
        //나중에 URL에 적절한 url 넣어서 작동시켜보기.
        //2016-07-24
    }
    public void setUpPageData()
    {
       // Data 가 정상적으로 server 에서 도착했을때 호출 되는 메소드.
        //teamData.dataBase.teamTitle;
        //teamData.dataBase.teamIntroduce; 등등...
    }

    public FragTeamInformation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_frag_team_information, container, false);

        setRecyclerViews(view);
        setViews(view);
        setNewData();

        return view;
    }

    public void setViews(View view)
    {
        btnShowNoticeList = (Button)view.findViewById(R.id.btnShowNotice);
        btnShowNoticeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoticeList.class);
                startActivityForResult(intent, ActivityCode.NoticeList_CODE);
            }
        });

        btnInviteFriend = (Button)view.findViewById(R.id.btnInviteFriend);
        btnInviteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InviteFriend.class);
                startActivityForResult(intent, ActivityCode.InviteFriend_COEE);
            }
        });

        IAMMAKER=false; //기본적으로 자신은 team 개설자가 아니라고 추정하고 프로그램 실행
        TVTeamOutOrDelete = (TextView)view. findViewById(R.id.TVTeamDelete);
        if(MyInformationData.getInstance().pageCompositionData.nickName != null && isTeamDataValid() ) {
            if (teamData.dataBase.teamMaker.nickName.equals(MyInformationData.getInstance().pageCompositionData.nickName)) {
                //자신이 팀 개설자인지 확인하는 과정.
                IAMMAKER = true;
            } else
                IAMMAKER = false;
        }
        TVTeamOutOrDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("팀을 " + (IAMMAKER ? "삭제하시겠습니까" : "탈퇴하시겠습니까")); //삭제, 나가기 기능 만들어야함.
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (IAMMAKER)
                            onDeleteTeam();
                        else
                            onGetOutOfTeam();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
    }

    public boolean isTeamDataValid()
    {
        //if(teamData.dataBase == null)
        //    return false;
        return false;
    }

    public void onDeleteTeam()
    {

    }

    public void onGetOutOfTeam()
    {

    }

    public void setRecyclerViews(View view)
    {
        teamMemberRecyclerView = (RecyclerView)view.findViewById(R.id.RVTeamMember);
        teamMemberRecyclerView.setItemAnimator(new DefaultItemAnimator());
        teamMemberRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        teamMemberViewAdapter= new RecyclerAdapter<>(R.layout.item_team_simple_member, new CustomViewFactory<TeamMemberSimpleView>() {
            @Override
            public TeamMemberSimpleView createViewHolder(View layout, ViewGroup parent) {
                TeamMemberSimpleView view =new TeamMemberSimpleView(layout, TeamMemberSimpleView.NORMAL);
                return view;
            }
        });

        teamMemberViewAdapter.addItem(new TeamMemberSimpleData());
        teamMemberViewAdapter.addItem(new TeamMemberSimpleData());
        teamMemberViewAdapter.addItem(new TeamMemberSimpleData());
        teamMemberViewAdapter.addItem(new TeamMemberSimpleData());
        teamMemberViewAdapter.addItem(new TeamMemberSimpleData());

        teamMemberRecyclerView.setAdapter(teamMemberViewAdapter);
    }
}
