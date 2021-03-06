package com.gemapps.remembrall.ui.model.bus;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by edu on 1/15/17.
 */

public class DbTransaction {

    @IntDef(flag=true, value={SAVE, ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TransactionType{}
    public static final int SAVE = 0;
    public static final int ERROR = 1<<1;

    private int mType;

    public DbTransaction(@TransactionType int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }
}
