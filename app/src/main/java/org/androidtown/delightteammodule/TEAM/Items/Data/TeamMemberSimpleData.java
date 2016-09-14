package org.androidtown.delightteammodule.TEAM.Items.Data;

import android.graphics.drawable.Drawable;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;

/**
 * Created by Chan on 2016-03-29.
 */
public class TeamMemberSimpleData extends AdapterItemData {
    public int emblemID;
    public String name;
    public String nickName;
    //이 아래것들은 Global Data 로 바뀔 수 있음.
    public String age;
    public String position;

    public transient Drawable profileImage;
}
