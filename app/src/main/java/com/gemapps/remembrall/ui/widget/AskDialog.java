package com.gemapps.remembrall.ui.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.gemapps.remembrall.R;

import java.io.Serializable;

/**
 * Created by eduardo.graciano on 12/26/17.
 */

public class AskDialog extends DialogFragment {

  public interface AskListener extends Serializable {
    void onPositiveClick();
    void onNegativeClick();
  }

  private static final String TAG = "AskDialog";
  private static final String MESSAGE_KEY = "MESSAGE_KEY";
  private static final String LISTENER_KEY = "LISTENER_KEY";

  public static void showAskDialog(FragmentManager fragmentManager, String message, AskListener listener) {
    AskDialog askDialog = AskDialog.newInstance(message, listener);
    askDialog.show(fragmentManager, TAG);
  }

  private static AskDialog newInstance(String message, AskListener listener) {
    AskDialog dialog = new AskDialog();
    Bundle bundle = new Bundle();
    bundle.putString(MESSAGE_KEY, message);
    bundle.putSerializable(LISTENER_KEY, listener);
    dialog.setArguments(bundle);
    return dialog;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    final AskListener listener = (AskListener) getArguments().getSerializable(LISTENER_KEY);
    builder.setMessage(getArguments().getString(MESSAGE_KEY));
    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        assert listener != null;
        listener.onPositiveClick();
        dialog.dismiss();
      }
    });

    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        assert listener != null;
        listener.onNegativeClick();
        dialog.dismiss();
      }
    });

    return builder.create();
  }

}
