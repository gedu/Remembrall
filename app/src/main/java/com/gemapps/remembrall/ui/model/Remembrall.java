package com.gemapps.remembrall.ui.model;

import android.util.Log;

import com.gemapps.remembrall.ui.model.bus.DbTransaction;
import com.gemapps.remembrall.ui.widget.FormUIHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by edu on 7/19/16.
 */

public class Remembrall extends RealmObject {

    private static final String TAG = "Remembrall";

    @PrimaryKey
    private String mId;
    private Client mClient;
    private Product mProduct;
    private RealmList<RememberAlarm> mRememberAlarms;

    public Remembrall() {}

    public Remembrall(FormUIHandler form, RealmList<RememberAlarm> alarms){

        this.mClient = form.buildClient();
        this.mRememberAlarms = alarms;
        this.mProduct = form.buildProduct();
        setPrimaryKey();
    }

    public Remembrall(String firstName, String lastName, String idCard, String address,
                      String email, String homePhone, String mobilePhone, RealmList<RememberAlarm> alarms,
                      String equipLabel, String equipNum, String testerNum, String terminalNum,
                      String price, String description, byte[] signImage) {

        this.mClient = new Client(firstName, lastName, idCard, address, email, homePhone, mobilePhone, signImage);
        this.mRememberAlarms = alarms;
        this.mProduct = new Product(equipLabel, equipNum, testerNum, terminalNum, price, description);
        setPrimaryKey();
    }

    private void setPrimaryKey(){
        if(mClient != null) mId = mClient.getIdCard();
    }

    public Product getProduct() {
        return mProduct;
    }

    public Client getClient() {
        return mClient;
    }

    public String getId(){
        return mId;
    }

    public List<RememberAlarm> getRememberAlarms() {
        return mRememberAlarms;
    }

    /**
     * Save the current obj in the Realm db
     */
    public void save() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(mClient);
                realm.copyToRealm(mProduct);
                realm.copyToRealm(mRememberAlarms);
                realm.copyToRealm(Remembrall.this);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                Log.d(TAG, "onSuccess");
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
