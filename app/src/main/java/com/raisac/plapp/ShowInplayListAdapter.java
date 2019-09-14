package com.raisac.plapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class ShowInplayListAdapter extends RecyclerView.Adapter<ShowInplayListAdapter.ViewHolder>{
    private Context context;
    private ArrayList<SongInfo> songInfos;

    public ShowInplayListAdapter(Context context, ArrayList<SongInfo>songInfos){
        this.context =context;
        this.songInfos =songInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.socialsong_module, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SongInfo songInfo =songInfos.get(position);
        holder.songnameHolder.setText(songInfo.getSongname());
    }

    @Override
    public int getItemCount() {
        return songInfos.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView songnameHolder;

        public ViewHolder(View itemView){
            super(itemView);

            songnameHolder = itemView.findViewById(R.id.songsocialname);
        }

    }
}
