package com.tal.pseudo_share.ui.details;


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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.imageStorage.ImageStorageManager;
import com.tal.pseudo_share.utilities.Callback;
import com.tal.pseudo_share.viewmodel.PseudoVM;

public class DetailsFragmentOne extends Fragment {


    public DetailsFragmentOne() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_details_fragment_one, container, false);
        String id = getActivity().getIntent().getStringExtra("id");
        PseudoVM viewModel = ViewModelProviders.of(getActivity()).get(PseudoVM.class);
       Pseudo pseudo = viewModel.getPseudos().getPseudoById(id);
                if(pseudo.getImageUrl()!=null)
                    ImageStorageManager.loadImage(pseudo.getImageUrl(), new Callback<Bitmap>() {
                        @Override
                        public void call(Bitmap data) {
                            ProgressBar pBar = getActivity().findViewById(R.id.progressBar);
                            pBar.setVisibility(View.INVISIBLE);
                            if (data != null) {
                                ImageView image = view.findViewById(R.id.pseudoImage);
                                image.setImageBitmap(data);
                            }
                        }
                    }, new Callback<Exception>() {
                        @Override
                        public void call(Exception data) {

                        }
                    });

                TextView title = view.findViewById(R.id.pseudoName);
                title.setText(pseudo.getName());
                TextView author=view.findViewById(R.id.author);
                author.setText(pseudo.getAuthor());
                TextView difficulty = view.findViewById(R.id.difficultyText);
                difficulty.setText(pseudo.getDifficulty().name());
                TextView type = view.findViewById(R.id.typeText);
                type.setText(pseudo.getType().name());
                ExpandableTextView description =  view
                        .findViewById(R.id.expand_text_view);
                description.setText(pseudo.getDescription());

        Button button = view.findViewById(R.id.nextButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new DetailsFragmentTwo();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

}
