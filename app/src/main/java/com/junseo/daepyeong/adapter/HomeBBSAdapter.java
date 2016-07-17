package com.junseo.daepyeong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.junseo.daepyeong.R;
import com.junseo.daepyeong.data.HomeBBSData;

import java.util.ArrayList;

/**
 * Created by Junseo on 2016-05-20.
 */
public class HomeBBSAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HomeBBSData> nbds = new ArrayList<>();
    private int nowPage = 0;

    public HomeBBSAdapter(Context context) {
        this.context = context;
        this.nbds = nbds;
    }

    @Override
    public int getCount() {
        return nbds.size();
    }

    @Override
    public HomeBBSData getItem(int position) {
        return nbds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.type_bbs, null);

        HomeBBSData nD = nbds.get(position);
        TextView title = (TextView) v.findViewById(R.id.item_title);
        TextView date = (TextView) v.findViewById(R.id.item_date);
        TextView hit = (TextView) v.findViewById(R.id.item_hit);
        TextView writer = (TextView) v.findViewById(R.id.item_writer);

        title.setText(nD.getTitle());
        date.setText(nD.getDate());
        hit.setText("조회수: " + nD.getHit());
        writer.setText("작성자: " + nD.getWriter() + " 선생님");
        return v;
    }

    public int getNowPage() {
        nowPage += 1;
        return nowPage;
    }

    public void addItem(HomeBBSData nD) {
        nbds.add(nD);
    }
}
