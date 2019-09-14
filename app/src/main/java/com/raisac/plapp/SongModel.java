package com.raisac.plapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SongModel extends AppCompatActivity {

    DatabaseReference mDatabaseUsers, mDatabasePlaylist;
    FirebaseDatabase mFirebaseDatabase;
    FirebaseUser mCurrentUser;
    FirebaseAuth mAuth;


    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_model);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseUsers = mFirebaseDatabase.getInstance().getReference().child("User");
        mDatabasePlaylist = mFirebaseDatabase.getInstance().getReference().child("Playlists");
        mCurrentUser = mAuth.getInstance().getCurrentUser();


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AppBarLayout mAppBarLayout = findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;

                    showOption(R.id.action_info, R.id.action_settings);
                } else if (isShow) {
                    isShow = false;
                    hideOption(R.id.action_info);
                }
            }
        });


        Query query = mDatabasePlaylist;
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Playlist>()
                .setQuery(query, Playlist.class)
                .build();
        FirebaseRecyclerAdapter<Playlist, mViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Playlist, mViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull mViewHolder holder, int position, @NonNull Playlist model) {

                holder.songName.setText(model.getSongUrl());

            }

            @NonNull
            @Override
            public mViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pl_songs_module, viewGroup, false);
                return new mViewHolder(view);
            }
        };

        String post_id = getIntent().getExtras().getString("postID");

        mDatabasePlaylist.child(post_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String uid = (String) dataSnapshot.child("uid").getValue();
                if (mCurrentUser.getUid().equals(uid)) {
                    fab.setBackgroundResource(R.drawable.add);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_playlist_model, menu);

        hideOption(R.id.action_info);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_info) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id1, int id2) {
        MenuItem item = menu.findItem(id2);
        item.setVisible(true);
    }

    public class mViewHolder extends RecyclerView.ViewHolder {

        TextView Uname, Pname, songName, Artistname, songsize;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            songName = itemView.findViewById(R.id.song_title);
            Artistname = itemView.findViewById(R.id.artist_name);
            songsize = itemView.findViewById(R.id.size_song);

        }
    }

}
