package org.androidtown.delightteammodule.ChanCustomViews;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.androidtown.delightteammodule.R;
import org.androidtown.delightteammodule.TEAM.Items.NotificationData.NotificationData;
import org.androidtown.delightteammodule.TEAM.Main.TeamMain;

/**
 * Created by Chan on 2016-05-27.
 */
public class DelightNotification {

    public static final String TAG = "Notification";

    public static final int DEFAULT = 100;

    public static void sendDelightNotification(Context c, String sample)
    {
        Notification.Builder notificationBuilder = new Notification.Builder(c);
        Log.d(TAG,"DefaultCreateCalled");
        notificationBuilder.setContentTitle("풋볼나우 : 샘플 Notification 입니다. " + sample);
        PendingIntent defaultIntent = PendingIntent.getActivity(c, DEFAULT, new Intent(c, TeamMain.class), PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(defaultIntent);

        notificationBuilder.setSmallIcon(R.drawable.logo1);
        notificationBuilder.setContentText("Sample Testing");
        notificationBuilder.setAutoCancel(true);

        final NotificationManager mgr = (NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
        mgr.notify(0,notificationBuilder.build());


    }

    public static void sendDelightNotification(Context c, NotificationData notificationData)
    {
        //나중에 data에 따라 layout 분기하기
        //data 가 PendingIntent 정보도 가지고 있는게 나을듯.
        Notification.Builder notificationBuilder = new Notification.Builder(c);
        Log.d(TAG,"DataBasedCreateCalled");

        notificationBuilder.build();
    }
}
