package com.junseo.daepyeong.fragment;

/**
 * Created by Junseo on 16. 3. 18..
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junseo.daepyeong.R;

@SuppressLint("ValidFragment")
public class Page3 extends Fragment {
    public Page3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.school_info_3, null);

        view.findViewById(R.id.mapCard).setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("geo:0,0?q=대평고등학교");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


        return view;
    }

}