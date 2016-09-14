package org.androidtown.delightteammodule.MATCH.Items.View;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchTalkData;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchTalkReplyData;
import org.androidtown.delightteammodule.MATCH.MoreMatch.TalkMatch;
import org.androidtown.delightteammodule.R;

import java.util.ArrayList;

/**
 * Created by Chan on 2016-07-17.
 */
public class MatchTalkView extends CustomViewHolder {

    public LinearLayout LLTalkView;
    RecyclerAdapter<MatchTalkReplyView, MatchTalkReplyData> childAdapter ;

    public MatchTalkView(final View itemView, RecyclerAdapter<MatchTalkReplyView, MatchTalkReplyData> adapter  ) {
        super(itemView);
        childAdapter=adapter;
        LLTalkView = (LinearLayout)itemView.findViewById(R.id.LLTalkView);
    }

    @Override
    public void setContents(AdapterItemData data) {
        super.setContents(data);
        //여기서 MatchTalkReplyView 의 setContents를 몽땅 호출해야함.
        final MatchTalkData Data = (MatchTalkData)data;
        LLTalkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TalkMatch.class);
                intent.putExtra("talkID", Data.talkID);
                v.getContext().startActivity(intent);
            }
        });
        if(Data.childData != null)
        {
            for(MatchTalkReplyData item : Data.childData) //MatchTalkReplyData은 ArrayList 타입에 맞추는거임, 추후에 수정할 것
            {
                childAdapter.addItem(item);
            }
        }
    }

}
