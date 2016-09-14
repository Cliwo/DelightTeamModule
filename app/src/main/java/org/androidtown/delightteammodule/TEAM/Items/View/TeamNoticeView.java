package org.androidtown.delightteammodule.TEAM.Items.View;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.androidtown.delightteammodule.ChanDirector.ActivityCode;
import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.MyTeam.Notice.NoticeDetail;

/**
 * Created by Chan on 2016-04-03.
 */
public class TeamNoticeView extends CustomViewHolder {

    public LinearLayout LLNoticeItemView;
    public ImageView IVNoti;

    private LinearLayout LLNoticeImageButtonView;
    private boolean isOpened;
    private boolean isAnimationOnGoing;
    private Context context;

    private Animation animationLeftToRight;
    private Animation animationRightToLeft;

    private RelativeLayout RLNoticeImagesLeftOver;
    public TeamNoticeView(View itemView) {
        super(itemView);
        context=itemView.getContext();
        LLNoticeItemView = (LinearLayout)itemView.findViewById(R.id.LLNoticeItemView);
        IVNoti = (ImageView)itemView.findViewById(R.id.IVNoti);
        LLNoticeImageButtonView = (LinearLayout)itemView.findViewById(R.id.LLNoticeImageButtonView);
        RLNoticeImagesLeftOver = (RelativeLayout)itemView.findViewById(R.id.RLNoticeImagesLeftOver);

        isAnimationOnGoing=false;
        isOpened=false;
        setAnimations();
        IVNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //애니메이션 작동
                Log.d("Notice", "OnClicked");
                if(!isAnimationOnGoing) {
                    LLNoticeImageButtonView.startAnimation(animationLeftToRight);
                    Log.d("Notice", "Animation Start");
                }

            }
        });

        RLNoticeImagesLeftOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isAnimationOnGoing) {
                    LLNoticeImageButtonView.startAnimation(animationRightToLeft);
                }
            }
        });
    }

    private void setAnimations() {

        //Load animation
        animationLeftToRight = AnimationUtils.loadAnimation(context, R.anim.translate_right);
        animationRightToLeft = AnimationUtils.loadAnimation(context, R.anim.translate_left);

       // LLNoticeImageButtonView.setAnimation(animationLeftToRight);
       // LLNoticeImageButtonView.setAnimation(animationRightToLeft);

        //Know when it ends to change visibility
        animationLeftToRight.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                isAnimationOnGoing=true;
                LLNoticeImageButtonView.setVisibility(View.VISIBLE);
                LLNoticeItemView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                isAnimationOnGoing=false;
                isOpened=true;
            }
        });

        animationRightToLeft.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                isAnimationOnGoing=true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimationOnGoing=false;
                isOpened=false;
                LLNoticeImageButtonView.setVisibility(View.INVISIBLE);
                LLNoticeItemView.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void setContents(AdapterItemData data) {
        super.setContents(data);

    }
}
