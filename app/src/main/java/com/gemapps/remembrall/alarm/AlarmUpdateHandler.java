package com.gemapps.remembrall.alarm;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.gemapps.remembrall.ui.model.Job;
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

    public void addAlarmAsync(final Job job){

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(job);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                Log.d(TAG, "onSuccess");
                AlarmStateManager.getInstance()
                        .registerAlarm(mContext, job.getId(), job.getDeliveries());
                //todo: new way to post an event creation
                new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                    EventBus.getDefault().post(new DbTransaction(DbTransaction.SAVE, job));
                  }
                }, 500);
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
