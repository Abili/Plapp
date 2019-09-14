package com.raisac.plapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.junit.internal.ArrayComparisonFailure;

import java.util.ArrayList;
import java.util.List;

public class PlayListSongs extends AppCompatActivity {

    private final static String TAG = "Tab1";
    private static final int RC_SIGN_IN = 1;
    final ArrayList<PlaylistModule> list = new ArrayList<>();
    Dialog dialog;
    Dialog playName;
    RecyclerView recyclerView, nameRecycler;
    createPlaylistAdapter songAdapter;
    LinearLayoutManager linearLayoutManager, linearLayoutManager2, linearLayoutManager3;
    DividerItemDecoration dividerItemDecoration;
    FloatingActionButton addsongTodialog;
    Button confirmBtn;
    Context context;
    ArrayList<PlaylistNameModel> editModelArrayList = new ArrayList<>();
    ValueEventListener valueEventListener;
    DatabaseReference mDatabaseReference;
    FirebaseDatabase mFirebaseDatabase;
    RecyclerView mUserList;
    DatabaseReference mDatabaseUsers, mPlayListRef;
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    ArrayList<UserObject> contactList, userList;
    RecyclerView songsRecyclerview;
    PlaylistSongsAdapter mAdapter;
    TextView tPlaylistnmae, tUsername, tPosting_time;
    String post_id = null;
    private FirebaseStorage storage;
    private StorageReference mStorageReference;
    private ChildEventListener mChildEventListener;
    private UsernameAdapter nameAdapter;
    private ArrayList<PlaylistModule> nameList;
    private PlaylistSongsAdapter playlistSongsAdapter;
    private ArrayList<SongInfo> _songs = new ArrayList<SongInfo>();
    private ArrayList<PlaylistModule> _Pl_songs = new ArrayList<PlaylistModule>();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private RecyclerView.Adapter mUserListAdapter;
    //private FirebaseDatabase mFirebaseDatabase;
    //private DatabaseReference mDatabaseReference;
    private ArrayList<PlaylisSongsModule> nSongsModule = new ArrayList<>();
    private ArrayList<List> songInfos;
    private RecyclerView.Adapter adapter;
    private FirebaseRecyclerAdapter<Post, PostViewHolder> nAdapter;
    SongAdapter mSongAdapter;
    private static final int UploadSongCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list_songs);

        songsRecyclerview = findViewById(R.id.playlistsongrecycler);

        confirmBtn = findViewById(R.id.sendListfab);
        addsongTodialog = findViewById(R.id.addsongtoDialog);
        addsongTodialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("audio/*");
                startActivityForResult(intent, UploadSongCode);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UploadSongCode && resultCode == RESULT_OK) {
            Uri selectedSong = data.getData();
            if (data != null) {
                loadSongs(selectedSong);
            }

        }

    }

    private void loadSongs(final Uri selectedSong) {

        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor = getContentResolver()
                .query(selectedSong, null, selection, null, null, null);

        try {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {

                // Note it's called "Display Name".  This is
                // provider-specific, and might not necessarily be the file name.
                final String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                // final String artistname =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                //Log.i(TAG,"Display Name: " + displayName);


                songsRecyclerview = findViewById(R.id.playlistsongrecycler);
                SongInfo songInfo = new SongInfo(displayName);
                _songs = new ArrayList<>();
                _songs.add(songInfo);
                songAdapter = new createPlaylistAdapter(this, _songs);
                songsRecyclerview.setAdapter(songAdapter);
                songsRecyclerview.setLayoutManager(linearLayoutManager3);

                linearLayoutManager3 = new LinearLayoutManager(context);
                dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        linearLayoutManager3.getOrientation());
                songsRecyclerview.addItemDecoration(dividerItemDecoration);


                Playlist playlist = new Playlist(null, null, null);
                mDatabaseUsers.push().setValue(playlist, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                        if (databaseError == null) {
                            String key = mDatabaseUsers.getKey();
                            StorageReference storageReference =
                                    FirebaseStorage.getInstance()
                                            .getReference(mAuth.getUid())
                                            .child(key).child(selectedSong.getLastPathSegment());

                            putSongInStorage(storageReference, selectedSong, key);
                        } else {
                            Log.w(TAG, "unable to write song to database0");
                        }
                    }
                });


            }
        } finally {
            cursor.close();
        }

    }

    private void putSongInStorage(StorageReference storageReference, Uri uri, final String key) {
        storageReference.putFile(uri).addOnCompleteListener(this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Playlist playlist = new Playlist(null, task.getResult().getStorage()
                            .getDownloadUrl().toString(), null);
                    mDatabaseUsers.child(key).setValue(playlist);
                } else {
                    Log.w(TAG, "image upload was not successful", task.getException());
                }
            }
        });

    }


}
