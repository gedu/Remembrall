package com.gemapps.remembrall;

import android.app.Application;

import com.gemapps.remembrall.migration.RemembrallMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by edu on 1/11/17.
 */

public class RemembrallApplication extends Application {

  private static final int REAL_VERSION = 2;

  @Override
  public void onCreate() {
    super.onCreate();

    Realm.init(this);

    RealmConfiguration configuration = new RealmConfiguration.Builder()
        .migration(new RemembrallMigration())
        .schemaVersion(REAL_VERSION).build();

    Realm.setDefaultConfiguration(configuration);
  }
}
