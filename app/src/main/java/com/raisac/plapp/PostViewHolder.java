package com.raisac.plapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class PostViewHolder extends RecyclerView.ViewHolder {

    TextView tvPlalistName, tvUsername;
    //CheckBox checkBox;
    //FloatingActionButton sendList;



    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
    }


    public void setTitle(String listName) {
        tvPlalistName = itemView.findViewById(R.id.playlist_username);
        tvPlalistName.setText(listName);
    }

    public void setSongUrl(Context context, ArrayList<SongInfo> songUrl) {
        //tvSongArtist = itemView.findViewById(R.id.playlist_title);
        //tvSongArtist.setText(songUrl);
    }

    public void setUsername(String author) {
        tvUsername = itemView.findViewById(R.id.PLusername);
        tvUsername.setText(author);

    }
}
