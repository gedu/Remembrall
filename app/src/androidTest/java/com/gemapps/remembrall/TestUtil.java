package com.gemapps.remembrall;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.gemapps.remembrall.data.RemembrallContract;

/**
 * Created by edu on 7/27/16.
 */

public class TestUtil {

    public static final SQLiteQueryBuilder mClientProdAlarmQueryBuilder;

    static{
        mClientProdAlarmQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //client INNER JOIN clientProdRem ON client.location_id = clientProdRem._id
        // AND product INNER JOIN clientProdRem ON product.location_id = clientProdRem._id
        mClientProdAlarmQueryBuilder.setTables(
                RemembrallContract.ClientEntry.TABLE_NAME + " INNER JOIN " +
                        RemembrallContract.ClientProdEntry.TABLE_NAME +
                        " ON " + RemembrallContract.ClientEntry.TABLE_NAME +
                        "." + RemembrallContract.ClientEntry._ID +
                        " = " + RemembrallContract.ClientProdEntry.TABLE_NAME +
                        "." + RemembrallContract.ClientProdEntry.COLUMN_CLIENT_ID +
                        " INNER JOIN " +
                        RemembrallContract.ProductEntry.TABLE_NAME  +
                        " ON " + RemembrallContract.ProductEntry.TABLE_NAME +
                        "." + RemembrallContract.ProductEntry._ID +
                        " = " + RemembrallContract.ClientProdEntry.TABLE_NAME +
                        "." + RemembrallContract.ClientProdEntry.COLUMN_PRODUCT_ID
        );
    }

    //TODO: improve query, duplicates ids
    public static final SQLiteQueryBuilder mCompleteQueryBuilder;
    static{
        mCompleteQueryBuilder = new SQLiteQueryBuilder();

        mCompleteQueryBuilder.setTables(
                RemembrallContract.AlarmEntry.TABLE_NAME + " INNER JOIN " +
                        RemembrallContract.ClientProdEntry.TABLE_NAME +
                        " ON " + RemembrallContract.AlarmEntry.ALARM_ID +
                        " = " + RemembrallContract.ClientProdEntry.CLI_PRO_ID + " INNER JOIN " +
                        RemembrallContract.ClientEntry.TABLE_NAME +
                        " ON " + RemembrallContract.ClientEntry.CLIENT_ID +
                        " = " + RemembrallContract.ClientProdEntry.COLUMN_CLIENT_ID + " INNER JOIN " +
                        RemembrallContract.ProductEntry.TABLE_NAME +
                        " ON " + RemembrallContract.ProductEntry.PRODUCT_ID +
                        " = " + RemembrallContract.ClientProdEntry.COLUMN_PRODUCT_ID

        );
    }

    public static ContentValues createProductValues(String label){
        ContentValues contentValues = new ContentValues();

        contentValues.put(RemembrallContract.ProductEntry.COLUMN_LABEL, label);
        contentValues.put(RemembrallContract.ProductEntry.COLUMN_PRODUCT_NUM, "00000000");
        contentValues.put(RemembrallContract.ProductEntry.COLUMN_TESTER_NUM, "11111111");
        contentValues.put(RemembrallContract.ProductEntry.COLUMN_TERMINAL_NUM, "00000000");
        contentValues.put(RemembrallContract.ProductEntry.COLUMN_PRICE, "111");
        contentValues.put(RemembrallContract.ProductEntry.COLUMN_DESCRIPTION, "Nice model");

        return contentValues;
    }

    public static ContentValues createRememberValues(long clientProdId){
        ContentValues contentValues = new ContentValues();

        contentValues.put(RemembrallContract.AlarmEntry.COLUMN_LABEL, "Call client");
        contentValues.put(RemembrallContract.AlarmEntry.COLUMN_DESCRIPTION, "Call John again");
        contentValues.put(RemembrallContract.AlarmEntry.COLUMN_START_DATE, 12345678);
        contentValues.put(RemembrallContract.AlarmEntry.COLUMN_END_DATE, 22345678);
        contentValues.put(RemembrallContract.AlarmEntry.COLUMN_ALARM_TYPE, 1);
        contentValues.put(RemembrallContract.AlarmEntry.COLUMN_CLIENT_PROD_ID, clientProdId);

        return contentValues;
    }

    public static ContentValues createClientValues(String firstName){
        ContentValues contentValues = new ContentValues();

        contentValues.put(RemembrallContract.ClientEntry.COLUMN_FIRST_NAME, firstName);
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_LAST_NAME, "Bar");
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_ADDRESS, "Fake Street 1234");
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_ID_CARD, "2134432");
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_EMAIL, "fooBar@gmail.com");
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_HOME_PHONE, "23112321");
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_MOBILE_PHONE, "2312312");
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_SIGN_IMAGE_PATH, "someplace/path");

        return contentValues;
    }

    public static long insertClient(SQLiteDatabase db, String firstName){

        ContentValues contentValues = TestUtil.createClientValues(firstName);

        return db.insert(RemembrallContract.ClientEntry.TABLE_NAME, null, contentValues);
    }

    public static long insertProduct(SQLiteDatabase db, String label){

        ContentValues contentValues = TestUtil.createProductValues(label);

        return db.insert(RemembrallContract.ProductEntry.TABLE_NAME, null, contentValues);
    }

    public static long insertAlarm(SQLiteDatabase db, long clientProdId){
        ContentValues contentValues = TestUtil.createRememberValues(clientProdId);

        return db.insert(RemembrallContract.AlarmEntry.TABLE_NAME, null, contentValues);
    }
}
