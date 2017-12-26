package com.gemapps.remembrall.ui.widget.validationedittext;

import android.content.Context;
import android.util.AttributeSet;

import com.gemapps.remembrall.R;

import java.util.regex.Pattern;

/**
 * Created by edu on 1/18/17.
 */

public class EmailValidationEditText extends ValidationEditionText {

  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
      + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  private static final Pattern mPattern = Pattern.compile(EMAIL_PATTERN);

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
  protected boolean validate() {
    return mPattern.matcher(getText()).matches();
  }

  @Override
  protected int errorMessage() {
    return R.string.error_email_pattern;
  }
}
