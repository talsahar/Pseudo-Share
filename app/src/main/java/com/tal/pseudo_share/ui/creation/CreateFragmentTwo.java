package com.tal.pseudo_share.ui.creation;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.DateConverter;
import com.tal.pseudo_share.viewmodel.CreatePseudoViewModel;
import com.webianks.library.PopupBubble;

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
            }
        });
        return view;
    }

}
