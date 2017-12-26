package com.gemapps.remembrall.ui.widget.validationedittext;

/**
 * Created by eduardo.graciano on 12/26/17.
 */

public interface TextValidation {

  boolean isValid();
  void showErrorMessage();
  String getIdVal();
}
