package com.tal.pseudo_share.ui.details;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.tal.pseudo_share.ui.BaseActivity;
import com.tal.pseudo_share.ui.creation.CreateFragmentTwo;
import com.tal.pseudo_share.viewmodel.DetailsViewModel;

public class DetailsFragmentOne extends Fragment {

    DetailsViewModel detailsViewModel;

    public DetailsFragmentOne() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_details_fragment_one, container, false);
        String id = getActivity().getIntent().getStringExtra("id");
        detailsViewModel = ViewModelProviders.of(getActivity()).get(DetailsViewModel.class);
        detailsViewModel.getPseudoLivedata(id).observe(this, new Observer<Pseudo>() {
            @Override
            public void onChanged(@Nullable Pseudo pseudo) {
                //download image async
                if (pseudo.getImageUrl() != null && !pseudo.getImageUrl().isEmpty())
                    detailsViewModel.getDrawableLivedata(pseudo.getImageUrl()).observe(DetailsFragmentOne.this, new Observer<Bitmap>() {
                        @Override
                        public void onChanged(@Nullable Bitmap bitmap) {
                            if (bitmap != null) {
                                ImageView image = view.findViewById(R.id.pseudoImage);
                                image.setImageBitmap(bitmap);
                            }
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
                ExpandableTextView description = (ExpandableTextView) view
                        .findViewById(R.id.expand_text_view);
                description.setText(pseudo.getDescription());
            }
        });

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
