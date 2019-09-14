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

public class SocialTabbAdapter extends RecyclerView.Adapter<SocialTabbAdapter.MyViewHolder> {

    public TextView username, count, playlistname;
    private ArrayList<socialTabModel> socialtab;
    private  Context context;
    SocialTabbAdapter socialTabbadapter;
    //private SongAdapter.OnItemClickListener mOnItemClickListener;


    public SocialTabbAdapter(Context context, ArrayList<socialTabModel> socialtabs) {
        this.context = context;
        this.socialtab = socialtabs;
    }
/*
    public void setOnItemClickListener(final SongAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }*/



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_social_tab, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final socialTabModel socialTabModel = socialtab.get(position);
        holder.postername.setText(socialtab.get(position).getUsername());
        //holder.postePic.setImageURI(socialtab.get(position).getProfileImageUrl());
        holder.timeofpost.setText(socialtab.get(position).getTimePosted());
        holder.caption.setText(socialtab.get(position).getCaption());
        holder.comments.setText(socialtab.get(position).getComments());
        holder.rates.setText(socialtab.get(position).getRates());
        holder.likes.setText(socialtab.get(position).getLikes());




        // holder.userpic.setImageResource(playListAdapterList.get(position).PlaylistName);


    }

    @Override
    public int getItemCount() {
        return socialtab.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView postername, timeofpost, comments, rates, likes, caption;
        ImageView postePic;
        TextView recView;

        public MyViewHolder(View itemView) {
            super(itemView);
            postePic = itemView.findViewById(R.id.posterpic);
            postername = itemView.findViewById(R.id.socialTabusername);
            timeofpost = itemView.findViewById(R.id.time_ofposting);
            caption = itemView.findViewById(R.id.socialTabCaption);
            comments = itemView.findViewById(R.id.commentcount);
            rates = itemView.findViewById(R.id.rate_count);
            likes = itemView.findViewById(R.id.likecount);


        }
    }
}


