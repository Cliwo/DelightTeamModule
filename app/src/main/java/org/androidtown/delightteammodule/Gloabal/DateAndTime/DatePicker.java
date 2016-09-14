package org.androidtown.delightteammodule.Gloabal.DateAndTime;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Chan on 2016-07-05.
 */
public class DatePicker {

    private static DatePicker datePicker;

    private Calendar calendar = Calendar.getInstance();
    private ChanDate currentDate = new ChanDate();

    private DatePicker()
    {
        currentDate.year = calendar.get(Calendar.YEAR);
        currentDate.month = calendar.get(Calendar.MONTH);
        currentDate.day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static DatePicker getInstance()
    {
        if(datePicker==null)
            datePicker = new DatePicker();
        return datePicker;
    }

    public void showDialog(Context context, final PickCallBack callBack)
    {
        //현재 시간정보 받아오기
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //year, dayOfMonth는 그냥 쓰면되고 month에는 +1를 해야한다.
                ChanDate date = new ChanDate();
                date.year = year;
                date.month = monthOfYear+1;
                date.day = dayOfMonth;
                callBack.onPicked(date);
            }
        },currentDate.year, currentDate.month, currentDate.day);
        datePickerDialog.show();
    }

    public void setDateToTextView(TextView view, ChanDate date, int customDateFormat)
    {
        //customDateFormat 이용하도록 수정해야함 (07.31)
        view.setText(date.getDateByFormat(customDateFormat));
        Log.d("DatePicker" , "setDateToTextView");
    }

}
