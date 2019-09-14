package com.raisac.plapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class soclaAdapter extends RecyclerView.Adapter<soclaAdapter.MyViewHolder> {

    public TextView username, Image, playlistname;
    private ArrayList<socialModel> socialARRay;

    public soclaAdapter(Context context, ArrayList<socialModel> socialplaylis) {

        this.socialARRay = socialplaylis;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.socialsong_module, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        socialModel socialmodel = socialARRay.get(position);
        holder.captionEdt.setText(socialARRay.get(position).getCaption());
        holder.Username.setText(socialARRay.get(position).getUsername());
        holder.ImageUrl.setText(socialARRay.get(position).getProfileImageUrl());


        // holder.userpic.setImageResource(playListAdapterList.get(position).PlaylistName);



    }

    @Override
    public int getItemCount() {
        return socialARRay.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView captionEdt, Username;
        TextView ImageUrl;

        public MyViewHolder(View itemView) {
            super(itemView);
            captionEdt = itemView.findViewById(R.id.songsocialname);
            Username = itemView.findViewById(R.id.singersocialname);
            ImageUrl = itemView.findViewById(R.id.socialDuration);

        }
    }
}

