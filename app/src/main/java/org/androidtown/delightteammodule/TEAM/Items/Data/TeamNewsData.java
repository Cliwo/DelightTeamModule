package org.androidtown.delightteammodule.TEAM.Items.Data;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanTime;

/**
 * Created by Chan on 2016-03-26.
 */
public class TeamNewsData extends AdapterItemData {

    private int teamNewsDataUniqueID;

    public TeamMemberSimpleData newsMaker;
    public ChanDate cDate;
    public ChanTime cTime;
    public String newsContent;
    public int replyNumber;

    public TeamNewsData()
    {
        //이건 CreateNews에서 쓰이는거
    }
    public TeamNewsData(int id, String date, String time)
    {
        //이거 예시 생성자임 바꿔야함
        //2016-07-24
        teamNewsDataUniqueID = id;
        cDate = new ChanDate(date);
        cTime = new ChanTime(time);
    }
    public int getTeamNewsDataUniqueID()
    {
        return teamNewsDataUniqueID;
    }
}
