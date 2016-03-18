package com.junseo.dphs;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * Created by Junseo on 16. 3. 19..
 */
public class Setting extends PreferenceActivity implements Preference.OnPreferenceClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        Preference box1 = (Preference)findPreference("setting_1_1");
        box1.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        switch(preference.getKey()) {
            case "setting_1_1":
                Toast.makeText(this,"box1 checked" ,Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

}

