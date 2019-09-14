package com.raisac.plapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class PlaylistNameAdapter extends RecyclerView.Adapter<PlaylistNameAdapter.MyViewHolder> {

    public TextView playlistname;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ArrayList<Playlist> nameList;


    public PlaylistNameAdapter(Context context, ArrayList<Playlist> playListAdapterList) {

        this.nameList = playListAdapterList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_playlists_model, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Playlist playList = nameList.get(position);
        holder.playlitsname.setText(nameList.get(position).getPlay_name());



    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView count;
        TextView playlitsname;
        ImageView userpic;

        public MyViewHolder(final View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.playlist_username);
            count = itemView.findViewById(R.id.PLcount);
            playlitsname = itemView.findViewById(R.id.playlist_title);
            userpic = itemView.findViewById(R.id.PLPic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //String post_id = databaseReference.getKey().toString();

                    Context context = v.getContext();
                    int pos = getAdapterPosition();

                    Intent intent = new Intent(context, DisplayPlaylist.class);
                    //intent.putExtra("postid", post_id);
                    context.startActivity(intent);


                }
            });
        }
    }
}
