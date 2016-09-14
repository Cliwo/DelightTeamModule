package org.androidtown.delightteammodule.TEAM.MyTeam;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.androidtown.delightteammodule.ChanDirector.ActivityCode;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamNewsData;
import org.androidtown.delightteammodule.TEAM.Items.View.TeamNewsView;
import org.androidtown.delightteammodule.TEAM.MyTeam.News.CreateNews;
import org.androidtown.delightteammodule.TEAM.MyTeam.News.NewsDetail;
import org.androidtown.delightteammodule.TEAM.MyTeam.Notice.NoticeDetail;

import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragTeamNews extends Fragment {

    RecyclerView teamNewsView;
    RecyclerAdapter<TeamNewsView, TeamNewsData> newsViewAdapter;

    LinearLayout teamNoticeView;
    FloatingActionButton fabWriteNews;
    /* 없는 기능
    2. 소식 댓글보기
     */

    public FragTeamNews() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_team_news, container, false);

        setViews(view);
        setRecyclerViews(view);
        //HttpUtilReceive util = new HttpUtilReceive(ConnectionManager.ConnectionManagerBuilder(TeamNewsData.class, newsViewAdapter));
        //util.execute(URL);
        sortBasedOnMostRecent();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("FragTeamNews", "OnActivityResult");
        //HttpUtilReceive util = new HttpUtilReceive(ConnectionManager.ConnectionManagerBuilder(TeamNewsData.class, newsViewAdapter));
        //util.execute(URL);
        //sortBasedOnMostRecent();
    }

    public void setViews(View view)
    {
        fabWriteNews = (FloatingActionButton)view.findViewById(R.id.fabWriteNews);
        fabWriteNews. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateNews.class);
                startActivityForResult(intent, ActivityCode.CreateNews_CODE);
            }
        });
        teamNoticeView= (LinearLayout)view.findViewById(R.id.LLTeamNewsNotice);
        //공지가 있으면 넣고, 없으면 GONE 해야함
        teamNoticeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoticeDetail.class);
                startActivityForResult(intent, ActivityCode.NoticeDetail_CODE);
            }
        });
    }
    public void setRecyclerViews(View view)
    {
        teamNewsView = (RecyclerView)view.findViewById(R.id.RVTeamNewsView);
        newsViewAdapter = new RecyclerAdapter<>(R.layout.item_team_news, new CustomViewFactory<TeamNewsView>() {
            @Override
            public TeamNewsView createViewHolder(View layout, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_news, parent, false);
                //위의 코드는 RecyclerView내에서 CardView의 width 설정에 match_parent가
                //정상적으로 작동하지 않기 때문에 그걸 바꾸기 위해서 쓰는 형식임
                //원래는 view = 생성자(lauout); 임
                final TeamNewsView teamNewsView = new TeamNewsView(view, parent);
                teamNewsView.LLReplyView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), NewsDetail.class);
                        intent.putExtra("teamNewsDataUniqueID", ((TeamNewsData)teamNewsView.getData()).getTeamNewsDataUniqueID());
                        startActivity(intent);
                    }
                });
                return teamNewsView;
            }
        });
        newsViewAdapter.addItem(new TeamNewsData(3, "2016/03/20", "13:30"));
        newsViewAdapter.addItem(new TeamNewsData(9, "2016/04/12", "11:30"));
        newsViewAdapter.addItem(new TeamNewsData(101, "2016/05/30", "10:30"));
        newsViewAdapter.addItem(new TeamNewsData(25, "2016/07/1", "9:00"));
        newsViewAdapter.addItem(new TeamNewsData(88, "2016/02/8", "20:00"));
        newsViewAdapter.addItem(new TeamNewsData(71, "2016/08/2", "12:00"));
        teamNewsView.setAdapter(newsViewAdapter);
        teamNewsView.setItemAnimator(new DefaultItemAnimator());
        teamNewsView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void sortBasedOnMostRecent()
    {
        newsViewAdapter.setSortWay(new Comparator<TeamNewsData>() {
            @Override
            public int compare(TeamNewsData lhs, TeamNewsData rhs) {
                return Integer.parseInt(rhs.cDate.getDateByFormat(ChanDate.PURE_NUMBER)) - Integer.parseInt(lhs.cDate.getDateByFormat(ChanDate.PURE_NUMBER));
            }
        });
        newsViewAdapter.sortItems();
    }
}
