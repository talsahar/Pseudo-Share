package com.tal.pseudo_share.view;

import android.app.Activity;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;


/**
 * Created by User on 15/12/2017.
 */

public class ProgressBarHandler {
    int progress;
    RingProgressBar progressBar;
    Timer timer;
    Runnable onComplete;

    public ProgressBarHandler(final Activity mainActivity, RingProgressBar progressBar){
        this.progressBar=progressBar;
        progressBar.setOnProgressListener(new RingProgressBar.OnProgressListener() {
            @Override
            public void progressToComplete() {
                timer.cancel();
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hide();
                    }
                });
                if(onComplete!=null)
                    onComplete.run();
            }
        });
    }




    public void setOnComplete(Runnable runnable){
        onComplete=runnable;
    }

    public void show(){
        timer=new Timer(true);
        progressBar.setVisibility(View.VISIBLE);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(progress<100)
                {
                    progress++;
                    progressBar.setProgress(progress);
                }            }
        }, 0, 10);
    }

    //should called from the main thread runOnUiThread
    public void hide(){
        progressBar.setVisibility(View.GONE);
        progress=0;
        progressBar.setProgress(progress);

    }

}
