package com.gemapps.remembrall.ui.widget.validationedittext.textwatcher;

import com.gemapps.remembrall.R;

import java.util.regex.Pattern;

/**
 * Created by edu on 1/18/17.
 */

public class EmailTextWatcher extends TextWatcherWrapper {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern mPattern = Pattern.compile(EMAIL_PATTERN);

    public EmailTextWatcher() {
        super();
    }

    @Override
    protected boolean isValid(String email) {
        return mPattern.matcher(email).matches();
    }

    @Override
    protected int getErrorMessage() {
        return R.string.error_email_pattern;
    }
}
