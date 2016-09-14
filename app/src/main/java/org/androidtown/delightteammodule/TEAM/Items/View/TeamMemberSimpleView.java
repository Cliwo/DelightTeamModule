package org.androidtown.delightteammodule.TEAM.Items.View;

import android.view.View;
import android.widget.LinearLayout;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.Gloabal.ChanPopUpMenuDialog;
import org.androidtown.delightteammodule.Gloabal.ChanPopUpMenuDialog_Person;
import org.androidtown.delightteammodule.Gloabal.PopUpMenuCallback;
import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-03-29.
 */
public class TeamMemberSimpleView extends CustomViewHolder{

    public LinearLayout LLTeamMember;
    public TeamMemberSimpleView(View itemView) {
        super(itemView);
        LLTeamMember = (LinearLayout)itemView.findViewById(R.id.LLTeamMember);

    }

    @Override
    public void setContents(AdapterItemData data) {
        super.setContents(data);
        //OnClick 을 data 기반으로 설정하기
        LLTeamMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChanPopUpMenuDialog_Person dialog = new ChanPopUpMenuDialog_Person(v.getContext());
                dialog.show();
            }
        });
        LLTeamMember.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ChanPopUpMenuDialog dialog = new ChanPopUpMenuDialog(v.getContext(), v, new PopUpMenuCallback() {
                    @Override
                    public boolean[] dialogPreference() {
                        boolean[] preference = {false, false, true};
                        return preference;
                    }

                    @Override
                    public void onModify() {
                        //X
                    }

                    @Override
                    public void onDelete() {
                        //X
                    }

                    @Override
                    public void onReport() {
                        //신고하기 설정
                    }
                });
                dialog.show();
                return false;
            }
        });
    }
}
