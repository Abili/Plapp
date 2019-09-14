package com.raisac.plapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class SongViewHolder  extends RecyclerView.ViewHolder {

    RecyclerView song;
    TextView sogname;
    SongAdapter songAdapter;


    public SongViewHolder(@NonNull View itemView) {
        super(itemView);
        RecyclerView rv;
    }
    public void bintPost(SongInfo songInfo, View.OnClickListener clickListener){
        sogname.setText(songInfo.getSongname());
    }
}