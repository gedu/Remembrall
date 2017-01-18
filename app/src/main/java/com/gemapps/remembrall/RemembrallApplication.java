package com.gemapps.remembrall;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by edu on 1/11/17.
 */

public class RemembrallApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
