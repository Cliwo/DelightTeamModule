package org.androidtown.delightteammodule.MATCH.MakeMatch;


import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.delightteammodule.Gloabal.Emblem.ChanSelectEmblemDialog;
import org.androidtown.delightteammodule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragMatchSelectRepresentative extends Fragment {


    public TextView TVTeamEdit;
    public ImageView IVTeamLogo;
    public RecyclerView RVTeamMember;
    public TextView IVAddTeamMember;


    public FragMatchSelectRepresentative() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_match_select_representative, container, false);
        setViews(view);
        setRecyclerViews(view);
        return view;
    }

    public void setViews(View view)
    {
        TVTeamEdit = (TextView)view.findViewById(R.id.TVTeamEdit);
        TVTeamEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChanSelectEmblemDialog dialog = new ChanSelectEmblemDialog(getContext(), IVTeamLogo);
                dialog.show();
            }
        });
        IVTeamLogo = (ImageView)view.findViewById(R.id.IVTeamLogo);
        IVTeamLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChanSelectEmblemDialog dialog = new ChanSelectEmblemDialog(getContext(), IVTeamLogo);
                dialog.show();
            }
        });
        IVAddTeamMember = (TextView)view.findViewById(R.id.IVAddTeamMember);
        IVAddTeamMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //기능 넣어야됨.
            }
        });
    }

    public void setRecyclerViews(View view)
    {
        //RVTeamMember = (RecyclerView)view.findViewById(R.id.RVTeamMember);
        //RVTeamMember.setLayoutManager(new GridLayoutManager(getContext(), 6)); //6개가 한줄
    }

}
