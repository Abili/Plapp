package com.raisac.plapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Playlist_model extends AppCompatActivity {

    RecyclerView usernames;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReferenceUsers, mDatabaseReferencePlaylist;
    ChildEventListener mChildEventListener;
    TextView playlisname, user_name, timestamp, numberOfPlaylists;
    ImageView userImage;
    FirebaseUser mCurrentUser;
    String postkey;
    FirebaseAuth mAuth;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_model);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseReferenceUsers = mFirebaseDatabase.getInstance().getReference().child("User");
        mDatabaseReferencePlaylist = mFirebaseDatabase.getInstance().getReference().child("Playlists");


        usernames = findViewById(R.id.user_plalistnameRecyce);
        layoutManager = new LinearLayoutManager(this);
        usernames.setLayoutManager(layoutManager);

        mCurrentUser = mAuth.getInstance().getCurrentUser();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_createsong);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Playlist_model.this, PlayListSongs.class));
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

    @Override
    protected void onResume() {
        super.onResume();
        loadInfo();

    }

    private void loadInfo() {
        postkey = getIntent().getExtras().getString("post_id");
        loadOtherspost(postkey);
    }

    private void loadOtherspost(String postkey) {
        String uid = mAuth.getUid();
        Query othersquery = mDatabaseReferenceUsers.child(uid);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Playlist>()
                .setQuery(othersquery, Playlist.class)
                .build();

        FirebaseRecyclerAdapter<User, UserPlayViewHolder> recyclerAdapter
                = new FirebaseRecyclerAdapter<User, UserPlayViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserPlayViewHolder holder, final int position, @NonNull User model) {
                holder.OthersName.setText(model.getUsername());
                Glide.with(getApplicationContext())
                        .load(model.getPhotoUrl())
                        .into(holder.Image_user);

                valueEventListener(holder);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String post_id = getRef(position).getKey();
                        Intent intent = new Intent(Playlist_model.this, SongModel.class);
                        intent.putExtra("postID", post_id);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public UserPlayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pl_songs_module, viewGroup, false);
                return new UserPlayViewHolder(view);
            }
        };

        usernames = findViewById(R.id.user_plalistnameRecyce);
        layoutManager = new LinearLayoutManager(this);
        usernames.setLayoutManager(layoutManager);
        usernames.setAdapter(recyclerAdapter);

    }

    private void valueEventListener(final UserPlayViewHolder holder) {
        mDatabaseReferencePlaylist.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String playname = (String) dataSnapshot.child("play_name").getValue();
                holder.OthersPlayName.setText(playname);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public class UserPlayViewHolder extends RecyclerView.ViewHolder {
        TextView Othertime, OthersName, OthersPlayName, OtherPlayNumber;
        ImageView Image_user;

        public UserPlayViewHolder(@NonNull View itemView) {
            super(itemView);
            OthersName = findViewById(R.id.PLusername);
            //Othertime = findViewById(R.id.PLtime);
            OtherPlayNumber = findViewById(R.id.PLcount);
            OthersPlayName = findViewById(R.id.PLname);
            Image_user = findViewById(R.id.PLPic);


        }
    }
}
