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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.DateConverter;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.viewmodel.CreatePseudoVM;
import com.tal.pseudo_share.viewmodel.PseudoVM;

import java.util.Date;

public class CreateFragmentTwo extends Fragment {

CreatePseudoVM viewModel;

    public CreateFragmentTwo() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel= ViewModelProviders.of(getActivity()).get(CreatePseudoVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_create_fragment_two, container, false);
        final EditText code=view.findViewById(R.id.code);

        String id=null;//the following block will called on edition
        if((id=getActivity().getIntent().getStringExtra("id"))!=null){
            PseudoVM detailsViewModel = ViewModelProviders.of(getActivity()).get(PseudoVM.class);
            Pseudo editPseudo=detailsViewModel.getPseudos().getPseudoById(id);
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

                Pseudo pseudo = viewModel.getUnreadyPseudo();
                pseudo.setContent(code.getText().toString());
                pseudo.setDate(DateConverter.toTimestamp(new Date()));

                   ProgressBar pBar = getActivity().findViewById(R.id.progressBar);
                   pBar.setVisibility(View.VISIBLE);

                    viewModel.updateLiveData();
                CreatePseudoActivity createPseudoActivity = (CreatePseudoActivity) getActivity();
                createPseudoActivity.finish();
            }
        });

        return view;
    }

}
