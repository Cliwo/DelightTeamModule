package org.androidtown.delightteammodule.MATCH.MoreMatch;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.Connection.DataSetUpCallback;
import org.androidtown.delightteammodule.Connection.HttpUtilSend;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanTime;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchTalkData;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchTalkReplyData;
import org.androidtown.delightteammodule.MATCH.Items.View.MatchTalkReplyView;
import org.androidtown.delightteammodule.MATCH.Items.View.MatchTalkView;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamDetailData;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragTalkMatch extends Fragment {

    RecyclerView talkView;
    RecyclerAdapter<MatchTalkView, MatchTalkData> talkAdapter;

    EditText ETTalk;
    LinearLayout LLTalk;

    public FragTalkMatch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_talk_match, container, false);
        MatchTalkData.context=getContext();
        setRecyclerView(view);
        return view;
    }
    public void setViews(View view)
    {
        ETTalk = (EditText)view.findViewById(R.id.ETTalk);
        ETTalk.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LLTalk.callOnClick();
                return true;
            }
        });
        LLTalk = (LinearLayout)view.findViewById(R.id.LLTalk);
        LLTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                makeTalk();
            }
        });
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ETTalk.getWindowToken(), 0);
    }
    public void setRecyclerView(View view)
    {
        talkView = (RecyclerView) view.findViewById(R.id.RVMatchTalk);
        talkAdapter = new RecyclerAdapter<>(R.layout.item_match_talk, new CustomViewFactory<MatchTalkView>() {
            @Override
            public MatchTalkView createViewHolder(View layout, ViewGroup parent) {
                RecyclerView replyView;
                RecyclerAdapter<MatchTalkReplyView, MatchTalkReplyData> talkReplyAdapter;

                replyView = (RecyclerView) layout.findViewById(R.id.RVReply);
                talkReplyAdapter = new RecyclerAdapter<>(R.layout.item_match_talk_reply, new CustomViewFactory<MatchTalkReplyView>() {
                    @Override
                    public MatchTalkReplyView createViewHolder(View layout, ViewGroup parent) {
                        MatchTalkReplyView replyView = new MatchTalkReplyView(layout);
                        return replyView;
                    }
                });

                replyView.setAdapter(talkReplyAdapter);
                replyView.setItemAnimator(new DefaultItemAnimator());
                replyView.setLayoutManager(new LinearLayoutManager(layout.getContext()));

                MatchTalkView talkView = new MatchTalkView(layout, talkReplyAdapter); //자식의 adapter를 가져다 줌으로서 나중에
                //자식의 RecyclerView에 접근가능.
                return talkView;
            }
        });

        talkView.setAdapter(talkAdapter);
        talkView.setItemAnimator(new DefaultItemAnimator());
        talkView.setLayoutManager(new LinearLayoutManager(getContext()));

        setRecyclerViewWithData();
    }
    public void setRecyclerViewWithData() {
        talkAdapter.ClearAllData();
        for(MatchTalkData data : MoreMatch.detailData.matchTalkData)
        {
            talkAdapter.addItem(data);
        }
        /*
        talkAdapter.setSortWay(new Comparator<MatchTalkData>() {
            @Override
            public int compare(MatchTalkData lhs, MatchTalkData rhs) {
                //날짜가 최신인 순서.
                if(Integer.parseInt(rhs.cDate.getDateByFormat(ChanDate.PURE_NUMBER)) - Integer.parseInt(lhs.cDate.getDateByFormat(ChanDate.PURE_NUMBER))==0)
                {
                    //시간이 최신인 순서
                    return Integer.parseInt(rhs.cTime.getTimeByFormat(ChanTime.PURE_NUMBER))-Integer.parseInt(lhs.cTime.getTimeByFormat(ChanTime.PURE_NUMBER));
                }
                else
                    return Integer.parseInt(rhs.cDate.getDateByFormat(ChanDate.PURE_NUMBER)) - Integer.parseInt(lhs.cDate.getDateByFormat(ChanDate.PURE_NUMBER));

            }
        });
        talkAdapter.sortItems();
    */
    }

    public void makeTalk()
    {
        String message = ETTalk.getText().toString();
        /*
        HttpUtilSend send = new HttpUtilSend(new DataSetUpCallback() {
            @Override
            public void onDataReceived(String result) {
                //새로고침 효과
                setRecyclerViewWithData();
            }
        });
        send.execute("URL");
        */
        ETTalk.setText("");
    }

}