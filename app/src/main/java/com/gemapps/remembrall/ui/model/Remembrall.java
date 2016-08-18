package com.gemapps.remembrall.ui.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gemapps.remembrall.data.RemembrallContract;
import com.gemapps.remembrall.data.RemembrallSqlHelper;

import org.json.JSONArray;

/**
 * Created by edu on 7/19/16.
 */

public class Remembrall {

    private static final String TAG = "Remembrall";

    private Client mClient;
    private Product mProduct;
    private JSONArray rememberAlarms;

    public Remembrall(String firstName, String lastName) {
        mClient = new Client(firstName, lastName);
    }

    public Remembrall(String firstName, String lastName, String idCard, String address,
                      String email, String homePhone, String mobilePhone, JSONArray alarms,
                      String equipLabel, String equipNum, String testerNum, String terminalNum,
                      String price, String description) {

        this.mClient = new Client(firstName, lastName, idCard, address, email, homePhone, mobilePhone);
        this.rememberAlarms = alarms;
        this.mProduct = new Product(equipLabel, equipNum, testerNum, terminalNum, price, description);
    }

    public Product getProduct() {
        return mProduct;
    }

    public Client getClient() {
        return mClient;
    }

    public JSONArray getRememberAlarms() {
        return rememberAlarms;
    }

    /**
     * Save the current obj in the db
     *
     * @return true if the saving went ok, otherwise false
     */
    public boolean save(Context context) {

        RemembrallSqlHelper helper = new RemembrallSqlHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        //Insert the client
        long clientId = db.insert(RemembrallContract.ClientEntry.TABLE_NAME,
                null, RemembrallContract.ClientEntry.buildContentValues(this));

        //Insert the product to the db
        long prodId = db.insert(RemembrallContract.ProductEntry.TABLE_NAME,
                null, RemembrallContract.ProductEntry.buildContentValues(this));

        //Insert all the alarms
        //Insert all the ids into the ClientProdRemem db
        ContentValues[] alarmContent = RemembrallContract.AlarmEntry.buildContentValues(this);

        db.beginTransaction();
        try {
            for (ContentValues cv : alarmContent) {
                long rememberId = db.insert(RemembrallContract.AlarmEntry.TABLE_NAME,
                        null, cv);

                if (rememberId != -1) {
                    ContentValues contentValues = RemembrallContract.ClientProdEntry
                            .buildContentValues(clientId, prodId);
                    db.insert(RemembrallContract.ClientProdEntry.TABLE_NAME, null, contentValues);
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        //TODO: get from prefs the save client into contacts boolean

        //TODO: I should check if any alarm fail and tell the user to try again
        return (clientId != -1 && prodId != -1);
    }
}
