package com.gemapps.remembrall.ui.widget.validationedittext.textwatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edu on 1/18/17.
 */

public class ValidationWatcherManager {

    private List<TextWatcherWrapper> mTextWatcherList = new ArrayList<>();

    private static ValidationWatcherManager mInstance;

    public static ValidationWatcherManager getInstance(){
        if(mInstance == null) mInstance = new ValidationWatcherManager();
        return mInstance;
    }

    public void addWatcher(TextWatcherWrapper textWatcher){

        mTextWatcherList.add(textWatcher);
    }

    public boolean formValidationPassed(){
        boolean allWatchersAreValid = true;

        for (TextWatcherWrapper textWatcher : mTextWatcherList){
            allWatchersAreValid &= textWatcher.isValid();
        }

        return allWatchersAreValid;
    }
}
