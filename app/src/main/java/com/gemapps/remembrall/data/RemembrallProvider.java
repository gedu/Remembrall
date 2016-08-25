package com.gemapps.remembrall.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by edu on 8/25/16.
 */

public class RemembrallProvider extends ContentProvider {

    static final int ALARM = 100;
    static final int ALARM_WITH_CLIENT_AND_PROD = 101;
    static final int CLIENT = 102;
    static final int PRODUCT = 103;

    private static final UriMatcher mUriMatcher;
    static{
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = RemembrallContract.CONTENT_AUTHORITY;

        mUriMatcher.addURI(authority, RemembrallContract.PATH_ALARM, ALARM);
        mUriMatcher.addURI(authority, RemembrallContract.PATH_ALARM + "/*", ALARM_WITH_CLIENT_AND_PROD);
        mUriMatcher.addURI(authority, RemembrallContract.PATH_CLIENT, CLIENT);
        mUriMatcher.addURI(authority, RemembrallContract.PATH_PRODUCT, PRODUCT);
    }

    private RemembrallSqlHelper mSqlHelper;

    @Override
    public boolean onCreate() {

        mSqlHelper = new RemembrallSqlHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = mUriMatcher.match(uri);

        switch (match){
            case ALARM: return RemembrallContract.AlarmEntry.CONTENT_TYPE_ITEM;
            case ALARM_WITH_CLIENT_AND_PROD: return RemembrallContract.AlarmEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
