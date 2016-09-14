package org.androidtown.delightteammodule.TEAM.Items.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.Gloabal.ChanPopUpMenuDialog;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanTime;
import org.androidtown.delightteammodule.Gloabal.PopUpMenuCallback;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamNewsData;

/**
 * Created by Chan on 2016-03-26.
 */
public class TeamNewsView extends CustomViewHolder {

    public LinearLayout LLReplyView;
    public ImageView IVMore;
    public TextView TVPostedTime;
    public TextView TVPostedDate;
    @Override
    public void setContents(AdapterItemData data) {
        super.setContents(data);
        TeamNewsData mData = (TeamNewsData)data;

        TVPostedDate.setText(mData.cDate.getDateByFormat(ChanDate.DOT_DOT_DOT_WITH_ZERO));
        TVPostedTime.setText(mData.cTime.getTimeByFormat(ChanTime.SEPARATED_WITH_COLON_WITH_ZERO));
        //OnClick 을 data 기반으로 설정하기
        IVMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChanPopUpMenuDialog dialog = new ChanPopUpMenuDialog(v.getContext(), v, new PopUpMenuCallback() {
                    @Override
                    public boolean[] dialogPreference() {
                        boolean[] preference = {true, true, true};
                        return preference;
                    }

                    @Override
                    public void onModify() {

                    }

                    @Override
                    public void onDelete() {

                    }

                    @Override
                    public void onReport() {

                    }
                });
                dialog.show();
            }
        });
    }

    public TeamNewsView(View itemView) {
        super(itemView);
    }
    public TeamNewsView(View itemView, ViewGroup parent)
    {
        super(itemView);
        IVMore = (ImageView)itemView.findViewById(R.id.IVMore);
        LLReplyView = (LinearLayout)itemView.findViewById(R.id.LLReplyView);
        TVPostedDate = (TextView)itemView.findViewById(R.id.TVPostedDate);
        TVPostedTime = (TextView)itemView.findViewById(R.id.TVPostedTime);
    }

}
