package org.androidtown.delightteammodule.TEAM.CreateTeam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanCustomViews.VerticalViewPager;
import org.androidtown.delightteammodule.ChanCustomViews.ViewPagerAdapter;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.GeneralData.TeamDataBase;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragMakeTeam extends Fragment {

    VerticalViewPager verticalViewPager;
    ViewPagerAdapter viewPagerAdapter;

    FragTeamName teamName = new FragTeamName();
    FragTeamConfigure teamConfigure = new FragTeamConfigure();
    FragTeamIntroduce teamIntroduce = new FragTeamIntroduce();
    TextView createTeam;

    static TeamDataBase data;

    public FragMakeTeam() {
        // Required empty public constructor
    }
    public void setNewData()
    {
        if(data==null)
            data = new TeamDataBase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_frag_make_team, container, false);
        setNewData();
        setViews(view);

        verticalViewPager = (VerticalViewPager)view.findViewById(R.id.VVVerticalViewPager);
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        //위에서 에러 가능
        teamName = new FragTeamName();
        teamConfigure = new FragTeamConfigure();
        teamIntroduce = new FragTeamIntroduce();
        viewPagerAdapter.addFragment(teamName, "Name");
        viewPagerAdapter.addFragment(teamConfigure, "Configure");
        viewPagerAdapter.addFragment(teamIntroduce, "Introduce");

        verticalViewPager.setAdapter(viewPagerAdapter);
        verticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 2 &&teamName.checkCompleted() && teamConfigure.checkCompleted() && teamIntroduce.checkCompleted()) {
                    //마지막 page이며, 모든 page가 정상적으로 채워졌을때.
                    createTeam.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
                    createTeam.setTextColor(ResourcesCompat.getColor(getResources(), R.color.WHITE, null));
                }
            }
            @Override
            public void onPageSelected(int position) {
                //여기 지금 에러있음 어떤 조건시, 다시 빨간색으로 안변함.
                //2016-07-25

                if(position == 2 && teamName.checkCompleted() && teamConfigure.checkCompleted() && teamIntroduce.checkCompleted())
                {
                    //마지막 page이며, 모든 page가 정상적으로 채워졌을때.
                    createTeam.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
                    createTeam.setTextColor(ResourcesCompat.getColor(getResources(), R.color.WHITE, null));
                    createTeam.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //한 번 complete 된 상황으로 만들고, 지운다음에 다음으로 넘어가버리는걸 방지, 항상 Complete 되어있어야 넘어갈수 있다.
                            if(teamName.checkCompleted() && teamConfigure.checkCompleted() && teamIntroduce.checkCompleted()) {
                                ((TeamCreate) getActivity()).completeMakingTeam(data);
                                data=null; //현재 완성한 data 는 보내버림
                            }
                            else {
                                ((TextView) v).setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorBright, null));
                                ((TextView) v).setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorDark, null));
                            }
                        }
                    });
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    public void setViews(View view)
    {
        createTeam = (TextView)view.findViewById(R.id.TVCreateTeam);
    }



}
