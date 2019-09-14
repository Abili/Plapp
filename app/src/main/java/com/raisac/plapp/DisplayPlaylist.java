package com.raisac.plapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisplayPlaylist extends AppCompatActivity {

    ArrayList<SongInfo> display_modules;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mSongsDRef, mNames, mPlaylisnames, mDTimeposted;
    createPlaylistAdapter displayAdapter;
    RecyclerView recyclerView, namesRecy;
    ChildEventListener mChildEventListener;
    //PlaylistDisplayAdapter Padapter;
    ArrayList<Playlist_Display_Module> P_modules;
    String muUserName;
    String mPlaylist;
    String mTimeposted;

    TextView displaname, playlist, timePosted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_playlist);
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        recyclerView = findViewById(R.id.display_plylist);
        namesRecy = findViewById(R.id.display_plylist);
        displaname = findViewById(R.id.playlist_username);
        playlist = findViewById(R.id.playlist_title);
        timePosted = findViewById(R.id.playlist_postTime);

        /*if the music posted does not belong to the viewer then post other peoples songs in this recyclerview */


        String userid = FirebaseAuth.getInstance().getUid();
        mSongsDRef = mFirebaseDatabase.getReference().child("Users").child("Playlists").child("playlist_name").child("songs").child(userid);

        mSongsDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    SongInfo songInfo = dataSnapshot.getValue(SongInfo.class);
                    display_modules.add(songInfo);
                    displayAdapter = new createPlaylistAdapter(DisplayPlaylist.this, display_modules);
                    recyclerView.setAdapter(displayAdapter);
                    recyclerView.hasFixedSize();
                    recyclerView.setLayoutManager(new LinearLayoutManager(DisplayPlaylist.this));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        String NamesUser_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mNames = mFirebaseDatabase.getReference().child("Users").child(NamesUser_id);
        mNames.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {

                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("name") != null) {
                        muUserName = map.get("name").toString();
                        displaname.setText(muUserName);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        String PL_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mPlaylisnames = mFirebaseDatabase.getReference().child("Users").child("Playlists").child("playlist_name").child(PL_user_id);
        mPlaylisnames.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("name") != null) {
                        mPlaylist = map.get("name").toString();
                        playlist.setText(mPlaylist);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        mSongsDRef.addChildEventListener(mChildEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSongsDRef.removeEventListener(mChildEventListener);

    }
}
