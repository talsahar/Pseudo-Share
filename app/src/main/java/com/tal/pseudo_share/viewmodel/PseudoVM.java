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

public class PseudoVM extends ViewModel {

    private PseudoListLiveData pseudoList;

    public PseudoVM() {

    }

    public PseudoListLiveData getPseudos() {
        if (pseudoList == null)
            pseudoList = PseudoRepository.getInstance().getAllPseudos();
        return pseudoList;
    }

    public void deletePseudo(Pseudo pseudo) {
        PseudoRepository.getInstance().deletePseudo(pseudo);
    }

}
