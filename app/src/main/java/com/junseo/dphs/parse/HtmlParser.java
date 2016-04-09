package com.junseo.dphs.parse;

import android.os.AsyncTask;

import net.htmlparser.jericho.Source;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Gomsang on 2016-04-09.
 * URL 을 입력하면 소스를 리턴하는 클래스.
 */
public class HtmlParser extends AsyncTask<String, String, String> {

    private String PARSE_URL;
    private Source source;
    private URL URL;

    public HtmlParser(String PARSE_URL){
        this.PARSE_URL = PARSE_URL;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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

            return html;
        }catch (Exception e){

        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

}
