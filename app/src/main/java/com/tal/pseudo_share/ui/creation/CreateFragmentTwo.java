package com.tal.pseudo_share.ui.creation;


import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Colors;
import com.tal.pseudo_share.data.Difficulty;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.utilities.Callback;
import com.tal.pseudo_share.view.ListPickerEditText;
import com.tal.pseudo_share.viewmodel.CreatePseudoViewModel;
import com.tal.pseudo_share.viewmodel.DetailsViewModel;

import java.util.Arrays;
import java.util.Date;

public class CreateFragmentTwo extends Fragment {

EditText code;
CreatePseudoViewModel viewModel;

    public CreateFragmentTwo() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel= ViewModelProviders.of(getActivity()).get(CreatePseudoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_create_fragment_two, container, false);
        code=view.findViewById(R.id.code);

        String id=null;//the following block will called on edition
        if((id=getActivity().getIntent().getStringExtra("id"))!=null){
            DetailsViewModel detailsViewModel = ViewModelProviders.of(getActivity()).get(DetailsViewModel.class);
            Pseudo editPseudo=detailsViewModel.getPseudoLivedata(id).getValue();
            code.setText(editPseudo.getContent());
        }

        Button button=view.findViewById(R.id.DoneCreationButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(code.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Please enter code",Toast.LENGTH_LONG);
                    return;
                }
                    viewModel.getUnreadyPseudo().setContent(code.getText().toString()).setDate(new Date());
                    viewModel.updateLiveData();
                CreatePseudoActivity createPseudoActivity = (CreatePseudoActivity) getActivity();
                createPseudoActivity.onDone();
            }
        });


        ListPickerEditText textColorPicker=view.findViewById(R.id.fontColorPicker);

        textColorPicker.setFloatingLabelText("Font Color");

        Callback<String> callback=new Callback<String>() {
            @Override
            public void call(String data) {

                code.setTextColor(Colors.getColorByString(data));
            }
        };
        String[] colors=Colors.generateColorArr();
        textColorPicker.handleDialog(new ListPickerEditText.MyPickerDialog(colors, "Choose a Difficulty"),callback);





        return view;
    }

}
