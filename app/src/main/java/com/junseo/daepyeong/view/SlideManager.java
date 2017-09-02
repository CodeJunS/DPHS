package com.junseo.daepyeong.view;

/**
 * Created by Junseo on 16. 2. 27..
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SlideManager extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    public static SlideManager newInstance(int layoutResId) {
        SlideManager slideManager = new SlideManager();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        slideManager.setArguments(args);

        return slideManager;
    }

    private int layoutResId;

    public SlideManager() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutResId, container, false);
    }

}