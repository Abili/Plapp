package com.raisac.plapp;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import static android.app.Activity.RESULT_OK;
import static com.raisac.plapp.CreatePlayList.SONG_PICKER;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment implements View.OnClickListener {


    private final static String TAG = "Tab1";
    private static final int RC_SIGN_IN = 1;
    TextView playlis_name, user_name, timestamp, numberOfPlaylists;

    String uName = null;
    RecyclerView recyclerView, mUserList;
    ArrayList<UsernameModule> list = new ArrayList<UsernameModule>();
    createPlaylistAdapter songAdapter;
    LinearLayoutManager linearLayoutManager, linearLayoutManager2, linearLayoutManager3;
    DividerItemDecoration dividerItemDecoration;
    Button send, cancel;
    Button sendList;
    FloatingActionButton addsongTodialog;
    RecyclerView Resusername;
    Context context;
    ArrayList<UsernameModule> editModelArrayList = new ArrayList<>();
    ValueEventListener valueEventListener;
    DatabaseReference mDatabaseReference, mDatabaseUsers, mPlayListRef;
    RecyclerView plsongs, nameRecycler;
    ArrayList<SongObject> songList;
    ArrayList<UserObject> contactList, userList;
    FloatingActionButton createplaylis;
    SongAdapter mAdapter;
    FirebaseUser user;
    ArrayList<Songs> songsArrayList;
    Uri selectedSong;
    String mUsername;
    Task<Uri> downloadUri;
    FirebaseRecyclerAdapter<Playlist, myViewHolder> recyclerAdapter;
    FirebaseUser mFirebaseUser;
    private FirebaseAuth mAuth;
    EditText plName;
    String Playlistname;
    private FirebaseUser mCurrentUser;
    private DatabaseReference songsDatabase;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseStorage storage;
    private StorageReference mStorageReference;
    private ChildEventListener mChildEventListener;
    private SongAdapter nameAdapter;
    private ArrayList<PlaylistModule> nameList;
    private PlaylistSongsAdapter playlistSongsAdapter;
    private ArrayList<Playlist> _songs = new ArrayList<Playlist>();
    private ArrayList<SongInfo> s_ongs = new ArrayList<SongInfo>();
    private ArrayList<PlaylistModule> _Pl_songs = new ArrayList<PlaylistModule>();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    ValueEventListener mValueEventListener;
    TextView playname;
    TextView Username;
    public Tab1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_playlist_model, container, false);
        createplaylis = rootView.findViewById(R.id.fab_createsong);
        RecyclerView recyclerView = rootView.findViewById(R.id.addmusichere);

        nameRecycler = getActivity().findViewById(R.id.user_plalistnameRecyce);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        //String user_id = mCurrentUser.getUid();
        songsDatabase = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.songs_node));
        mPlayListRef = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.playlists_node));
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child(getString(R.string.users_node));
        mUserList = rootView.findViewById(R.id.dialog_createPlst);
        songList = new ArrayList<>();

        plsongs = rootView.findViewById(R.id.playlistsongrecycler);

        mStorageReference = FirebaseStorage.getInstance().getReference();

        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDatabaseReference = mFirebaseDatabase.getInstance().getReference();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                if (user1 != null) {

                    String mUsername = mCurrentUser.getDisplayName();
                    if (mCurrentUser.getPhotoUrl() != null) {
                    }


                } else {
                    startActivity(new Intent(getContext(), Register.class));
                }
            }
        };

        createplaylis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNamedialog();
            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query1 = reference.child(getString(R.string.users_node))
                .orderByKey()
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    User user = singleSnapshot.getValue(User.class);
                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: " + user.toString());

                    playname = getActivity().findViewById(R.id.homePlayname);
                    Username = getActivity().findViewById(R.id.homeUsername);
                    playname.setText(user.getUsername());

                    ImageView Image = getActivity().findViewById(R.id.homeimageView);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabaseUsers.addListenerForSingleValueEvent(mValueEventListener);



        String userid = mAuth.getUid();
        Query query = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.playlists_node));
            FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Playlist>()
                    .setQuery(query, Playlist.class)
                    .build();

            recyclerAdapter = new FirebaseRecyclerAdapter<Playlist, myViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Playlist model) {
                   uName = getActivity().getIntent().getExtras().getString("username");
                    final String post_key = getRef(position).getKey();
                    holder.playNameTextView.setText(model.getPlay_name());
                    holder.senderTextView.setText(uName);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), Playlist_model.class);
                            intent.putExtra("post_id", post_key);
                            startActivity(intent);
                        }
                    });
                }

                @NonNull
                @Override
                public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.playlist_module, viewGroup, false);
                    return new myViewHolder(view);
                }
            };
            nameRecycler = getActivity().findViewById(R.id.user_plalistnameRecyce);
            nameRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            nameRecycler.setAdapter(recyclerAdapter);

        }



    private void loadSongs() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("audio/*");
        startActivityForResult(intent, SONG_PICKER);

    }


    private void loadNamedialog() {

        final Dialog playName = new Dialog(getContext());
        playName.setCanceledOnTouchOutside(false);
        playName.setContentView(R.layout.pla_name);
        playName.setTitle("Create Playlist");
        playName.show();

        final Button cancel;
        final Button create;
        cancel = playName.findViewById(R.id.dialog_cancel);
        create = playName.findViewById(R.id.confirmname);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playName.dismiss();
            }
        });
        plName = playName.findViewById(R.id.pl_caption);


        plName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() > 0) {
                    create.setEnabled(true);

                } else {
                    create.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = mAuth.getUid();
                Playlistname = plName.getText().toString();
                Playlist playlist = new Playlist(Playlistname, null, user_id);
                mPlayListRef.child(user_id).push().setValue(playlist);
                loadSongsDialog();
                playName.dismiss();

            }

            private void loadSongsDialog() {
                final Dialog dialogCplaylist = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_DarkActionBar);
                dialogCplaylist.setTitle("create playlist");
                dialogCplaylist.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                dialogCplaylist.setContentView(R.layout.activity_play_list_songs);
                dialogCplaylist.setCanceledOnTouchOutside(false);
                FloatingActionButton addsongTodialog = dialogCplaylist.findViewById(R.id.addsongtoDialog);
                final TextView playlistname = dialogCplaylist.findViewById(R.id.playlistCaption);
                dialogCplaylist.show();


                songAdapter = new createPlaylistAdapter(getContext(), s_ongs);
                recyclerView = dialogCplaylist.findViewById(R.id.playlistsongrecycler);
                recyclerView.setAdapter(songAdapter);
                recyclerView.setLayoutManager(linearLayoutManager2);

                linearLayoutManager2 = new LinearLayoutManager(getContext());
                dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        linearLayoutManager2.getOrientation());

                final Button confirmButton = dialogCplaylist.findViewById(R.id.sendListfab);

                recyclerView.addItemDecoration(dividerItemDecoration);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialogCplaylist.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        confirmButton.setVisibility(View.GONE);
                        Playlist playlist = new Playlist(Playlistname,downloadUri.toString(), mCurrentUser.getUid());
                        dialogCplaylist.dismiss();

                    }
                });

                addsongTodialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadSongs();

                    }
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    checkUserPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void checkUserPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 123);
                return;
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SONG_PICKER && resultCode == RESULT_OK) {
            Uri selectedSong = data.getData();
            if (data != null) {
                Log.i(TAG, "Uri:" + selectedSong.toString());
                showSong(selectedSong);
            }

        }
    }

    private void showSong(final Uri uri) {

        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor = getActivity().getContentResolver()
                .query(uri, null, selection, null, null, null);

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


                final Playlist playlist = new Playlist(Playlistname, displayName, mCurrentUser.getUid());
                String key = mPlayListRef.getKey();

                mStorageReference = FirebaseStorage.getInstance().getReference();
                putSongInStorage(mStorageReference, uri, key);

                s_ongs = new ArrayList<SongInfo>();
                s_ongs.add(new SongInfo(displayName));
                songAdapter = new createPlaylistAdapter(getContext(), s_ongs);
                recyclerView.setAdapter(songAdapter);
                recyclerView.setLayoutManager(linearLayoutManager3);
                linearLayoutManager3 = new LinearLayoutManager(context);
                dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        linearLayoutManager3.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
            }
        } finally {
            cursor.close();
        }
    }

    private void putSongInStorage(StorageReference mStorageReference, Uri uri, String key) {
        final String userid = mAuth.getUid();
        StorageReference ref = mStorageReference.child(userid).child(uri.getLastPathSegment());
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String username = (String) dataSnapshot.child(userid).child("username").getValue();
                        Playlist playlist = new Playlist(Playlistname, downloadUri.toString(), username);
                        mPlayListRef.child(userid).child("songs").push().setValue(playlist);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendListfab:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
        recyclerAdapter.startListening();
    }
//private ArrayList<PlaylistNameModel> populateList() {


    @Override
    public void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(mAuthStateListener);
        recyclerAdapter.stopListening();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView playNameTextView, PLamount, senderTextView;


        public myViewHolder(View v) {
            super(v);
            playNameTextView = itemView.findViewById(R.id.PLname);
            PLamount = itemView.findViewById(R.id.PLcount);
            senderTextView = itemView.findViewById(R.id.PLusername);
        }
    }

}



