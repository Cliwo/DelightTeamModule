package org.androidtown.delightteammodule.MATCH.Items.Data;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanTime;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamMemberSimpleData;

/**
 * Created by Chan on 2016-07-17.
 */
public class MatchTalkReplyData extends AdapterItemData {
    //혹시 ReplyData에 어떤 reply에 댓글을 달았는지 저장해서
    // 노티를 날려야하는 사람을 찾아내도록 만들 수 있을거 같에서 reply랑 원래 댓글이랑 분리시킴
    public TeamMemberSimpleData talkMaker; //노티가 날라가야되는 사람.
    public TeamMemberSimpleData replyMaker;
    public ChanDate cDate;
    public ChanTime cTime;
    public String replyContents;
    public MatchTalkReplyData()
    {

    }
    public MatchTalkReplyData(String s)
    {
        replyContents=s;
    }
}
