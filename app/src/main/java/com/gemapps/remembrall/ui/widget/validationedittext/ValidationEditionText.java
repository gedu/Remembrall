package com.gemapps.remembrall.ui.widget.validationedittext;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;

import com.gemapps.remembrall.ui.widget.validationedittext.textwatcher.ValidationWatcherManager;

/**
 * Created by edu on 1/18/17.
 */

public abstract class ValidationEditionText extends TextInputEditText implements TextValidation {

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

  private void init() {
    ValidationWatcherManager.getInstance().addWatcher(this);
  }

  @Override
  public void showErrorMessage() {
    setError(getResources().getString(errorMessage()));
  }

  @Override
  public boolean isValid() {
    return validate();
  }

  @Override
  public String getIdVal() {
    return getText().toString();
  }

  protected abstract boolean validate();
  protected abstract int errorMessage();
}
