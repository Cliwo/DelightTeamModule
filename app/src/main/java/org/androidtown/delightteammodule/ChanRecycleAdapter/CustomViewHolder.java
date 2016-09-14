package org.androidtown.delightteammodule.ChanRecycleAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Chan on 2016-03-22.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder implements AdapterItemView {

    //Recycler 뷰에서만 쓰도록 만든 ViewHolder를 정의하는 class임
    //List 뷰에서는 이런 작업이 필요없음 , Adpater 형식이 자유라서
    private AdapterItemData data;

    public CustomViewHolder(View itemView) {
        super(itemView);
        //itemView.setClickable(true);
    }

    @Override
    public void setContents(AdapterItemData data) {
        this.data=data;
    }

    public AdapterItemData getData()
    {
        return data;
    }
}