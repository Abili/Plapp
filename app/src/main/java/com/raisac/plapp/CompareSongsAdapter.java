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

public class CompareSongsAdapter extends RecyclerView.Adapter<CompareSongsAdapter.MyViewHolder> {

    public TextView username, count, playlistname;
    private ArrayList<comparesongsModule> comparesongsArrayList;

    public CompareSongsAdapter(Context context,ArrayList<comparesongsModule> playListAdapterList) {

        this.comparesongsArrayList = playListAdapterList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_compare_songs_module, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        comparesongsModule comparesongsadapter = comparesongsArrayList.get(position);
        holder.songname1.setText(comparesongsArrayList.get(position).getSongname());
        holder.songname2.setText(comparesongsArrayList.get(position).getSongname2());
        holder.artistname1.setText(comparesongsArrayList.get(position).getArtistname());
        holder.artistname2.setText(comparesongsArrayList.get(position).getArtistname2());
        holder.count2.setText(comparesongsArrayList.get(position).getCount2());
        holder.timepost.setText(comparesongsArrayList.get(position).getTimeofPosting());
        holder.count1.setText(comparesongsArrayList.get(position).getCount1());

        // holder.userpic.setImageResource(playListAdapterList.get(position).PlaylistName);



    }

    @Override
    public int getItemCount() {
        return comparesongsArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView songname1, songname2;
        TextView count1,count2;
        TextView artistname1,artistname2, timepost;


        public MyViewHolder(View itemView) {
            super(itemView);
            songname1 = itemView.findViewById(R.id.comparesong_name);
            songname2 = itemView.findViewById(R.id.comparesong_name2);
            artistname1 = itemView.findViewById(R.id.compareArtistname);
            artistname2 = itemView.findViewById(R.id.compareArtistname2);
            count1 = itemView.findViewById(R.id.compareVoteCount);
            count2 = itemView.findViewById(R.id.compareVoteCount2);
            timepost = itemView.findViewById(R.id.postTiming);

        }
    }
}
