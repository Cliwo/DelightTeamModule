package org.androidtown.delightteammodule.Gloabal;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-08-22.
 */
public class ChanPopUpMenuDialog_Person extends Dialog {

    ImageView IVCancel;

    public ChanPopUpMenuDialog_Person(Context context) {
        super(context);

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
