package com.tal.pseudo_share.ui.external;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tal.pseudo_share.R;


public class AboutFragment extends Fragment {


    public AboutFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_about, container, false);
        TextView text=view.findViewById(R.id.aboutText);
        Linkify.addLinks(text,Linkify.ALL);
        return view;
    }

}
