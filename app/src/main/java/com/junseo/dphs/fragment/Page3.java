package com.junseo.dphs.fragment;

/**
 * Created by Junseo on 16. 3. 18..
 */
import android.annotation.SuppressLint;
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

@SuppressLint("ValidFragment")
public class Page3 extends Fragment {
    Context mContext;

    public Page3(Context context) {
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.school_info_3, null);

        return view;
    }

}