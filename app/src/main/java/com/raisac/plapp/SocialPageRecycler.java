package com.raisac.plapp;


import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SocialPageRecycler extends AppCompatActivity{
    String captin, likes, comments, rates, pstinfTime, nameUser;
    int Dphoto;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DividerItemDecoration dividerItemDecoration;
    private ArrayList<socialTabModel> timelineAlist;
    private SocialTabbAdapter timelineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_page_recycler);


        timelineAlist = new ArrayList<>();
        recyclerView = findViewById(R.id.timelinerecycler);
        FloatingActionButton fab = findViewById(R.id.fabcsocialPlaylist);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialogCplaylist = new Dialog(v.getContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                dialogCplaylist.setTitle("Update PlayLine");
                dialogCplaylist.setContentView(R.layout.activity_update_time_line);
                dialogCplaylist.setCanceledOnTouchOutside(false);

                FloatingActionButton fabUpdateTimeLne = dialogCplaylist.findViewById(R.id.updateFab);

                fabUpdateTimeLne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* EditText EDtcaption = dialogCplaylist.findViewById(R.id.newplaylistEDT);
                        String s  = EDtcaption.getText().toString();
                        socialTabModel socialTM = new socialTabModel(s);
                        timelineAlist.add(socialTM);
                        SocialTabbAdapter socialAdapter = new SocialTabbAdapter(v.getContext(),timelineAlist);
                        recyclerView.setAdapter(socialAdapter);
                        dialogCplaylist.dismiss();*/


                        //recyclerView.addItemDecoration(dividerItemDecoration);
                        linearLayoutManager = new LinearLayoutManager(v.getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        //dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                           //     linearLayoutManager.getOrientation());



                    }
                });

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialogCplaylist.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;


                dialogCplaylist.show();


            }
        });


       /* captin = findViewById(R.id.socialTabCaption);
        likes = findViewById(R.id.likecount);
        comments = findViewById(R.id.commentcount);
        rates = findViewById(R.id.rate_count);
        pstinfTime = findViewById(R.id.time_ofposting);
        nameUser = findViewById(R.id.socialTabusername);
        //Dphoto = findViewById(R.id.posterpic).toString();


        socialTabModel socialtabmodel = new socialTabModel(captin, likes, comments, rates, pstinfTime, nameUser, Dphoto);
        timelineAlist.add(socialtabmodel);
        timelineAdapter = new SocialTabbAdapter(this, timelineAlist);
        recyclerView.setAdapter(timelineAdapter);
        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration(dividerItemDecoration);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());*/


    }
}
