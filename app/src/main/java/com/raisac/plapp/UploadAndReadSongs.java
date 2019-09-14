package com.raisac.plapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static com.raisac.plapp.CreatePlayList.SONG_PICKER;

public class UploadAndReadSongs extends AppCompatActivity {
    LinearLayoutManager layoutManager;
    FirebaseDatabase mdatabase;
    DatabaseReference mdatabaseReference;
    FirebaseStorage storage;
    createPlaylistAdapter songAdapter;
    RecyclerView downloadong;
    StorageReference mStorageReference;
    ChildEventListener mchildEventListener;
    DividerItemDecoration itemDecoration;

    ArrayList<SongInfo> _songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_and_read_songs);
        _songs = new ArrayList<>();

        mdatabaseReference = mdatabase.getInstance().getReference().child("Plapp");
        downloadong = findViewById(R.id.downloadsongs);
        layoutManager = new LinearLayoutManager(this);
        downloadong.setLayoutManager(layoutManager);


        Query posQuery = mdatabase.getInstance().getReference().child("Plapp");
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<SongInfo>()
                .setQuery(posQuery, SongInfo.class)
                .build();
        FirebaseRecyclerAdapter<SongInfo, SongViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<SongInfo, SongViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SongViewHolder holder, int position, @NonNull SongInfo model) {
                final DatabaseReference postRef = getRef(position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent intent = new Intent();
                    }
                });

            }

            @NonNull
            @Override
            public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_module, parent, false);
                return new SongViewHolder(view);
            }
        };
        downloadong.setAdapter(recyclerAdapter);


    }


    public void clickMe(View view) {


        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("audio/*");
        startActivityForResult(intent, SONG_PICKER);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SONG_PICKER && resultCode == RESULT_OK) {
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            if (data != null) {
                uri = data.getData();
                //Log.i(TAG, "Uri:" + uri.toString());
                showSong(uri);
            }

        }
    }

    private void showSong(Uri uri) {

        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor = getContentResolver()
                .query(uri, null, selection, null, null, null);

        // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
        // "if there's anything to look at, look at it" conditionals.
        if (cursor != null && cursor.moveToFirst()) {

            // Note it's called "Display Name".  This is
            // provider-specific, and might not necessarily be the file name.
            final String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            // final String artistname =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            //Log.i(TAG,"Display Name: " + displayName);


            SongInfo songInfo = new SongInfo(displayName);
            _songs = new ArrayList<>();
            _songs.add(songInfo);
            songAdapter = new createPlaylistAdapter(this, _songs);
            downloadong.setAdapter(songAdapter);


            downloadong.setLayoutManager(layoutManager);

            layoutManager = new LinearLayoutManager(this);

            //final String user_id = mAuth.getCurrentUser().getUid();
            //final DatabaseReference songs = FirebaseDatabase.getInstance().getReference("songs").child(user_id);
            mStorageReference = storage.getInstance().getReference("songs");
            StorageReference audioRef = mStorageReference.child(displayName);


            audioRef.putFile(uri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                    SongInfo songInfo = new SongInfo(downloadUrl.toString());
                    mdatabaseReference.push().setValue(songInfo);

                    //mSongsDRef.push().setValue(songInfo);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}