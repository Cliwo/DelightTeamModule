package org.androidtown.delightteammodule.TEAM.MyTeam;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import org.androidtown.delightteammodule.ChanDirector.ActivityCode;
import org.androidtown.delightteammodule.Connection.ConnectionManager;
import org.androidtown.delightteammodule.Connection.HttpUtilReceive;
import org.androidtown.delightteammodule.MATCH.MakeMatch.MakeMatch;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamScheduleData;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamScheduleHeaderData;
import org.androidtown.delightteammodule.TEAM.MyTeam.Schedule.CreateSchedule;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragTeamSchedule extends Fragment {

    ScheduleHeaderRecyclerAdapter scheduleViewAdapter;
    RecyclerView scheduleRecyclerView;

    FloatingActionButton fab;
    LinearLayout fabMakeSchedule;
    LinearLayout fabMakeMatch;

    Animation rotateForward;
    Animation rotateBackward;
    Animation translateUp;
    Animation translateDown;
    Animation translatePowerUp;
    Animation translatePowerDown;

    boolean isUp=false;
    boolean isRotated=false;

    public FragTeamSchedule() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_team_schedule, container, false);

        setRecyclerViews(view);
        setAnimations(view); //항상 setAnimations 다음에 setViews 가 호출되야함
        setViews(view);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* 데이터 다시 받고 소팅해야함 */
    }

    public void setRecyclerViews(View view)
    {
        scheduleRecyclerView=(RecyclerView)view.findViewById(R.id.RVScheduleView);
        scheduleViewAdapter=new ScheduleHeaderRecyclerAdapter();

        scheduleViewAdapter.addHeader(new TeamScheduleHeaderData());
        scheduleViewAdapter.addItem(new TeamScheduleData()).addItem(new TeamScheduleData()).addItem(new TeamScheduleData());

        scheduleRecyclerView.setItemAnimator(new DefaultItemAnimator());
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        scheduleRecyclerView.setAdapter(scheduleViewAdapter);

        //소트 해야함.
    }
    public void setAnimations(View view)
    {
        rotateBackward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backword);
        rotateForward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        translateDown = AnimationUtils.loadAnimation(getContext(), R.anim.translate_down);
        translateUp = AnimationUtils.loadAnimation(getContext(), R.anim.translate_up);
        translatePowerDown = AnimationUtils.loadAnimation(getContext(), R.anim.translate_power_down);
        translatePowerUp = AnimationUtils.loadAnimation(getContext(), R.anim.translate_power_up);

        SampleAnimationListener sampleAnimationListener = new SampleAnimationListener();
        rotateForward.setAnimationListener(sampleAnimationListener);
        rotateBackward.setAnimationListener(sampleAnimationListener);

        // translateDown.setAnimationListener(sampleAnimationListener);
        //  translateUp.setAnimationListener(sampleAnimationListener);
        //   translatePowerUp.setAnimationListener(sampleAnimationListener);
        //   translatePowerDown.setAnimationListener(sampleAnimationListener);
    }

    public void setViews(View view)
    {
        fab= (FloatingActionButton)view.findViewById(R.id.fabMakeSchedule);
        fabMakeMatch = (LinearLayout)view.findViewById(R.id.LLMakeMatch);
        fabMakeMatch.setVisibility(View.INVISIBLE);

        fabMakeSchedule = (LinearLayout)view.findViewById(R.id.LLMakeTeamSchedule);
        fabMakeSchedule.setVisibility(View.INVISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatingActionButton fabThis = (FloatingActionButton) v;
                if (!isRotated) {
                    fabThis.startAnimation(rotateForward);
                    fabMakeMatch.setVisibility(View.VISIBLE);
                    fabMakeMatch.startAnimation(translateUp);

                    fabMakeSchedule.setVisibility(View.VISIBLE);
                    fabMakeSchedule.startAnimation(translatePowerUp);

                } else {
                    fabThis.startAnimation(rotateBackward);
                    fabMakeMatch.startAnimation(translateDown);
                    fabMakeSchedule.startAnimation(translatePowerDown);
                }
            }
        });

        fabMakeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.callOnClick();
                Intent intent = new Intent(v.getContext(), CreateSchedule.class);
                startActivityForResult(intent, ActivityCode.ScheduleEdit_CODE);
            }
        });

        fabMakeMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.callOnClick();
                Intent intent = new Intent(v.getContext(), MakeMatch.class);
                startActivityForResult(intent, 1234); //막 넣엇음 2016 07 25
            }
        });
    }

    class SampleAnimationListener implements Animation.AnimationListener
    {
        @Override
        public void onAnimationEnd(Animation animation) {

            if(isUp) {
                fabMakeMatch.setVisibility(View.INVISIBLE);
                fabMakeSchedule.setVisibility(View.INVISIBLE);
                isUp=false;
            }

            if(isRotated) {
                isRotated = false;
                isUp=false;
            }
            else {
                isRotated = true;
                isUp = true;
            }

        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


}
