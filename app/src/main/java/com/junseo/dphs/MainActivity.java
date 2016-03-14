package com.junseo.dphs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.TextView;

import com.junseo.dphs.meal.MealActivity;
import com.junseo.dphs.helper.SharedPreferenceManager;
import com.junseo.dphs.var.FixedVar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView ddayText;
    private TextView resultText;

    private int dYear=2016;        //디데이 연월일 변수
    private int dMonth=4;
    private int dDay=15;

    private int tYear;           //오늘 연월일 변수
    private int tMonth;
    private int tDay;

    private long d;
    private long t;
    private long r;

    private int resultNumber=0;

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
        }else {
        }

        CardView cardView = (CardView) findViewById(R.id.facebook_cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://messaging/765784586792493"));
                startActivity(intent);
            }
        });

        CardView cardView3 = (CardView) findViewById(R.id.meal_cardView);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MealActivity.class);
                startActivity(intent);
            }
        });

        ddayText=(TextView)findViewById(R.id.ddayText);
        resultText=(TextView)findViewById(R.id.resultText);

        Calendar calendar =Calendar.getInstance();              //현재 날짜 불러옴
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH);
        tDay = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar dCalendar =Calendar.getInstance();
        dCalendar.set(dYear, dMonth - 1, dDay);

        t=calendar.getTimeInMillis();                 //오늘 날짜를 밀리타임으로 바꿈
        d=dCalendar.getTimeInMillis();              //디데이날짜를 밀리타임으로 바꿈
        Log.d("daylog", "current time : "+ t +" set time : " + d);
        r=(d-t)/(24*60*60*1000);                 //디데이 날짜에서 오늘 날짜를 뺀 값을 '일'단위로 바꿈
        Log.d("daylog", "so day : " + r );

        resultNumber=(int)r;
        updateDisplay();
    }

    private void updateDisplay(){

        ddayText.setText(String.format("%d년 %d월 %d일",dYear, dMonth, dDay));

        if(resultNumber>=0){
            resultText.setText(String.format("D-%d", resultNumber));
        }
        else{
            int absR=Math.abs(resultNumber);
            resultText.setText(String.format("D+%d", absR));
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
}
