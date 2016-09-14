package org.androidtown.delightteammodule.Gloabal.AddressAndLocation;

import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-08-06.
 */
public class GlobalRegionView extends CustomViewHolder {

    public RelativeLayout RLRegion;
    ImageView IVRegion;
    TextView TVRegion;

    public GlobalRegionView(View itemView) {
        super(itemView);
        IVRegion = (ImageView)itemView.findViewById(R.id.IVRegion);
        TVRegion= (TextView)itemView.findViewById(R.id.TVRegion);
        RLRegion = (RelativeLayout)itemView.findViewById(R.id.RLRegion);
        
    }

    @Override
    public void setContents(AdapterItemData data) {
        super.setContents(data);
        GlobalRegionData Data = (GlobalRegionData)data;
        IVRegion.setBackground(ResourcesCompat.getDrawable(GlobalRegionData.context.getResources(), Data.ImageID, null));
        TVRegion.setText(Data.regionName);
        if(Data.isSelected)
            RLRegion.setBackgroundColor(ResourcesCompat.getColor(GlobalRegionData.context.getResources(), R.color.colorBright, null));
        else
            RLRegion.setBackgroundColor(ResourcesCompat.getColor(GlobalRegionData.context.getResources(), R.color.WHITE, null));
    }
}
