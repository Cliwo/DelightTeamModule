package org.androidtown.delightteammodule.TEAM.CreateTeam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.delightteammodule.Gloabal.ChanTextInputDialog;
import org.androidtown.delightteammodule.Gloabal.Emblem.ChanSelectEmblemDialog;
import org.androidtown.delightteammodule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragTeamName extends Fragment implements View.OnClickListener {

    static boolean isCompleted=true;

    TextView TVInputName;
    ImageView IVTeamEmblem;
    ImageView IVTeamSelect;


    public FragTeamName() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_team_name, container, false);
        setViews(view);


        return view;
    }

    public void setViews(View view)
    {
        TVInputName = (TextView)view.findViewById(R.id.TVInputName);
        TVInputName.setOnClickListener(this);
        IVTeamEmblem = (ImageView)view.findViewById(R.id.IVTeamEmblem);
        IVTeamEmblem.setOnClickListener(this);
        IVTeamSelect = (ImageView)view.findViewById(R.id.IVTeamSelect);
        IVTeamEmblem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.TVInputName:
                ChanTextInputDialog dialog = new ChanTextInputDialog(v.getContext(), TVInputName);
                dialog.setTitle("팀 이름 입력");
                dialog.show();
                break;

            case R.id.IVTeamEmblem:
            case R.id.IVTeamSelect:
                ChanSelectEmblemDialog dialog2 = new ChanSelectEmblemDialog(v.getContext(), IVTeamEmblem);
                dialog2.show();
                break;

        }
        checkCompleted(); //나중에 수정하기 2016-07-20
    }

    public boolean checkCompleted()
    {
        setTeamName(TVInputName.getText().toString());
        //setEmblemID();
        //위가 올바르면 isCompleted true로 하고 리턴
        return isCompleted;
    }
    private void setTeamName(String s)
    {
        FragMakeTeam.data.teamTitle=s;
    }
    private void setEmblemID(int r)
    {
        FragMakeTeam.data.emblemID=r;
    }
}
