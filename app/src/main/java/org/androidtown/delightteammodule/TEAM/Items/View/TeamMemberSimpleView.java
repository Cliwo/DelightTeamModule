package org.androidtown.delightteammodule.TEAM.Items.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.Gloabal.ChanPopUpMenuDialog;
import org.androidtown.delightteammodule.Gloabal.ChanPopUpMenuDialog_Person;
import org.androidtown.delightteammodule.Gloabal.PopUpMenuCallback;
import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamMemberSimpleData;
import org.androidtown.delightteammodule.TEAM.Items.Data.TeamSimpleData;

/**
 * Created by Chan on 2016-03-29.
 */
public class TeamMemberSimpleView extends CustomViewHolder{

    public RelativeLayout RLTeamMember;
    public ImageView IVMemberAction ;
    int type;
    public static int NORMAL = 0;
    public static int SELECT = 1;

    public TeamMemberSimpleView(View itemView, int Type) {
        super(itemView);
        type = Type;
        IVMemberAction = (ImageView)itemView.findViewById(R.id.IVMemberAction);
        IVMemberAction.setVisibility(View.INVISIBLE);
        RLTeamMember = (RelativeLayout)itemView.findViewById(R.id.RLTeamMember);
    }
    public void activateAction()
    {
        IVMemberAction.setVisibility(View.VISIBLE);
    }
    @Override
    public void setContents(AdapterItemData data) {
        super.setContents(data);
        //OnClick 을 data 기반으로 설정하기
        if(type == NORMAL) {
            RLTeamMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChanPopUpMenuDialog_Person dialog = new ChanPopUpMenuDialog_Person(v.getContext(), ((TeamMemberSimpleData) getData()).nickName);
                    dialog.show();
                }
            });
        }
        RLTeamMember.setOnLongClickListener(new View.OnLongClickListener() {
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
