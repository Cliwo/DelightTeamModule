package org.androidtown.delightteammodule.TEAM.Items.Data;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.ChanAddress;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanTime;

/**
 * Created by Chan on 2016-03-28.
 */
public class TeamScheduleData extends AdapterItemData{

    public final static int MATCH = 0;
    public final static int WAIT = 1;
    public final static int PREVIEW = 2;
    public final static int REVIEW = 3;
    public final static int SCHEDULE = 4;

    public ChanDate cDate;
    public  ChanTime cTime;

    public int viewKind; //Match, Wait, Preview, Review, Schedule

    public String scheduleTitle;
    public TeamMemberSimpleData scheduleMaker;
    public String formation;

    public ChanAddress location; //닉네임을 가져와서 View에 set
}
