package com.tal.pseudo_share.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;


public class ListPickerEditText extends MaterialEditText {

    MyPickerDialog dialog;
    Context context;

    public ListPickerEditText(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setBaseColor(Color.WHITE);
        setTextColor(Color.WHITE);
        this.setErrorColor(Color.RED);
        setPrimaryColor(Color.WHITE);
        this.setFloatingLabel(FLOATING_LABEL_NORMAL);
        this.setFloatingLabelText("Change It");
        setSingleLineEllipsis(true);
        setSingleLine(true);
        setCursorVisible(false);
        setKeyListener(null);

        // setAccentTypeface(Typeface.create("Roboto-LightItalic.ttf", Typeface.NORMAL));


        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Activity activity = (Activity) context;
                    dialog.show(activity.getFragmentManager(), "TAG");

                }
            }
        });


    }

    public void handleDialog(MyPickerDialog dialog) {
        this.dialog = dialog;
        dialog.setPickerDelegate(new PickerDelegate() {
            @Override
            public void onChosen(String chosen) {
                setText(chosen);
                clearFocus();
            }
        });
    }


    interface PickerDelegate {
        void onChosen(String chosen);
    }

    @SuppressLint("ValidFragment")
    public static class MyPickerDialog extends DialogFragment {
        private final String title;
        private final String[] options;
        PickerDelegate pickerDelegate;



        public MyPickerDialog(String[] options,String title) {
        this.title=title;
        this.options=options;

        }

        public void setPickerDelegate(PickerDelegate pickerDelegate) {
            this.pickerDelegate = pickerDelegate;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(title)
                    .setItems(options,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    pickerDelegate.onChosen(options[which]);
                                }
                            });
            return builder.create();
        }
    }


}
