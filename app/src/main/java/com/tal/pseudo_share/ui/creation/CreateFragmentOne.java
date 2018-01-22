package com.tal.pseudo_share.ui.creation;


import android.app.Activity;
import android.app.DialogFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Difficulty;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.data.PseudoType;
import com.tal.pseudo_share.ui.BaseActivity;
import com.tal.pseudo_share.ui.main.MainActivity;
import com.tal.pseudo_share.view.ListAlertDialog;
import com.tal.pseudo_share.view.ListPickerEditText;
import com.tal.pseudo_share.viewmodel.CreatePseudoViewModel;
import com.tal.pseudo_share.viewmodel.DetailsViewModel;
import com.tal.pseudo_share.viewmodel.StaticMutablesHolder;

import java.io.InputStream;
import java.util.Arrays;

import static android.app.Activity.RESULT_OK;


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
        viewModel = ViewModelProviders.of(getActivity()).get(CreatePseudoViewModel.class);

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

        String id=null;//the following block will called on edition
        if((id=getActivity().getIntent().getStringExtra("id"))!=null){
            DetailsViewModel detailsViewModel = ViewModelProviders.of(getActivity()).get(DetailsViewModel.class);
            Pseudo editPseudo=detailsViewModel.getPseudoLivedata(id).getValue();
            description.setText(editPseudo.getDescription());
            title.setText(editPseudo.getName());
            difficultyPicker.setText(editPseudo.getDifficulty().name());
            typePicker.setText(editPseudo.getType().name());
            detailsViewModel.getDrawableLivedata(editPseudo.getImageUrl()).observe(this, new Observer<Bitmap>() {
                @Override
                public void onChanged(@Nullable Bitmap bitmap) {
                    picture.setImageBitmap(bitmap);
                }
            });
            viewModel.getUnreadyPseudo().setId(id);
        }

        difficultyPicker.setFloatingLabelText("Choose Difficulty");
        final String[] difficulties = Arrays.toString(Difficulty.values()).replaceAll("^.|.$", "").split(", ");
        difficultyPicker.handleDialog(new ListPickerEditText.MyPickerDialog(difficulties, "Choose a Difficulty"));

        typePicker.setFloatingLabelText("Choose Subdomain");
        final String[] pseudoTypes = Arrays.toString(PseudoType.values()).replaceAll("^.|.$", "").split(", ");
        typePicker.handleDialog(new ListPickerEditText.MyPickerDialog(pseudoTypes, "Choose Algorithm Subdomain"));

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
                        }
                        else if(which == 1){
                            //load from gallery.
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
                    viewModel.getUnreadyPseudo().setDescription(description.getText().toString())
                            .setDifficulty(Difficulty.valueOf(difficultyPicker.getText().toString()))
                            .setPseudoType(PseudoType.valueOf(typePicker.getText().toString()))
                            .setName(title.getText().toString());

                    BaseActivity activity = (BaseActivity) getActivity();
                    activity.loadFragment(R.id.contentContainer, new CreateFragmentTwo());
                }
            }

            private boolean verifyFields() {
                if (title.getText().toString().isEmpty())
                    StaticMutablesHolder.exceptionMutableLiveData.setValue(new Exception("Please enter a title."));
                else if (difficultyPicker.getText().toString().isEmpty())
                    StaticMutablesHolder.exceptionMutableLiveData.setValue(new Exception("Please enter difficulty."));
                else if (typePicker.getText().toString().isEmpty())
                    StaticMutablesHolder.exceptionMutableLiveData.setValue(new Exception("Please enter pseudo type."));
                else if (description.getText().toString().isEmpty())
                    StaticMutablesHolder.exceptionMutableLiveData.setValue(new Exception("Please enter description."));
                else return true;
                return false;
            }
        });
        return view;
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picture.setImageBitmap(imageBitmap);
            viewModel.setImageBitmap(imageBitmap);
        }


    }
}
