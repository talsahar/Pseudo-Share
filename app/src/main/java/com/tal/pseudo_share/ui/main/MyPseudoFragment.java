package com.tal.pseudo_share.ui.main;

import android.arch.lifecycle.LiveData;
import android.content.DialogInterface;
import android.content.Intent;

import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.ui.creation.CreatePseudoActivity;
import com.tal.pseudo_share.ui.details.DetailsActivity;
import com.tal.pseudo_share.view.ListAlertDialog;

import java.util.List;

/**
 * Created by User on 12/01/2018.
 */

public class MyPseudoFragment extends AbstractListFragment {

    @Override
    public LiveData<List<Pseudo>> loadData() {
        return pseudoVM.getPseudos().getMyPseudosLiveData();
    }

    @Override
    public void onListFragmentInteraction(final Pseudo item) {
        String[] options = {"Details", "Edit", "Delete"};
        ListAlertDialog.newInstance("Choose your action", options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             switch (which){
                 case 0:
                     Intent intentA = new Intent(getActivity(), DetailsActivity.class);
                     intentA.putExtra("id", item.getId());
                     startActivity(intentA);
                     break;
                 case 1:
                     Intent intent2 = new Intent(getActivity(), CreatePseudoActivity.class);
                     intent2.putExtra("id", item.getId());
                     startActivity(intent2);
                     break;
                 case 2:pseudoVM.deletePseudo(item);
             }
            }
        }).show(getActivity().getFragmentManager(),"TAG");
    }
}
