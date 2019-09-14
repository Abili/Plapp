package com.raisac.plapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UsernameAdapter extends RecyclerView.Adapter<UsernameAdapter.ViewHolder>{

private Context context;
private ArrayList<User>name;

    public UsernameAdapter(Context context, ArrayList<User> name_List) {

        this.name = name_List;
        this.context = context;
    }

    @NonNull
    @Override
    public UsernameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_playlists_model, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsernameAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(name.get(position).getUsername());

        }

    @Override

    public int getItemCount() {

        name = new ArrayList<>();
        return name.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, time, number;
        ImageView pfirlepic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.playlist_username);
            time= itemView.findViewById(R.id.username_time);
            number = itemView.findViewById(R.id.usernameAmount);
            pfirlepic= itemView.findViewById(R.id.username_profile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, DisplayPlaylist.class);
                    context.startActivity(intent);
                }
            });

        }
    }
}