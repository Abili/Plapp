package com.raisac.plapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    String mName;
    String mPhoto;
    String mAbout;

    TextView edtName, edtAbout;
    ImageView imgProfile, camera;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseUsers;
    FirebaseAuth mAuth;
    Button savebtn, cancelBtn;
    CardView optionsBtns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgProfile = findViewById(R.id.settings_profilepic);


//        savebtn = findViewById(R.id.save_changesBtn);
//        cancelBtn = findViewById(R.id.cancelBtn);
        optionsBtns = findViewById(R.id.optionsbtns);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        getUserInfo();

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Settings.this, Settings_Profile.class));

            }
        });


    }

    private void getUserInfo() {
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("profileImageUrl") != null) {
                        mPhoto = map.get("profileImageUrl").toString();
                        Glide.with(getApplication()).load(mPhoto).into(imgProfile);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_profilepic:


                break;

            case R.id.settings_aboutuser:

                Dialog about = new Dialog(this);
                about.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                about.setContentView(R.layout.settings_profile);
                WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
                layoutParams2.copyFrom(about.getWindow().getAttributes());
                layoutParams2.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams2.height = WindowManager.LayoutParams.MATCH_PARENT;

                about.show();

                break;

            case R.id.settings_username:

                break;


            case R.id.about_card:
                //to edit the about us Textview


                break;
        }
    }


}

