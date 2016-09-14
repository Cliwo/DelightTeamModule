package org.androidtown.delightteammodule.TEAM.Items.GeneralData;

import org.androidtown.delightteammodule.TEAM.Items.Data.TeamMemberSimpleData;

/**
 * Created by Chan on 2016-07-24.
 */
public class TeamDataWithMembers {

    public TeamDataBase dataBase;
    //Base 에 멤버만 추가한거임

    public TeamMemberSimpleData[] teamMembers; // 이거 ArrayList로 안바꿔도되나? MatchDetailData는 어레이리스트인데 07 - 31

    //이 Data를 한꺼번에 받아버리고, 나중에 연령별, 포지션별로 분석해서, 포지션이 몇명이 있는지, 나이가 몇명있는지 보여주면됨.
}
