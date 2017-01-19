package com.gemapps.remembrall.ui.widget.validationedittext;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;

import com.gemapps.remembrall.ui.widget.validationedittext.textwatcher.TextWatcherWrapper;

/**
 * Created by edu on 1/18/17.
 */

public abstract class ValidationEditionText extends TextInputEditText {

    public ValidationEditionText(Context context) {
        super(context);
        init();
    }

    public ValidationEditionText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ValidationEditionText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init(){

        TextWatcherWrapper emptyTextWatcher = getWatcher();
        emptyTextWatcher.setListener(new TextWatcherWrapper.TextValidationListener() {
            @Override
            public void onValidated(boolean isValid, @StringRes int errorMessage) {

                if(!isValid) setError(getResources().getString(errorMessage));
            }
        });
        addTextChangedListener(emptyTextWatcher);
    }

    protected abstract TextWatcherWrapper getWatcher();
}
