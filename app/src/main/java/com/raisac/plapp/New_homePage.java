package com.raisac.plapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raisac.plapp.LoadSongs;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class New_homePage extends AppCompatActivity implements View.OnClickListener {
    ImageView userImage, overFlowMenu;
    TextView homeUsername, number_of_views, number_of_friends;
    RecyclerView myPlaylist, OtherPlaylists;
    FloatingActionButton homeButtonFab;
    Dialog home_menu_dialog;
    LinearLayout homeMenu;
    TextView new_home_createBtn;
    EditText new_create_playlistEdt;
    Dialog playlistDialog;
    LoadSongs loadSongs = new LoadSongs();

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference playListRef;
    DatabaseReference userRef;
    FirebaseUser mUser;
    public static String Required = "Required";
    public static String TAG = "New_homePage";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_home_page);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        playListRef = mFirebaseDatabase.getReference().child("PlayLists");
        userRef = mFirebaseDatabase.getReference().child("Users");

        playlistDialog = new Dialog(New_homePage.this);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        new_create_playlistEdt = playlistDialog.findViewById(R.id.new_home_Edt_playlistname);

        new_home_createBtn = findViewById(R.id.new_home_Pl_createBtn);

        init();

    }

    public void init() {
        getPlaylists();
        DialogCreatePlayList dialogCreatePlayList = new DialogCreatePlayList();
        dialogCreatePlayList.show(getSupportFragmentManager(), "DialogCreatePlayList");
    }

    private void getPlaylists() {
    }

    private void open_new_home_menu() {
        findViewById(R.id.newHome_card_menu).setVisibility(View.VISIBLE);
        findViewById(R.id.newHome_cancel_fab).setVisibility(View.VISIBLE);
        findViewById(R.id.new_homefab).setVisibility(View.GONE);
    }

    private void close_New_home_menu() {
        findViewById(R.id.newHome_card_menu).setVisibility(View.GONE);
        findViewById(R.id.newHome_cancel_fab).setVisibility(View.GONE);
        findViewById(R.id.new_homefab).setVisibility(View.VISIBLE);

    }

    private void createDialog() {
        init();
    }
    


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_create_playlist:
                createDialog();
                break;

            case R.id.newHome_cancel_fab:
                close_New_home_menu();
                break;

            case R.id.new_homefab:
                open_new_home_menu();
                break;
        }
    }
}
