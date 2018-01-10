package com.tal.pseudo_share.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tal.pseudo_share.model.db.serverDB.PseudoFirebase;
import com.tal.pseudo_share.model.entities.Pseudo;
import com.tal.pseudo_share.model.storage.FirebaseStorageModel;
import com.tal.pseudo_share.model.utils.MyApplication;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by User on 06/01/2018.
 */

public class PseudoRepository {
    public static final PseudoRepository instance = new PseudoRepository();
    PseudoRepository(){

    }

    public void storePseudo(final Pseudo pseudo, Bitmap imageBitmap, final Runnable onComplete){

        new FirebaseStorageModel(new FirebaseStorageModel.FirebaseFileStorageDelegate() {
            @Override
            public void onUploadComplete(Uri result) {
                pseudo.setImageUrl(result.toString());

                SharedPreferences.Editor editor = MyApplication.getMyContext().getSharedPreferences("TAG", MODE_PRIVATE).edit();
                editor.putLong("lastUpdateDate", pseudo.lastUpdated);
                editor.commit();
                PseudoFirebase.addPseudo(pseudo, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        AppLocalStore.db.pseudoDao().insertAll(pseudo);
                        onComplete.run();
                    }
                });
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


    MutableLiveData<List<Pseudo>> pseudoListLiveData;
    public LiveData<List<Pseudo>> getPseudoList(){
        if(pseudoListLiveData==null)
        synchronized (this){
            if(pseudoListLiveData==null){
                pseudoListLiveData=new MutableLiveData<>();
                //1. get the last update date
                long lastUpdateDate = 0;
                try {
                    lastUpdateDate = MyApplication.getMyContext()
                            .getSharedPreferences("TAG", MODE_PRIVATE).getLong("lastUpdateDate", 0);
                }catch (Exception e){

                }
                PseudoFirebase.getAllPseudosAndObserve(lastUpdateDate, new PseudoFirebase.Callback<List<Pseudo>>() {
                    @Override
                    public void onComplete(List<Pseudo> data) {
                        updateLocalStorage(data);
                    }
                });
            }
        }
        return pseudoListLiveData;
    }




    private void updateLocalStorage(List<Pseudo> data) {
        Log.d("TAG", "got items from firebase: " + data.size());
        MyTask task = new MyTask();
        task.execute(data);
    }

class MyTask extends AsyncTask<List<Pseudo>,String,List<Pseudo>>{


    @Override
    protected List<Pseudo> doInBackground(List<Pseudo>[] lists) {
        if(lists.length==1) {
            List<Pseudo> data = lists[0];
            long lastUpdateDate = 0;
            try {
                lastUpdateDate = MyApplication.getMyContext()
                        .getSharedPreferences("TAG", MODE_PRIVATE).getLong("lastUpdateDate", 0);
            }catch (Exception e){
            }
            if (data != null && data.size() > 0) {
                //3. update the local DB
                long reacentUpdate = lastUpdateDate;
                for (Pseudo pseudo : data) {
                    AppLocalStore.db.pseudoDao().insertAll(pseudo);
                    if (pseudo.lastUpdated > reacentUpdate) {
                        reacentUpdate = pseudo.lastUpdated;
                    }
                }
                SharedPreferences.Editor editor = MyApplication.getMyContext().getSharedPreferences("TAG", MODE_PRIVATE).edit();
                editor.putLong("lastUpdateDate", reacentUpdate);
                editor.commit();
            }
            List<Pseudo> pseudoList = AppLocalStore.db.pseudoDao().getAll();
            Log.d("TAG","finish updateEmployeeDataInLocalStorage in thread");

            return pseudoList;
        }
        return null;    }

    @Override
    protected void onPostExecute(List<Pseudo> pseudos) {
        super.onPostExecute(pseudos);
        pseudoListLiveData.setValue(pseudos);
    }
}

}
