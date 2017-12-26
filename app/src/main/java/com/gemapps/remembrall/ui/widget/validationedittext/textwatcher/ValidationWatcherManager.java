package com.gemapps.remembrall.ui.widget.validationedittext.textwatcher;

import com.gemapps.remembrall.ui.widget.validationedittext.TextValidation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edu on 1/18/17.
 */

public class ValidationWatcherManager {

    private List<TextValidation> mTextWatcherList = new ArrayList<>();

    private static ValidationWatcherManager mInstance;

    public static ValidationWatcherManager getInstance(){
        if(mInstance == null) mInstance = new ValidationWatcherManager();
        return mInstance;
    }

    public void addWatcher(TextValidation textWatcher){

        mTextWatcherList.add(textWatcher);
    }

    public boolean formValidationPassed(){

        for (TextValidation textWatcher : mTextWatcherList){

          if (!textWatcher.isValid()) {
            textWatcher.showErrorMessage();
            return false;
          }
        }

        return true;
    }

    public String getFieldWithError() {
      for (TextValidation textWatcher : mTextWatcherList){

        if (!textWatcher.isValid()) {
          return textWatcher.getIdVal();
        }
      }
      return "";
    }
}
