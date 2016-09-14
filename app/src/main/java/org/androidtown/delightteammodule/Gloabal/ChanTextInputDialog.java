package org.androidtown.delightteammodule.Gloabal;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.delightteammodule.Connection.ConnectionManager;
import org.androidtown.delightteammodule.R;

/**
 * Created by Chan on 2016-07-27.
 */
public class ChanTextInputDialog extends Dialog implements View.OnClickListener{

    /*
    2016 07 31 현재
    팀 이름을 입력받을 때와 (팀 생성시)
    매치 운동장 닉네임, 즉 상세 설명을 적을 때 (매치 생성시)
    사용됩니다.
     */
    private TextView textView;

    private TextView TVTitle;
    private TextView TVCancel;
    private TextView TVAccept;
    private EditText ETText;

    private String Title;
    private boolean isAccepted;

    public ChanTextInputDialog(Context context, TextView textView) {
        super(context);
        this.textView=textView;
        isAccepted=false;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.TVCancel:
                isAccepted=false;
                dismiss();
                break;

            case R.id.TVAccept:
                String text = ETText.getText().toString();
                if(text.equals("") || text == null)
                {
                    //이 기준은 모든 TextInputDialog에 적용되는거임, 잘 생각해야함. 하나의 activity에 종속되는 기준이 아님.
                    Toast.makeText(getContext(), "한 글자 이상 입력해 주세요.", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    isAccepted = true;
                    dismiss();
                }
                break;
        }
    }

    public void setTitle(String text)
    {
        Title= text;
    }
    private void setWidth()
    {
        WindowManager.LayoutParams layoutParams =getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_global_text_input);
        setWidth();
        TVTitle = (TextView)findViewById(R.id.TVTitle);
        if(Title != null)
            TVTitle.setText(Title);
        TVCancel= (TextView)findViewById(R.id.TVCancel);
        TVAccept= (TextView)findViewById(R.id.TVAccept);
        ETText = (EditText)findViewById(R.id.ETText);

        TVCancel.setOnClickListener(this);
        TVAccept.setOnClickListener(this);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (isAccepted) {
                    String text = ETText.getText().toString();
                    textView.setText(text);
                }
            }
        });
    }


}
