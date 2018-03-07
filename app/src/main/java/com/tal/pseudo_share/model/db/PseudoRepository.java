package com.tal.pseudo_share.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.net.Uri;

import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.AuthenticationRepository;
import com.tal.pseudo_share.model.imageStorage.ImageStorageManager;
import com.tal.pseudo_share.utilities.Callback;
import com.tal.pseudo_share.utilities.ExceptionHandler;
import com.tal.pseudo_share.viewmodel.PseudoListLiveData;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by User on 06/01/2018.
 */

public class PseudoRepository {

    static PseudoRepository instance;

    PseudoListLiveData pseudoListLiveData;
    private Semaphore  semTask;


    public void releaseBinding(){
        PseudoFirebase.releaseBinding(new Callback<Void>() {
            @Override
            public void call(Void data) {

            }
        });
    }

    public static PseudoRepository getInstance() {
        if (instance==null)
            instance=new PseudoRepository();
        return instance;
    }

    private PseudoRepository() {
        semTask = new Semaphore(1);

        String username = AuthenticationRepository.getInstance().getCurrUsername();
        if (username == null)
            throw new NullPointerException("null username reference");
        else pseudoListLiveData = new PseudoListLiveData(username);
    }

    public void storePseudo(final Pseudo pseudo, Bitmap imageBitmap, final Runnable onComplete) {
        if(imageBitmap!=null)
        ImageStorageManager.storeImage(imageBitmap, pseudo.getId(), new Callback<Uri>() {
            @Override
            public void call(Uri data) {
                if (data != null)
                    pseudo.setImageUrl(data.toString());
                PseudoFirebase.addPseudo(pseudo, new Callback<Pseudo>() {
                    @Override
                    public void call(Pseudo data) {
                        MyStorage.database.pseudoDao().insert(pseudo);
                        onComplete.run();
                    }
                });
            }
        }, new Callback<Exception>() {
            @Override
            public void call(Exception data) {
                ExceptionHandler.set(data);
            }
        });
        else//no image
            PseudoFirebase.addPseudo(pseudo, new Callback<Pseudo>() {
                @Override
                public void call(Pseudo data) {
                    MyStorage.database.pseudoDao().insert(pseudo);
                    onComplete.run();
                }
            });
    }


    public PseudoListLiveData getAllPseudos() {
        long lastUpdateDate = MyStorage.getLastUpdate();

            PseudoFirebase.getAllPseudosAndObserve(lastUpdateDate, new Callback<List<Pseudo>>() {
                @Override
                public void call(List<Pseudo> data) {

                    Runnable onComplete = new Runnable() {
                        @Override
                        public void run() {
                            PseudoFirebase.releaseBinding(new Callback<Void>() {
                                @Override
                                public void call(Void data) {
                                    getAllPseudos();
                                }
                            });
                        }
                    };

                    new ServerDataUpdateHandler(semTask, pseudoListLiveData,onComplete).execute(data);
                }
            });

        return pseudoListLiveData;
    }

    public Pseudo getPseudoById(final String id) {
        for (Pseudo p : pseudoListLiveData.getAllPseudosLiveData().getValue()) {
            if (p.getId().equals(id))
                return p;
        }
        return null;
    }

    public void deletePseudo(final Pseudo pseudo) {
        if(pseudo.getImageUrl()!=null)
        ImageStorageManager.deleteImage(pseudo.getImageUrl(), true);
        MyStorage.database.pseudoDao().delete(pseudo);
        pseudoListLiveData.removeIfContains(pseudo);

        PseudoFirebase.deletePseudo(pseudo);
    }

}
