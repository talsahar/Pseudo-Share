package com.tal.pseudo_share.ui.main.categories;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.PseudoType;
import com.tal.pseudo_share.ui.details.DetailsFragmentTwo;

public class CategoriesMainFragment extends Fragment implements CategoriesViewAdapter.OnListFragmentInteractionListener {


    public CategoriesMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_item_list, container, false);
        Context context = view.getContext();
        view.setLayoutManager(new LinearLayoutManager(context));
        CategoriesViewAdapter adapter = new CategoriesViewAdapter(this, PseudoType.values());
        view.setAdapter(adapter);
        return view;


    }

    @Override
    public void onListFragmentInteraction(PseudoType item) {
      /*  getActivity().getIntent().putExtra("category",item.name());
        Fragment newFragment = new CategoryFragment();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.commit();
        Log.d("TAG",item.name()+" clicked");*/
    }
}
