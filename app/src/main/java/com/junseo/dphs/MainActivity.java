package com.junseo.dphs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.junseo.dphs.helper.SharedPreferenceManager;
import com.junseo.dphs.info.School_Info;
import com.junseo.dphs.var.FixedVar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView ddayText;
    private TextView resultText;

    private int dYear = 2016;        //디데이 연월일 변수
    private int dMonth = 11;
    private int dDay = 17;

    private int tYear;           //오늘 연월일 변수
    private int tMonth;
    private int tDay;

    private long d;
    private long t;
    private long r;

    private int resultNumber = 0;

    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.image);
        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        setPalette();

        final SharedPreferenceManager spm2 = new SharedPreferenceManager(MainActivity.this, FixedVar.PREF_INTRO);
        if (spm2.getIntroPref()) {
            startActivity(new Intent(MainActivity.this, IntroActivity.class));
            finish();
        } else {
        }

        CardView cardView = (CardView) findViewById(R.id.facebook_cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.facebook_cardView:
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://messaging/765784586792493"));
                            startActivity(intent);
                            break;
                        } catch (Exception e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.facebook.katana")));
                            break;
                        }
                    default:
                        break;
                }
            }
        });

        CardView cardView3 = (CardView) findViewById(R.id.meal_cardView);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.junseo.dphs.meal.MealActivity.class);
                startActivity(intent);
            }
        });

        CardView cardView4 = (CardView) findViewById(R.id.schoolEx_cardView);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.daepyeong.hs.kr");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
            }
        });

        CardView cardView5 = (CardView) findViewById(R.id.school_info_cardView);
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, School_Info.class);
                startActivity(intent2);
            }
        });

        CardView cardView6 = (CardView) findViewById(R.id.notice_CardView);
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(intent2);
            }
        });

        CardView cardView7 = (CardView) findViewById(R.id.home_CardView);
        cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent3);
            }
        });

        CardView cardView8 = (CardView) findViewById(R.id.suneung_cardView);
        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "2016학년도 수능: 2016.11.17\n         D- " +resultNumber, Toast.LENGTH_LONG).show();
            }
        });

        ddayText = (TextView) findViewById(R.id.ddayText);
        resultText = (TextView) findViewById(R.id.resultText);

        Calendar calendar = Calendar.getInstance();              //현재 날짜 불러옴
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH);
        tDay = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar dCalendar = Calendar.getInstance();
        dCalendar.set(dYear, dMonth - 1, dDay);

        t = calendar.getTimeInMillis();                 //오늘 날짜를 밀리타임으로 바꿈
        d = dCalendar.getTimeInMillis();              //디데이날짜를 밀리타임으로 바꿈
        Log.d("daylog", "current time : " + t + " set time : " + d);
        r = (d - t) / (24 * 60 * 60 * 1000);                 //디데이 날짜에서 오늘 날짜를 뺀 값을 '일'단위로 바꿈
        Log.d("daylog", "so day : " + r);

        resultNumber = (int) r;
        updateDisplay();
    }

    private void updateDisplay() {

        ddayText.setText(String.format("%d년 %d월 %d일", dYear, dMonth, dDay));

        if (resultNumber >= 0) {
            resultText.setText(String.format("D-%d", resultNumber));
        } else {
            int absR = Math.abs(resultNumber);
            resultText.setText(String.format("D+%d", absR));
        }
        if (resultNumber == 0) {
            resultText.setText(String.format("D-DAY", resultNumber));
        }
    }

    private void setPalette() {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                int primary = getResources().getColor(R.color.colorPrimary);
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            Intent intent = new Intent(MainActivity.this, Setting.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
