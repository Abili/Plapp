package com.raisac.plapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class CreatePlayList extends AppCompatActivity implements SelectebleVewholder.OnItemSelectedListener {

    static final int SONG_PICKER = 1;
    private static final String TAG = "CreatePlayList";
    FirebaseStorage storage;
    StorageReference storageReference;
    SelectableAdapter songAdapter;
    Dialog dialog;
    SelectableAdapter Sadapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DividerItemDecoration dividerItemDecoration;
    CheckBox checkBox;
    FloatingActionButton sendList;
    StorageReference storageRef;
    EditText playlistname;
    createPlaylistAdapter createplaylistadapter;
    ChildEventListener childEventListener;
    FirebaseDatabase firebaseDatabase;
    RecyclerTouchListener recyclerTouchListener;

    Context context;
    DatabaseReference databaseReference;
    FirebaseHelper helper;
    String Plcaption;

    private ArrayList<SongInfo> _songs = new ArrayList<SongInfo>();
    private ArrayList<PlaylistModule> playListAdapterList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();


        recyclerView = findViewById(R.id.addmusichere);
        //recyclerView.setAdapter(songAdapter);


        FloatingActionButton fab = findViewById(R.id.fabcreatePlaylist);
        checkUserPermission();
        init();
    }

    public void init() {

    }

    private void checkUserPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                return;
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadSongs();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    checkUserPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }

    }

    private void loadSongs() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("audio/*");
        startActivityForResult(intent, SONG_PICKER);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SONG_PICKER && resultCode == RESULT_OK) {
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            if (data != null) {
                uri = data.getData();
                Log.i(TAG, "Uri:" + uri.toString());
                showSong(uri);
            }

            // SongInfo s = new SongInfo(selectedImageUri.getLastPathSegment());
            //_songs.add(s);
        }

/*
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                    _songs.add(new SongInfo(name, artist, url));
                    createPlaylistAdapter songAdapter= new createPlaylistAdapter(this, _songs);
                    recyclerView.setAdapter(songAdapter);

                    recyclerView.setLayoutManager(linearLayoutManager);

                    linearLayoutManager = new LinearLayoutManager(CreatePlayList.this);
                    dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                            linearLayoutManager.getOrientation());
                    recyclerView.addItemDecoration(dividerItemDecoration);


                } while (cursor.moveToNext());
            }

            cursor.close();
             }*/

    }

    private void showSong(Uri uri) {
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor = getContentResolver()
                .query(uri, null, selection, null, null, null);

        try {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {

                // Note it's called "Display Name".  This is
                // provider-specific, and might not necessarily be the file name.
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                //Log.i(TAG,"Display Name: " + displayName);

                //String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                //String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                //_songs = new ArrayList<>();


                _songs.add(new SongInfo(displayName));
                songAdapter = new SelectableAdapter(this, _songs, false);
                recyclerView.setAdapter(songAdapter);

                recyclerView.setLayoutManager(linearLayoutManager);

                linearLayoutManager = new LinearLayoutManager(CreatePlayList.this);
                dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        linearLayoutManager.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);

                //int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            }
        } finally {
            cursor.close();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Homesettings:
                Intent settings = new Intent(this, Settings.class);


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(SelectableItem item) {
        List<SongInfo> selectedItems = Sadapter.getSelectedItems();
        Snackbar.make(recyclerView,"Selected item is "+ item.getSongname()+
                ", Totally  selectem item count is "+selectedItems.size(),Snackbar.LENGTH_LONG).show();
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


}
