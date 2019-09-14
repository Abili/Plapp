package com.raisac.plapp;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static java.security.AccessController.getContext;

public class HomeSlider extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_STATUS = 1;
    Toolbar toolbar;
    ViewPager pager;
    MyPagerAdapter adapter;
    SlidingPaneLayout tabs;
    CharSequence Titles[] = {"Home", "Events"};
    int Numboftabs = 2;
    ImageView overflow;
    TabLayout tabLayout;
    EditText search;
    ImageView searchBtn, searchBackArrow;
    TextView appname;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_slider);
        mFirebaseAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        tabLayout = findViewById(R.id.home_tablayout);

        searchBackArrow = findViewById(R.id.backsearchBtn);

        search.setVisibility(View.GONE);
        searchBtn = findViewById(R.id.searchBtn);
        appname = findViewById(R.id.appNameonHome);


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBtn.setVisibility(View.GONE);
                overflow.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                appname.setVisibility(View.GONE);
            }
        });
        overflow = findViewById(R.id.homeslider_overflow);
        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(HomeSlider.this, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.tab_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.newPlaylist:

                                startActivity(new Intent(HomeSlider.this, PlayListSongs.class));

                                break;
                            case R.id.favourites:

                                Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();

                                break;

                            case R.id.Homesettings:

                                startActivity(new Intent(HomeSlider.this, Settings.class));

                                break;

                            case R.id.signout:

                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(HomeSlider.this, Register.class));
                                finish();
                                return true;
                        }

                        return false;
                    }
                });
            }
        });

        ViewPager viewPager = findViewById(R.id.pager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.home_tablayout);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newPlaylist:
                startActivity(new Intent(this, PlayListSongs.class));
                break;

            case R.id.favs:

                break;

            case R.id.Homesettings:
                startActivity(new Intent(this, Settings.class));

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        searchBtn.setVisibility(View.VISIBLE);
        overflow.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        search.setVisibility(View.GONE);
        appname.setVisibility(View.VISIBLE);
        return;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backsearchBtn:
                searchBtn.setVisibility(View.VISIBLE);
                overflow.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                appname.setVisibility(View.VISIBLE);

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.tab_menu, menu);
        return super.onCreateOptionsMenu(menu);


    }
}
