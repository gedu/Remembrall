package com.gemapps.remembrall.data;

import android.net.Uri;
import android.provider.BaseColumns;

import static com.gemapps.remembrall.data.RemembrallContract.RememberEntry.COLUMN_END_DATE;
import static com.gemapps.remembrall.data.RemembrallContract.RememberEntry.COLUMN_REMEMBER_TYPE;
import static com.gemapps.remembrall.data.RemembrallContract.RememberEntry.COLUMN_START_DATE;

/**
 * Created by edu on 7/21/16.
 */

public class RemembrallContract {

    public static final String CONTENT_AUTHORITY = "com.gemapps.remembrall.data";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Possible paths
    public static final String PATH_CLIENT   = "client";
    public static final String PATH_REMEMBER = "remember";
    public static final String PATH_PRODUCTO = "product";

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
        public static final String COLUMN_REMEMBER_ID = "remember_id";
        public static final String COLUMN_PRODUCT_ID = "product_id";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +

                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                COLUMN_ID_CARD + " TEXT NOT NULL, " +
                COLUMN_ADDRESS + " TEXT NOT NULL," +
                COLUMN_EMAIL + " TEXT DEFAULT '', " +
                COLUMN_HOME_PHONE + " TEXT, " +
                COLUMN_MOBILE_PHONE + " TEXT, " +
                COLUMN_SIGN_IMAGE_PATH + " TEXT, " +
                COLUMN_REMEMBER_ID + " INTEGER NOT NULL, " +
                COLUMN_PRODUCT_ID + " INTEGER NOT NULL, " +

                // Set up the remember column as a foreign key to remember table.
                " FOREIGN KEY (" + COLUMN_REMEMBER_ID + ") REFERENCES " +
                RememberEntry.TABLE_NAME + " (" + RememberEntry._ID + "), " +

                " FOREIGN KEY (" + COLUMN_PRODUCT_ID + ") REFERENCES " +
                ProductEntry.TABLE_NAME + " (" + ProductEntry._ID + "); ";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
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


    }

    public static final class ProductEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PRODUCTO).build();

        public static final String TABLE_NAME = "product";

        public static final String COLUMN_LABEL = "label";
        public static final String COLUMN_PRODUCT_NUM = "product_num";
        public static final String COLUMN_TESTER_NUM = "tester_num";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +

                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                COLUMN_LABEL + " TEXT NOT NULL, " +
                COLUMN_PRODUCT_NUM + " TEXT NOT NULL, " +
                COLUMN_TESTER_NUM + " TEXT NOT NULL, " +
                COLUMN_START_DATE + " INTEGER NOT NULL, " +
                COLUMN_END_DATE + " INTEGER NOT NULL," +
                COLUMN_REMEMBER_TYPE + " INTEGER NOT NULL); ";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}

/*

TABLES:

client
_id | first_name | last_name | dni | address | email | home_phone | mobile_phone | sign_path | remember_id | product_id

remember
_id | label | description | start_date | end_date (ts) | remember_type (notification = 0, email = 1, both = 3)

product
_id | label | n_product |

 */