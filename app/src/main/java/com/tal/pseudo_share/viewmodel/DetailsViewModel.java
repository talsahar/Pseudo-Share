package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;

import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.db.PseudoRepository;

/**
 * Created by User on 13/01/2018.
 */

public class DetailsViewModel extends ViewModel {

    MutableLiveData<Pseudo> pseudoMutableLiveData;
    MutableLiveData<Bitmap> imageMutableLiveData;
    MutableLiveData<Boolean> progressBarStatusMutableData=new MutableLiveData<>();


    public MutableLiveData<Pseudo> getPseudoLivedata(String id){
        if(pseudoMutableLiveData==null||pseudoMutableLiveData.getValue()==null||!pseudoMutableLiveData.getValue().getId().equals(id))
        pseudoMutableLiveData=PseudoRepository.loadPseudoById(id);
return pseudoMutableLiveData;
    }

    public MutableLiveData<Bitmap> getDrawableLivedata(String path){
        if(imageMutableLiveData==null||imageMutableLiveData.getValue()==null)
            imageMutableLiveData=PseudoRepository.loadPseudoDrawable(path);
        return imageMutableLiveData;
    }

    public MutableLiveData<Boolean> getProgressBarStatusMutableData(){
        return progressBarStatusMutableData;
    }

    public void setProgressBarStatusMutableData(boolean status){
        progressBarStatusMutableData.setValue(status);
    }
}
