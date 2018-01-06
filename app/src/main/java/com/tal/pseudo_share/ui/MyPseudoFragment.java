package com.tal.pseudo_share.ui;

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

import com.tal.pseudo_share.MyItemRecyclerViewAdapter;
import com.tal.pseudo_share.R;
import com.tal.pseudo_share.db.entity.Pseudo;
import com.tal.pseudo_share.viewmodel.MyPseudoViewModel;

import java.util.LinkedList;
import java.util.List;


public class MyPseudoFragment extends Fragment {
//liveData: setValue()- main thread , postValue()- worker thread
    private OnListFragmentInteractionListener mListener;
    private MyPseudoViewModel myPseudoViewModel;
    private MyItemRecyclerViewAdapter adapter;

    public MyPseudoFragment() {
    }

    public static MyPseudoFragment newInstance(OnListFragmentInteractionListener listener) {
        MyPseudoFragment fragment = new MyPseudoFragment();
        fragment.mListener=listener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPseudoViewModel=ViewModelProviders.of(this).get(MyPseudoViewModel.class);
        myPseudoViewModel.setData(getData());
        myPseudoViewModel.getData().observe(this, new Observer<List<Pseudo>>() {
            @Override
            public void onChanged(@Nullable List<Pseudo> pseudos) {
                adapter.notifyDataSetChanged();
            }
        });

    }

    public void addData(long i){
        List<Pseudo> list=new LinkedList<>();
        list.add(new Pseudo("Tal"+i,"tal","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal2"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal3"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal4"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal"+i,"tal","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal2"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal3"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal4"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal"+i,"tal","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal2"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal3"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal4"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal"+i,"tal","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal2"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal3"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal4"+i,"tal2","sadasd",null,null,null,43));list.add(new Pseudo("Tal"+i,"tal","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal2"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal3"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal4"+i,"tal2","sadasd",null,null,null,43));list.add(new Pseudo("Tal"+i,"tal","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal2"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal3"+i,"tal2","sadasd",null,null,null,43));
        list.add(new Pseudo("Tal4"+i,"tal2","sadasd",null,null,null,43));
        myPseudoViewModel.setData(list);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_item_list, container, false);
        Context context = view.getContext();
        view.setLayoutManager(new LinearLayoutManager(context));
        adapter=new MyItemRecyclerViewAdapter(myPseudoViewModel.getData(),mListener);
        view.setAdapter(adapter);
        return view;
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Pseudo item);
    }
}
