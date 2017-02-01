package com.gemapps.remembrall.util;

import com.gemapps.remembrall.data.RemembrallContract;
import com.gemapps.remembrall.ui.model.Delivery;
import com.gemapps.remembrall.ui.model.Job;

import io.realm.Realm;

/**
 * Created by edu on 2/1/17.
 */

public class RealmUtil {

    public static void deleteJobs(Realm realm, final String id){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Job jobToDelete = realm.where(Job.class)
                        .equalTo(RemembrallContract.JobEntry.COLUMN_ID, id)
                        .findFirst();
                for (Delivery d : jobToDelete.getDeliveries()) d.deleteFromRealm();
                jobToDelete.deleteFromRealm();
            }
        });
    }
}
