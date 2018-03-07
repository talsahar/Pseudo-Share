package com.tal.pseudo_share.ui.creation;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.data.PseudoType;
import com.tal.pseudo_share.model.imageStorage.ImageStorageManager;
import com.tal.pseudo_share.utilities.Callback;
import com.tal.pseudo_share.utilities.ExceptionHandler;
import com.tal.pseudo_share.view.ListAlertDialog;
import com.tal.pseudo_share.view.ListPickerEditText;
import com.tal.pseudo_share.viewmodel.CreatePseudoVM;
import com.tal.pseudo_share.viewmodel.PseudoVM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;


public class CreateFragmentOne extends Fragment {
    MaterialEditText title;
    RoundedImageView picture;
    ListPickerEditText difficultyPicker;
    ListPickerEditText typePicker;
    CreatePseudoVM viewModel;
    EditText description;

    public CreateFragmentOne() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(CreatePseudoVM.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_fragment_one, container, false);
        description = view.findViewById(R.id.expandable_text);
        title = view.findViewById(R.id.pseudoName);
        difficultyPicker = view.findViewById(R.id.difficultyPicker);
        typePicker = view.findViewById(R.id.typePicker);
        picture = view.findViewById(R.id.pseudoImage);

        String id = null;//the following block will called on edition
        if ((id = getActivity().getIntent().getStringExtra("id")) != null) {
            PseudoVM detailsViewModel = ViewModelProviders.of(getActivity()).get(PseudoVM.class);
            Pseudo editPseudo = detailsViewModel.getPseudos().getPseudoById(id);
            description.setText(editPseudo.getDescription());
            title.setText(editPseudo.getName());
            difficultyPicker.setText(editPseudo.getDifficulty().name());
            typePicker.setText(editPseudo.getType().name());
            ImageStorageManager.loadImage(editPseudo.getImageUrl(), new Callback<Bitmap>() {
                @Override
                public void call(Bitmap data) {
                    if(data!=null)
                        picture.setImageBitmap(data);
                }
            }, new Callback<Exception>() {
                @Override
                public void call(Exception data) {

                }
            });
            viewModel.getUnreadyPseudo().setId(id);
        }

        difficultyPicker.setFloatingLabelText("Choose Difficulty");
        final String[] difficulties = Arrays.toString(Difficulty.values()).replaceAll("^.|.$", "").split(", ");
        difficultyPicker.handleDialog(new ListPickerEditText.MyPickerDialog(difficulties, "Choose a Difficulty"),null);

        typePicker.setFloatingLabelText("Choose Subdomain");
        final String[] pseudoTypes = Arrays.toString(PseudoType.values()).replaceAll("^.|.$", "").split(", ");
        typePicker.handleDialog(new ListPickerEditText.MyPickerDialog(pseudoTypes, "Choose Algorithm Subdomain"),null);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] options = {"Camera", "Gallery"};
                ListAlertDialog.newInstance("Choose Image Source", options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                            }
                        } else if (which == 1) {
                            Intent intent = new Intent(
                                    Intent.ACTION_PICK);
                            File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                            String path = pictureDirectory.getPath();
                            Uri data = Uri.parse(path);
                            intent.setDataAndType(data, "image/*");
                            startActivityForResult(intent, IMAGE_GALLERY_REQUEST);


                        }
                    }
                }).show(getActivity().getFragmentManager(),
                        "TAG");
            }
        });

        view.findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyFields()) {
                    Pseudo pseudo=viewModel.getUnreadyPseudo();
                    pseudo.setDescription(description.getText().toString());
                    pseudo.setDifficulty(Difficulty.valueOf(difficultyPicker.getText().toString()));
                    pseudo.setType(PseudoType.valueOf(typePicker.getText().toString()));
                    pseudo.setName(title.getText().toString());

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.contentContainer, new CreateFragmentTwo());
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            });

        return view;
    }

            private boolean verifyFields() {
                if (title.getText().toString().isEmpty())
                    ExceptionHandler.set(new Exception("Please enter a title."));
                else if (difficultyPicker.getText().toString().isEmpty())
                    ExceptionHandler.set(new Exception("Please enter difficulty."));
                else if (typePicker.getText().toString().isEmpty())
                    ExceptionHandler.set(new Exception("Please enter pseudo type."));
                else if (description.getText().toString().isEmpty())
                    ExceptionHandler.set(new Exception("Please enter description."));
                else return true;
                return false;
            }


    //on chosen image async
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int IMAGE_GALLERY_REQUEST = 2;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = null;
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
            } else if (requestCode == IMAGE_GALLERY_REQUEST) {
                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
            if(bitmap!=null){
                picture.setImageBitmap(bitmap);
                viewModel.setImageBitmap(bitmap);
            }
        }
    }
}
