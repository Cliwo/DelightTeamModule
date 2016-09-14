package org.androidtown.delightteammodule.TEAM.Items.View;

import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Main.TeamInformationDialog;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamDetailData;

/**
 * Created by Chan on 2016-03-22.
 */
public class TeamDetailView extends CustomViewHolder {

    public static Integer evenNumberCounter = new Integer(0);

    public ImageView IVEmblem;
    public ImageView IVNew;

    public ImageView IVDelete;

    public TextView TVTeamName;
    public TextView TVTeamSomeDate; //최근날짜 아니면 신청한 날짜, 아무튼 날짜 date임
    public TextView TVAvgManner;
    public TextView TVMemberNumber;
    public TextView TVAvgAge;

    public LinearLayout backgroundLayout;

    //나중에 코드 수정하기

    public TeamDetailView(View itemView) {
        super(itemView);
        backgroundLayout = (LinearLayout)itemView.findViewById(R.id.LLEmblemAndInformation);

        IVEmblem = (ImageView)itemView.findViewById(R.id.IVTeamEmblem);
        IVNew = (ImageView)itemView.findViewById(R.id.IVNew);
        IVDelete = (ImageView)itemView.findViewById(R.id.IVDelete);

        TVTeamName = (TextView)itemView.findViewById(R.id.TVTeamName);
        TVTeamSomeDate = (TextView)itemView.findViewById(R.id.TVTeamSomeDate);
        TVAvgManner = (TextView)itemView.findViewById(R.id.TVTeamMannerPoint);
        TVMemberNumber = (TextView)itemView.findViewById(R.id.TVTeamMemberNumber);
        TVAvgAge = (TextView)itemView.findViewById(R.id.TVTeamAverageAge);
    }

    @Override
    public void setContents(AdapterItemData data) {
        super.setContents(data);
        TeamDetailData mData = (TeamDetailData)data;
        evenNumberCounter++;
        if(evenNumberCounter % 2 ==1)
            backgroundLayout.setBackgroundColor(mData.color);
        else //하드 코딩 이용, 괜찮을련지.. 2016-07-19
            backgroundLayout.setBackgroundColor(ResourcesCompat.getColor(mData.context.getResources(), R.color.WHITE,null));
        backgroundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamInformationDialog teamInformationDialog = new TeamInformationDialog(v.getContext(), ((TeamDetailData)getData()).teamUniqueID);
                teamInformationDialog.show();
            }
        });

        if(mData.isNew)
            IVNew.setVisibility(View.VISIBLE);
        else
            IVNew.setVisibility(View.INVISIBLE);

        IVEmblem.setBackground(mData.emblem);
        TVTeamName.setText(mData.teamTitle);
        TVTeamSomeDate.setText(mData.cDate.getDateByFormat(ChanDate.SLASH_SLASH_SLASH_WITH_ZERO));
        TVAvgAge.setText(Double.toString(mData.avgMemberAge));
        TVAvgManner.setText(Integer.toString(mData.avgManner));
        TVMemberNumber.setText(mData.currentMemberNumber +"/"+mData.maximumMemberNumber);
    }
}
