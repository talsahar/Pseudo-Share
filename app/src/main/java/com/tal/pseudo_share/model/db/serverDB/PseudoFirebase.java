package com.tal.pseudo_share.model.db.serverDB;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.utils.MyApplication;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 06/01/2018.
 */

public class PseudoFirebase {
    public interface Callback<T> {
        void onComplete(T data);
    }
    public static void getAllPseudosAndObserve(@Nullable Long lastUpdate, final Callback<List<Pseudo>> callback){
        Log.d("TAG","getAllPseudoAndObserve from firebase");
        Query query=FirebaseDatabase.getInstance().getReference("pseudos");
        query=query.orderByChild("lastUpdate").startAt(lastUpdate);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             List<Pseudo> list=new LinkedList<Pseudo>();
             for(DataSnapshot snap:dataSnapshot.getChildren())
                 list.add(snap.getValue(Pseudo.class));
             callback.onComplete(list);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //callback.onComplete(null);
                }
        });
    }

    public static void addPseudo(Pseudo pseudo,OnCompleteListener<Void> onComplete){
        Log.d("TAG","adding pseudo to firebase");
        HashMap<String,Object> map=pseudo.toJson();
        FirebaseDatabase.getInstance().getReference("pseudos").child(pseudo.id).setValue(map).addOnCompleteListener(onComplete);
    }

}
