package com.raisac.plapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;



public class PlaylistViewHolder extends RecyclerView.ViewHolder {

    RecyclerView song;
    TextView playname, usaname;
    ArrayList<SongInfo> songInfoArrayList;
    SongAdapter songAdapter;
    //CheckBox checkBox;
    //FloatingActionButton sendList;



    public PlaylistViewHolder(@NonNull View itemView) {
        super(itemView);
        RecyclerView rv;
    }


    public void setSongUrl(ArrayList<SongInfo> songUrl) {
         song= itemView.findViewById(R.id.playSongs);

        // songAdapter = new SongAdapter(PlaylistViewHolder.this, songInfoArrayList);
        song.setAdapter(songAdapter);
    }

    public void setPlayName(String playName) {
        playname = itemView.findViewById(R.id.playlist_title);
        playname.setText(playName);
    }

    public void setUsername(String userName) {

        usaname = itemView.findViewById(R.id.playlist_username);
        usaname.setText(userName);
    }
}


