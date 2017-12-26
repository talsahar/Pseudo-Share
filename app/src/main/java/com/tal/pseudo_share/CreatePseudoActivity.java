package com.tal.pseudo_share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dd.morphingbutton.MorphingAnimation;
import com.dd.morphingbutton.MorphingButton;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tal.pseudo_share.controller.LoginActivity;
import com.tal.pseudo_share.view.ListPickerEditText;
import com.tal.pseudo_share.view.ProgressBarHandler;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

import static com.rengwuxian.materialedittext.MaterialEditText.FLOATING_LABEL_NORMAL;

public class CreatePseudoActivity extends MyMainActivity {
    RoundedImageView picture;
    ProgressBarHandler progressBarHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pseudo);
        ListPickerEditText listPicker = findViewById(R.id.difficultyPicker);
        listPicker.setFloatingLabelText("Choose Difficulty");
        listPicker.handleDialog(new ListPickerEditText.MyPickerDialog(new String[]{"Easy", "Normal", "Hard"}, "Choose a Difficulty"));

        initImagePicker();

        RingProgressBar ringProgressBar = findViewById(R.id.progress_bar);
        progressBarHandler = new ProgressBarHandler(this, ringProgressBar);
    findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressBarHandler.show();
        }
    });

    }



    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = 0;

    private void initImagePicker() {
         picture = findViewById(R.id.pseudoImage);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picture.setImageBitmap(imageBitmap);
        }
    }


}
