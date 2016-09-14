package org.androidtown.delightteammodule.MATCH.Items.View;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanTime;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchCardCommercial;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchCardData;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchDataBase;
import org.androidtown.delightteammodule.MATCH.Main.MatchMain;
import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-07-02.
 */
public class MatchCardView extends CustomViewHolder {

    public LinearLayout LLMatchCardContent;
    public RelativeLayout RLCommercialView;
    public LinearLayout LLTransparentMatchCardSpecification;
    public CardView CVMatchCardView;

    public ImageView IVCancel;
    public ImageView IVTeamEmblem;
    public TextView TVMatchTitle;
    public TextView TVMatchRepresentativeTeam;
    public TextView TVMatchAddress;
    public TextView TVMatchMoment; //Date and Time
    public TextView TVMatchFormation;
    public TextView TVMatchManner;
    public TextView TVMatchLevel;
    public TextView TVMatchGroundNickName;

    //아래꺼는 더미
    public Button btnMatchTransparentAction;

    private boolean isAnimationOnGoing;
    private boolean isOpened;

    private Context context;

    private int state;

    public Animation animationFadeIn;
    public Animation animationFadeOut;


    public MatchCardView(View itemView, int state) {
        super(itemView);
        context=itemView.getContext();
        this.state=state;

        setViews();
        checkFragmentNumber(state);
        isAnimationOnGoing=false;
        setAnimations();
    }

    @Override
    public void setContents(AdapterItemData data) {
        super.setContents(data);
        if(data instanceof MatchCardCommercial) //commercial 이 포함되는 작은 범위기 때문에 먼저 체크함, 순서가 바뀌면 안됨.
        {
            LLMatchCardContent.setVisibility(View.GONE);
        }
        else if(data instanceof MatchCardData)
        {
            MatchCardData Data = (MatchCardData)data;
            RLCommercialView.setVisibility(View.GONE);
            IVTeamEmblem.setBackground(Data.baseData.matchRepresentativeTeam.emblem);
            TVMatchTitle.setText(Data.baseData.matchTitle);
            TVMatchFormation.setText(Data.baseData.matchFormation);
            TVMatchManner.setText(Data.baseData.matchManner);
            TVMatchRepresentativeTeam.setText(Data.baseData.matchRepresentativeTeam.teamTitle);
            TVMatchMoment.setText(Data.baseData.cDate.getDateByFormat(ChanDate.WITH_KOREAN) + Data.baseData.cTime.getTimeByFormat(ChanTime.SEPARATED_WITH_COLON_WITH_ZERO_WITH_AM_PM));
            TVMatchAddress.setText(Data.baseData.matchAddress.addressInformation);
            TVMatchLevel.setText(Data.baseData.matchLevel);
            TVMatchGroundNickName.setText(Data.baseData.matchAddress.nickName);
        }
    }

    public void setViews()
    {
        LLMatchCardContent = (LinearLayout)itemView.findViewById(R.id.LLMatchCardContent);
        RLCommercialView = (RelativeLayout)itemView.findViewById(R.id.RLCommercialView);
        CVMatchCardView = (CardView)itemView.findViewById(R.id.CVMatchCardView);
        //아래 기능은 Search 에서만 Activate 해야함, Search 가 아닌경우 그냥 바로 보여줘야함.
        LLTransparentMatchCardSpecification = (LinearLayout)itemView.findViewById(R.id.LLTransparentMatchCardSpecification);

        IVTeamEmblem = (ImageView)itemView.findViewById(R.id.IVEmblem);
        TVMatchTitle=(TextView)itemView.findViewById(R.id.TVMatchTitle);
        TVMatchMoment=(TextView)itemView.findViewById(R.id.TVMatchMoment);
        TVMatchFormation=(TextView)itemView.findViewById(R.id.TVMatchFormation);
        TVMatchManner=(TextView)itemView.findViewById(R.id.TVMatchManner);
        TVMatchRepresentativeTeam=(TextView)itemView.findViewById(R.id.TVMatchRepresentativeTeam);
        TVMatchAddress=(TextView)itemView.findViewById(R.id.TVMatchAddress);
        TVMatchLevel=(TextView)itemView.findViewById(R.id.TVMatchLevel);
        TVMatchGroundNickName =(TextView)itemView.findViewById(R.id.TVMatchGroundNickName);
        IVCancel = (ImageView)itemView.findViewById(R.id.IVCancel);
        IVCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAnimationOnGoing)
                    LLTransparentMatchCardSpecification.startAnimation(animationFadeOut);
            }
        });

        btnMatchTransparentAction = (Button)itemView.findViewById(R.id.btnMatchTransparentAction);

    }
    public void setAnimations()
    {
        animationFadeIn = AnimationUtils.loadAnimation(context, R.anim.alpha_fade_in);
        animationFadeOut = AnimationUtils.loadAnimation(context,R.anim.alpha_fade_out);
        animationFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LLTransparentMatchCardSpecification.setVisibility(View.VISIBLE);
                isAnimationOnGoing=true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimationOnGoing=false;
                isOpened = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimationOnGoing = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LLTransparentMatchCardSpecification.setVisibility(View.INVISIBLE);
                isAnimationOnGoing = false;
                isOpened = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void checkFragmentNumber(int fragNumber)
    {
        if(fragNumber == MatchDataBase.MATCH_SEARCH) {
            CVMatchCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("CardView", "OnClick, isAnimation : "+isAnimationOnGoing +" isOpend: "+isOpened);
                    if (!isAnimationOnGoing && !isOpened)
                        LLTransparentMatchCardSpecification.startAnimation(animationFadeIn);
                }
            });
        }

    }
}
