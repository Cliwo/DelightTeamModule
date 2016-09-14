package org.androidtown.delightteammodule.MATCH.Items.View;

import android.view.View;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchTalkReplyData;
import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-07-17.
 */
public class MatchTalkReplyView extends CustomViewHolder {

    TextView contents;

    public MatchTalkReplyView(View itemView) {
        super(itemView);
        contents = (TextView) itemView.findViewById(R.id.TVTalkContents);
    }

    @Override
    public void setContents(AdapterItemData data) {
        super.setContents(data);
        MatchTalkReplyData Data = (MatchTalkReplyData)data;
        contents.setText(Data.replyContents);
    }
}
