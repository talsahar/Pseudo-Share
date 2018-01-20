package com.tal.pseudo_share.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.db.PseudoRepository;

/**
 * Created by User on 13/01/2018.
 */

public class DetailsViewModel extends ViewModel {

    LiveData<Pseudo> pseudoMutableLiveData;
    LiveData<Bitmap> imageMutableLiveData;


    public LiveData<Pseudo> getPseudoLivedata(String id){
        if(pseudoMutableLiveData==null||pseudoMutableLiveData.getValue()==null||!pseudoMutableLiveData.getValue().getId().equals(id))
        pseudoMutableLiveData=PseudoRepository.getInstance().loadPseudoById(id);
return pseudoMutableLiveData;
    }

    public LiveData<Bitmap> getDrawableLivedata(String path){
        if(imageMutableLiveData==null||imageMutableLiveData.getValue()==null)
            imageMutableLiveData=PseudoRepository.getInstance().loadPseudoDrawable(path);
        return imageMutableLiveData;
    }


}
