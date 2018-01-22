package com.tal.pseudo_share.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.ui.details.DetailsActivity;
import com.tal.pseudo_share.view.ListAlertDialog;

import java.util.List;

/**
 * Created by User on 12/01/2018.
 */

public class OnlinePseudoFragment extends AbstractListFragment {

    @Override
    public LiveData<List<Pseudo>> loadData() {
        return allPseudoViewModel.getAllPseudos();
    }

    @Override
    public void onListFragmentInteraction(final Pseudo item) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("id", item.getId());
                startActivity(intent);
            }



}
