package com.gemapps.remembrall.migration;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by eduardo.graciano on 12/26/17.
 */

public class RemembrallMigration implements RealmMigration {

  @Override
  public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

    if (oldVersion == 2) migrateSignAddition(realm);
  }

  /**
   * I added back the sign bitmap
   */
  private void migrateSignAddition(DynamicRealm realm) {
    RealmSchema schema = realm.getSchema();

    RealmObjectSchema clientSchema = schema.get("Client");

    if (!clientSchema.hasField("mSignImage")) {
      clientSchema.addField("mSignImage", byte[].class)
          .transform(new RealmObjectSchema.Function() {
            @Override
            public void apply(DynamicRealmObject obj) {
              obj.set("mSignImage", new byte[0]);
            }
          });
    } else {
      clientSchema.setNullable("mSignImage", true);
    }
  }
}
