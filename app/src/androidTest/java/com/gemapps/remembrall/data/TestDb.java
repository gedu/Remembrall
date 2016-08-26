package com.gemapps.remembrall.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.gemapps.remembrall.TestUtil;

import java.util.HashSet;

import static com.gemapps.remembrall.TestUtil.insertAlarm;
import static com.gemapps.remembrall.TestUtil.insertClient;
import static com.gemapps.remembrall.TestUtil.insertProduct;

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
        tableNameHashSet.add(RemembrallContract.AlarmEntry.TABLE_NAME);

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
        long id = insertProduct(db, "Envolvente");

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

    public void testClientTable(){

        RemembrallSqlHelper helper = new RemembrallSqlHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        long clientId = insertClient(db, "Foo");

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

    public void testClientProductTable(){

        RemembrallSqlHelper helper = new RemembrallSqlHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        long clientId = insertClient(db, "Foo");
        long productId = insertProduct(db, "Bar");

        if(clientId != -1 && productId != -1){

            long clientProId = insertClientProd(db, clientId, productId);

            if(clientProId != -1){

                SQLiteDatabase readDb = helper.getReadableDatabase();

                Cursor cursor = readDb.query(RemembrallContract.ClientProdEntry.TABLE_NAME,
                        null,
                        RemembrallContract.ClientProdEntry._ID+"= ?",
                        new String[]{String.valueOf(clientProId)},
                        null, null, null);

                if(cursor != null){
                    cursor.moveToPosition(-1);
                    while(cursor.moveToNext()){

                        long cId = cursor.getLong(cursor.getColumnIndex(RemembrallContract.ClientProdEntry.COLUMN_CLIENT_ID));
                        long pId = cursor.getLong(cursor.getColumnIndex(RemembrallContract.ClientProdEntry.COLUMN_PRODUCT_ID));

                        assertEquals(clientId, cId);
                        assertEquals(productId, pId);

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

    public void testAlarmTable(){
        RemembrallSqlHelper helper = new RemembrallSqlHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        long clientId = insertClient(db, "Foo");
        long productId = insertProduct(db, "Bar");
        long clientProId = insertClientProd(db, clientId, productId);
        long alarmId = insertAlarm(db, clientProId);

        if(alarmId != -1){

            SQLiteDatabase readDb = helper.getReadableDatabase();

            Cursor cursor = readDb.query(RemembrallContract.AlarmEntry.TABLE_NAME,
                    null,
                    RemembrallContract.AlarmEntry._ID+"= ?",
                    new String[]{String.valueOf(alarmId)},
                    null, null, null);

            if(cursor != null){
                cursor.moveToPosition(-1);
                while(cursor.moveToNext()){

                    String label = cursor.getString(cursor.getColumnIndex(RemembrallContract.AlarmEntry.COLUMN_LABEL));

                    assertEquals(label, "Call client");
                }
                cursor.close();
            }
            readDb.close();
        }
        db.close();
    }

    public void testGetAllTableData(){
        RemembrallSqlHelper helper = new RemembrallSqlHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        long clientId = insertClient(db, "Mega foo");
        long client2Id = insertClient(db, "Lol");

        long productId = insertProduct(db, "Weird");
        long product2Id = insertProduct(db, "REALLLY");

        long clientProId = insertClientProd(db, clientId, productId);
        long client2ProId = insertClientProd(db, client2Id, product2Id);

        long alarmId = insertAlarm(db, clientProId);
        insertAlarm(db, client2ProId);

        Cursor cursor = TestUtil.mCompleteQueryBuilder.query(helper.getReadableDatabase(),
                RemembrallContract.PROJECTION_ALL, RemembrallContract.AlarmEntry.ALARM_ID+" = ?",
                new String[]{String.valueOf(alarmId)}, null,
                null, null);
        assertNotNull(cursor);

        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){

            String clientFirstName = cursor.getString(cursor.getColumnIndex(RemembrallContract.ClientEntry.COLUMN_FIRST_NAME));
            long cliId = cursor.getLong(cursor.getColumnIndex(RemembrallContract.ClientEntry.CLIENT_ID));

            String prodLabel = cursor.getString(cursor.getColumnIndex(RemembrallContract.ProductEntry.COLUMN_LABEL));
            long proId = cursor.getLong(cursor.getColumnIndex(RemembrallContract.ProductEntry.PRODUCT_ID));

            long cliProdCliId = cursor.getLong(cursor.getColumnIndex(RemembrallContract.ClientProdEntry.COLUMN_CLIENT_ID));
            long cliProdProdId = cursor.getLong(cursor.getColumnIndex(RemembrallContract.ClientProdEntry.COLUMN_PRODUCT_ID));

            String alarmLabel = cursor.getString(cursor.getColumnIndex(RemembrallContract.AlarmEntry.COLUMN_LABEL));
            long alaId = cursor.getLong(cursor.getColumnIndex(RemembrallContract.AlarmEntry.ALARM_ID));

            assertEquals("Mega foo", clientFirstName);
            assertEquals("Weird", prodLabel);

            assertEquals(clientId, cliId);
            assertEquals(productId, proId);
            assertEquals(alarmId, alaId);
            Log.d(TAG, "testGetAllTableData: " +
                            "\nClient first name: " + clientFirstName +
                            "\nClient id: " + cliId + "("+clientId+")"+
                            "\n\nProd label: " + prodLabel +
                            "\nProd id: " + proId + "("+productId+")"+
                            "\n\nClient Product Client Id: " + cliProdCliId +
                            "\nClient Product Product Id: " + cliProdProdId +
                            "\n\nAlarm label: " + alarmLabel +
                            "\nalarm id: " + alaId + "("+alarmId+")"
            );
        }
    }

    private long insertClientProd(SQLiteDatabase db, long clientId, long productId) {

        ContentValues contentValues = RemembrallContract.ClientProdEntry.buildContentValues(clientId, productId);

        return db.insert(RemembrallContract.ClientProdEntry.TABLE_NAME, null, contentValues);
    }
}
