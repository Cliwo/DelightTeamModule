package org.androidtown.delightteammodule.Gloabal.AddressAndLocation;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-08-06.
 */
public class GlobalRegionData extends AdapterItemData{
    //지역선택에서 나타나는 Item 들의 Data (예 : 고양시, 일산동구, 수원시 , 우만동)
    public String regionCode;
    public int ImageID;
    public String regionName;
    public transient boolean isSelected;

    public GlobalRegionData()
    {
        ImageID= R.drawable.logo1;
        regionName = "경기도";
        isSelected=false;
    }
    //public int RegionID;

    public GlobalRegionData(String text)//test 용
    {
        ImageID= R.drawable.logo1;
        regionName=text;
        isSelected=false;
    }



    @Override
    public boolean equals(Object o) {
        GlobalRegionData data = (GlobalRegionData)o;

        //나중에 지역코드가 생기면 지역코드만 비교하면됨.
        if(ImageID == data.ImageID && regionName.equals(data.regionName))
            return true;
        return false;
    }
}
