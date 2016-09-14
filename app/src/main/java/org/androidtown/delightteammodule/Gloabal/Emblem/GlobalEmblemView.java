package org.androidtown.delightteammodule.Gloabal.Emblem;

import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-07-30.
 */
public class GlobalEmblemView extends CustomViewHolder {

    private ChanSelectEmblemDialog mother;
    public ImageView IVEmblem;

    public GlobalEmblemView(View itemView, ChanSelectEmblemDialog mother) {
        super(itemView);
        IVEmblem = (ImageView)itemView.findViewById(R.id.IVEmblem);
        this.mother=mother;
    }

    @Override
    public AdapterItemData getData() {
        return super.getData();
    }

    @Override
    public void setContents(AdapterItemData data) {
        super.setContents(data);
        final GlobalEmblemData Data = (GlobalEmblemData)data;
        IVEmblem.setBackground(Data.Emblem);
        IVEmblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChanSelectEmblemDialog.EmblemResourceID = Data.EmblemID;
                Log.d("Emblem", "ID : " + ChanSelectEmblemDialog.EmblemResourceID + " Data : " + Data.EmblemID);
                mother.onChoiceComplete(); //선택 하면 확인을 한 것처럼 만듬 바로 선택
            }
        });

    }

}
