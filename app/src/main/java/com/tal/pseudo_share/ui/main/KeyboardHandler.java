package com.tal.pseudo_share.ui.main;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by tal on 2/7/2018.
 */
//will auto hide on screen touch
public class KeyboardHandler {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void setupUI(final Activity activity, View parent) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(parent instanceof EditText)) {
            parent.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (parent instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) parent).getChildCount(); i++) {
                View innerView = ((ViewGroup) parent).getChildAt(i);
                setupUI(activity,innerView);
            }
        }
    }
}
