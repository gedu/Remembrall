package com.gemapps.remembrall.ui.widget.validationedittext;

import android.content.Context;
import android.util.AttributeSet;

import com.gemapps.remembrall.R;

/**
 * Created by edu on 1/18/17.
 */

public class TextValidationEditText extends ValidationEditionText {

  public TextValidationEditText(Context context) {
    super(context);
  }

  public TextValidationEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TextValidationEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected boolean validate() {
    return getText().length() > 0;
  }

  @Override
  protected int errorMessage() {
    return R.string.error_empty_text;
  }
}
