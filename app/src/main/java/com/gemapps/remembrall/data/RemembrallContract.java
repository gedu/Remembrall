package com.gemapps.remembrall.data;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.gemapps.remembrall.ui.model.Client;
import com.gemapps.remembrall.ui.model.Product;
import com.gemapps.remembrall.ui.model.RememberAlarm;
import com.gemapps.remembrall.ui.model.Remembrall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by edu on 7/21/16.
 */

public class RemembrallContract {
    private static final String TAG = "RemembrallContract";

    public static final String CONTENT_AUTHORITY = "com.gemapps.remembrall.data";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Possible paths
    public static final String PATH_CLIENT   = "client";
    public static final String PATH_REMEMBER = "remember";
    public static final String PATH_PRODUCT = "product";
    public static final String PATH_CLIENT_PROD_REMEM = "client_prod_remem";

    public static final class ClientEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CLIENT).build();

        public static final String TABLE_NAME = "client";

        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_ID_CARD = "id_card";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_HOME_PHONE = "home_phone";
        public static final String COLUMN_MOBILE_PHONE = "mobile_phone";
        public static final String COLUMN_SIGN_IMAGE_PATH = "sign_image_path";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +

                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                COLUMN_ID_CARD + " TEXT NOT NULL, " +
                COLUMN_ADDRESS + " TEXT NOT NULL," +
                COLUMN_EMAIL + " TEXT DEFAULT '', " +
                COLUMN_HOME_PHONE + " TEXT, " +
                COLUMN_MOBILE_PHONE + " TEXT, " +
                COLUMN_SIGN_IMAGE_PATH + " TEXT);";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static ContentValues buildContentValues(Remembrall remembrall){

            Client client = remembrall.getClient();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_FIRST_NAME, client.getFirstName());
            contentValues.put(COLUMN_LAST_NAME, client.getLastName());
            contentValues.put(COLUMN_ID_CARD, client.getIdCard());
            contentValues.put(COLUMN_ADDRESS, client.getAddress());
            contentValues.put(COLUMN_EMAIL, client.getEmail());
            contentValues.put(COLUMN_HOME_PHONE, client.getHomePhone());
            contentValues.put(COLUMN_MOBILE_PHONE, client.getMobilePhone());
            contentValues.put(COLUMN_SIGN_IMAGE_PATH, "");
            contentValues.put(COLUMN_FIRST_NAME, client.getFirstName());

            return contentValues;
        }
    }

    public static final class RememberEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_REMEMBER).build();

        public static final String TABLE_NAME = "remember";

        public static final String COLUMN_LABEL = "label";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_START_DATE = "start_date";
        public static final String COLUMN_END_DATE = "end_date";
        public static final String COLUMN_REMEMBER_TYPE = "remember_type";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +

                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                COLUMN_LABEL + " TEXT NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_START_DATE + " INTEGER NOT NULL, " +
                COLUMN_END_DATE + " INTEGER NOT NULL," +
                COLUMN_REMEMBER_TYPE + " INTEGER NOT NULL); ";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static ContentValues[] buildContentValues(Remembrall remembrall) {

            JSONArray rememberAlarms = remembrall.getRememberAlarms();
            int length = rememberAlarms.length();
            ContentValues[] contentAlarms = new ContentValues[length];

            for (int i = 0; i < length; i++) {

                try {
                    JSONObject obj = rememberAlarms.getJSONObject(i);
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(COLUMN_LABEL, obj.getString(RememberAlarm.ALARM_LABEL));
                    contentValues.put(COLUMN_DESCRIPTION, obj.getString(RememberAlarm.ALARM_DESCRIPTION));
                    contentValues.put(COLUMN_START_DATE, obj.getLong(RememberAlarm.ALARM_START_DATE));
                    contentValues.put(COLUMN_END_DATE, obj.getLong(RememberAlarm.ALARM_END_DATE));
                    contentValues.put(COLUMN_REMEMBER_TYPE, obj.getInt(RememberAlarm.ALARM_TYPE));

                    contentAlarms[i] = contentValues;

                } catch (JSONException e) {
                    Log.e(TAG, "ERROR adding obj num "+i);
                }
            }

            return contentAlarms;
        }
    }

    public static final class ProductEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PRODUCT).build();

        public static final String TABLE_NAME = "product";

        public static final String COLUMN_LABEL = "label";
        public static final String COLUMN_PRODUCT_NUM = "product_num";
        public static final String COLUMN_TESTER_NUM = "tester_num";
        public static final String COLUMN_TERMINAL_NUM = "terminal_num";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_DESCRIPTION = "description";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +

                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                COLUMN_LABEL + " TEXT NOT NULL, " +
                COLUMN_PRODUCT_NUM + " TEXT NOT NULL, " +
                COLUMN_TESTER_NUM + " TEXT NOT NULL, " +
                COLUMN_TERMINAL_NUM + " INTEGER NOT NULL, " +
                COLUMN_PRICE + " INTEGER NOT NULL," +
                COLUMN_DESCRIPTION + " INTEGER NOT NULL);";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static ContentValues buildContentValues(Remembrall remembrall) {
            ContentValues contentValues = new ContentValues();

            Product product = remembrall.getProduct();

            contentValues.put(COLUMN_LABEL, product.getEquipLabel());
            contentValues.put(COLUMN_PRODUCT_NUM, product.getEquipNum());
            contentValues.put(COLUMN_TESTER_NUM, product.getTesterNum());
            contentValues.put(COLUMN_TERMINAL_NUM, product.getTerminalNum());
            contentValues.put(COLUMN_PRICE, product.getPrice());
            contentValues.put(COLUMN_DESCRIPTION, product.getDescription());

            return contentValues;
        }
    }

    public static class ClientProdRememEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CLIENT_PROD_REMEM).build();

        public static final String TABLE_NAME = "client_prod_remem";

        public static final String COLUMN_CLIENT_ID = "client_id";
        public static final String COLUMN_PRODUCT_ID = "product_id";
        public static final String COLUMN_REMEMBER_ID = "remember_id";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +

                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CLIENT_ID + " INTEGER NOT NULL, " +
                COLUMN_PRODUCT_ID + " INTEGER NOT NULL, " +
                COLUMN_REMEMBER_ID + " INTEGER NOT NULL, "+

                " FOREIGN KEY (" + COLUMN_CLIENT_ID + ") REFERENCES " +
                ClientEntry.TABLE_NAME + " (" + ClientEntry._ID + "), " +

                " FOREIGN KEY (" + COLUMN_PRODUCT_ID + ") REFERENCES " +
                ProductEntry.TABLE_NAME + " (" + ProductEntry._ID + "), " +

                " FOREIGN KEY (" + COLUMN_REMEMBER_ID + ") REFERENCES " +
                RememberEntry.TABLE_NAME + " (" + RememberEntry._ID + ") " +

                ");";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static ContentValues buildContentValues(long clientId, long productId, long remAlarmId) {

            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_CLIENT_ID, clientId);
            contentValues.put(COLUMN_PRODUCT_ID, productId);
            contentValues.put(COLUMN_REMEMBER_ID, remAlarmId);

            return contentValues;
        }
    }

}

/*

TABLES:

client
_id | first_name | last_name | dni | address | email | home_phone | mobile_phone | sign_path | remember_id  | product_id

remember
_id | label | description | start_date | end_date (ts) | remember_type (notification = 0, email = 1, both = 3)

product
_id | label | n_product | n_terminal | price | description

 */