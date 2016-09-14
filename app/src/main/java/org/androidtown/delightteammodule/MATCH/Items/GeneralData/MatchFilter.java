package org.androidtown.delightteammodule.MATCH.Items.GeneralData;

import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.GlobalRegionData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;

import java.io.Serializable;

/**
 * Created by Chan on 2016-08-01.
 */
public class MatchFilter implements Serializable{
    public GlobalRegionData region1;
    public GlobalRegionData region2;
    public GlobalRegionData region3;
    public ChanDate dateFrom;
    public ChanDate dateTo;
    public String formation;
    public String formationText;
    public int manner;
}
