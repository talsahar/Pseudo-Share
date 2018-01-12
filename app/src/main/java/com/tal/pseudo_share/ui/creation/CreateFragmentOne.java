package com.tal.pseudo_share.ui.creation;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Difficulty;
import com.tal.pseudo_share.data.PseudoType;
import com.tal.pseudo_share.view.ListPickerEditText;
import com.tal.pseudo_share.viewmodel.CreatePseudoViewModel;

import java.util.Arrays;


public class CreateFragmentOne extends Fragment {
    MaterialEditText title;
    RoundedImageView picture;
    ListPickerEditText difficultyPicker;
    ListPickerEditText typePicker;
    CreatePseudoViewModel viewModel;
    EditText description;
    public CreateFragmentOne() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=ViewModelProviders.of(getActivity()).get(CreatePseudoViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_fragment_one, container, false);
        description=view.findViewById(R.id.expandable_text);
        title=view.findViewById(R.id.pseudoName);

        difficultyPicker = view.findViewById(R.id.difficultyPicker);
        difficultyPicker.setFloatingLabelText("Choose Difficulty");
        final String[] difficulties= Arrays.toString(Difficulty.values()).replaceAll("^.|.$", "").split(", ");
        difficultyPicker.handleDialog(new ListPickerEditText.MyPickerDialog(difficulties, "Choose a Difficulty"));

        typePicker = view.findViewById(R.id.typePicker);
        typePicker.setFloatingLabelText("Choose Subdomain");
        String[] pseudoTypes= Arrays.toString(PseudoType.values()).replaceAll("^.|.$", "").split(", ");
        typePicker.handleDialog(new ListPickerEditText.MyPickerDialog(pseudoTypes, "Choose Algorithm Subdomain"));

        picture = view.findViewById(R.id.pseudoImage);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setProgressBarStatus(true);
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
                if(verifyFields()){
                    viewModel.getUnreadyPseudo().setDescription(description.getText().toString());
                    viewModel.getUnreadyPseudo().difficulty=(difficultyPicker.getText().toString());
                    viewModel.getUnreadyPseudo().type=(typePicker.getText().toString());
                    viewModel.getUnreadyPseudo().setName(title.getText().toString());
                    Fragment newFragment = new CreateFragmentTwo();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contentContainer, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }

            private boolean verifyFields() {
                if(title.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Please enter a title.", Toast.LENGTH_SHORT).show();
                else if(difficultyPicker.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Please enter difficulty.", Toast.LENGTH_SHORT).show();
                else if(typePicker.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Please enter pseudo type.", Toast.LENGTH_SHORT).show();
                else if(description.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Please enter description.", Toast.LENGTH_SHORT).show();
                else return true;
                return false;
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
            viewModel.setImageBitmap(imageBitmap);
            viewModel.setProgressBarStatus(false);
        }
    }
}