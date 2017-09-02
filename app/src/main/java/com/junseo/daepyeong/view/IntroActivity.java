package com.junseo.daepyeong.view;

/**
 * Created by Junseo on 16. 2. 27..
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro2;
import com.junseo.daepyeong.helper.SharedPreferenceManager;
import com.junseo.daepyeong.var.FixedVar;
import com.junseo.daepyeong.R;

public class IntroActivity extends AppIntro2 {
    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(SlideManager.newInstance(R.layout.intro));
        addSlide(SlideManager.newInstance(R.layout.intro2));
        addSlide(SlideManager.newInstance(R.layout.intro3));
        addSlide(SlideManager.newInstance(R.layout.intro4));

        setFlowAnimation();
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNextPressed() {
    }


    @Override
    public void onDonePressed() {
        final SharedPreferenceManager spm = new SharedPreferenceManager(IntroActivity.this, FixedVar.PREF_INTRO);//ReviewPrefManager로 다시 요청 다이얼로그가 안 뜨게 설정
        spm.setIntroPref(false);

        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {
    }

    public void getStarted(View v){
        loadMainActivity();
    }
}