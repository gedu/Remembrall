package com.gemapps.remembrall.ui.widget.validationedittext;

import android.content.Context;
import android.util.AttributeSet;

import com.gemapps.remembrall.ui.widget.validationedittext.textwatcher.EmailTextWatcher;
import com.gemapps.remembrall.ui.widget.validationedittext.textwatcher.TextWatcherWrapper;

/**
 * Created by edu on 1/18/17.
 */

public class EmailValidationEditText extends ValidationEditionText {

    public EmailValidationEditText(Context context) {
        super(context);
    }

    public EmailValidationEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmailValidationEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected TextWatcherWrapper getWatcher() {
        return new EmailTextWatcher();
    }
}
