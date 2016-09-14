package org.androidtown.delightteammodule.MATCH.Items.Data;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanTime;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamMemberSimpleData;

import java.util.ArrayList;

/**
 * Created by Chan on 2016-07-17.
 */
public class MatchTalkData extends AdapterItemData {

    public TeamMemberSimpleData talkMaker;
    public ChanDate cDate;
    public ChanTime cTime;
    public String talkContent;
    public int talkID;

    //여기에 MatchTalkReplyData의 data를 다 가지고 있어야함
    public ArrayList<MatchTalkReplyData> childData;

    public MatchTalkData(ArrayList<MatchTalkReplyData> data)
    {
        childData=data;
    }

    public MatchTalkData()
    {

    }
}
