package com.junseo.daepyeong.store;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by junse on 2017-09-03.
 */

public class BarcodePref {

    private Context context;
    private SharedPreferences barcodePref;
    private SharedPreferences.Editor barcodePrefEditor;

    public BarcodePref(Context context) {
        this.context = context;
        this.barcodePref = context.getSharedPreferences("loginInfo", context.MODE_PRIVATE);
        this.barcodePrefEditor = this.barcodePref.edit();
    }

    public void setBarcodeData(String barcode) {

        barcodePrefEditor.putString("barcode", barcode);
        barcodePrefEditor.apply();
    }

    public String getBarcodeData() {
        return barcodePref.getString("barcode", "");
    }

    public void removeBarcodeData() {
        barcodePrefEditor.remove("barcode");
        barcodePrefEditor.apply();
    }
}