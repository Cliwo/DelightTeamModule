package org.androidtown.delightteammodule.NOTIFICATION.Items.View;

import android.view.View;
import android.widget.LinearLayout;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-09-02.
 */
public class NotificationView extends CustomViewHolder {
    public LinearLayout LLButtons;

    public NotificationView(View itemView) {
        super(itemView);
        LLButtons = (LinearLayout)itemView.findViewById(R.id.LLButtons);
    }

    @Override
    public AdapterItemData getData() {
        return super.getData();
    }

    @Override
    public void setContents(AdapterItemData data) {
        super.setContents(data);
    }
}
