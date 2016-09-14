package org.androidtown.delightteammodule.TEAM.CreateTeam;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Chan on 2016-05-02.
 */
public class UnScrolledViewPager extends ViewPager {
    private boolean isPagingEnabled = true ;

    public UnScrolledViewPager(Context context) {
        super(context);
    }

    public UnScrolledViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isPagingEnabled)
            return super.onTouchEvent(ev);
        return false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(isPagingEnabled)
            return super.onInterceptTouchEvent(ev);
        return false;
    }

    public void setPagingEnabled(boolean data) {
        this.isPagingEnabled = data;
    }
}
