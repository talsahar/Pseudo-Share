package com.tal.pseudo_share.model.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.utilities.Callback;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 06/01/2018.
 */

public class PseudoFirebase {

    static Query query;



    public static void getAllPseudosAndObserve(@Nullable Long lastUpdate, final Callback<List<Pseudo>> callback) {
        query = FirebaseDatabase.getInstance().getReference("pseudos");
        query = query.orderByChild("lastUpdate").startAt(lastUpdate);

        query.addValueEventListener(new ValueEventListener() {
            List<Pseudo> list = new LinkedList<Pseudo>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren())
                    list.add(snap.getValue(Pseudo.class));
                callback.call(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.call(list);
            }
        });
    }

    public static void addPseudo(final Pseudo pseudo, final Callback<Pseudo> onComplete) {
        Log.d("TAG", "Adding pseudo to firebase name:" + pseudo.getName());
        HashMap<String, Object> map = pseudo.toJson();
        FirebaseDatabase.getInstance().getReference("pseudos").child(pseudo.getId()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onComplete.call(pseudo);
            }
        });
    }

    public static void deletePseudo(final Pseudo pseudo) {
        Log.d("TAG", "Deleting pseudo from firebase name:" + pseudo.getName());
        pseudo.setDeleted(true);
        HashMap<String, Object> map = pseudo.toJson();
        FirebaseDatabase.getInstance().getReference("pseudos").child(pseudo.getId()).updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
            }
        });
    }

    public static void releaseBinding(final Callback<Void> onComplete) {
        if(query!=null)
            query.removeEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    onComplete.call(null);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    onComplete.call(null);
                }
            });
    }
}
