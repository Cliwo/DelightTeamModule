package org.androidtown.delightteammodule.Gloabal.DateAndTime;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Chan on 2016-07-05.
 */
public class TimePicker {

    private static TimePicker timePicker;

    private Calendar calendar = Calendar.getInstance();
    private ChanTime currentTime = new ChanTime();

    private TimePicker()
    {
        currentTime.hour = calendar.get(Calendar.HOUR_OF_DAY);
        currentTime.minute = calendar.get(Calendar.MINUTE);
    }

    public static TimePicker getInstance()
    {
        if(timePicker==null)
            timePicker = new TimePicker();
        return timePicker;
    }

    public void showDialog(Context context,final PickCallBack callBack)
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                ChanTime time = new ChanTime();
                time.hour=hourOfDay;
                time.minute=minute;
                callBack.onPicked(time);
            }
        },currentTime.hour, currentTime.minute, false);
        timePickerDialog.show();
    }


    public void setTImeToTextView(TextView view, ChanTime time, int customDateFormat)
    {
        view.setText(time.getTimeByFormat(customDateFormat));
    }
}
