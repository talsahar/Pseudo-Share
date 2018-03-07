package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.tal.pseudo_share.model.AuthenticationRepository;
import com.tal.pseudo_share.model.db.PseudoRepository;

/**
 * Created by talsahar73 on 10/01/2018.
 */

    public class AuthenticationVM extends ViewModel {
    LiveData<FirebaseUser> userLiveData;


    public void signin(String email, String password) {
    AuthenticationRepository.getInstance().login(email,password);
    }

    public void signup(String email, String password,String nickname) {
        AuthenticationRepository.getInstance().signup(email,password,nickname);
    }

    public void signout(){
        AuthenticationRepository.getInstance().logout();
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        if(userLiveData==null)
            userLiveData=AuthenticationRepository.getInstance().getUserMutableLiveData();
        return userLiveData;
    }





}
