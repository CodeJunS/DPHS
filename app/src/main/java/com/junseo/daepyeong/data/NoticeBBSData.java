package com.junseo.daepyeong.data;

/**
 * Created by Gomsang on 2016-04-09.
 */
public class NoticeBBSData {

    private String notice;
    private String title;
    private String writer;
    private String date;
    private String url;
    private int hit;

    public NoticeBBSData (){

    }

    public String getNotice() {
        return  notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }
}
