package com.junseo.daepyeong.parse;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import com.junseo.daepyeong.adapter.NoticeBBSAdapter;
import com.junseo.daepyeong.data.NoticeBBSData;
import com.junseo.daepyeong.var.FixedVar;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gomsang on 2016-04-09.
 * 모든 html 파싱 담당
 */

public class HtmlParser extends AsyncTask<String, String, String> {

    private String PARSE_URL;
    private int mod;

    private Helper helper;
    private ProgressDialog progressDialog;

    private Context context;

    public HtmlParser(Context context,  NoticeBBSAdapter noticeBBSAdapter){
        this.context = context;
        helper = new Helper(0);
        helper.init(noticeBBSAdapter);
        PARSE_URL = FixedVar.HP_ROOT + FixedVar.MODULE_NOTICE + "/" + noticeBBSAdapter.getNowPage();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "",
                "정보를 가져오는 중입니다.", true);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(PARSE_URL);
            HttpResponse response = client.execute(request);

            String html = "";
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
                while((line = reader.readLine()) != null)
            {
                str.append(line);
            }
            in.close();
            html = str.toString();

             helper.analyze(new Source(html));

            return html;
        }catch (Exception e){
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        helper.sync();
        progressDialog.dismiss();
    }

    public class Helper{

        private NoticeBBSAdapter noticeBBSAdapter;
        private int mod;

        public Helper(int mod){
            this.mod = mod;
            // 예 ) 헬퍼의 모드 0번은 공지사항 파싱
        }

        public void init(Object object){
            switch (mod){
                case 0:
                    noticeBBSAdapter = (NoticeBBSAdapter) object;
                    break;
            }
        }

        public void analyze(final Source source){
            switch (mod){
                case 0:
                    try {
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
                            Log.e("이거", article.getJSONArray(0).getString(1));
                            NoticeBBSData nD = new NoticeBBSData();

                            if (Objects.equals(article.getJSONArray(0).getString(1), ":not:tic:")) {
                                nD.setNotice("[공지]");
                            } else {
                                nD.setNotice("");
                            }

                            nD.setTitle(article.getString(6));
                            nD.setDate(article.getJSONArray(2).getString(0));
                            nD.setHit(article.getInt(4));
                            nD.setWriter(article.getJSONArray(5).getString(1));
                            nD.setUrl(article.getJSONArray(0).getString(2));
                            noticeBBSAdapter.addItem(nD);
                            Log.d("noticeData", "제목 : " + nD.getTitle() + " 날짜 : " + nD.getDate() + " 조회수 : " + nD.getHit() + " 작성자 : " + nD.getWriter() + " 링크 : " + nD.getUrl());
                        }

                    }catch (Exception e){

                    }
                    break;
            }
        }

        public void sync(){
            switch (mod){
                case 0:
                    noticeBBSAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
