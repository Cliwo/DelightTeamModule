package org.androidtown.delightteammodule.ChanRecycleAdapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Chan on 2016-03-22.
 */
public class RecyclerAdapter<T extends CustomViewHolder, D extends AdapterItemData> extends RecyclerView.Adapter<T> {
    //T에 뷰어를, D에 데이터를 넣어주면 어떤 어댑터도 한 방에 만든다.

    protected final int layoutID;
    private final CustomViewFactory<T> factory;
    ArrayList<D> items = new ArrayList<D>();
    Comparator<D> sortWay = null;

    public RecyclerAdapter(int layoutID, CustomViewFactory<T> factory) {
        this.layoutID=layoutID;
        this.factory=factory;
        //factory를 외부에서 넣어주어야함.
    }

    //ex) RecyclerAdapter<Item_MatchView, Item_MatchData>
    //    (R.id.item_matchview, new CustomViewFactory<Item_MatchView> factory { ... });
    //    이런식으로
    //    ...내에는 Item_MatchView를 만들어주는 코드 (객체를 생성해서 반환)

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = View.inflate(parent.getContext(), layoutID, null);
        T view= factory.createViewHolder(layout, parent);
        return view;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        holder.setContents(items.get(position));    //에러안나요!
    }

    public RecyclerAdapter addItem(D data)
    {
        items.add(data);
        notifyDataSetChanged();
        return this;
    }

    public RecyclerAdapter addItemAtTop(D data)
    {
        items.add(0,data);
        notifyDataSetChanged();
        return this;
    }

    public void ClearAllData()
    {
        items.clear();
    }

    public void setSortWay(Comparator<D> way)
    {
        sortWay=way;
    }
    public void sortItems()
    {
        if(sortWay==null) {
            Log.d("RecyclerAdapter", "sortWay is null");
            return;
        }
        Collections.sort(items, sortWay);
        notifyDataSetChanged();
    }
}