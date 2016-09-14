package org.androidtown.delightteammodule.Gloabal.Emblem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;

/**
 * Created by Chan on 2016-07-30.
 */
public class GlobalEmblemData extends AdapterItemData {

    public int EmblemID;
    public transient Drawable Emblem;

    public GlobalEmblemData(int RID)
    {
        EmblemID=RID;
        Emblem = ResourcesCompat.getDrawable(context.getResources(), EmblemID, null);
    }
}
