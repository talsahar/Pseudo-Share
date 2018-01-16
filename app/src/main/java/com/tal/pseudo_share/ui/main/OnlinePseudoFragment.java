package com.tal.pseudo_share.ui.main;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tal.pseudo_share.data.Pseudo;

import java.util.List;

/**
 * Created by User on 12/01/2018.
 */

public class OnlinePseudoFragment extends AbstractListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           data=myPseudoViewModel.getAllPseudos();
           super.observeData();

    }

    @Override
    public void onListFragmentInteraction(Pseudo item) {

    }

    @Override
    public void onLongClickInteraction(Pseudo item) {

    }
}
