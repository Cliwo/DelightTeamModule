package org.androidtown.delightteammodule.TEAM.Items.Data;


import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;

/**
 * Created by Chan on 2016-03-22.
 */
public class TeamSimpleData extends AdapterItemData {

    public transient Drawable emblem;
    public int emblemID;
    public String teamTitle;

    public int teamUniqueID; //팀을 인식하는 고유 번호.

    public TeamSimpleData(int emblemID, String name)
    {
        this.teamTitle=name;
        this.emblemID=emblemID;
        emblem = ResourcesCompat.getDrawable(context.getResources(), this.emblemID, null);
    }

    @Override
    public String toString()
    {
        return  "Emblem ID : " + emblemID + "TeamTitle : " + teamTitle;
    }

}
