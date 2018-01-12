package com.tal.pseudo_share.ui.main;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tal.pseudo_share.data.Pseudo;

import java.util.List;

/**
 * Created by User on 12/01/2018.
 */

public class MyPseudoFragment extends AbstractListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data=myPseudoViewModel.getMyPseudos();
        data.observe(this, new Observer<List<Pseudo>>() {
            @Override
            public void onChanged(@Nullable List<Pseudo> pseudos) {
                adapter.notifyDataSetChanged();
                myPseudoViewModel.setProgressBarStatus(false);
            }
        });
    }

    @Override
    public void onListFragmentInteraction(Pseudo item) {

    }
}
