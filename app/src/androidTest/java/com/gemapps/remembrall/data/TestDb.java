package com.gemapps.remembrall.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.gemapps.remembrall.TestUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashSet;

/**
 * Created by edu on 7/27/16.
 */

public class TestDb extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {

        deleteDatabase();
    }

    private void deleteDatabase(){
        mContext.deleteDatabase(RemembrallSqlHelper.DATABASE_NAME);
    }

    public void testCreateDb(){

        SQLiteDatabase db = new RemembrallSqlHelper(mContext).getWritableDatabase();
        assertTrue(db.isOpen());

        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue("Error: This means that the database has not been created correctly",
                cursor.moveToFirst());

        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(RemembrallContract.ClientEntry.TABLE_NAME);
        tableNameHashSet.add(RemembrallContract.ProductEntry.TABLE_NAME);
        tableNameHashSet.add(RemembrallContract.RememberEntry.TABLE_NAME);

        do{
            tableNameHashSet.remove(cursor.getString(0));
        }while (cursor.moveToNext());

        assertTrue("Error: Your database was created without entry tables",
                tableNameHashSet.isEmpty());

        cursor.close();
    }

    public void testProductTable(){

        RemembrallSqlHelper helper = new RemembrallSqlHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = insertProduct(db);

        if(id != -1){

            SQLiteDatabase readDb = helper.getReadableDatabase();

            Cursor cursor = readDb.query(RemembrallContract.ProductEntry.TABLE_NAME,
                    null,
                    RemembrallContract.ProductEntry._ID+"= ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);

            if(cursor != null){
                cursor.moveToPosition(-1);
                while(cursor.moveToNext()){

                    String label = cursor.getString(cursor.getColumnIndex(RemembrallContract.ProductEntry.COLUMN_LABEL));

                    assertEquals(label, "Envolvente");
                }
                cursor.close();
            }
            readDb.close();
        }
        db.close();
    }

    public void testRememberTable(){
        RemembrallSqlHelper helper = new RemembrallSqlHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        long id = insertRemember(db);

        if(id != -1){

            SQLiteDatabase readDb = helper.getReadableDatabase();

            Cursor cursor = readDb.query(RemembrallContract.RememberEntry.TABLE_NAME,
                    null,
                    RemembrallContract.RememberEntry._ID+"= ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);

            if(cursor != null){
                cursor.moveToPosition(-1);
                while(cursor.moveToNext()){

                    String label = cursor.getString(cursor.getColumnIndex(RemembrallContract.RememberEntry.COLUMN_LABEL));

                    assertEquals(label, "Call client");
                }
                cursor.close();
            }
            readDb.close();
        }
        db.close();
    }

    public void testClientTable(){

        RemembrallSqlHelper helper = new RemembrallSqlHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        long productId = insertProduct(db);
        long rememberId = insertRemember(db);

        ContentValues contentValues = TestUtil.createClientValues(productId, rememberId);

        long clientId = db.insert(RemembrallContract.ClientEntry.TABLE_NAME, null, contentValues);

        if(clientId != -1){

            SQLiteDatabase readDb = helper.getReadableDatabase();

            Cursor cursor = readDb.query(RemembrallContract.ClientEntry.TABLE_NAME,
                    null,
                    RemembrallContract.ClientEntry._ID+"= ?",
                    new String[]{String.valueOf(clientId)},
                    null, null, null);

            if(cursor != null){
                cursor.moveToPosition(-1);
                while(cursor.moveToNext()){

                    String label = cursor.getString(cursor.getColumnIndex(RemembrallContract.ClientEntry.COLUMN_FIRST_NAME));

                    assertEquals(label, "Foo");
                }
                cursor.close();
            }
            readDb.close();
        }
        db.close();
    }

    public void testClientTableManyAlarms(){

        RemembrallSqlHelper helper = new RemembrallSqlHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        JSONArray productIds = new JSONArray();

        for (int i = 0; i < 10; i++) {

            long productId = insertProduct(db);
            productIds.put(productId);
        }
        long rememberId = insertRemember(db);

        ContentValues contentValues = TestUtil.createClientValues(productIds, rememberId);

        long clientId = db.insert(RemembrallContract.ClientEntry.TABLE_NAME, null, contentValues);

        if(clientId != -1){

            SQLiteDatabase readDb = helper.getReadableDatabase();

            Cursor cursor = readDb.query(RemembrallContract.ClientEntry.TABLE_NAME,
                    null,
                    RemembrallContract.ClientEntry._ID+"= ?",
                    new String[]{String.valueOf(clientId)},
                    null, null, null);

            if(cursor != null){
                cursor.moveToPosition(-1);
                while(cursor.moveToNext()){

                    String label = cursor.getString(cursor.getColumnIndex(RemembrallContract.ClientEntry.COLUMN_FIRST_NAME));
                    String cProductIds = cursor.getString(cursor.getColumnIndex(RemembrallContract.ClientEntry.COLUMN_PRODUCT_ID));

                    try {
                        JSONArray pIds = new JSONArray(cProductIds);
                        assertEquals(pIds.length(), 10);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    assertEquals(label, "Foo");
                }
                cursor.close();
            }
            readDb.close();
        }
        db.close();
    }

    private long insertProduct(SQLiteDatabase db){

        ContentValues contentValues = TestUtil.createProductValues();

        return db.insert(RemembrallContract.ProductEntry.TABLE_NAME, null, contentValues);
    }

    private long insertRemember(SQLiteDatabase db){
        ContentValues contentValues = TestUtil.createRememberValues();

        return db.insert(RemembrallContract.RememberEntry.TABLE_NAME, null, contentValues);
    }
}
