package org.androidtown.delightteammodule.Gloabal;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-07-24.
 */
public class ChanPopUpMenuDialog extends Dialog implements View.OnClickListener {

    View view;

    private static final int ADJUST_X_POSITION = 150;
    private static final int ADJUST_Y_POSITION = 870;

    public static final int MODIFY = 0;
    public static final int DELETE= 1;
    public static final int REPORT = 2;

    public static final int TEXT_VIEW_NUMBER=3; //전체 수

    public TextView[] textViews; //연결해야하는 TextView들
    private PopUpMenuCallback callback;

    public ChanPopUpMenuDialog(Context context, View clickedView, PopUpMenuCallback callback) {
        super(context);
        this.view=clickedView;
        this.callback=callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_global_pop_up_menu);

        adjustPosition();
        setViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.TVModify:
                callback.onModify();
                break;
            case R.id.TVDelete:
                callback.onDelete();
                break;
            case R.id.TVReport:
                callback.onReport();
                break;
        }
    }

    private void setViews()
    {
        //callback의 preference를 보고 결정
        boolean[] preferences = callback.dialogPreference();

        textViews= new TextView[TEXT_VIEW_NUMBER];
        //새로운 textView 가 추가되면 id찾아서 연결시키고, TEXT_VIEW_NUMBER 수정하고 onClick 리스너 수정하면 됨.
        textViews[MODIFY] = (TextView)findViewById(R.id.TVModify);
        textViews[DELETE] = (TextView)findViewById(R.id.TVDelete);
        textViews[REPORT] = (TextView)findViewById(R.id.TVReport);

        for(int i=0;i<TEXT_VIEW_NUMBER; i++) {
            if(!preferences[i])
                textViews[i].setVisibility(View.GONE);
            else
                textViews[i].setOnClickListener(this);
        }
    }
    private void adjustPosition()
    {
        int locations[] = new int[2];
        view.getLocationOnScreen(locations);

        WindowManager.LayoutParams wmlp = getWindow().getAttributes();

        wmlp.x = locations[0]-ADJUST_X_POSITION;
        wmlp.y = locations[1]-ADJUST_Y_POSITION;
        //너무 큰 값이 반환 되서, 보정값으로 뺌, 왜 이렇게 해야하는진 모르겠는데
        //이렇게 한 경우 정상적으로 TeamInformation 페이지에서 출력됨
        wmlp.gravity = Gravity.CENTER;
        wmlp.flags  &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND; // Dialog 가 생길 때 화면 검게 변하는거 방지
        getWindow().setAttributes(wmlp);
    }

}
