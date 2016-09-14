package org.androidtown.delightteammodule.TEAM.CreateTeam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.androidtown.delightteammodule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragTeamIntroduce extends Fragment {

    EditText ETIntroduce;

    static boolean isCompleted=true;

    boolean introduceCheck = false;

    public void isIntroduceValid(String introduce)
    {
        if(introduce.length() > 10)
            introduceCheck=true;
        else
            introduceCheck=false;
    }
    public FragTeamIntroduce() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_team_introduce, container, false);
        setViews(view);
        return view;
    }

    public void setViews(View view)
    {
        ETIntroduce = (EditText)view.findViewById(R.id.ETIntroduce);
        ETIntroduce.setHint("열 글자 이상 입력해 주세요.");
        ETIntroduce.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //해야되는 것, 모든 변환이 끝난 후 최종적으로 text가 "" 가 아니면 completed 조건 true임
                //모든 변환이 끝난 후 data 반영하기
                FragMakeTeam.data.teamIntroduce = s.toString();
            }
        });
    }

    public boolean checkCompleted()
    {
        return isCompleted;
    }

}
