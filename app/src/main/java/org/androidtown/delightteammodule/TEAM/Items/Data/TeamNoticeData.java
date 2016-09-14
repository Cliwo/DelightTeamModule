package org.androidtown.delightteammodule.TEAM.Items.Data;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanTime;

/**
 * Created by Chan on 2016-04-03.
 */
public class TeamNoticeData extends AdapterItemData {

    public boolean isOnMain;

    public String noticeTitle;
    public String noticeContent;

    public TeamMemberSimpleData noticeMaker;
    public ChanDate cDate;
    public ChanTime cTime;

    public int watcherNumber;
}
