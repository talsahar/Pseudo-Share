package com.tal.pseudo_share.ui.details;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.viewmodel.PseudoVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragmentTwo extends Fragment {


    public DetailsFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_details_fragment_two, container, false);
        String id=getActivity().getIntent().getStringExtra("id");
        PseudoVM pseudoVM = ViewModelProviders.of(getActivity()).get(PseudoVM.class);
        Pseudo pseudo = pseudoVM.getPseudos().getPseudoById(id);
        TextView code = view.findViewById(R.id.code_text);
        code.setText(pseudo.getContent());

        Button button=view.findViewById(R.id.continue_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }


}
