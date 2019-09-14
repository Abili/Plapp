package com.raisac.plapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    ArrayList<Songs> songsArrayList;
    private Tab1 mActivity;



    public SongAdapter(ArrayList<Songs> mSongsArrayList, Tab1 activity) {
        songsArrayList = mSongsArrayList;
        mActivity =activity;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_module, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        ViewHolder rcv = new ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Songs songs = (Songs)  songsArrayList.get(position);
        if(songs.user!=null) {


            holder.Uname.setText(songs.user.username);
            holder.songurl.setText(songs.downloadUrl);
        }
    }

    @Override
    public int getItemCount() {
        songsArrayList = new ArrayList<>();
        return songsArrayList.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Uname, songurl;
        RecyclerView recyclerView;
        public ViewHolder(View view){
            super(view);
            Uname = view.findViewById(R.id.songTitle);
            songurl = itemView.findViewById(R.id.artistname);
        }
    }

    public  void addSong(Songs songs){
        songsArrayList.add(0,songs);
        notifyDataSetChanged();
    }
}
