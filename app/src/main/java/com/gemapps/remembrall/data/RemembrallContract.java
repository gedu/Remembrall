package com.gemapps.remembrall.data;

import android.net.Uri;

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