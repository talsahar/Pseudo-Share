package com.tal.pseudo_share.model.db;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.tal.pseudo_share.model.db.serverDB.PseudoFirebase;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.storage.FirebaseStorageModel;
import com.tal.pseudo_share.model.storage.ImageStorageModel;
import com.tal.pseudo_share.model.utils.MyApplication;
import com.tal.pseudo_share.model.utils.PseudoSorter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 06/01/2018.
 */

public class PseudoRepository {

    static MutableLiveData<List<Pseudo>> pseudoListLiveData = new MutableLiveData<>();
    static MutableLiveData<List<Pseudo>> myPseudos = new MutableLiveData<>();

    static MutableLiveData<Exception> exceptionMutableLiveData = new MutableLiveData<>();

    static {
        pseudoListLiveData.setValue(new LinkedList<Pseudo>());
        myPseudos.setValue(new LinkedList<Pseudo>());
    }

    public static void storePseudo(final Pseudo pseudo, Bitmap imageBitmap, final Runnable onComplete) {
        ImageStorageModel.storeImage(imageBitmap, pseudo.id + ".jpg", new FirebaseStorageModel.OnUploadCompleteListener() {
            @Override
            public void onUploadComplete(Uri result) {
                pseudo.setImageUrl(result.toString());
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
                exceptionMutableLiveData.setValue(exception);
                Log.d("TAG", "failed uploading image");
            }
        });
    }

    //load async and return mutableData
    public static MutableLiveData<Pseudo> loadPseudoById(final String id) {
        final MutableLiveData<Pseudo> pseudo = new MutableLiveData<>();
        new AsyncTask<String, String, Pseudo>() {
            @Override
            protected Pseudo doInBackground(String... id) {
                for (Pseudo p : pseudoListLiveData.getValue()) {
                    if (p.getId().equals(id[0])) {
                        return p;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Pseudo p) {
                super.onPostExecute(p);
                if(p!=null)
                    pseudo.setValue(p);
            }
        }.execute(id);

        return pseudo;
        }


    private static boolean lock = false;
    private static boolean haveLoaded = false;

    public static MutableLiveData<List<Pseudo>> getAllPseudos() {
        //its binding will called one time
        long lastUpdateDate = MyApplication.getLastUpdate();
        if (haveLoaded == false && lock == false) {
            lock = true;
            PseudoFirebase.getAllPseudosAndObserve(lastUpdateDate, new PseudoFirebase.Callback<List<Pseudo>>() {
                @Override//update local
                public void onComplete(List<Pseudo> data) {
                    StoreOnLocalTask task = new StoreOnLocalTask();
                    task.execute(data);
                }
            });
        }

        return pseudoListLiveData;
    }

    public static MutableLiveData<List<Pseudo>> getMyPseudos() {
        if (haveLoaded == false)
            getAllPseudos();
        return myPseudos;
    }


    public static MutableLiveData<Exception> getExceptionMutableLiveData() {
        return exceptionMutableLiveData;
    }

    public static MutableLiveData<Bitmap> loadPseudoDrawable(String path) {
       final MutableLiveData<Bitmap> drawableMutableLiveData=new MutableLiveData<>();
        ImageStorageModel.loadImage(path, new FirebaseStorageModel.OnDownloadCompleteListener() {
            @Override
            public void onDownloadComplete(Bitmap result) {
               drawableMutableLiveData.setValue(result);
            }

            @Override
            public void onDownloadFailed(Exception exception) {
                drawableMutableLiveData.setValue(null);

            }
        });
        return drawableMutableLiveData;
    }

    static class StoreOnLocalTask extends AsyncTask<List<Pseudo>, String, List<Pseudo>> {
        @Override
        protected List<Pseudo> doInBackground(List<Pseudo>[] lists) {
            if (lists.length == 1) {
                List<Pseudo> data = lists[0];
                if (data != null && data.size() > 0) {
                    //3. update the local DB
                    long lastUpdate = MyApplication.getLastUpdate();
                    for (Pseudo pseudo : data) {
                        AppLocalStore.db.pseudoDao().insertAll(pseudo);
                        if (pseudo.lastUpdate > lastUpdate) {
                            lastUpdate = pseudo.lastUpdate;
                        }
                    }
                    MyApplication.updateLastUpdate(lastUpdate);
                }
                List<Pseudo> pseudoList = AppLocalStore.db.pseudoDao().getAll();
/*
for(Pseudo p:pseudoList)
    AppLocalStore.db.pseudoDao().delete(p);

*/
                Log.d("TAG", "finish store on local task in thread");
                PseudoSorter.sortbyDate(pseudoList);
                return pseudoList;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Pseudo> pseudos) {
            super.onPostExecute(pseudos);
            pseudoListLiveData.setValue(pseudos);
            List<Pseudo> myPseudosTMP = new LinkedList<>();
            for (Pseudo p : pseudos) {
                String s = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                if (p.getAuthor().equals(s))
                    myPseudosTMP.add(p);
            }
            myPseudos.setValue(myPseudosTMP);
            haveLoaded = true;
            lock = false;
        }
    }


}
