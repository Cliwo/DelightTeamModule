package org.androidtown.delightteammodule.SIGNUP;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.androidtown.delightteammodule.R;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragContract extends Fragment {


    TextView TVContract;
    TextView TVAccept;

    public FragContract() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_contract, container, false);

        TVContract  = (TextView)view.findViewById(R.id.TVContract);
        TVAccept = (TextView)view.findViewById(R.id.TVAcceptContract);
        TVAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUp)getActivity()).setCurrentPage(1);
                Log.d("Contract" , "OnClick");
            }
        });
        return view;
    }

}
