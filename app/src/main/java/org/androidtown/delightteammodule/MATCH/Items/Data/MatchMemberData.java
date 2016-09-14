package org.androidtown.delightteammodule.MATCH.Items.Data;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.MATCH.Items.View.MatchMemberView;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamMemberSimpleData;

/**
 * Created by Chan on 2016-07-12.
 */
public class MatchMemberData extends AdapterItemData {

    public static final int AS_A_PLAYER_OF_HOME = 101; //경기자
    public static final int AS_A_CANDIDATE_OF_HOME = 201; //후보자
    public static final int AS_A_PLAYER_OF_AWAY = 102; //경기자
    public static final int AS_A_CANDIDATE_OF_AWAY = 202; //후보자
    public static final int AS_A_WAITING = 301; //대기자

    public TeamMemberSimpleData teamMemberSimpleData;
    public String manner;

    public int state;

    public MatchMemberData( int kind) {
        state=kind;
    }
    public MatchMemberData(){
    }
}
