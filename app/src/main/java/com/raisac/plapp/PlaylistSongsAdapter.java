package com.raisac.plapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaylistSongsAdapter extends RecyclerView.Adapter<PlaylistSongsAdapter.MyViewHolder> {

    public TextView username, count, playlistname;
    private ArrayList<PlaylisSongsModule> pl_songslist;

    public PlaylistSongsAdapter(Context context, ArrayList<PlaylisSongsModule> pl_songslist) {

        this.pl_songslist = pl_songslist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pl_songs_module, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PlaylisSongsModule playListadapter = pl_songslist.get(position);
        holder.song_name.setText(pl_songslist.get(position).song_Title);
        //holder.song_duration.setText(pl_songslist.get(position).song_duration);
        //holder.song_size.setText(pl_songslist.get(position).song_size);
        //holder.userpic.setImageResource(playListAdapterList.get(position).PlaylistName);


    }

    @Override
    public int getItemCount() {
        return pl_songslist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView song_name;
        TextView song_duration;
        TextView song_size;

        public MyViewHolder(View itemView) {
            super(itemView);
            song_name = itemView.findViewById(R.id.song_title);
            song_name.setSingleLine(true);
            //song_duration = itemView.findViewById(R.id.PLcount);
            song_size = itemView.findViewById(R.id.size_song);
        }
    }
}
