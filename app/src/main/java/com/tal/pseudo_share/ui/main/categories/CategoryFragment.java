package com.tal.pseudo_share.ui.main.categories;


import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.data.PseudoType;
import com.tal.pseudo_share.ui.details.DetailsActivity;
import com.tal.pseudo_share.ui.main.AbstractListFragment;

import java.util.List;

public class CategoryFragment extends AbstractListFragment {


    public CategoryFragment() {
    }

    @Override
    public LiveData<List<Pseudo>> loadData() {
        PseudoType type=PseudoType.toPseudoType(getActivity().getIntent().getStringExtra("category"));
        return allPseudoViewModel.getPseudosByCategory(type);
    }

    @Override
    public void onListFragmentInteraction(Pseudo item) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("id", item.getId());
        startActivity(intent);
    }
}
