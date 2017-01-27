package com.gemapps.remembrall.alarm;

import android.content.Context;
import android.util.Log;

import com.gemapps.remembrall.ui.model.Remembrall;
import com.gemapps.remembrall.ui.model.bus.DbTransaction;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;

/**
 * Created by edu on 1/27/17.
 */

public class AlarmUpdateHandler {

    private static final String TAG = "AlarmUpdateHandler";

    private Context mContext;

    public AlarmUpdateHandler(Context context) {
        mContext = context;
    }

    public void addAlarmAsync(final Remembrall remembrall){

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(remembrall.getClient());
                realm.copyToRealm(remembrall.getProduct());
                realm.copyToRealm(remembrall);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                Log.d(TAG, "onSuccess");
                AlarmStateManager.getInstance().registerAlarm(mContext, remembrall.getDeliveries());
                EventBus.getDefault().post(new DbTransaction(DbTransaction.SAVE));
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                //TODO: add a try again
                Log.w(TAG, "onError", error);
                EventBus.getDefault().post(new DbTransaction(DbTransaction.ERROR));
            }
        });

        realm.close();
    }
}
