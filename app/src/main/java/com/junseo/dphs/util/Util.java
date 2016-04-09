package com.junseo.dphs.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Gomsang on 2016-04-09.
 */
public class Util {
    public Util(){

    }

    public static void openBroswer(Context context, String URL) {
        Uri uri = Uri.parse(URL);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }
}
