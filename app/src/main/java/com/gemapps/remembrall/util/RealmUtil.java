package com.gemapps.remembrall.util;

import com.gemapps.remembrall.data.RemembrallContract;
import com.gemapps.remembrall.ui.model.Delivery;
import com.gemapps.remembrall.ui.model.Remembrall;

import io.realm.Realm;

/**
 * Created by edu on 2/1/17.
 */

public class RealmUtil {

    public static void deleteRemembralls(Realm realm, final String id){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Remembrall remToDelete = realm.where(Remembrall.class)
                        .equalTo(RemembrallContract.ClientProdEntry.COLUMN_ID, id)
                        .findFirst();
                for (Delivery d : remToDelete.getDeliveries())
                    d.deleteFromRealm();
                remToDelete.deleteFromRealm();
            }
        });
    }
}
