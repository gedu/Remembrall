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

                jobToDelete.getDeliveries().deleteAllFromRealm();
                jobToDelete.deleteFromRealm();
            }
        });
    }

    public static void deleteDelivery(Realm realm, final String jobId, final int deliveryId){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Job jobToDelete = realm.where(Job.class)
                        .equalTo(RemembrallContract.JobEntry.COLUMN_ID, jobId)
                        .findFirst();

                Delivery deliveryToDelete = jobToDelete.getDeliveries().where()
                    .equalTo(RemembrallContract.DeliveryEntry.COLUMN_ID, deliveryId)
                    .findFirst();

                deliveryToDelete.deleteFromRealm();
            }
        });
    }
}
