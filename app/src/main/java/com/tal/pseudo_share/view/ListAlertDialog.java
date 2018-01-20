package com.tal.pseudo_share.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.tal.pseudo_share.R;

import java.util.List;

/**
 * Created by User on 13/01/2018.
 */

public class ListAlertDialog extends DialogFragment {

    DialogInterface.OnClickListener listener;
    String title;
    String[] options;


    public static ListAlertDialog newInstance(String title, String[] options, DialogInterface.OnClickListener listener) {
        ListAlertDialog dialog = new ListAlertDialog();
        dialog.listener = listener;
        dialog.title = title;
        dialog.options = options;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new
                AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setItems(options, listener);
        return builder.create();
    }

}
