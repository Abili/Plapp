package com.raisac.plapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class soclaSongsAdapter  extends RecyclerView.Adapter<soclaSongsAdapter.MyViewHolder> {

    public TextView username, count, playlistname;
    private ArrayList<socilaplayListmodule> socialplaylis;

    public soclaSongsAdapter(Context context,ArrayList<socilaplayListmodule> socialplaylis) {

        this.socialplaylis = socialplaylis;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.socialsong_module, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        socilaplayListmodule comparesongsadapter = socialplaylis.get(position);
        holder.songname.setText(socialplaylis.get(position).getSongsocialname());
        holder.songDuration.setText(socialplaylis.get(position).getSongsocialduration());
        holder.artistname.setText(socialplaylis.get(position).getArtistsocialname());


        // holder.userpic.setImageResource(playListAdapterList.get(position).PlaylistName);



    }

    @Override
    public int getItemCount() {
        return socialplaylis.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView songname, artistname;
        TextView songDuration;

        public MyViewHolder(View itemView) {
            super(itemView);
            songname = itemView.findViewById(R.id.songsocialname);
            artistname = itemView.findViewById(R.id.singersocialname);
            songDuration = itemView.findViewById(R.id.socialDuration);

        }
    }
}

