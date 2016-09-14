package org.androidtown.delightteammodule.MATCH.Items.View;

import android.view.View;
import android.widget.LinearLayout;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchMemberData;
import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-07-12.
 */
public class MatchMemberView extends CustomViewHolder {

    LinearLayout LLMatchMemberView;

    public MatchMemberView(View itemView) {
        super(itemView);
        LLMatchMemberView = (LinearLayout)itemView.findViewById(R.id.LLMatchMemberView);
    }

    @Override
    public void setContents(AdapterItemData data) {
        super.setContents(data);
        MatchMemberData Data  = (MatchMemberData) data;

    }
}
