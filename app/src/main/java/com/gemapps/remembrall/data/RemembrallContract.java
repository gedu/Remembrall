package com.gemapps.remembrall.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;

import com.gemapps.remembrall.ui.model.Client;
import com.gemapps.remembrall.ui.model.Product;
import com.gemapps.remembrall.ui.model.Remembrall;

/**
 * Created by edu on 7/21/16.
 */

public class RemembrallContract {
    private static final String TAG = "RemembrallContract";

    public static final String CONTENT_AUTHORITY = "com.gemapps.remembrall.data";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Possible paths
    public static final String PATH_CLIENT   = "client";
    public static final String PATH_ALARM = "alarm";
    public static final String PATH_PRODUCT = "product";
    public static final String PATH_CLIENT_PROD_REMEM = "client_prod_remem";

    public static final String[] PROJECTION_ALL = new String[]{
        AlarmEntry._ID_AS, ClientProdEntry._ID_AS,
            AlarmEntry.COLUMN_ALARM_TYPE, AlarmEntry.COLUMN_DESCRIPTION, AlarmEntry.COLUMN_END_DATE,
            AlarmEntry.COLUMN_LABEL, AlarmEntry.COLUMN_START_DATE, ClientProdEntry.COLUMN_CLIENT_ID,
            ClientProdEntry.COLUMN_PRODUCT_ID, ClientEntry._ID_AS, ClientEntry.COLUMN_FIRST_NAME,
            ClientEntry.COLUMN_LAST_NAME, ClientEntry.COLUMN_ID_CARD, ClientEntry.COLUMN_ADDRESS,
            ClientEntry.COLUMN_EMAIL, ClientEntry.COLUMN_HOME_PHONE, ClientEntry.COLUMN_MOBILE_PHONE,
            ProductEntry._ID_AS, ProductEntry.COLUMN_LABEL, ProductEntry.COLUMN_TESTER_NUM,
            ProductEntry.COLUMN_TERMINAL_NUM, ProductEntry.COLUMN_PRICE, ProductEntry.COLUMN_DESCRIPTION
    };

    //TODO: set some values like UNIQUE with a CONSTRAINT
    public static final class ClientEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CLIENT).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
                "/" + CONTENT_AUTHORITY + "/" + PATH_CLIENT;

        public static final String TABLE_NAME = "client";

        public static final String CLIENT_ID = "cli_id";
        public static final String _ID_AS = TABLE_NAME+"."+_ID + " AS " + CLIENT_ID;

        public static final String COLUMN_FIRST_NAME = "mFirstName";
        public static final String COLUMN_LAST_NAME = "mLastName";
        public static final String COLUMN_ID_CARD = "mIdCard";
        public static final String COLUMN_ADDRESS = "mAddress";
        public static final String COLUMN_EMAIL = "mEmail";
        public static final String COLUMN_HOME_PHONE = "mHomePhone";
        public static final String COLUMN_MOBILE_PHONE = "mMobilePhone";
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

    public static final class AlarmEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_ALARM).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
                "/" + CONTENT_AUTHORITY + "/" + PATH_ALARM;

        public static final String CONTENT_TYPE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                "/" + CONTENT_AUTHORITY + "/" + PATH_ALARM;

        public static final String TABLE_NAME = "alarm";

        public static final String ALARM_ID = "al_id";
        public static final String _ID_AS = TABLE_NAME+"."+_ID + " AS " + ALARM_ID;

        public static final String COLUMN_LABEL = "mLabel";
        public static final String COLUMN_DESCRIPTION = "mDescription";
        public static final String COLUMN_START_DATE = "mStartDate";
        public static final String COLUMN_END_DATE = "mEndDate";
        public static final String COLUMN_ALARM_TYPE = "mAlarmType";
        public static final String COLUMN_CLIENT_PROD_ID = "client_prod_id";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +

                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                COLUMN_LABEL + " TEXT NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_START_DATE + " INTEGER NOT NULL, " +
                COLUMN_END_DATE + " INTEGER NOT NULL," +
                COLUMN_ALARM_TYPE + " INTEGER NOT NULL," +
                COLUMN_CLIENT_PROD_ID + " INTEGER NOT NULL," +
                " FOREIGN KEY (" + COLUMN_CLIENT_PROD_ID + ") REFERENCES " +
                ClientProdEntry.TABLE_NAME + " (" + ClientProdEntry._ID + ") " +
                "); ";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class ProductEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PRODUCT).build();

        public static final String TABLE_NAME = "product";
        public static final String PRODUCT_ID = "pro_id";
        public static final String _ID_AS = TABLE_NAME+"."+_ID + " AS " + PRODUCT_ID;

        public static final String COLUMN_LABEL = "mEquipLabel";
        public static final String COLUMN_PRODUCT_NUM = "mEquipNum";
        public static final String COLUMN_TESTER_NUM = "mTesterNum";
        public static final String COLUMN_TERMINAL_NUM = "mTerminalNum";
        public static final String COLUMN_PRICE = "mPrice";
        public static final String COLUMN_DESCRIPTION = "mDescription";

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

    public static class ClientProdEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CLIENT_PROD_REMEM).build();

        public static final String TABLE_NAME = "client_prod";
        public static final String CLI_PRO_ID = "cli_pro_id";
        public static final String _ID_AS = TABLE_NAME+"."+_ID + " AS " + CLI_PRO_ID;

        public static final String COLUMN_ID = "mId";
        public static final String COLUMN_CLIENT_ID = "mClient";
        public static final String COLUMN_PRODUCT_ID = "mProduct";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +

                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CLIENT_ID + " INTEGER NOT NULL, " +
                COLUMN_PRODUCT_ID + " INTEGER NOT NULL, " +

                " FOREIGN KEY (" + COLUMN_CLIENT_ID + ") REFERENCES " +
                ClientEntry.TABLE_NAME + " (" + ClientEntry._ID + "), " +

                " FOREIGN KEY (" + COLUMN_PRODUCT_ID + ") REFERENCES " +
                ProductEntry.TABLE_NAME + " (" + ProductEntry._ID + ") " +

                ");";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static ContentValues buildContentValues(long clientId, long productId) {

            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_CLIENT_ID, clientId);
            contentValues.put(COLUMN_PRODUCT_ID, productId);

            return contentValues;
        }
    }

}

/*

TABLES:

client
_id | first_name | last_name | dni | address | email | home_phone | mobile_phone | sign_path | remember_id  | product_id

alarm
_id | alarm_label | alarm_description | start_date | end_date (ts) | remember_type (notification = 0, email = 1, both = 3) | client_prod_id

product
_id | prod_label | n_product | n_terminal | price | prod_description


client_prod
_id | client_id | product_id
 */