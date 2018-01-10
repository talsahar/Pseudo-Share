package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.tal.pseudo_share.model.db.PseudoRepository;
import com.tal.pseudo_share.model.entities.Pseudo;

import java.util.UUID;

/**
 * Created by User on 06/01/2018.
 */
//get pseudo obj build your pseudo and call create
public class CreatePseudoViewModel extends ViewModel {
    private final MutableLiveData<Pseudo> pseudoWhenReady;
    Pseudo pseudo;
    Bitmap imageBitmap;

    private final MutableLiveData<Boolean> progressBarStatus;

    public CreatePseudoViewModel(){
        pseudo=new Pseudo();
       pseudo.id=UUID.randomUUID().toString();
       pseudo.setAuthor(AuthenticationModel.getCurrentUser().getUid());
        pseudoWhenReady=new MutableLiveData<>();
        progressBarStatus=new MutableLiveData<>();
    }
    public MutableLiveData<Pseudo> getLiveData(){
        return pseudoWhenReady;
    }

    public Pseudo getUnreadyPseudo(){
        return pseudo;
    }

    //called when ready
    public void updateLiveData(){
        pseudoWhenReady.setValue(pseudo);

    }
    public void build(final Runnable onComplete) {

        PseudoRepository.instance.storePseudo(pseudo,imageBitmap,onComplete);


        }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public void setProgressBarStatus(boolean b){
        progressBarStatus.setValue(b);
    }
    public MutableLiveData<Boolean> getProgressBarStatus() {
        return progressBarStatus;
    }

}
