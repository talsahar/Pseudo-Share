package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.authentication.AuthenticationRepository;
import com.tal.pseudo_share.model.db.PseudoRepository;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 30/12/2017.
 */

public class MyPseudoViewModel extends ViewModel {

    private MutableLiveData<List<Pseudo>> allPseudos;
    private MutableLiveData<List<Pseudo>> myPseudos;

    private MutableLiveData<Exception> exceptionMutableLiveData;
    private MutableLiveData<Boolean> progressBarStatus = new MutableLiveData<>();

    public MyPseudoViewModel() {

    }

    public MutableLiveData<List<Pseudo>> getAllPseudos() {
        if (allPseudos == null)
            allPseudos = PseudoRepository.getAllPseudos();
        return allPseudos;
    }

    public MutableLiveData<List<Pseudo>> getMyPseudos() {
        if (myPseudos == null)
            myPseudos=PseudoRepository.getMyPseudos();
        return myPseudos;
    }

    //exception
    public MutableLiveData<Exception> getException() {
        if (exceptionMutableLiveData == null)
            exceptionMutableLiveData = PseudoRepository.getExceptionMutableLiveData();
        return exceptionMutableLiveData;
    }

    //progress bar
    public void setProgressBarStatus(boolean b) {
        progressBarStatus.setValue(b);
    }

    public MutableLiveData<Boolean> getProgressBarStatus() {
        return progressBarStatus;
    }

}
