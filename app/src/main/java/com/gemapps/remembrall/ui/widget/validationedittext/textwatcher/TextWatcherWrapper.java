package com.gemapps.remembrall.ui.widget.validationedittext.textwatcher;

import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

/**
 * Created by edu on 1/18/17.
 */

public abstract class TextWatcherWrapper implements TextWatcher {

    private static final String TAG = "TextWatcherWrapper";
    public interface TextValidationListener {
        void onValidated(boolean isValid, @StringRes int errorMessage);
    }

    private boolean mCanStartValidation = false;
    private boolean mIsValid = false;
    private TextValidationListener mListener;

    TextWatcherWrapper(){
        ValidationWatcherManager.getInstance().addWatcher(this);
    }

    public void setListener(TextValidationListener listener){
        mListener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mCanStartValidation = true;
    }

    @Override
    public void afterTextChanged(Editable s) {

        if(mCanStartValidation) broadcastValidation(s);
    }

    private void broadcastValidation(Editable text){
        mIsValid = isValid(text.toString());
        if(mListener != null) mListener.onValidated(mIsValid, getErrorMessage());
        else Log.w(TAG, "No listener attached.");
    }

    public boolean isValid(){
        return mIsValid;
    }

    protected abstract boolean isValid(String text);
    protected abstract @StringRes int getErrorMessage();
}
