package org.androidtown.delightteammodule.Gloabal.DateAndTime;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Chan on 2016-07-05.
 */
public class ChanDate implements MomentData, Serializable{

    public int year;
    public int month;
    public int day;

    public static final int DEFAULT = 0;
    public static final int SLASH_SLASH_SLASH_WITH_ZERO = 0;
    public static final int SLASH_SLASH_SLASH_WITHOUT_ZERO = 1;
    public static final int PURE_NUMBER=2;
    public static final int DOT_DOT_DOT_WITH_ZERO=3;
    public static final int WITH_KOREAN = 4;
    public ChanDate() {
    }

    public ChanDate(String date) {
        String [] splitDate = date.split("/");

        year = Integer.parseInt(splitDate[0]);
        month = Integer.parseInt(splitDate[1]);
        day = Integer.parseInt(splitDate[2]);
    }

    public static String getCurrentDateByFormat(int format)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1; //month에 1 더해줘야 됨
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        switch (format)
        {
            case SLASH_SLASH_SLASH_WITHOUT_ZERO:
                return year+"/"+month+"/"+day;
            case SLASH_SLASH_SLASH_WITH_ZERO:
                if(month<10)
                    return year+"/0"+month+"/"+day;
                return year+"/"+month+"/"+day;
            case PURE_NUMBER:
                return year*1000 +month*100 +day+"";
            case DOT_DOT_DOT_WITH_ZERO:
                return year+"."+month+"."+day;
            case WITH_KOREAN:
                return year+"년"+month+"월"+day+"일";
            default: //Same with SLASH_SLASH_SLASH_WITHOUT_ZERO:
                return year+"/"+month+"/"+day;
        }
    }

    public String getDateByFormat(int format)
    {
        switch (format)
        {
            case SLASH_SLASH_SLASH_WITHOUT_ZERO:
                return year+"/"+month+"/"+day;
            case SLASH_SLASH_SLASH_WITH_ZERO:
                if(month<10)
                    return year+"/0"+month+"/"+day;
                return year+"/"+month+"/"+day;
            case PURE_NUMBER:
                return year*1000 +month*100 +day+"";
            case DOT_DOT_DOT_WITH_ZERO:
                return year+"."+month+"."+day;
            case WITH_KOREAN:
                return year+"년"+month+"월"+day+"일";
            default: //Same with SLASH_SLASH_SLASH_WITHOUT_ZERO:
                return year+"/"+month+"/"+day;
        }
    }

    public void rewindTheDay(int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, this.day);

        calendar.add(Calendar.DAY_OF_MONTH, -day);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1; //month에 1 더해줘야 됨
        this.day = calendar.get(Calendar.DAY_OF_MONTH);

    }
}
