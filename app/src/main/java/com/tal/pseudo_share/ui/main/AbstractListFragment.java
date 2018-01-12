package com.tal.pseudo_share.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tal.pseudo_share.ui.listAdapters.MyItemRecyclerViewAdapter;
import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.viewmodel.MyPseudoViewModel;

import java.util.List;


abstract class AbstractListFragment extends Fragment implements MyItemRecyclerViewAdapter.OnListFragmentInteractionListener {
    protected MyPseudoViewModel myPseudoViewModel;
    protected MyItemRecyclerViewAdapter adapter;
    protected MutableLiveData<List<Pseudo>> data;

    public AbstractListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPseudoViewModel=ViewModelProviders.of(getActivity()).get(MyPseudoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_item_list, container, false);
        Context context = view.getContext();
        view.setLayoutManager(new LinearLayoutManager(context));
        adapter=new MyItemRecyclerViewAdapter(data,this);
        view.setAdapter(adapter);
        return view;
    }





}
