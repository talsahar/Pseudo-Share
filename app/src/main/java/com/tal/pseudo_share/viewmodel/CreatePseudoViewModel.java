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
//call getUndreadyPseudo()->Pseudo set your attributes then call updateLiveDate() when you ready to create the object.
public class CreatePseudoViewModel extends ViewModel {
    private MutableLiveData<Pseudo> pseudoWhenReady;
    Pseudo.PseudoBuilder builder;
    Bitmap imageBitmap;

    public MutableLiveData<Pseudo> getPseudoLiveData() {
        if (pseudoWhenReady == null)
            pseudoWhenReady = new MutableLiveData<>();
        return pseudoWhenReady;
    }

    //observe it in your activity, notifies when storing completed.
    public Pseudo.PseudoBuilder getUnreadyPseudo() {
        if (builder == null) {
            builder = Pseudo.builder(UUID.randomUUID().toString());
            builder.setAuthor(AuthenticationRepository.getUserMutableLiveData().getValue().getDisplayName());
        }
        return builder;
    }

    //called when you done building your pseudo. it will store it on storage.
    public void updateLiveData() {
        final Pseudo readyPseudo = builder.build();
        PseudoRepository.getInstance().storePseudo(readyPseudo, imageBitmap, new Runnable() {
            @Override
            public void run() {
                pseudoWhenReady.setValue(readyPseudo);
            }
        });
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }




}
