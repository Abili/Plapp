package com.raisac.plapp;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;

public class FirebaseHelper {

    DatabaseReference db;
    FirebaseStorage storage;
    StorageReference storageRef;
    Boolean saved = null;
    ArrayList<String> songInfo = new ArrayList<>();

    public FirebaseHelper(StorageReference db) {
        //this.db = db;
    }

    //SAVE
    public Boolean save(SongInfo songInfo) {
        Uri selectedSong = Uri.parse(songInfo.getSongname());


        if (songInfo == null) {
            saved = false;
        } else {

            try {
                storageRef = storage.getInstance().getReference("chat_photos");
                final UploadTask photoRef = (UploadTask) storageRef.putFile(selectedSong).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }

        }

        return saved;
    }

    //READ
    public ArrayList<String> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return songInfo;
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        songInfo.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            String name = ds.getValue(SongInfo.class).getSongname();
            songInfo.add(name);
        }
    }

}