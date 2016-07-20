package com.gemapps.remembrall.util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by edu on 7/20/16.
 */

public class Util {

    /**
     * Helper method to determine if the device has an large screen. For
     * example, 7" tablets are large.
     */
    public static boolean isLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
