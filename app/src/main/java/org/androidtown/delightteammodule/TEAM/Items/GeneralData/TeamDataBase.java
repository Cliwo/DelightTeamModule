package org.androidtown.delightteammodule.TEAM.Items.GeneralData;

import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.ChanAddress;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.GlobalRegionData;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamMemberSimpleData;

/**
 * Created by Chan on 2016-07-20.
 */
public class TeamDataBase {

    private int teamUniqueID;

    public TeamMemberSimpleData teamMaker;

    public String teamTitle; //FragTeamName
    public String teamIntroduce; //FragTeamIntroduce

    public int emblemID; //FragTeamName, 팀 앰블럼

    public boolean isPublic; //FragTeamConfigure
    public boolean hasOwnGround; //FragTeamConfigure

    public GlobalRegionData[] regions = new GlobalRegionData[3]; //FragTeamConfigure (ChanRegionSelectDialog)
    public ChanAddress groundAddress = new ChanAddress(); //여기에 nickName 포함 //FragTeamConfigure

    public void setTeamUniqueID(int val)
    {
        teamUniqueID=val;
    }
}
