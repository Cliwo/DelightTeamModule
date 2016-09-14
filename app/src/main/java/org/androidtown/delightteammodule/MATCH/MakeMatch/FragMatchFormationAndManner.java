package org.androidtown.delightteammodule.MATCH.MakeMatch;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.androidtown.delightteammodule.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragMatchFormationAndManner extends Fragment implements RadioButton.OnClickListener {

    static boolean isCompleted=true;

    public RadioButton[] radioButtons = new RadioButton[5];
    public Spinner SPMatchFormation;
    public Spinner SPMatchFormationText;
    public SeekBar SBManner;
    public TextView TVProgress;
    public FragMatchFormationAndManner() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_match_formation_and_manner, container, false);
        setViews(view);

        return view;
    }

    public void setViews(View view)
    {
        setSpinners(view);
        setRadioButtons(view);
        SBManner = (SeekBar)view.findViewById(R.id.SBManner);
        TVProgress = (TextView)view.findViewById(R.id.TVProgress);
        SBManner.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TVProgress.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MakeMatch.data.matchManner = seekBar.getProgress()+"";
            }
        });
    }

    @Override
    public void onClick(View v) {
        RadioButton selectedItem = (RadioButton)v;
        for(int i =0;i<5;i++)
        {
            if(radioButtons[i] == v) {
                MakeMatch.data.matchLevelIntValue = i;
                break;
            }
        }
        MakeMatch.data.matchLevel=selectedItem.getText().toString();
        Log.d("RadioButton", "난이도 설정 : "+MakeMatch.data.matchLevel);
    }
    public void setRadioButtons(View view)
    {
        radioButtons[0] = (RadioButton)view.findViewById(R.id.radioButton0);
        radioButtons[1] = (RadioButton)view.findViewById(R.id.radioButton1);
        radioButtons[2] = (RadioButton)view.findViewById(R.id.radioButton2);
        radioButtons[3] = (RadioButton)view.findViewById(R.id.radioButton3);
        radioButtons[4] = (RadioButton)view.findViewById(R.id.radioButton4);
        radioButtons[0].setOnClickListener(this);
        radioButtons[1].setOnClickListener(this);
        radioButtons[2].setOnClickListener(this);
        radioButtons[3].setOnClickListener(this);
        radioButtons[4].setOnClickListener(this);
    }
    public void setSpinners(View view)
    {
        SPMatchFormation=(Spinner)view.findViewById(R.id.SPMatchFormation);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_settings, new String[]{"3 vs 3", "6 vs 6","11 vs 11" }) {

            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                return v;
            }
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;
            }
        };
        SPMatchFormation.setAdapter(adapter);
        SPMatchFormation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MakeMatch.data.matchFormation = parent.getItemAtPosition(position).toString();
                MakeMatch.data.matchFormationIntValue = position;
                Log.d("SpinnerSelected", "스피너에서 값이 선택됨 : "+MakeMatch.data.matchFormation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SPMatchFormationText=(Spinner)view.findViewById(R.id.SPMatchFormationText);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_settings, new String[]{"풋살", "축구" }) {

            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                return v;
            }
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;
            }
        };
        SPMatchFormationText.setAdapter(adapter2);
        SPMatchFormationText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MakeMatch.data.matchFormationText = parent.getItemAtPosition(position).toString();
                Log.d("SpinnerSelected", "스피너(Text)에서 값이 선택됨 : "+MakeMatch.data.matchFormationText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public boolean checkCompleted()
    {
        return isCompleted;
    }

}
