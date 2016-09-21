package org.androidtown.delightteammodule.Gloabal;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import org.androidtown.delightteammodule.Connection.DataSetUpCallback;
import org.androidtown.delightteammodule.Connection.HttpUtilSend;
import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-08-22.
 */
public class ChanPopUpMenuDialog_Person extends Dialog {

    ImageView IVCancel;

    public ChanPopUpMenuDialog_Person(Context context, String nickName) {
        super(context);
        /*
        HttpUtilSend send = new HttpUtilSend(new DataSetUpCallback() {
            @Override
            public void onDataReceived(String result) {
                onConnectionComplete(result);
            }
        });
        send.execute("URL",nickName);
        URL -> 서버 URL
        */
    }

    public void onConnectionComplete(String result)
    {
        //여기서 Data 받았을 때 처리
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_global_pop_up_menu_person);
        setViews();
    }

    public void setViews()
    {
        IVCancel = (ImageView)findViewById(R.id.IVCancel);
        IVCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
