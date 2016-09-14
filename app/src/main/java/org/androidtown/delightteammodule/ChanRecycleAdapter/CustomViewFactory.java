package org.androidtown.delightteammodule.ChanRecycleAdapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Chan on 2016-03-22.
 */
public interface CustomViewFactory <T extends CustomViewHolder> {
    T createViewHolder(View layout, ViewGroup parent);
}