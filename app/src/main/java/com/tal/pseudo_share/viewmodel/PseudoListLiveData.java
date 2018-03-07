package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.tal.pseudo_share.data.Pseudo;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 18/01/2018.
 */

public class PseudoListLiveData {
    private String userName;
    private MutableLiveData<List<Pseudo>> myPseudos;
    private MutableLiveData<List<Pseudo>> allPseudos;

    public PseudoListLiveData(String userName){
        this.userName=userName;
        myPseudos=new MutableLiveData<>();
        allPseudos=new MutableLiveData<>();
        myPseudos.setValue(new LinkedList<Pseudo>());
        allPseudos.setValue(new LinkedList<Pseudo>());
    }

    public void setValue(List<Pseudo> value) {
        allPseudos.setValue(value);
        List<Pseudo> myPseudosNewList=new LinkedList<>();
        for(Pseudo p:value)
            if(p.getAuthor().equals(userName))
                myPseudosNewList.add(p);
        myPseudos.setValue(myPseudosNewList);
    }

    public LiveData<List<Pseudo>> getMyPseudosLiveData() {
        return myPseudos;
    }

    public LiveData<List<Pseudo>> getAllPseudosLiveData() {
        return allPseudos;
    }

    public Pseudo getPseudoById(String id){
        for (Pseudo pseudo:allPseudos.getValue())
            if(pseudo.getId().equals(id))
                return pseudo;
        return null;
    }

    public void removeIfContains(Pseudo pseudo) {
        myPseudos.getValue().remove(pseudo);
        allPseudos.getValue().remove(pseudo);
        myPseudos.setValue(myPseudos.getValue());
        allPseudos.setValue(allPseudos.getValue());
    }

}
