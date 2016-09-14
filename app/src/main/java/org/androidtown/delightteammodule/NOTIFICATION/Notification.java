package org.androidtown.delightteammodule.NOTIFICATION;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.NOTIFICATION.Items.Data.NotificationData;
import org.androidtown.delightteammodule.NOTIFICATION.Items.View.NotificationView;
import org.androidtown.delightteammodule.R;

public class Notification extends AppCompatActivity {


    private RecyclerView RVConsentNotification;
    private RecyclerView RVGeneralNotification;
    private RecyclerAdapter<NotificationView, NotificationData> consentNotificationAdapter;
    private RecyclerAdapter<NotificationView, NotificationData> generalNotificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RVConsentNotification = (RecyclerView)findViewById(R.id.RVConsentNotification);
        RVGeneralNotification = (RecyclerView)findViewById(R.id.RVGeneralNotification);

        consentNotificationAdapter = new RecyclerAdapter<>(R.layout.item_notification, new CustomViewFactory<NotificationView>() {
            @Override
            public NotificationView createViewHolder(View layout, ViewGroup parent) {
                NotificationView view = new NotificationView(layout);
                return view;
            }
        });
        generalNotificationAdapter = new RecyclerAdapter<>(R.layout.item_notification, new CustomViewFactory<NotificationView>() {
            @Override
            public NotificationView createViewHolder(View layout, ViewGroup parent) {
                NotificationView view = new NotificationView(layout);
                view.LLButtons.setVisibility(View.GONE);
                return view;
            }
        });

        RVConsentNotification.setItemAnimator(new DefaultItemAnimator());
        RVConsentNotification.setLayoutManager(new LinearLayoutManager(this));
        RVConsentNotification.setAdapter(consentNotificationAdapter);

        RVGeneralNotification.setItemAnimator(new DefaultItemAnimator());
        RVGeneralNotification.setLayoutManager(new LinearLayoutManager(this));
        RVGeneralNotification.setAdapter(generalNotificationAdapter);

        for(int i=0;i<4; i++)
            consentNotificationAdapter.addItem(new NotificationData());
        for(int i=0;i<6; i++)
            generalNotificationAdapter.addItem(new NotificationData());

    }

}
