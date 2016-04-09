package com.junseo.dphs.parse;

import android.content.Context;
import android.util.Log;

import com.junseo.dphs.data.NoticeBBSData;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gomsang on 2016-04-09.
 */
public class DataParser {

    Context context;

    public DataParser(Context context) {
        this.context = context;
    }

    public ArrayList<NoticeBBSData> getNoticeDatas() {
        ArrayList<NoticeBBSData> noticeBBSDatas = new ArrayList<>();

        try {
            Source source = new Source(new HtmlParser("http://daepyeong.hs.kr/notice.brd?").execute().get());
            Element contentElement = (Element) source.getAllElements(HTMLElementName.SCRIPT).get(3);
            Pattern pattern = Pattern.compile("var Posts=(.*?)];");
            Matcher matcher = pattern.matcher(contentElement.getContent());

            String jsonStr = null;
            while (matcher.find()) {
                jsonStr = matcher.group(1) + "]";
            }

            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 1; i < jsonArray.length(); i++) {
                JSONArray article = jsonArray.getJSONArray(i);
                NoticeBBSData nD = new NoticeBBSData();
                nD.setTitle(article.getString(6));
                nD.setDate(article.getJSONArray(2).getString(0));
                nD.setHit(article.getInt(4));
                nD.setWriter(article.getJSONArray(5).getString(1));
                nD.setUrl(article.getJSONArray(0).getString(2));
                noticeBBSDatas.add(nD);
                Log.d("noticeData", "제목 : " + nD.getTitle() + " 날짜 : " + nD.getDate() + " 조회수 : " + nD.getHit() + " 작성자 : " + nD.getWriter() + " 링크 : " + nD.getUrl());
            }

        } catch (Exception e) {
            Log.d("parseError", e + "");
        }
        return noticeBBSDatas;
    }
}
