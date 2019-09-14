package com.raisac.plapp;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;



public class DialogCreatePlayList extends DialogFragment {

    private static final String TAG = "DialogCreatPlayList";

    private SeekBar mSeekBar;
    private EditText mPlaylistName;
    private TextView mCreatePlaylist, mCancelPlaylist;
    private int mUserSecurityLevel;
    private int mSeekProgress;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.new_home_create_dialog, container, false);
        mPlaylistName= view.findViewById(R.id.new_home_Edt_playlistname);
        mCreatePlaylist =  view.findViewById(R.id.new_home_Pl_createBtn);
        mCancelPlaylist =  view.findViewById(R.id.new_home_cancelBtn);
        mCreatePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mPlaylistName.getText().toString().equals("")){
                    Log.d(TAG, "onClick: creating new chat room");

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        //get the new chatroom unique id
                        String playlistId = reference
                                .child(getString(R.string.playlists_node))
                                .push().getKey();

                        //create the chatroom
                        Playlist playlist = new Playlist();
                        playlist.setPlay_name(mPlaylistName.getText().toString());
                        playlist.setTime_stamp(getTimestamp());
                        playlist.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        playlist.setPlaylist_id(playlistId);


                        //insert the new chatroom into the database
                        reference
                                .child(getString(R.string.playlists_node))
                                .child(playlistId)
                                .setValue(playlist);

                        //create a unique id for the message
                        String songid = reference
                                .child(getString(R.string.playlists_node))
                                .push().getKey();

                        //insert the first message into the chatroom
                        Songs songs = new Songs();

                        songs.setSong_url("Welcome to the new playlist");
                        songs.setTimestamp(getTimestamp());
                        reference
                                .child(getString(R.string.playlists_node))
                                .child(playlistId)
                                .child(getString(R.string.songs_node))
                                .child(songid)
                                .setValue(songs);
                        ((New_homePage)getActivity()).init();
                        getDialog().dismiss();
                    }else{
                        Toast.makeText(getActivity(), "insuffient security level", Toast.LENGTH_SHORT).show();
                    }

                }

        });

        return view;
    }

    /**
     * Return the current timestamp in the form of a string
     * @return
     */
    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));
        return sdf.format(new Date());
    }

}

















