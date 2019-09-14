package com.raisac.plapp;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.raisac.plapp.compareSongs.comparesongsModule;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2 extends Fragment implements View.OnClickListener {
    protected static final int TIMER_RUNTIME = 180000; // in ms --> 10s
    static final int SONG_PICKER = 1;
    static final String TAG = "UpdateTimeLine";
    RecyclerView recyclerView;

    View.OnClickListener showpopUp;
    String captin, likes, comments, rates, pstinfTime, nameUser;
    int Dphoto;
    LinearLayoutManager linearLayoutManager;
    DividerItemDecoration dividerItemDecoration;
    ImageView comparesongs, voteSong1, voteSong2, recordVoice, uploadvideo, importAudio;
    RecyclerView social_recyclerView;
    TextView mComparesong1, mComparesong2, mArtstCompare, mArtistCompare2,
            mCountcompare1, mCountcompare2, mPostTimesetting;
    TextView txtTimerCount;
    int count = 0;
    EditText createCaption;
    FloatingActionButton fabCompareConfirm, fabCompareCancel, fabUpdateTimeLne;
    Context context;
    Dialog dialog;
    PopupWindow popup;
    soclaSongsAdapter soclasongsadapter;
    boolean mbActive;
    ImageView download;
    Handler handler = new Handler();
    RelativeLayout downlodRel;
    ImageView downloadSong, playsong;
    SocialTabbAdapter soclaadapter;
    private ArrayList<socialTabModel> timelineAlist;
    private SocialTabbAdapter timelineAdapter;
    private String selectedAudiopath;
    private ArrayList<comparesongsModule> compSongs = new ArrayList<comparesongsModule>();
    private ArrayList<socilaplayListmodule> socialplaylis;
    private ArrayList<socialTabModel> socialARRay;
    private ProgressBar progressTimer;


    public Tab2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_social_page_recycler, container, false);


        FloatingActionButton socialplaylist = rootView.findViewById(R.id.fabcsocialPlaylist);
        recyclerView = rootView.findViewById(R.id.timelinerecycler);


        downlodRel = rootView.findViewById(R.id.downloadRelaView);
        playsong = rootView.findViewById(R.id.playsocialsong);
        downloadSong = rootView.findViewById(R.id.playsocialsong);

        socialplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialogCplaylist = new Dialog(v.getContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                dialogCplaylist.setTitle("Update PlayLine");
                dialogCplaylist.setContentView(R.layout.activity_update_time_line);
                dialogCplaylist.setCanceledOnTouchOutside(false);




                social_recyclerView = dialogCplaylist.findViewById(R.id.updatsocialRecycler);

                ImageView comparesongs = dialogCplaylist.findViewById(R.id.compareSongsImgView);
                FloatingActionButton cancel = dialogCplaylist.findViewById(R.id.cancel);
                mCountcompare2 = dialogCplaylist.findViewById(R.id.compareVoteCount2);
                mArtstCompare = dialogCplaylist.findViewById(R.id.compareArtistname);
                mArtistCompare2 = dialogCplaylist.findViewById(R.id.compareArtistname2);
                mCountcompare1 = dialogCplaylist.findViewById(R.id.compareVoteCount);
                mComparesong1 = dialogCplaylist.findViewById(R.id.comparesong_name);
                mComparesong2 = dialogCplaylist.findViewById(R.id.comparesong_name2);
                fabCompareCancel = dialogCplaylist.findViewById(R.id.fabaddsong_toCompare);


                importAudio = dialogCplaylist.findViewById(R.id.importsongs);
                uploadvideo = dialogCplaylist.findViewById(R.id.uploadvideos);
                recordVoice = dialogCplaylist.findViewById(R.id.recordSong);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogCplaylist.dismiss();
                    }
                });

                comparesongs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog2 = new Dialog(v.getContext());
                        dialog2.setTitle("Compare Songs");
                        dialog2.setContentView(R.layout.activity_compare_songs_module);

                        final TextView mPostTimesetting = dialog2.findViewById(R.id.postTiming);
                        voteSong1 = dialog2.findViewById(R.id.compareSongSelcetion);
                        voteSong2 = dialog2.findViewById(R.id.compareSongSelcetion2);
                        voteSong1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                count++;
                                mCountcompare1.setText(String.valueOf(count));
                                voteSong1.setActivated(false);
                                voteSong1.setEnabled(false);

                            }
                        });
                        voteSong2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                count++;
                                mCountcompare2.setText(String.valueOf(count));
                                voteSong2.setEnabled(false);

                            }
                        });


                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                        layoutParams.copyFrom(dialog2.getWindow().getAttributes());
                        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                        mPostTimesetting.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar mCurrentTime = Calendar.getInstance();
                                int day = mCurrentTime.get(Calendar.DAY_OF_MONTH);
                                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                                int min = mCurrentTime.get(Calendar.MINUTE);
                                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                        hourOfDay = (hourOfDay / 24);
                                        mPostTimesetting.setText(hourOfDay + "days" + ":" + minute);
                                    }
                                }, hour, min, false

                                );
                                timePickerDialog.setTitle("Select Time");
                                timePickerDialog.show();


                            }
                        });

                        dialog2.show();

                    }
                });
                importAudio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent audiointent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        audiointent.setType("audio/*");
                        startActivityForResult(audiointent, SONG_PICKER);

                    }
                });
                uploadvideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent videoIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        videoIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        videoIntent.setType("video/*");
                        startActivityForResult(videoIntent, SONG_PICKER);
                    }
                });
                recordVoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


                FloatingActionButton fabUpdateTimeLne = dialogCplaylist.findViewById(R.id.updateFab);

                fabUpdateTimeLne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timelineAlist = new ArrayList<>();

                        EditText EDtcaption = dialogCplaylist.findViewById(R.id.newplaylistEDT);
                        RecyclerView resView = dialogCplaylist.findViewById(R.id.updatsocialRecycler);
                        String res = resView.getAdapter().toString();


                        String s = EDtcaption.getText().toString();
                        socialTabModel socialTM = new socialTabModel(s,res);
                        timelineAlist.add(socialTM);
                        SocialTabbAdapter socialAdapter = new SocialTabbAdapter(v.getContext(), timelineAlist);
                        recyclerView.setAdapter(socialAdapter);
                        dialogCplaylist.dismiss();



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
        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.playsocialsong:


                break;
            case R.id.updateFab:


                String caption = createCaption.getText().toString().trim();

                /*socialTabModel socialtabmodel = new socialTabModel(caption);
                socialARRay.add(socialtabmodel);
                Intent intent = new Intent(this, Tab2.class);
                intent.putExtra("TlinCaption", socialARRay);
                startActivityForResult(intent, 1);


                soclaadapter = new SocialTabbAdapter(this, socialARRay);
                TextView newCaption = findViewById(R.id.socialTabCaption);
                newCaption.setText(caption);
*/

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SONG_PICKER && resultCode == RESULT_OK) {
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            if (data != null) {
                uri = data.getData();
                Log.i(TAG, "Uri:" + uri.toString());
                getPath(uri);
            }
        }
    }

    private void getPath(Uri uri) {
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor = getContext().getContentResolver()
                .query(uri, null, selection, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {

                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                //String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                //String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                socilaplayListmodule listmodule = new socilaplayListmodule(name);
                ArrayList<socilaplayListmodule> socialplaylis = new ArrayList<>();
                socialplaylis.add(listmodule);
                soclaSongsAdapter soclasongsadapter = new soclaSongsAdapter(getContext(), socialplaylis);

                social_recyclerView.setAdapter(soclasongsadapter);

                linearLayoutManager = new LinearLayoutManager(getContext());
                social_recyclerView.setLayoutManager(linearLayoutManager);
                dividerItemDecoration = new DividerItemDecoration(social_recyclerView.getContext(), linearLayoutManager.getOrientation());
                social_recyclerView.addItemDecoration(dividerItemDecoration);


            }
        } finally {

            cursor.close();
        }
    }

    public void startTimer() {
        final Thread timerThread = new Thread() {
            @Override
            public void run() {
                mbActive = true;
                try {
                    int waited = 0;
                    while (mbActive && (waited < TIMER_RUNTIME)) {
                        sleep(1000);
                        if (mbActive) {
                            waited += 1000;

                            updateProgress(waited);
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    //onContinue();
                }
            }

        };
        timerThread.start();

    }

    /*private void onContinue() {
        handler.post(new Runnable() {
            @Override
            public void run() {

                txtCallContactUsNumber
                        .setText(CommonVariable.CallForSupporMessage
                                + contactInfo.MobileNo);

            }
        });*/


    public void updateProgress(final int timePassed) {
        if (null != progressTimer) {
            // Ignore rounding error here
            final int progress = progressTimer.getMax() * timePassed
                    / TIMER_RUNTIME;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i("timePassed", "timePassed=" + timePassed);

                    DateFormat formatter = new SimpleDateFormat("mm:ss");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timePassed);

                    //downlodRel = findViewById(R.id.downloadRelaView);

                    txtTimerCount.findViewById(R.id.downloadProgress);
                    String timer = formatter.format(calendar.getTime());
                    formatter.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
                    txtTimerCount.setText(formatter.format(timePassed));

                }
            });

            progressTimer.setProgress(progress);

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    private void showpopUp(View v) {

        final PopupWindow popup = new PopupWindow(getContext());

        final View layout = getLayoutInflater().inflate(R.layout.song_menu, null);
        popup.setContentView(layout);
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);


        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        download = layout.findViewById(R.id.playsocialsong);
        downloadSong = layout.findViewById(R.id.download);
        playsong = layout.findViewById(R.id.play);
        downloadSong.setOnClickListener(this);
        playsong.setOnClickListener(this);

        playsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.download:

                        downlodRel = layout.findViewById(R.id.downloadRelaView);
                        download = layout.findViewById(R.id.playsocialsong);
                        download.setImageResource(R.drawable.ic_file_download_black_24dp);
                        downlodRel.setVisibility(View.VISIBLE);
                        popup.dismiss();
                        startTimer();
                        break;

                    case R.id.play:
                        downlodRel = layout.findViewById(R.id.downloadRelaView);
                        download = layout.findViewById(R.id.playsocialsong);
                        downlodRel.setVisibility(View.INVISIBLE);
                        download.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                        popup.dismiss();

                        break;
                }
            }
        });


        popup.showAsDropDown(v);
    }
    View.OnClickListener ShowpopUp(View v){

    return showpopUp;}
}

