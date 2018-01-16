package com.tal.pseudo_share.ui.main;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.ui.details.DetailsActivity;
import com.tal.pseudo_share.view.ListAlertDialog;

import java.util.List;

/**
 * Created by User on 12/01/2018.
 */

public class MyPseudoFragment extends AbstractListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = myPseudoViewModel.getMyPseudos();
        super.observeData();
    }


    @Override
    public void onListFragmentInteraction(Pseudo item) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("id", item.getId());
        startActivity(intent);
    }

    @Override
    public void onLongClickInteraction(final Pseudo item) {
        String[] options = {"Details", "Edit", "Delete"};
        ListAlertDialog optionFragment = ListAlertDialog.newInstance("Choose your action", options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myPseudoViewModel.setProgressBarStatus(true);
                if (which == 0) {
                    onLongClickInteraction(item);
                } else if (which == 1) {
                } else {

                }
            }
        });
        optionFragment.show(getActivity().getFragmentManager(),
                "TAG");
    }
}
