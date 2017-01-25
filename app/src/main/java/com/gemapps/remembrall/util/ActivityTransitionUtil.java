package com.gemapps.remembrall.util;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Window;

/**
 * Created by edu on 1/25/17.
 */

public class ActivityTransitionUtil {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setFadeTransition(Window window){
        Transition fade = new Fade();

        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        window.setExitTransition(fade);
        window.setEnterTransition(fade);
    }
}
