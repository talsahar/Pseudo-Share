package com.tal.pseudo_share.model.db;

import android.os.AsyncTask;
import android.util.Log;

import com.tal.pseudo_share.data.DateConverter;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.data.PseudoSorter;
import com.tal.pseudo_share.utilities.MyApplication;
import com.tal.pseudo_share.viewmodel.PseudoListLiveData;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by User on 18/01/2018.
 */

public class NewPseudosHandler extends AsyncTask<List<Pseudo>, String, Set<Pseudo>> {

    private final Runnable onComplete;
    Semaphore semaphore;
    PseudoListLiveData pseudoListLiveData;
    public NewPseudosHandler(Semaphore semaphore, PseudoListLiveData pseudoListLiveData,Runnable onComplete){
        this.semaphore=semaphore;
        this.pseudoListLiveData=pseudoListLiveData;
        this.onComplete=onComplete;
    }

    private Set<Pseudo> filterDeleted(List<Pseudo> list) {
        Set<Pseudo> toDeleteList = new HashSet<>();
        for (Pseudo pseudo : list) {
            if (pseudo.getIsDeleted()) {
                for (Pseudo p : list)
                    if (p==pseudo)
                        toDeleteList.add(p);
            }
        }

        list.removeAll(toDeleteList);
        return toDeleteList;
    }

    @Override
    protected Set<Pseudo> doInBackground(List<Pseudo>[] lists) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            Set<Pseudo> toDelete=null;
            List<Pseudo> data = lists[0];
            if (data != null && data.size() > 0) {
                toDelete=filterDeleted(data);
                long lastUpdate = MyStorage.getLastUpdate();
                for (Pseudo pseudo : data) {
                    MyStorage.database.pseudoDao().insert(pseudo);
                    long pseudoLastUpdate=pseudo.getLastUpdate();
                    if (pseudoLastUpdate > lastUpdate) {
                        lastUpdate = pseudoLastUpdate;
                    }
                }
                MyStorage.updateLastUpdate(lastUpdate);
            }

            return toDelete;
    }

    @Override
    protected void onPostExecute(Set<Pseudo> toDelete) {
        super.onPostExecute(toDelete);
        if(toDelete!=null)
        for (Pseudo pseudo : toDelete) {
            MyStorage.imageStorageManager.deleteImage(pseudo.getImageUrl(),false);
            MyStorage.database.pseudoDao().delete(pseudo);
        }

        List<Pseudo> pseudoList = MyStorage.database.pseudoDao().selectAll();
        PseudoSorter.sortbyDate(pseudoList);
        pseudoListLiveData.setValue(pseudoList);
        onComplete.run();
        semaphore.release();

    }
}

