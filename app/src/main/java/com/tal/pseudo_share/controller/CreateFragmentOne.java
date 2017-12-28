package com.tal.pseudo_share.controller;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;
import com.tal.pseudo_share.R;
import com.tal.pseudo_share.view.ListPickerEditText;
import com.tal.pseudo_share.view.ProgressBarHandler;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;


public class CreateFragmentOne extends Fragment {
    RoundedImageView picture;

    public CreateFragmentOne() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_fragment_one, container, false);
        ListPickerEditText difficultyPicker = view.findViewById(R.id.difficultyPicker);
        difficultyPicker.setFloatingLabelText("Choose Difficulty");
        difficultyPicker.handleDialog(new ListPickerEditText.MyPickerDialog(new String[]{"Easy", "Normal", "Hard"}, "Choose a Difficulty"));

        ListPickerEditText typePicker = view.findViewById(R.id.typePicker);
        typePicker.setFloatingLabelText("Choose Subdomains");
        typePicker.handleDialog(new ListPickerEditText.MyPickerDialog(new String[]{"Implementation", "Strings", "Sorting", "Search", "Graph Theory", "Greedy", "Dynamic Programming", "Recursion"}, "Choose Algorithm Subdomain"));


        picture = view.findViewById(R.id.pseudoImage);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        view.findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new CreateFragmentTwo();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = 0;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picture.setImageBitmap(imageBitmap);
        }
    }


}
