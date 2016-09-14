package org.androidtown.delightteammodule.Gloabal.Emblem;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-07-27.
 */
public class ChanSelectEmblemDialog extends Dialog implements View.OnClickListener {

    private ImageView imageView;

    private TextView TVTitle;
    private TextView TVCancel;
    private RecyclerView RVEmblem;

    private RecyclerAdapter<GlobalEmblemView, GlobalEmblemData> adapter ;

    private boolean isAccepted;
    private String Title;

    public static int EmblemResourceID;

    public ChanSelectEmblemDialog(Context context, ImageView resultIV) {
        super(context);
        this.imageView=resultIV;

    }

    public void setTitle(String text)
    {
        Title=text;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.TVCancel:
                isAccepted=false;
                dismiss();
                break;
        }
    }

    public void onChoiceComplete()
    {
        isAccepted=true;
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_global_emblem_select);
        setWidth();

        final ChanSelectEmblemDialog clone = this;

        TVTitle = (TextView)findViewById(R.id.TVTitle);
        if(Title != null)
            TVTitle.setText(Title);
        TVCancel= (TextView)findViewById(R.id.TVCancel);

        TVCancel.setOnClickListener(this);

        GlobalEmblemData.context=getContext(); //emblem 구체화 위해서
        RVEmblem = (RecyclerView)findViewById(R.id.RVEmblem);
        adapter = new RecyclerAdapter<>(R.layout.item_global_emblem, new CustomViewFactory<GlobalEmblemView>() {
            @Override
            public GlobalEmblemView createViewHolder(View layout, ViewGroup parent) {
                GlobalEmblemView view = new GlobalEmblemView(layout, clone);
                return view;
            }
        });
        RVEmblem.setLayoutManager(new GridLayoutManager(getContext(), 3));
        RVEmblem.setItemAnimator(new DefaultItemAnimator());
        RVEmblem.setAdapter(adapter);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (isAccepted) {
                    Log.d("Emblem", "ID : " + ChanSelectEmblemDialog.EmblemResourceID );
                    imageView.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), EmblemResourceID, null));
                }
            }
        });

        adapter.addItem(new GlobalEmblemData(R.drawable.logo1));
        adapter.addItem(new GlobalEmblemData(R.drawable.sample_emblem));
        adapter.addItem(new GlobalEmblemData(R.drawable.logo1));
        adapter.addItem(new GlobalEmblemData(R.drawable.sample_emblem));
        adapter.addItem(new GlobalEmblemData(R.drawable.logo1));
    }

    public void setWidth()
    {
        WindowManager.LayoutParams layoutParams =getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) layoutParams);
    }
}
