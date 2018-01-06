package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.tal.pseudo_share.db.entity.Pseudo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 30/12/2017.
 */

public class MyPseudoViewModel extends ViewModel {

    private final MutableLiveData<List<Pseudo>> data;

    public MyPseudoViewModel() {
        data=new MutableLiveData<>();
        data.setValue(new LinkedList<Pseudo>());
    }

    public MutableLiveData<List<Pseudo>> getData(){
        return data;
    }

    public void setData(List<Pseudo> data){
        this.data.setValue(data);
    }


}
