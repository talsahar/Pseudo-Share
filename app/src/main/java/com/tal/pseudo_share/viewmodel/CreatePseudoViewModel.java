package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;
import com.tal.pseudo_share.model.authentication.AuthenticationRepository;
import com.tal.pseudo_share.model.db.PseudoRepository;
import com.tal.pseudo_share.data.Pseudo;

import java.util.UUID;

/**
 * Created by User on 06/01/2018.
 */
//get pseudo obj build your pseudo and call create
public class CreatePseudoViewModel extends ViewModel {
    PseudoRepository pseudoRepository;
    private MutableLiveData<Pseudo> pseudoWhenReady;
    Pseudo pseudoBuilder;
    Bitmap imageBitmap;
    private MutableLiveData<Boolean> progressBarStatus;

    public MutableLiveData<Pseudo> getLiveData(){
        if(pseudoWhenReady==null)
            pseudoWhenReady=new MutableLiveData<>();
        return pseudoWhenReady;
    }
    public Pseudo getUnreadyPseudo(){
        if(pseudoBuilder==null){
            pseudoBuilder=new Pseudo();
            pseudoBuilder.id=UUID.randomUUID().toString();
            pseudoBuilder.setAuthor(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        }
        return pseudoBuilder;
    }

    //called when ready
    public void updateLiveData(){
        pseudoWhenReady.setValue(pseudoBuilder);

    }
    public void build(final Runnable onComplete) {
        if(pseudoRepository==null)
            pseudoRepository=new PseudoRepository();
        pseudoRepository.storePseudo(pseudoBuilder,imageBitmap,onComplete);
        }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public void setProgressBarStatus(boolean b){
        progressBarStatus.setValue(b);
    }
    public MutableLiveData<Boolean> getProgressBarStatus() {
        if(progressBarStatus==null)
            progressBarStatus=new MutableLiveData<>();
        return progressBarStatus;
    }

    public void clear() {
        pseudoWhenReady=null;
        pseudoBuilder=null;
        imageBitmap=null;
    }
}
