package org.androidtown.delightteammodule.MATCH.BrowseMatch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewFactory;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.androidtown.delightteammodule.Connection.DataSetUpCallback;
import org.androidtown.delightteammodule.Connection.HttpUtilSend;
import org.androidtown.delightteammodule.MATCH.Items.Data.MatchCardData;
import org.androidtown.delightteammodule.MATCH.Items.GeneralData.MatchDataBase;
import org.androidtown.delightteammodule.MATCH.Items.View.MatchCardView;
import org.androidtown.delightteammodule.R;

public class BrowseMatch extends AppCompatActivity {

    EditText ETSearch;
    TextView TVSearch;

    RecyclerView RVMatchCard;
    RecyclerAdapter<MatchCardView, MatchCardData> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ETSearch = (EditText)findViewById(R.id.ETSearch);
        ETSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //글자가 변하면서 추천검색어 띄울 수 있으면 띄우기
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        TVSearch = (TextView)findViewById(R.id.TVSearch);
        TVSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerViewWithData();
            }
        });
        RVMatchCard = (RecyclerView)findViewById(R.id.RVMatchCard);
        adapter = new RecyclerAdapter<>(R.layout.item_match_card, new CustomViewFactory<MatchCardView>() {
            @Override
            public MatchCardView createViewHolder(View layout, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_card, parent, false);
                MatchCardView matchCardView = new MatchCardView(view, MatchDataBase.MATCH_SEARCH);
                return matchCardView;
            }
        });
        RVMatchCard.setAdapter(adapter);
        RVMatchCard.setItemAnimator(new DefaultItemAnimator());
        RVMatchCard.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setRecyclerViewWithData()
    {
        adapter.ClearAllData();
        // 이거 이용해서 검색한 결과를 반영하기
        /*
        HttpUtilSend send = new HttpUtilSend(new DataSetUpCallback() {
            @Override
            public void onDataReceived(String result) {
                Gson gson = new Gson();
                adapter.addItem(gson.fromJson(result, MatchCardData.class));
            }
        });
        send.execute("URL",
                "matchName",ETSearch.getText().toString());
        */
        ETSearch.setText("");
    }
}
