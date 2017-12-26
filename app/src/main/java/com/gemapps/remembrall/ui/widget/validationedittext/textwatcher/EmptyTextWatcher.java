package com.gemapps.remembrall.ui.widget.validationedittext.textwatcher;

import android.support.annotation.StringRes;

import com.gemapps.remembrall.R;

/**
 * Created by edu on 1/18/17.
 */
@Deprecated
public class EmptyTextWatcher extends TextWatcherWrapper {

    public EmptyTextWatcher() {
        super();
    }

    @Override
    protected boolean isValid(String text) {
        return !text.isEmpty();
    }

    @Override
    protected @StringRes int getErrorMessage() {
        return R.string.error_empty_text;
    }
}
