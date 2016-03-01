package com.junseo.dphs.helper;

/**
 * Created by Junseo on 16. 2. 28..
 */
import android.content.Context;
import android.content.SharedPreferences;

import com.junseo.dphs.var.FixedVar;

/**
 * Original Source by Gomsang, GyungRok KIM.
 */
public class SharedPreferenceManager {

    private Context context;

    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    public SharedPreferenceManager(Context context, int mod) {
        this.context = context;
        changePref(mod);
    }
    public void changePref(int mod) {
        switch (mod) {
            case FixedVar.PREF_INTRO:
                pref = context.getSharedPreferences("intro", context.MODE_PRIVATE);
        }


        prefEditor = pref.edit();
    }

    public void setIntroPref(boolean value) {
        prefEditor.putBoolean("introperm", value);
        prefEditor.apply();
    }

    public boolean getIntroPref() {
        return pref.getBoolean("introperm", true);
    }

}