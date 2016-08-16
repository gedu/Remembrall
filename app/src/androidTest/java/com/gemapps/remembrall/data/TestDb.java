package com.gemapps.remembrall.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.gemapps.remembrall.TestUtil;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by edu on 7/27/16.
 */

public class TestDb extends AndroidTestCase {

    private static final String TAG = "TestDb";
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

        long clientId = insertClient(db);

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

    public void testClientProductAlarmsTable(){

        RemembrallSqlHelper helper = new RemembrallSqlHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        long clientId = insertClient(db);
        long productId = insertProduct(db);
        long rememberId = insertRemember(db);

        if(clientId != -1 && productId != -1 && rememberId != -1){

            long cliProdRemId = db.insert(RemembrallContract.ClientProdRememEntry.TABLE_NAME, null,
                    RemembrallContract.ClientProdRememEntry.buildContentValues(clientId, productId, rememberId));

            if(cliProdRemId != -1){

                SQLiteDatabase readDb = helper.getReadableDatabase();

                Cursor cursor = readDb.query(RemembrallContract.ClientProdRememEntry.TABLE_NAME,
                        null,
                        RemembrallContract.ClientProdRememEntry._ID+"= ?",
                        new String[]{String.valueOf(cliProdRemId)},
                        null, null, null);

                if(cursor != null){
                    cursor.moveToPosition(-1);
                    while(cursor.moveToNext()){

                        long cId = cursor.getLong(cursor.getColumnIndex(RemembrallContract.ClientProdRememEntry.COLUMN_CLIENT_ID));
                        long pId = cursor.getLong(cursor.getColumnIndex(RemembrallContract.ClientProdRememEntry.COLUMN_PRODUCT_ID));
                        long rId = cursor.getLong(cursor.getColumnIndex(RemembrallContract.ClientProdRememEntry.COLUMN_REMEMBER_ID));

                        assertEquals(clientId, cId);
                        assertEquals(productId, pId);
                        assertEquals(rememberId, rId);

                        Cursor cursorCli = readDb.query(RemembrallContract.ClientEntry.TABLE_NAME,
                                null,
                                RemembrallContract.ClientEntry._ID + "= ?",
                                new String[]{String.valueOf(cId)},
                                null, null, null);

                        if(cursorCli != null){
                            cursorCli.moveToPosition(-1);
                            while(cursorCli.moveToNext()){

                                String firstName = cursorCli.getString(cursorCli.getColumnIndex(RemembrallContract.ClientEntry.COLUMN_FIRST_NAME));

                                assertEquals("Foo", firstName);
                            }
                            cursorCli.close();
                        }
                    }

                    cursor.close();
                }
                readDb.close();
            }
        }

        db.close();
    }

    public void testInnerJoinTables(){

        RemembrallSqlHelper helper = new RemembrallSqlHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        long clientId = insertClient(db);
        long productId = insertProduct(db);
        long rememberId = insertRemember(db);

        if(clientId != -1 && productId != -1 && rememberId != -1) {

            long cliProdRemId = db.insert(RemembrallContract.ClientProdRememEntry.TABLE_NAME, null,
                    RemembrallContract.ClientProdRememEntry.buildContentValues(clientId, productId, rememberId));

            Cursor cursor = TestUtil.mClientProdAlarmQueryBuilder.query(helper.getReadableDatabase(),
                    null,
                    null,
                    null, null, null, null);

            assertNotNull(cursor);
            if(cursor != null){
                Log.d(TAG, "testInnerJoinTables:");
                cursor.moveToPosition(-1);
                while (cursor.moveToNext()){

                    Log.d(TAG, "testInnerJoinTables: "+ Arrays.asList(cursor.getColumnNames()));
                    String label = cursor.getString(cursor.getColumnIndex(RemembrallContract.ClientEntry.COLUMN_FIRST_NAME));
                    long _id = cursor.getLong(cursor.getColumnIndex(RemembrallContract.RememberEntry._ID));

                    Log.d(TAG, "testInnerJoinTables: "+label);
                    Log.d(TAG, "testInnerJoinTables: "+_id);
                }
            }
        }

    }

    private long insertClient(SQLiteDatabase db){

        ContentValues contentValues = TestUtil.createClientValues();

        return db.insert(RemembrallContract.ClientEntry.TABLE_NAME, null, contentValues);
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
