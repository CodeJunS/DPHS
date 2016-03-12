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
import android.view.View;
import android.widget.ImageView;
import android.support.design.widget.CollapsingToolbarLayout;

import com.junseo.dphs.meal.MealActivity;
import com.junseo.dphs.helper.SharedPreferenceManager;
import com.junseo.dphs.var.FixedVar;

public class MainActivity extends AppCompatActivity {

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
