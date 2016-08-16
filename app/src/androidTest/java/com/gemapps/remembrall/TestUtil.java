package com.gemapps.remembrall;

import android.content.ContentValues;
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
                        RemembrallContract.ClientProdRememEntry.TABLE_NAME +
                        " ON " + RemembrallContract.ClientEntry.TABLE_NAME +
                        "." + RemembrallContract.ClientEntry._ID +
                        " = " + RemembrallContract.ClientProdRememEntry.TABLE_NAME +
                        "." + RemembrallContract.ClientProdRememEntry.COLUMN_CLIENT_ID +
                        " INNER JOIN " +
                        RemembrallContract.ProductEntry.TABLE_NAME  +
                        " ON " + RemembrallContract.ProductEntry.TABLE_NAME +
                        "." + RemembrallContract.ProductEntry._ID +
                        " = " + RemembrallContract.ClientProdRememEntry.TABLE_NAME +
                        "." + RemembrallContract.ClientProdRememEntry.COLUMN_PRODUCT_ID
        );
    }

    public static ContentValues createProductValues(){
        ContentValues contentValues = new ContentValues();

        contentValues.put(RemembrallContract.ProductEntry.COLUMN_LABEL, "Envolvente");
        contentValues.put(RemembrallContract.ProductEntry.COLUMN_PRODUCT_NUM, "00000000");
        contentValues.put(RemembrallContract.ProductEntry.COLUMN_TESTER_NUM, "11111111");
        contentValues.put(RemembrallContract.ProductEntry.COLUMN_TERMINAL_NUM, "00000000");
        contentValues.put(RemembrallContract.ProductEntry.COLUMN_PRICE, "111");
        contentValues.put(RemembrallContract.ProductEntry.COLUMN_DESCRIPTION, "Nice model");

        return contentValues;
    }

    public static ContentValues createRememberValues(){
        ContentValues contentValues = new ContentValues();

        contentValues.put(RemembrallContract.RememberEntry.COLUMN_LABEL, "Call client");
        contentValues.put(RemembrallContract.RememberEntry.COLUMN_DESCRIPTION, "Call John again");
        contentValues.put(RemembrallContract.RememberEntry.COLUMN_START_DATE, 12345678);
        contentValues.put(RemembrallContract.RememberEntry.COLUMN_END_DATE, 22345678);
        contentValues.put(RemembrallContract.RememberEntry.COLUMN_REMEMBER_TYPE, 1);

        return contentValues;
    }

    public static ContentValues createClientValues(){
        ContentValues contentValues = new ContentValues();

        contentValues.put(RemembrallContract.ClientEntry.COLUMN_FIRST_NAME, "Foo");
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_LAST_NAME, "Bar");
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_ADDRESS, "Fake Street 1234");
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_ID_CARD, "2134432");
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_EMAIL, "fooBar@gmail.com");
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_HOME_PHONE, "23112321");
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_MOBILE_PHONE, "2312312");
        contentValues.put(RemembrallContract.ClientEntry.COLUMN_SIGN_IMAGE_PATH, "someplace/path");

        return contentValues;
    }
}
