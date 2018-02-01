package com.tal.pseudo_share.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.AuthenticationRepository;
import com.tal.pseudo_share.utilities.Callback;
import com.tal.pseudo_share.viewmodel.PseudoListLiveData;
import com.tal.pseudo_share.viewmodel.StaticMutablesHolder;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by User on 06/01/2018.
 */

public class PseudoRepository {

    PseudoListLiveData pseudoListLiveData;
    Callback<Exception> exceptionCallback;
    private Semaphore semQuery, semTask;




    public static class Holder {
        static PseudoRepository instance;
    }

    //called on login to prevent preview authorized user data collisions **have to be called on after authorization**.
    public static PseudoRepository initInstance(){
        MyStorage.firebaseDb.reset();
        Holder.instance=new PseudoRepository();
        return Holder.instance;
    }

    public static PseudoRepository getInstance() {
        return Holder.instance;
    }

    private PseudoRepository() {
        semQuery = new Semaphore(1);
        semTask = new Semaphore(1);
        exceptionCallback = new Callback<Exception>() {
            @Override
            public void call(Exception exception) {
                exception.printStackTrace();
                StaticMutablesHolder.exceptionMutableLiveData.setValue(exception);
                StaticMutablesHolder.progressStatus.setValue(false);
            }
        };
        String username = AuthenticationRepository.getCurrUsername();
        if (username == null)
            throw new NullPointerException("null username reference");
        else pseudoListLiveData = PseudoListLiveData.getInstance(username);
    }

    public void storePseudo(final Pseudo pseudo, Bitmap imageBitmap, final Runnable onComplete) {
        StaticMutablesHolder.progressStatus.setValue(true);
        MyStorage.imageStorageManager.storeImage(imageBitmap, pseudo.getImageFileName(), new Callback<Uri>() {
            @Override
            public void call(Uri data) {
                if (data != null)
                    pseudo.setImageUrl(data.toString());
                MyStorage.firebaseDb.addPseudo(pseudo, new Callback<Pseudo>() {
                    @Override
                    public void call(Pseudo data) {
                        MyStorage.database.pseudoDao().insert(pseudo);
                        onComplete.run();
                        StaticMutablesHolder.progressStatus.setValue(false);
                    }
                });
            }
        }, exceptionCallback);
    }


    public LiveData<List<Pseudo>> getAllPseudos() {
        StaticMutablesHolder.progressStatus.setValue(true);
        long lastUpdateDate = MyStorage.getLastUpdate();

        boolean hasLocked = semQuery.tryAcquire();
        if (hasLocked) {
            MyStorage.firebaseDb.getAllPseudosAndObserve(lastUpdateDate, new Callback<List<Pseudo>>() {
                @Override
                public void call(List<Pseudo> data) {
                    new RecentPseudosHandler(semTask, pseudoListLiveData, new Runnable() {
                        @Override
                        public void run() {
                            StaticMutablesHolder.progressStatus.setValue(false);
                        }
                    }).execute(data);
                }
            });
        } else {
            StaticMutablesHolder.exceptionMutableLiveData.setValue(new Exception("You are blocked on get all pseudos"));
        }

        return pseudoListLiveData;
    }


    public LiveData<Bitmap> loadPseudoDrawable(String path) {
        StaticMutablesHolder.progressStatus.setValue(true);
        final MutableLiveData<Bitmap> drawableMutableLiveData = new MutableLiveData<>();
        MyStorage.imageStorageManager.loadImage(path, new Callback<Bitmap>() {
            @Override
            public void call(Bitmap data) {
                drawableMutableLiveData.setValue(data);
                StaticMutablesHolder.progressStatus.setValue(false);
            }
        }, new Callback<Exception>() {
            @Override
            public void call(Exception data) {
                exceptionCallback.call(data);
            }
        });
        return drawableMutableLiveData;
    }

    public LiveData<Pseudo> loadPseudoById(final String id) {
        final MutableLiveData<Pseudo> pseudo = new MutableLiveData<>();
        for (Pseudo p : pseudoListLiveData.getValue()) {
            if (p.getId().equals(id)) {
                pseudo.setValue(p);
                return pseudo;
            }
        }
        return pseudo;
    }

    public void deletePseudo(final Pseudo pseudo) {
        StaticMutablesHolder.progressStatus.setValue(true);
        MyStorage.imageStorageManager.deleteImage(pseudo.getImageUrl(), true);
        MyStorage.database.pseudoDao().delete(pseudo);
        pseudoListLiveData.removeIfContains(pseudo);


        MyStorage.firebaseDb.deletePseudo(pseudo, new Callback<Pseudo>() {
            @Override
            public void call(Pseudo data) {
                StaticMutablesHolder.progressStatus.setValue(false);
            }
        });
    }

    public LiveData<List<Pseudo>> getMyPseudos() {
        return pseudoListLiveData.getMyPseudosLiveData();
    }



}
