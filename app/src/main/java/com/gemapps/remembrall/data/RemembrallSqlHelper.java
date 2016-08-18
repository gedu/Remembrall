package com.gemapps.remembrall.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gemapps.remembrall.data.RemembrallContract.*;
/**
 * Created by edu on 7/27/16.
 */

public class RemembrallSqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "remembrall.db";

    public RemembrallSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(AlarmEntry.SQL_CREATE_TABLE);
        db.execSQL(ProductEntry.SQL_CREATE_TABLE);
        db.execSQL(ClientEntry.SQL_CREATE_TABLE);
        db.execSQL(ClientProdEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(AlarmEntry.SQL_DROP_TABLE);
        db.execSQL(ProductEntry.SQL_DROP_TABLE);
        db.execSQL(ClientEntry.SQL_DROP_TABLE);
        db.execSQL(ClientProdEntry.SQL_DROP_TABLE);

        onCreate(db);
    }
}
