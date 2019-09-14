package com.raisac.plapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class UserPage_social extends AppCompatActivity implements View.OnClickListener {
    Toolbar Hometoolbar;
    RecyclerView recycler;
    DatabaseReference mUsers, mMessages;
    FirebaseDatabase mFirebase;
    private ImageView messages, notifications, profilePic, username_home;
    private TextView messagesCount, notificaationsCount, about, media, hot, buddies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_userpage);

        messages = findViewById(R.id.userhome_messages);
        notifications = findViewById(R.id.userhome_notification);
        profilePic = findViewById(R.id.userhome_profile);
        messagesCount = findViewById(R.id.userhome_messages_count);
        notificaationsCount = findViewById(R.id.userhome_notify_count);
        about = findViewById(R.id.userhome_about);
        media = findViewById(R.id.userhome_media);
        hot = findViewById(R.id.userhome_hot);
        buddies = findViewById(R.id.userhme_media);
        Hometoolbar = findViewById(R.id.userhome_toolbar);
        recycler = findViewById(R.id.userhome_recycler);
        username_home = findViewById(R.id.username_home);

        mUsers = mFirebase.getInstance().getReference().child("Users");
        mUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                   if(map.get("name")!=null){

                   }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onClick(View v) {

    }
}
