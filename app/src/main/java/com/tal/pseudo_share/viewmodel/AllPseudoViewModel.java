package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.db.PseudoRepository;

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
}
