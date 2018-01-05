package com.gemapps.remembrall.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

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
    public static final String PATH_JOB = "job";

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
    }

    public static class DeliveryEntry {
      public static final String COLUMN_ID = "mId";
    }

    public static class JobEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_JOB).build();
        public static final String COLUMN_ID = "mId";
        public static final String COLUMN_CLIENT_ID = "mClient";
        public static final String COLUMN_PRODUCT_ID = "mProduct";
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