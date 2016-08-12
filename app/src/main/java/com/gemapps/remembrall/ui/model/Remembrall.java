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

    //TODO: remove
    public String firstName;
    public String lastName;

    private JSONArray rememberAlarms;
    public long startDate;
    public long endDate;

    private Product mProduct;

    public Remembrall(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
     * @return
     */
    public long save(Context context) {
        long id = -1;

        RemembrallSqlHelper helper = new RemembrallSqlHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        //Insert the product to the db
        long prodId = db.insert(RemembrallContract.ProductEntry.TABLE_NAME,
                null, RemembrallContract.ProductEntry.buildContentValues(this));

        //Insert all the alarms
        ContentValues[] alarmContent = RemembrallContract.RememberEntry.buildContentValues(this);
        JSONArray alarmIds = new JSONArray();

        db.beginTransaction();
        try {
            for (ContentValues cv : alarmContent) {
                long rememberId = db.insert(RemembrallContract.RememberEntry.TABLE_NAME,
                        null, cv);

                if (rememberId != -1) alarmIds.put(rememberId);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        //Insert the client
        id = db.insert(RemembrallContract.ClientEntry.TABLE_NAME,
                null, RemembrallContract.ClientEntry.buildContentValues(this));

        //TODO: get from prefs the save client into contacts boolean

        //Insert all the ids into the ClientProdRemem db


        return id;
    }
}
