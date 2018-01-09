package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.tal.pseudo_share.model.authentication.AuthenticationModel;
import com.tal.pseudo_share.model.db.PseudoRepository;
import com.tal.pseudo_share.model.db.serverDB.PseudoFirebase;
import com.tal.pseudo_share.model.entities.Pseudo;
import com.tal.pseudo_share.model.storage.FirebaseStorageModel;

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

            new FirebaseStorageModel(new FirebaseStorageModel.FirebaseFileStorageDelegate() {
                @Override
                public void onUploadComplete(Uri result) {
                    pseudo.setImageUrl(result.toString());
                    PseudoRepository.instance.storePseudo(pseudo,onComplete);
                }

                @Override
                public void onUploadFailed(Exception exception) {
                    Log.d("TAG","failed uploading image");
                }

                @Override
                public void onDownloadComplete(Bitmap result) {

                }

                @Override
                public void onDownloadFailed(Exception exception) {

                }
            }).storeImage(imageBitmap,pseudo.id+".jpg");


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
