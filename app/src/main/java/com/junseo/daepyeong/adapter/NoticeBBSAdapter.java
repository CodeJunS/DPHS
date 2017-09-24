package com.junseo.daepyeong.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.junseo.daepyeong.data.NoticeBBSData;
import com.junseo.daepyeong.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Gomsang on 2016-04-09.
 */
public class NoticeBBSAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NoticeBBSData> nbds = new ArrayList<>();
    private int nowPage = 0;

    public NoticeBBSAdapter(Context context) {
        this.context = context;
        this.nbds = nbds;
    }

    @Override
    public int getCount() {
        return nbds.size();
    }

    @Override
    public NoticeBBSData getItem(int position) {
        return nbds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.type_bbs, null);

        NoticeBBSData nD = nbds.get(position);
        TextView title = v.findViewById(R.id.item_title);
        TextView date = v.findViewById(R.id.item_date);
        TextView hit = v.findViewById(R.id.item_hit);
        TextView writer = v.findViewById(R.id.item_writer);

        if (Objects.equals(nD.getNotice(), "")) {
            title.setText(nD.getTitle());
        } else {
            String styledText = "<font color='red'>[공지] </font>" + nD.getTitle();
            title.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
        }
        date.setText(nD.getDate());
        hit.setText("조회수: " + nD.getHit());
        writer.setText("작성자: " + nD.getWriter() + " 선생님");
        return v;
    }

    public int getNowPage() {
        nowPage += 1;
        return nowPage;
    }

    public void addItem(NoticeBBSData nD) {
        nbds.add(nD);
    }
}
