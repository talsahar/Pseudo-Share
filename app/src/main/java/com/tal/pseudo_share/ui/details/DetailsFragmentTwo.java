package com.tal.pseudo_share.ui.details;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.viewmodel.DetailsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragmentTwo extends Fragment {

DetailsViewModel detailsViewModel;

    public DetailsFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_details_fragment_two, container, false);
        String id=getActivity().getIntent().getStringExtra("id");
        detailsViewModel = ViewModelProviders.of(getActivity()).get(DetailsViewModel.class);
        detailsViewModel.setProgressBarStatusMutableData(true);
        detailsViewModel.getPseudoLivedata(id).observe(this, new Observer<Pseudo>() {
                    @Override
                    public void onChanged(@Nullable Pseudo pseudo) {
                        TextView code = view.findViewById(R.id.code_text);
                        code.setText(pseudo.getContent());
                    }
                });

        Button button=view.findViewById(R.id.continue_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        detailsViewModel.setProgressBarStatusMutableData(false);
        return view;
    }


}
