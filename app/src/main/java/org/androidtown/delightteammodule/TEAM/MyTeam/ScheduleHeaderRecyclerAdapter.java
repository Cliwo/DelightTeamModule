package org.androidtown.delightteammodule.TEAM.MyTeam;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamScheduleData;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamScheduleData;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamScheduleHeaderData;
import org.androidtown.delightteammodule.TEAM.Items.View.TeamScheduleHeaderView;
import org.androidtown.delightteammodule.TEAM.Items.View.TeamScheduleView;

import java.util.ArrayList;

/**
 * Created by Chan on 2016-03-28.
 */
public class ScheduleHeaderRecyclerAdapter extends RecyclerView.Adapter<TeamScheduleView> {


    //2016.03.28 나중에 이 부분 뜯어 고치기, 이게 최선인가, 맘에 안듬
    //RecyclerAdapter를 강화해서 만들고 싶음

    ArrayList<TeamScheduleData> items;
    private static final int HEADER =0;
    private static final int ITEM=1;

    public ScheduleHeaderRecyclerAdapter()
    {
        items=new ArrayList<>();
    }

    public ScheduleHeaderRecyclerAdapter addItem(TeamScheduleData data)
    {
        items.add(data);
        notifyDataSetChanged();
        return this;
    }

    public ScheduleHeaderRecyclerAdapter addHeader(TeamScheduleHeaderData data)
    {
        items.add(data);
        notifyDataSetChanged();
        return this;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof TeamScheduleHeaderData)
            return HEADER;
        else
            return ITEM;
    }

    @Override
    public TeamScheduleView onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEADER)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_schedule_header ,parent, false);
            return new TeamScheduleHeaderView(view);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_schedule ,parent,false);
            return new TeamScheduleView(view);
        }
    }

    @Override
    public void onBindViewHolder(TeamScheduleView holder, int position) {
        if(holder instanceof TeamScheduleHeaderView)
            holder.setContents((TeamScheduleHeaderData)items.get(position));
        else
            holder.setContents((TeamScheduleData)items.get(position));
    }

}
