package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.data.PseudoType;
import com.tal.pseudo_share.model.db.PseudoRepository;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 30/12/2017.
 */

public class AllPseudoViewModel extends ViewModel {

    private LiveData<List<Pseudo>> allPseudos;
    private LiveData<List<Pseudo>> myPseudos;

    public AllPseudoViewModel() {

    }

    public LiveData<List<Pseudo>> getAllPseudos() {
        if (allPseudos == null)
            allPseudos = PseudoRepository.getInstance().getAllPseudos();
        return allPseudos;
    }





    public void deletePseudo(Pseudo pseudo) {
        PseudoRepository.getInstance().deletePseudo(pseudo);
    }

    public LiveData<List<Pseudo>> getMyPseudos() {
        if (myPseudos == null)
            myPseudos = PseudoRepository.getInstance().getMyPseudos();
        return myPseudos;
    }

    public void initRepository() {
        PseudoRepository.initInstance();
    }

    public LiveData<List<Pseudo>> getPseudosByCategory(PseudoType type) {
        MutableLiveData<List<Pseudo>> liveList=new MutableLiveData<>();
        List<Pseudo> list=new LinkedList<>();
        for(Pseudo p:allPseudos.getValue()){
            if(p.getType()==type)
                list.add(p);
        }
        liveList.setValue(list);
        return liveList;
    }
}
