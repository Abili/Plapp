package com.raisac.plapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.playlistHolder>{
    Context context;
    private ArrayList<New_playlist> playlistNames =new ArrayList<>();

    @NonNull
    @Override
    public NewAdapter.playlistHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(context).inflate(R.layout.new_playlist_model,viewGroup, false );

        return new NewAdapter.playlistHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewAdapter.playlistHolder playlistHolder, int i) {

        //playlistHolder.username.setText(New_playlist.class);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class playlistHolder extends RecyclerView.ViewHolder {
        TextView username, playlisname, playlistcount, recommended;


        public playlistHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_new_playlist_model);
            playlisname = itemView.findViewById(R.id.playlistname_new_playlist_model);
            playlistcount = itemView.findViewById(R.id.playlistCount_new_palaylist_model);
            recommended = itemView.findViewById(R.id.recommended_new_palaylist_model);
        }
    }
}

