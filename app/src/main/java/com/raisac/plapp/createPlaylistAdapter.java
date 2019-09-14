package com.raisac.plapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class createPlaylistAdapter extends RecyclerView.Adapter<createPlaylistAdapter.SongHolder> {

    createPlaylistAdapter songAdapter;
    int count = 0;
    TextView fabTv;
    boolean Visibility = false;
    FloatingActionButton sendList;
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    private ArrayList<SongInfo> _songs = new ArrayList<SongInfo>();
    private Context context;
    //private SongAdapter.OnItemClickListener mOnItemClickListener;
    boolean isSelect = true;


    public createPlaylistAdapter(Context context, ArrayList<SongInfo> songs) {
        this.context = context;
        this._songs = songs;
    }

    @Override
    public createPlaylistAdapter.SongHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View myView = LayoutInflater.from(context).inflate(R.layout.create_p_module, viewGroup, false);
        return new createPlaylistAdapter.SongHolder(myView);
    }

    @Override
    public void onBindViewHolder(final createPlaylistAdapter.SongHolder songHolder, final int position) {
        final SongInfo s = _songs.get(position);


        songHolder.tvSongName.setText(s.getSongname());
        songHolder.tvSongArtist.setText(s.getArtistname());
        songHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongInfo item = _songs.get(songHolder.getAdapterPosition());
            }
        });

    }


    @Override
    public int getItemCount() {
        return _songs.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        TextView tvSongName, tvSongArtist;
        CheckBox checkBox;
        FloatingActionButton sendList;

        public SongHolder(final View itemView) {
            super(itemView);

            tvSongName = itemView.findViewById(R.id.songname);
            tvSongArtist = itemView.findViewById(R.id.singername);
            sendList = itemView.findViewById(R.id.sendListfab);

            // checkBox= itemView.findViewById(R.id.songChecked);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    final String user_id = mAuth.getCurrentUser().getUid();

                    final AlertDialog.Builder deleteSong = new AlertDialog.Builder(v.getContext());
                    deleteSong.setCancelable(false);

                    deleteSong.setMessage(R.string.DeleteSong).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference songs = FirebaseDatabase.getInstance().getReference("songs").child(user_id);

                            int pos = getAdapterPosition();
                            _songs.remove(pos);

                            notifyItemRemoved(pos);


                        }
                    }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    deleteSong.create();
                    deleteSong.show();

                }
            });


            tvSongArtist.setSingleLine(true);
            tvSongName.setSingleLine(true);


        }
    }

}

