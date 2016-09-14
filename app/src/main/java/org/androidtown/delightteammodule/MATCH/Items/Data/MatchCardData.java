package org.androidtown.delightteammodule.MATCH.Items.Data;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.ChanAddress;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanTime;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchDataBase;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamMemberSimpleData;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamSimpleData;

/**
 * Created by Chan on 2016-07-02.
 */
public class MatchCardData extends AdapterItemData {

    public String maximumMatchMember;
    public String currentMatchMember;

    public MatchDataBase baseData;
    //매치 기본적인 데이터에, 현재 차있는 인원 수 , 맥시멈 인원수가 추가된거임.

    public MatchCardData() {
        // 아래는 dummy
        baseData = new MatchDataBase();
        baseData.matchTitle = "초보만 오십시요.";
        baseData.matchManner = "89이상";
        baseData.matchLevel = "하";
        baseData. matchFormation = "11 vs 11";
        maximumMatchMember = "22";
        currentMatchMember = "8";

        baseData.matchRepresentativeTeam = new TeamSimpleData(R.drawable.logo1, "풋볼나우 FC");
        baseData.cTime = new ChanTime("16:00");
        baseData.cDate = new ChanDate("2016/07/31");
        baseData.matchAddress = new ChanAddress("경기도 수원시 영통구 우만2동", "경기대학교", 0 , 0);

        //여기까지
    }


}
