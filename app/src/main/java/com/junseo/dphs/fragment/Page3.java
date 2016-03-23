package com.junseo.dphs.fragment;

/**
 * Created by Junseo on 16. 3. 18..
 */
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.junseo.dphs.R;

public class Page3 extends Fragment {
    public View view;

    private Context context;

    public Page3(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.school_info_3, container, false);


        // FloatingActionsMenu rightLabels = (FloatingActionsMenu) view.findViewById(R.id.htc);

        view.findViewById(R.id.mapCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=대평고등학교");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
//                Uri uri = Uri.parse("https://www.google.com/maps/place/%EB%AA%85%EC%9D%B8%EC%A4%91%ED%95%99%EA%B5%90/@37.2918464,126.9908149,17z/data=!3m1!4b1!4m2!3m1!1s0x357b42d4bf771ee3:0x6df4316f07df23bf?hl=ko");
//                Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                it.setPackage("com.google.android.apps.maps");
//                startActivity(it);
            }
        });

       /* view.findViewById(R.id.action_htc2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:070-8620-4560"));
                startActivity(intent);
            }
        }); */


        /*FloatingActionButton addedTwice = new FloatingActionButton(context);
        addedTwice.setTitle("Added twice");
        rightLabels.addButton(addedTwice);
        rightLabels.removeButton(addedTwice);
        rightLabels.addButton(addedTwice);*/

        return view;
    }
}
