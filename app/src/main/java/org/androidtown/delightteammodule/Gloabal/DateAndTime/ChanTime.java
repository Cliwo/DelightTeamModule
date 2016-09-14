package org.androidtown.delightteammodule.Gloabal.DateAndTime;

import java.util.Calendar;

/**
 * Created by Chan on 2016-07-05.
 */
public class ChanTime implements MomentData{
    public int hour;
    public int minute;

    public static final int DEFAULT = 0;
    public static final int SEPARATED_WITH_COLON_WITH_ZERO = 0;
    public static final int SEPARATED_WITH_COLON_WITH_ZERO_WITH_AM_PM = 1;
    public static final int PURE_NUMBER=2;
    public ChanTime()
    {

    }
    public ChanTime(String time)
    {
        String [] splitDate = time.split(":");

        hour = Integer.parseInt(splitDate[0]);
        minute = Integer.parseInt(splitDate[1]);
    }

    public static String getCurrentTimeByFormat(int format)
    {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        switch (format)
        {
            case SEPARATED_WITH_COLON_WITH_ZERO: {
                if(minute > 9)
                    return hour + ":" + minute;
                else if(minute==0)
                    return hour + ":00";
                else
                    return hour + ":0" + minute;
            }
            case SEPARATED_WITH_COLON_WITH_ZERO_WITH_AM_PM:
            {
                if(hour > 12)
                {
                    if(minute > 9)
                        return hour-12 + ":" + minute + "PM";
                    else if(minute==0)
                        return hour-12 + ":00"+ "PM";
                    else
                        return hour-12 + ":0" + minute+ "PM";
                }
                else
                {
                    if(minute > 9)
                        return hour + ":" + minute + "AM";
                    else if(minute==0)
                        return hour + ":00" + "AM";
                    else
                        return hour + ":0" + minute + "AM";
                }
            }
            case PURE_NUMBER:

                return hour*100 + minute +"";
            default:
            {
                if(minute > 9)
                    return hour + ":" + minute;
                else if(minute==0)
                    return hour + ":00";
                else
                    return hour + ":0" + minute;
            }
        }
    }


    public String getTimeByFormat(int format)
    {
        switch (format)
        {
            case SEPARATED_WITH_COLON_WITH_ZERO:
            {
                if(minute > 9)
                    return hour + ":" + minute;
                else if(minute==0)
                    return hour + ":00";
                else
                    return hour + ":0" + minute;
            }
            case SEPARATED_WITH_COLON_WITH_ZERO_WITH_AM_PM:
            {
                if(hour > 12)
                {
                    if(minute > 9)
                        return hour-12 + ":" + minute + "PM";
                    else if(minute==0)
                        return hour-12 + ":00"+ "PM";
                    else
                        return hour-12 + ":0" + minute+ "PM";
                }
                else
                {
                    if(minute > 9)
                        return hour + ":" + minute + "AM";
                    else if(minute==0)
                        return hour + ":00" + "AM";
                    else
                        return hour + ":0" + minute + "AM";
                }
            }
            default: //SEPARATED_WITH_COLON_WITH_ZERO
            {
                if(minute > 9)
                    return hour + ":" + minute;
                else if(minute==0)
                    return hour + ":00";
                else
                    return hour + ":0" + minute;
            }
        }
    }

}
