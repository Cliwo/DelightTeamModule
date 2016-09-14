package org.androidtown.delightteammodule.TEAM.Items.View;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanDirector.ActivityCode;
import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.Gloabal.ChanPopUpMenuDialog;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.CreateTeam.TeamCreate;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamSimpleData;

/**
 * Created by Chan on 2016-03-22.
 */
public class TeamSimpleView extends CustomViewHolder{

    public ImageView teamEmblem;
    public TextView teamName;
    public TextView TVNewsCount;

    public TeamSimpleView(View itemView) {
        super(itemView);
        teamEmblem = (ImageView)itemView.findViewById(R.id.IVTeamEmblem);
        teamName=(TextView)itemView.findViewById(R.id.TVTeamName);
        TVNewsCount = (TextView)itemView.findViewById(R.id.TVNewsCount);
        TVNewsCount.setVisibility(View.INVISIBLE);
    }

    public void setNewsCountVisible()
    {
        TVNewsCount.setVisibility(View.VISIBLE);
    }

    @Override
    public void setContents(AdapterItemData data) {
        super.setContents(data);
        TeamSimpleData mData = (TeamSimpleData)data;
        teamEmblem.setBackground(mData.emblem);
        teamName.setText(mData.teamTitle);
    }

}
