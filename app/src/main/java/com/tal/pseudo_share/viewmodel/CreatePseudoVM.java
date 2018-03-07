package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;
import com.tal.pseudo_share.model.AuthenticationRepository;
import com.tal.pseudo_share.model.db.PseudoRepository;
import com.tal.pseudo_share.data.Pseudo;

import java.util.UUID;

/**
 * Created by User on 06/01/2018.
 */
public class CreatePseudoVM extends ViewModel {
    private MutableLiveData<Pseudo> pseudoWhenReady;
    Pseudo newPseudo;
    Bitmap imageBitmap;

    public MutableLiveData<Pseudo> getCreatedLiveData() {
        if (pseudoWhenReady == null)
            pseudoWhenReady = new MutableLiveData<>();
        return pseudoWhenReady;
    }

    //observe it in your activity, notifies when storing completed.
    public Pseudo getUnreadyPseudo() {
        if (newPseudo == null) {
            newPseudo = new Pseudo(UUID.randomUUID().toString());
            newPseudo.setAuthor(AuthenticationRepository.getInstance().getCurrUsername());
        }
        return newPseudo;
    }


    //called when you done building your pseudo.
    public void updateLiveData() {

        PseudoRepository.getInstance().storePseudo(newPseudo, imageBitmap, new Runnable() {
            @Override
            public void run() {
                pseudoWhenReady.setValue(newPseudo);
            }
        });
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }




}
