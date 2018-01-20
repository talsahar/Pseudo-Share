package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.AuthenticationRepository;
import com.tal.pseudo_share.model.db.PseudoRepository;

import java.util.UUID;

/**
 * Created by User on 20/01/2018.
 */

public class EditPseudoViewModel extends ViewModel {

    private MutableLiveData<Pseudo> pseudoWhenReady = new MutableLiveData<>();
    private LiveData<Pseudo> builder;
    Bitmap imageBitmap;



    public void setImageBitmap(Bitmap bitmap) {
        this.imageBitmap = bitmap;
    }

    //called when you done building your pseudo. it will store it on storage.
    public void updateLiveData() {
        PseudoRepository.getInstance().storePseudo(builder.getValue(), imageBitmap, new Runnable() {
            @Override
            public void run() {
                pseudoWhenReady.setValue(builder.getValue());
            }
        });
    }

    public LiveData<Pseudo> getPseudoBuilder(String id) {
        if (builder == null || builder.getValue() == null || !builder.getValue().getId().equals(id))
            builder = PseudoRepository.getInstance().loadPseudoById(id);
        return builder;
    }

    public MutableLiveData<Pseudo> getPseudoWhenReady() {
        return pseudoWhenReady;
    }


}
