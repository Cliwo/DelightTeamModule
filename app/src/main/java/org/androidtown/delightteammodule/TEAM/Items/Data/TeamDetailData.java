package org.androidtown.delightteammodule.TEAM.Items.Data;

import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-03-22.
 */
public class TeamDetailData extends AdapterItemData {

    public int teamUniqueID;
    public transient int color; //이건 코드로만 수정, 서버에서 받지 않음.
    public transient ChanDate cDate; //madeDate로 생성
    public transient Drawable emblem;

    public boolean isNew;

    public String teamTitle;
    public String madeDate;

    public int emblemID;
    public int avgManner;
    public int currentMemberNumber;
    public int maximumMemberNumber;
    public double avgMemberAge;

    public TeamDetailData(int emblemId, boolean isNew, String Date, int mannerPoint, int memberNumber) {
        this.color = ResourcesCompat.getColor(context.getResources(), R.color.colorBright, null);
        this.emblemID=emblemId;
        emblem = ResourcesCompat.getDrawable(context.getResources(), this.emblemID, null);

        //아랫 부분 나중에 수정 생성자 parameter와 함께
        this.isNew=isNew;
        this.teamTitle = "풋볼 나우";
        this.madeDate = Date;
        this.avgManner=mannerPoint;
        this.currentMemberNumber=memberNumber;
        this.maximumMemberNumber=20;
        this.avgMemberAge=23.5;
        //여기 까지

        cDate = new ChanDate(madeDate);
    }

}
