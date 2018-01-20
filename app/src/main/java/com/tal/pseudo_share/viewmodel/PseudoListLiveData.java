package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.MainThread;

import com.tal.pseudo_share.data.Pseudo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 18/01/2018.
 */

public class PseudoListLiveData extends MutableLiveData<List<Pseudo>> {
    private static PseudoListLiveData instance;
    private String userName;
    private MutableLiveData<List<Pseudo>> myPseudosLiveData;

    private PseudoListLiveData(String userName){
        this.userName=userName;
        myPseudosLiveData=new MutableLiveData<>();
        myPseudosLiveData.setValue(new LinkedList<Pseudo>());
        setValue(new LinkedList<Pseudo>());

    }

    @Override
    public void setValue(List<Pseudo> value) {
        super.setValue(value);
        List<Pseudo> myPseudos=new LinkedList<>();
        for(Pseudo p:value)
            if(p.getAuthor().equals(userName))
                myPseudos.add(p);
        myPseudosLiveData.setValue(myPseudos);
    }

    @MainThread
    public static PseudoListLiveData getInstance(String userName) {
        if (instance == null) {
            instance = new PseudoListLiveData(userName);
        }
        return instance;
    }



    public LiveData<List<Pseudo>> getMyPseudosLiveData() {
        return myPseudosLiveData;
    }

    public void removeIfContains(Pseudo pseudo) {
            myPseudosLiveData.getValue().remove(pseudo);
            getValue().remove(pseudo);
            myPseudosLiveData.setValue(myPseudosLiveData.getValue());
            setValue(getValue());

    }
}
