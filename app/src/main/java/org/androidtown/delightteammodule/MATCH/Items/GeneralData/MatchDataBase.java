package org.androidtown.delightteammodule.MATCH.Items.GeneralData;

import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.ChanAddress;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanTime;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamMemberSimpleData;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamSimpleData;

/**
 * Created by Chan on 2016-07-31.
 */
public class MatchDataBase {
    public static transient final int MATCH_SEARCH = 0; //static 은 기본적으로 transient 일텐데, 확인 사살용으로 써놓음.
    public static transient final int MATCH_WAITING = 1;
    public static transient final int MATCH_PREVIEW= 2;
    public static transient final int MATCH_REVIEW = 3;

    public int matchUniqueID;
    public int matchState;

    public TeamSimpleData matchRepresentativeTeam; //07-31 추가적으로 설정하는 부분 없음, 내 대표팀에서 가져올 수는 있음.

    public String matchMakerNickName;
    public String matchTitle; //매치 제목
    public String matchManner;
    public String matchLevel;
    public int matchLevelIntValue;
    public String matchFormation;
    public int matchFormationIntValue;
    public String matchFormationText;

    //기본적으로 new 안하고, 데이터 입력받는곳에서 받음
    public ChanTime cTime; //매치 시간
    public ChanDate cDate; //매치 날짜

    public ChanAddress matchAddress;



}
