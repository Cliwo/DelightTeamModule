package org.androidtown.delightteammodule.MATCH.Items.GeneralData;

import org.androidtown.delightteammodule.MATCH.Items.Data.MatchMemberData;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchTalkData;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchTalkReplyData;

import java.util.ArrayList;

/**
 * Created by Chan on 2016-07-31.
 */
public class MatchDataDetail {

    public MatchDataBase baseData; //FragInformation 에 활용
    public ArrayList<MatchMemberData> matchMemberData; //FragLineUp 에 활용
    public ArrayList<MatchTalkData> matchTalkData; //FragTalk 에 활용, 이 안에 reply 도 다 들어있어야함.
}
