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


        /* FloatingActionsMenu rightLabels = (FloatingActionsMenu) view.findViewById(R.id.pci);

        view.findViewById(R.id.action_pci1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:070-8620-4500"));
                startActivity(intent);
            }
        });
        view.findViewById(R.id.action_pci2).setOnClickListener(new View.OnClickListener() {
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